package com.zephyrr.ftp.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.zephyrr.ftp.etc.Config;

/*
 * The main class responsible for accepting incoming 
 * connections and spawning new sessions.
 */

public class FTP {
	// The ServerSocket listening for new connections
	private ServerSocket serverSock;
	// A list of all connected sessions
	private ArrayList<Session> sessions;

	public FTP() {
		// Initialize real quick
		sessions = new ArrayList<Session>();
	}

	// Begins listening for connections
	public void startServer() {
		try {
			// Binds to the listen port identified in the config
			serverSock = new ServerSocket(Integer.parseInt(Config.get("PORT")));
				// Stopping is for wimps
			while (true) {
				// Accept a connection
				Socket s = serverSock.accept();
				// If we feel like allowing more users, then
				// the connection will be allowed.
				if (sessions.size() < Integer.parseInt(Config
						.get("MAX_CLIENTS")))
					sessions.add(new Session(s, this));
			}
			// serverSock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Allows a session to be removed from the list upon terminating
	public void removeSession(Session s) {
		sessions.remove(s);
	}

	// Main application entry point
	public static void main(String[] args) {
		new FTP().startServer();
	}
}
