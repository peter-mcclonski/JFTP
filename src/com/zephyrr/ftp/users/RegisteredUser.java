package com.zephyrr.ftp.users;

/*
 * A user instance loaded from the accounts database.  This
 * class is used to check against login attempts and retrieve
 * user permissions.
 *
 * @author Peter Jablonski
 */

public class RegisteredUser extends User {
	// Password
	private String pass;

	public RegisteredUser(String name, String pass, String[] perms) {
		// Convert from parameters to attributes
		setName(name);
		this.pass = pass;
		setPermissions(new PermissionSet(perms));
	}

	// Retrieves the permission set for this user
	protected PermissionSet getPermissions() {
		return perms;
	}

	// Checks if this account matches a user's credentials
	public boolean isValidLogin(User other, String otherPass) {
		return (other.getName().equals(getName()) && otherPass.equals(pass));
	}
}
