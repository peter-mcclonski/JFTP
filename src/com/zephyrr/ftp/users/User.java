package com.zephyrr.ftp.users;

public class User {
	private String name;
	protected PermissionSet perms;
	public User() {
		name = null;
		perms = null;
	}
	public boolean isAuthorized() {
		return perms.isAuthed();
	}
	public boolean attemptAuthorization(String pass) {
		if(AccountManager.isUser(name)) {
			RegisteredUser ru = AccountManager.getUser(name);
			if(ru.isValidLogin(this, pass)) {
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
}
