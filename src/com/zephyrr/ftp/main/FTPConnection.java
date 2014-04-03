package com.zephyrr.ftp.main;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class FTPConnection {
	private Socket sock;
	private Session sess;
	private BufferedReader read;
	private BufferedOutputStream write;
	public FTPConnection(Socket sock, Session sess) throws IOException {
		this.sock = sock;
		this.sess = sess;
		read = new BufferedReader(
				new InputStreamReader(
					sock.getInputStream()));
		write = new BufferedOutputStream(sock.getOutputStream());
	}
	public void sendMessage(String msg) {
		try {
			System.out.println("Sending message: " + msg);
			byte[] finalOut = (msg + "\r\n").getBytes();
			write.write(finalOut, 0, finalOut.length);
			write.flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public String getMessage() {
		try {
			return read.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void close() {
		try {
			read.close();
			write.close();
			sock.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public Session getSession() {
		return sess;
	}
}
