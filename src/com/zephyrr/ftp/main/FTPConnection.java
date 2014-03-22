package com.zephyrr.ftp.main;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class FTPConnection implements Runnable {
	private Socket sock;
	private BufferedReader read;
	private DataOutputStream write;
	public FTPConnection(Socket sock) throws IOException {
		this.sock = sock;
		read = new BufferedReader(
				new InputStreamReader(
					sock.getInputStream()));
		write = new DataOutputStream(sock.getOutputStream());
		new Thread(this).start();
	}
	public void sendMessage(StatusCode code) {
		try {
			write.writeChars(code.getCode() + " " + code.getMsg() + "\r\n");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		String line;
		try {
			System.out.println("Got in here!");
			sendMessage(StatusCode.READY);
			while((line = read.readLine()) != null) {
				System.out.println(line);
			}
			read.close();
			write.close();
			sock.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
