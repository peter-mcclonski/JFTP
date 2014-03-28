package com.zephyrr.ftp.users;

import java.io.File;

public class PermissionSet {
	private File homeDir;
	private int perms;
	private boolean authed;
	public PermissionSet(String[] args) {
		homeDir = new File(args[1]); // Needs to be put in context of root path
		if(!homeDir.exists())
			homeDir.mkdirs();
		perms = Integer.parseInt(args[0]);
	}
	public boolean hasPermission(Permission p) {
		return (perms & p.getInt()) != 0;
	}
	public boolean isAuthed() {
		return authed;
	}
}
