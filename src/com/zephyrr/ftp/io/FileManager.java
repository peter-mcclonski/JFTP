package com.zephyrr.ftp.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import com.zephyrr.ftp.etc.Config;
import com.zephyrr.ftp.users.User;

public class FileManager {
	private static HashMap<File, User> locks;
	private static HashMap<File, FileInputStream> readers;
	private static HashMap<File, FileOutputStream> writers;
	private static HashMap<User, String> renames;
	private static String HOMEDIR = Config.get("HOMEDIR");
	static {
		locks = new HashMap<File, User>();
		renames = new HashMap<User, String>();
		readers = new HashMap<File, FileInputStream>();
		writers = new HashMap<File, FileOutputStream>();
	}

	private static String getTruePath(String rel) {
		return (HOMEDIR + rel).replaceAll("//", "/");
	}

	public static boolean write(String abstractPath, byte[] msg, User u,
			boolean append) {
		File out = getFile(abstractPath);
		try {
			File parent = out.getParentFile();
			if (!parent.exists())
				parent.mkdirs();
			out.createNewFile();
			if (!locks.containsKey(out))
				locks.put(out, u);
			else if (!locks.get(out).equals(u) || readers.containsKey(out))
				return false;
			if (!writers.containsKey(out))
				writers.put(out, new FileOutputStream(out, append));
			writers.get(out).write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean closeWriter(String abstractPath, User u) {
		File out = getFile(abstractPath);
		if (!locks.get(out).equals(u))
			return false;
		try {
			writers.get(out).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writers.remove(out);
		locks.remove(out);
		return true;
	}

	public static ReadResult read(String abstractPath, int count, User u) {
		ReadResult rr = new ReadResult();
		File f = getFile(abstractPath);
		if (!locks.containsKey(f))
			locks.put(f, u);
		else if (!locks.get(f).equals(u) || writers.containsKey(f)) {
			rr.setLocked(true);
			return rr;
		}
		try {
			if (!readers.containsKey(f))
				readers.put(f, new FileInputStream(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rr.setData(new byte[count]);
		int result;
		try {
			result = readers.get(f).read(rr.getData());
		} catch (IOException e) {
			result = -1;
			e.printStackTrace();
		}
		if (result != count) {
			rr.setFinished(true);
			locks.remove(f);
			readers.remove(f);
		}
		rr.setTrueLength(result);
		return rr;
	}

	public static void queueRename(String abstractPath, User u) {
		renames.put(u, abstractPath);
	}

	public static String dequeueRename(User u) {
		if (renames.containsKey(u))
			return renames.get(u);
		return null;
	}

	public static void rename(String oldName, String newName) {
		getFile(oldName).renameTo(getFile(newName));
	}

	public static boolean isLocked(String abstractPath) {
		return locks.containsKey(getFile(abstractPath));
	}

	public static void lock(String abstractPath, User u) {
		if (!isLocked(abstractPath))
			locks.put(getFile(abstractPath), u);
	}

	public static void unlock(String abstractPath) {
		if (isLocked(abstractPath))
			locks.remove(getFile(abstractPath));
	}

	public static File getFile(String abstractPath) {
		return new File(getTruePath(abstractPath));
	}

	public static String convertPath(String path) {
		System.out.println("HOMEDIR: " + HOMEDIR);
		System.out.println("Path: " + path);
		return ("/" + (path + "//").substring(HOMEDIR.length()) + "/").replaceAll("//", "/");
	}
}
