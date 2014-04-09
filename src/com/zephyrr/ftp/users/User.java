package com.zephyrr.ftp.users;

import java.io.File;

public class User {
	private String name;
	protected PermissionSet perms;

	public User() {
		name = null;
		perms = null;
	}

	public boolean isAuthorized() {
		return perms != null && perms.isAuthed();
	}

	public boolean attemptAuthorization(String pass) {
		if (AccountManager.isUser(name)) {
			RegisteredUser ru = AccountManager.getUser(name);
			if (ru.isValidLogin(this, pass)
					&& ru.hasPermission(Permission.ENABLED)) {
				perms = ru.getPermissions();
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String s) {
		name = s;
	}

	public boolean hasPermission(Permission p) {
		return perms.hasPermission(p);
	}

	public void setPermissions(PermissionSet p) {
		perms = p;
	}

	public File getHomeDir() {
		return perms.getHome();
	}

	public String getHome() {
		return perms.getHomeString();
	}

	public boolean equals(Object other) {
		return other instanceof User
				&& ((User) other).getName().equals(getName());
	}
}
