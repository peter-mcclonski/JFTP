package com.zephyrr.ftp.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.zephyrr.ftp.etc.Config;

public class FTP {
	private ServerSocket serverSock;
	private ArrayList<Session> sessions;

	public FTP() {
		sessions = new ArrayList<Session>();
	}

	public void startServer() {
		try {
			serverSock = new ServerSocket(Integer.parseInt(Config.get("PORT")));
			while (true) {
				Socket s = serverSock.accept();
				if (sessions.size() < Integer.parseInt(Config
						.get("MAX_CLIENTS")))
					sessions.add(new Session(s, this));
			}
			// serverSock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeSession(Session s) {
		sessions.remove(s);
	}

	public static void main(String[] args) {
		new FTP().startServer();
	}
}
