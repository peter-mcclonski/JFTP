package com.zephyrr.ftp.io;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import com.zephyrr.ftp.users.User;

public class FileManager {
	private static HashMap<File, User> locks;
	private static HashMap<File, FileInputStream> readers;
	private static HashMap<File, FileOutputStream> writers;
	private static String HOMEDIR = "/home/Phoenix/.ftp/pub";
	static {
		locks = new HashMap<File, User>();
		readers = new HashMap<File, FileInputStream>();
		writers = new HashMap<File, FileOutputStream>();
	}
	private static String getTruePath(String rel) {
		//System.out.println((HOMEDIR + rel).replaceAll("//", "/"));
		return (HOMEDIR + rel).replaceAll("//", "/");
	}
	public static boolean write(String abstractPath, byte[] msg, User u) {
		File out = getFile(abstractPath);
		try {
			File parent = out.getParentFile();
			if(!parent.exists())
				parent.mkdirs();
			out.createNewFile();
			if(!locks.containsKey(out))
				locks.put(out, u);
			else if(!locks.get(out).equals(u) || readers.containsKey(out)) 
				return false;
			if(!writers.containsKey(out))
				writers.put(out, new FileOutputStream(out));
			writers.get(out).write(msg);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	public static boolean closeWriter(String abstractPath, User u) {
		File out = getFile(abstractPath);
		if(!locks.get(out).equals(u))
			return false;
		try {
			writers.get(out).close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		writers.remove(out);
		locks.remove(out);
		return true;
	}
	public static ReadResult read(String abstractPath, int count, User u) {
		ReadResult rr = new ReadResult();
		File f = getFile(abstractPath);
		if(!locks.containsKey(f)) 
			locks.put(f, u);
		else if(!locks.get(f).equals(u) || writers.containsKey(f)) {
			rr.setLocked(true);
			return rr;
		}
		try {
			if(!readers.containsKey(f))
				readers.put(f, new FileInputStream(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rr.setData(new byte[count]);
		int result;
		try {
			result = readers.get(f).read(rr.getData());
		} catch(IOException e) {
			result = -1;
			e.printStackTrace();
		}
		if(result != count) {
			rr.setFinished(true);
			locks.remove(f);
			readers.remove(f);
		}
		rr.setTrueLength(result);
		return rr;
	}
	public static File getFile(String abstractPath) {
		return new File(getTruePath(abstractPath));
	}
}
