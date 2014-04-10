package com.zephyrr.ftp.users;

import java.io.File;

/*
 * Represents a user account.
 *
 * @author Peter Jablonski
 */

public class User {
	// The username of this user
	private String name;
	// This user's permissions.  I honestly forget why this 
	// is protected, rather than private.
	protected PermissionSet perms;

	public User() {
		// Set defaults
		name = null;
		perms = null;
	}

	// Check if the user is logged in
	public boolean isAuthorized() {
		// And we do that by checking its permissions
		return perms != null && perms.isAuthed();
	}

	// Attempt to log in a user based on its username and the
	// given password.
	public boolean attemptAuthorization(String pass) {
		// First check if there is an account with that name
		if (AccountManager.isUser(name)) {
			// Get the account info for that username
			RegisteredUser ru = AccountManager.getUser(name);
			// Check if our login info matches and that account
			// is enabled in the first place
			if (ru.isValidLogin(this, pass)
					&& ru.hasPermission(Permission.ENABLED)) {
				// Guess it worked.  Let's copy over the perms.
				perms = ru.getPermissions();
				return true;
			}
		}
		// Awwwww, it didn't work :(
		return false;
	}

	// Get this user's Username
	public String getName() {
		return name;
	}

	// Sets this user's username
	public void setName(String s) {
		name = s;
	}
	
	// Check if this user has a given permission
	public boolean hasPermission(Permission p) {
		return perms.hasPermission(p);
	}

	// Sets this user's permissions
	public void setPermissions(PermissionSet p) {
		perms = p;
	}

	// Get the file representing the user's home directory
	public File getHomeDir() {
		return perms.getHome();
	}

	// Get a string representation of this user's home directory
	public String getHome() {
		return perms.getHomeString();
	}

	// To be considered equal, the object must be a User with the
	// same username.
	public boolean equals(Object other) {
		return other instanceof User
				&& ((User) other).getName().equals(getName());
	}
}
