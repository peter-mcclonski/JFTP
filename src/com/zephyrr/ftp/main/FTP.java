package com.zephyrr.ftp.main;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.File;

public class FTP {
	private File pubPath;
	private int port;
	private ServerSocket serverSock;
	private FTPConnection control, data;
	public FTP(String pub) {
		pubPath = new File(pub);
		if(!pubPath.exists())
			pubPath.mkdirs();
		port = 21;
	}
	public FTP(String pub, int port) {
		this(pub);
		this.port = port;
	}

	public void startServer() {
		try {
			serverSock = new ServerSocket(port);
			while(true) {
				control = new FTPConnection(serverSock.accept());
			}
//			serverSock.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FTP server;
		if(args.length >= 1) {
			if(args.length >= 2) {
				try {
					server = new FTP(args[0], Integer.parseInt(args[1]));
				} catch(NumberFormatException e) {
					server = new FTP(args[0]);
				}
			} else server = new FTP(args[0]);
		} else {
			server = new FTP(new File("").getAbsolutePath());
		}
		server.startServer();
	}
}
