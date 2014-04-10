package com.zephyrr.ftp.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import com.zephyrr.ftp.main.Session;

/*
 * Socket wrapper for easier handling of messages between the 
 * server and client.
 *
 * @author Peter Jablonski
 */

public class FTPConnection {
	// Socket this connection is running through
	private Socket sock;
	// Parent session
	private Session sess;
	// Reader for reading strings
	private BufferedReader read;
	// Reader for reading bytes
	private DataInputStream dread;
	// Writer for pushing bytes (representing both strings and raw bytes)
	private BufferedOutputStream write;

	public FTPConnection(Socket sock, Session sess) throws IOException {
		// Initialize everything
		this.sock = sock;
		this.sess = sess;
		read = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		dread = new DataInputStream(sock.getInputStream());
		write = new BufferedOutputStream(sock.getOutputStream());
	}

	// Sends a basic string to the client
	public void sendMessage(String msg) {
		sendMessageBytes((msg + "\r\n").getBytes());
	}
	
	// Sends bytes to the client
	public void sendMessageBytes(byte[] msg) {
		sendPartialMessageBytes(msg, 0, msg.length);
	}

	// Sends a portion of a byte array to the client.
	public void sendPartialMessageBytes(byte[] msg, int start, int len) {
		try {
			// Write the portion to the socket
			write.write(msg, start, len);
			// And flush, because apparently that helps
			write.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Reads a string from the socket
	public String getMessage() {
		try {
			return read.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Reads an array of bytes from the socket
	public byte[] getMessageBytes() {
		// We aren't sure how many we're going to read,
		// so we'll store them in an ArrayList to start.
		ArrayList<Byte> al = new ArrayList<Byte>();
		// Hey, we'll know when we're done because it'll 
		// break.  Let's just use that as a flag!
		boolean noCrash = true;
		while (noCrash) {
			try {
				// Read one byte at a time
				al.add(dread.readByte());
			} catch (IOException e) {
				// Until we crash
				noCrash = false;
			}
		}
		// And convert the ArrayList to an array
		byte[] ret = new byte[al.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = al.get(i).byteValue();
		return ret;
	}

	// Closes the connection
	public void close() {
		try {
			read.close();
			write.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Retrieves the parent session
	public Session getSession() {
		return sess;
	}
}
