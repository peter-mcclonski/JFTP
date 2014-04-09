package com.zephyrr.ftp.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import com.zephyrr.ftp.main.Session;

public class FTPConnection {
	private Socket sock;
	private Session sess;
	private BufferedReader read;
	private DataInputStream dread;
	private BufferedOutputStream write;

	public FTPConnection(Socket sock, Session sess) throws IOException {
		this.sock = sock;
		this.sess = sess;
		read = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		dread = new DataInputStream(sock.getInputStream());
		write = new BufferedOutputStream(sock.getOutputStream());
	}

	public void sendMessage(String msg) {
		System.out.println("Sending message: " + msg);
		sendMessageBytes((msg + "\r\n").getBytes());
	}

	public void sendMessageBytes(byte[] msg) {
		sendPartialMessageBytes(msg, 0, msg.length);
	}

	public void sendPartialMessageBytes(byte[] msg, int start, int len) {
		try {
			write.write(msg, start, len);
			write.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getMessage() {
		try {
			return read.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] getMessageBytes() {
		ArrayList<Byte> al = new ArrayList<Byte>();
		boolean noCrash = true;
		while (noCrash) {
			try {
				al.add(dread.readByte());
			} catch (IOException e) {
				System.out.println(al.size());
				noCrash = false;
			}
		}
		byte[] ret = new byte[al.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = al.get(i).byteValue();
		if (al.size() != 0)
			System.out.println(ret[al.size() - 1]);
		return ret;
	}

	public void close() {
		try {
			read.close();
			write.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Session getSession() {
		return sess;
	}
}
