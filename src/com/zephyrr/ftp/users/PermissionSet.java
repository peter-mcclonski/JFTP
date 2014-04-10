package com.zephyrr.ftp.users;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;

/*
 * Container class for holding information on what a user is allowed
 * to do.
 *
 * @author Peter Jablonski
 */

public class PermissionSet {
	// The user's home directory.  They cannot go above this.
	private String home;
	private File homeDir;
	// Permissions are represented as an int, with each individual
	// permission being a power of two.
	private int perms;
	// Whether the user is logged in.
	private boolean authed;

	public PermissionSet(String[] args) {
		// Convert arguments to attributes
		home = args[1];
		homeDir = FileManager.getFile(args[1]);
		// Make the home directory if it doesn't exist
		if (!homeDir.exists())
			homeDir.mkdirs();
		perms = Integer.parseInt(args[0]);
		authed = true;
	}

	// Checks if the user has a given permission.
	public boolean hasPermission(Permission p) {
		// We do a bitwise AND on that permission's power of 2
		return (perms & p.getInt()) != 0;
	}

	// Check whether the user is logged in
	public boolean isAuthed() {
		return authed;
	}

	// Get the home directory as a file
	public File getHome() {
		return homeDir;
	}
	
	// Get the home directory as a string
	public String getHomeString() {
		return home;
	}
}
