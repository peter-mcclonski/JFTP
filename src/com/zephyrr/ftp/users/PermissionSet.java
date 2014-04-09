package com.zephyrr.ftp.users;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;

public class PermissionSet {
	private String home;
	private File homeDir;
	private int perms;
	private boolean authed;

	public PermissionSet(String[] args) {
		home = args[1];
		homeDir = FileManager.getFile(args[1]);
		if (!homeDir.exists())
			homeDir.mkdirs();
		perms = Integer.parseInt(args[0]);
		authed = true;
	}

	public boolean hasPermission(Permission p) {
		return (perms & p.getInt()) != 0;
	}

	public boolean isAuthed() {
		return authed;
	}

	public File getHome() {
		return homeDir;
	}
	
	public String getHomeString() {
		return home;
	}
}
