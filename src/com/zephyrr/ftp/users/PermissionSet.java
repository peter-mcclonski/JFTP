package com.zephyrr.ftp.users;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;

public class PermissionSet {
	private File homeDir;
	private int perms;
	private boolean authed;

	public PermissionSet(String[] args) {
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
}
