package com.zephyrr.ftp.main;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class FTPConnection implements {
	private Socket sock;
	private BufferedReader read;
	private DataOutputStream write;
	public FTPConnection(Socket sock) throws IOException {
		this.sock = sock;
		read = new BufferedReader(
				new InputStreamReader(
					sock.getInputStream()));
		write = new DataOutputStream(sock.getOutputStream());
	}
	public void sendMessage(String msg) {
		try {
			write.writeChars(msg + "\r\n");
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
	}
}
