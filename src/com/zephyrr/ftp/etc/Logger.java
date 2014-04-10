package com.zephyrr.ftp.etc;

import java.util.Queue;
import java.util.LinkedList;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * A self-contained log system for writing system logs without
 * interference.  The logger runs in its own thread, checking
 * for new information every ten seconds.  If new information 
 * is found, it is written to the log file.
 */

public class Logger implements Runnable {
	// The system-generated logger instance
	private static Logger instance;
	static {
		instance = new Logger();
	}
	// Access the system instance
	public static Logger getInstance() {
		return instance;
	}
	
	// The writer
	private FileOutputStream pw;
	// The queue holding new information to be written
	private volatile Queue<String> queue;
	private Logger() {
		queue = new LinkedList<String>();
		try {
			// Open the logfile for writing (on append)
			pw = new FileOutputStream(Config.get("LOGFILE"), true);
		} catch(IOException e) {
			e.printStackTrace();
		}
		// And start it up
		new Thread(this).start();
	}
	// Adds a new line to the queue for writing.
	public synchronized void enqueue(String s) {
		queue.add(s);
	}
	public void run() {
		// Because stopping is for wimps
		while(true) {
			try {
				// Stop for ten seconds between checks.
				// Maybe it's not so wimpy, after all?
				Thread.sleep(10000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			try {
				// Write all queued data to the file
				while(queue.peek() != null) {
					pw.write(queue.remove().getBytes());
				}
				// Save the file, and reopen our writer
				pw.close();
				pw = new FileOutputStream(Config.get("LOGFILE"), true);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
