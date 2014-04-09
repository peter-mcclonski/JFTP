package com.zephyrr.ftp.etc;

import java.util.Queue;
import java.util.LinkedList;
import java.io.FileOutputStream;
import java.io.IOException;

public class Logger implements Runnable {
	private static Logger instance;
	static {
		instance = new Logger();
	}
	public static Logger getInstance() {
		return instance;
	}
	
	private FileOutputStream pw;
	private volatile Queue<String> queue;
	private Logger() {
		queue = new LinkedList<String>();
		try {
			pw = new FileOutputStream(Config.get("LOGFILE"), true);
		} catch(IOException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}
	public synchronized void enqueue(String s) {
		queue.add(s);
	}
	public void run() {
		while(true) {
			try {
				Thread.sleep(10000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			try {
				while(queue.peek() != null) {
					pw.write(queue.remove().getBytes());
				}
				pw.close();
				pw = new FileOutputStream(Config.get("LOGFILE"), true);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
