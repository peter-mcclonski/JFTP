package com.zephyrr.ftp.users;

public enum Permission {
	ENABLED(1), 
	READFILE(2), 
	WRITEFILE(4), 
	DELETEFILE(8), 
	READDIR(16), 
	MAKEDIR(32), 
	DELETEDIR(64);

	private int ival;

	Permission(int val) {
		ival = val;
	}

	public int getInt() {
		return ival;
	}
}
