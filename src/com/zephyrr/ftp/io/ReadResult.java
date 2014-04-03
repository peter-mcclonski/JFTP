package com.zephyrr.ftp.io;

public class ReadResult {
	private String data;
	private boolean locked, done;
	public ReadResult() {
		data = "";
		locked = false;
		done = false;
	}
	public void setLocked(boolean b) {
		locked = b;
	}
	public void setFinished(boolean b) {
		done = b;
	}
	public void addToResult(String s) {
		data += s;
	}
	public boolean isLocked() { return locked; }
	public boolean isFinished() { return done; }
	public String getData() { return data; }
}
