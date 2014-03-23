package com.zephyrr.ftp.users;

public class User {
	private String name;
	protected PermissionSet perms;
	public User() {
		name = null;
		pass = null;
		perms = null;
	}
	public boolean isAuthorized() {
		return isAuthed;
	}
	public void attemptAuthorization(String pass) {
		// TODO
	}
	public String getName() {
		return name;
	}
	public void setName(String s) {
		name = s;
	}
	public void hasPermission(UserAction ua) {
		// TODO
	}
}
