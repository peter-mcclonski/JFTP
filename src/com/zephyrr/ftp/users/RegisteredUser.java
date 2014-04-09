package com.zephyrr.ftp.users;

public class RegisteredUser extends User {
	private String pass;

	public RegisteredUser(String name, String pass, String[] perms) {
		setName(name);
		this.pass = pass;
		System.out.println(name + ": " + pass);
		setPermissions(new PermissionSet(perms));
	}

	protected PermissionSet getPermissions() {
		return perms;
	}

	public boolean isValidLogin(User other, String otherPass) {
		return (other.getName().equals(getName()) && otherPass.equals(pass));
	}
}
