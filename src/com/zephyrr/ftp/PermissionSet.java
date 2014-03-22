package com.zephyrr.ftp.users;

public class PermissionSet {
	private File homeDir;
	private boolean view, read, write, pull;
	private boolean authed;
	public PemissionSet(String[] args) {
		homeDir = ""; // TODO
		view = true;
		read = true;
		pull = true;
		write = false;
		authed = true;
		parsePerms(args);
	}
	private void parsePerms(String[] args) {
		for(String s : args) {
			String[] pair = s.split("=");
			switch(pair[0].toLowerCase()) {
				case "home":	homeDir = new File(pair[1]);	break;
				case "view":	view = Boolean.parseBoolean(pair[1]);	break;
				case "read":	view = Boolean.parseBoolean(pair[1]);	break;
				case "write":	view = Boolean.parseBoolean(pair[1]);	break;
				case "pull":	view = Boolean.parseBoolean(pair[1]);	break;
			}
		}
	}
}
