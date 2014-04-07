package com.zephyrr.ftp.io;

public class ReadResult {
	private byte[] data;
	private boolean locked, done;
	private int tLength;
	public ReadResult() {
		tLength = 0;
		data = new byte[tLength];
		locked = false;
		done = false;
	}
	public int getTrueLength() {
		return tLength;
	}
	public void setTrueLength(int i) {
		tLength = i;
	}
	public void setLocked(boolean b) {
		locked = b;
	}
	public void setFinished(boolean b) {
		done = b;
	}
	public void setData(byte[] d) {
		data = d;
	}
	public boolean isLocked() { return locked; }
	public boolean isFinished() { return done; }
	public byte[] getData() { return data; }
}
