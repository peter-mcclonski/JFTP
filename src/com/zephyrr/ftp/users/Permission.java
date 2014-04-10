package com.zephyrr.ftp.users;

/*
 * A simple enum matching a permission to its 
 * power of two.
 *
 * @author Peter Jablonski
 */

public enum Permission {
	ENABLED(1), 		// Account enabled (can be used to ban)
	READFILE(2), 		// Can retrieve files
	WRITEFILE(4), 		// Can upload files
	DELETEFILE(8), 		// Can delete files
	READDIR(16), 		// I don't think this is ever used
	MAKEDIR(32), 		// Can create directories
	DELETEDIR(64);		// Can remove directories (this overrides DeleteFile)

	// The power of 2 associated with this permission
	private int ival;

	Permission(int val) {
		ival = val;
	}
	
	// Retrieve the associated power of 2
	public int getInt() {
		return ival;
	}
}
