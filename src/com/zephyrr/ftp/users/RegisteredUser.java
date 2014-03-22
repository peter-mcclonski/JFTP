package com.zephyrr.ftp.users;

public class RegisteredUser extends User {
	private String pass;
	public RegisteredUser(String name, String pass, String[] perms) {
		setName(name);
		this.pass = pass;
		setPermissions(new PermissionSet(perms));
	}
	protected PermissionSet getPermissions() {
		return perms;
	}

	public PermissionSet isValidLogin(User other, String otherPass) {
		if(other.getName().equals(getName()) && 
				otherPass.equals(pass))
			return getPermissions();
		return null;
	}
}
