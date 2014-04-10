package com.zephyrr.ftp.io;

/*
 * Packaging class for holding the results of read operations.
 *
 * @author Peter Jablonski
 */

public class ReadResult {
	// The data read from the file.
	private byte[] data;
	// Whether the file is locked
	// Whether the end of the file has been reached
	private boolean locked, done;
	// The amount of data read into the array.
	private int tLength;

	public ReadResult() {
		// Assign a few defaults
		tLength = 0;
		data = new byte[tLength];
		locked = false;
		done = false;
	}
	
	// Returns the amount read into the data array
	public int getTrueLength() {
		return tLength;
	}

	// Defines the amount read into the data array
	public void setTrueLength(int i) {
		tLength = i;
	}

	// Sets whether the file was locked
	public void setLocked(boolean b) {
		locked = b;
	}

	// Marks whether the end of the file has been reached
	public void setFinished(boolean b) {
		done = b;
	}

	// Sets the data array
	public void setData(byte[] d) {
		data = d;
	}

	// Checks whether the file was locked
	public boolean isLocked() {
		return locked;
	}

	// Checks whether the end of the file was reached
	public boolean isFinished() {
		return done;
	}

	// Retrieves the read data
	public byte[] getData() {
		return data;
	}
}
