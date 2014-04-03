package com.zephyrr.ftp.io;

import java.io.File;

public class FileManager {
	private static HashMap<File, User> locks;
	private static HashMap<File, Scanner> readers;
	private static String HOMEDIR = "~/ftp/.pub/";
	static {
		locks = new HashMap<File, Boolean>();
	}
	private static String getTruePath(String rel) {
		return (HOMEDIR + rel).replaceAll("//", "/");
	}
	public static ReadResult read(String abstractPath, int count, User u) {
		ReadResult rr = new ReadResult();
		File f = new File(getTruePath(abstractPath));
		if(!locks.containsKey(f)) 
			locks.put(f, u);
		else if(!locks.get(f).equals(u)) {
			rr.setLocked(true);
			return rr;
		}
		if(!readers.containsKey(f))
			readers.put(new Scanner(f));
		for(int i = 0; i < count && readers.get(f).hasNext(); i++)
			rr.addToResult(readers.get(f).next();
		if(!readers.get(f).hasNext()) {
			rr.setFinished(true);
			locks.remove(f);
			readers.remove(f);
		}
		return rr;
	}
}
