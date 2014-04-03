package com.zephyrr.ftp.main;

import com.zephyrr.ftp.users.User;
import com.zephyrr.ftp.commands.Command;
import com.zephyrr.ftp.commands.CommandList;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.Arrays;

public class Session implements Runnable {
	private volatile FTPConnection control, data;
	private User user;
	private boolean active;
	private CommandList last;
	private String dir;
	public Session(Socket sock) {
		try {
			control = new FTPConnection(sock, this);
		} catch(IOException e) {
			e.printStackTrace();
		}
		dir = "/";
		active = true;
		data = null;
		user = new User();
		new Thread(this).start();
	}
	public void run() {
		control.sendMessage("220 Service ready for new user.");
		String line;
		while(active && (line = control.getMessage()) != null) {
			line = line.trim();
			System.out.println(line);
			String cmdString = line.split(" ")[0];
			String[] args = line.replaceFirst(cmdString, "").trim().split(" ");
			System.out.println(Arrays.toString(args)); 
			try {
				CommandList.valueOf(cmdString.toUpperCase()).getCommand().execute(this, args);
				last = CommandList.valueOf(cmdString.toUpperCase());
			} catch (IllegalArgumentException e) {
				control.sendMessage("502 Command not implemented");
			}
		}
		if(control != null)
			control.close();
		if(data != null)
			data.close();
	}
	public CommandList getLastCommand() {
		return last;
	}
	public User getUser() {
		return user;
	}
	private synchronized void setData(FTPConnection ftpc) {
		data = ftpc;
	}
	public ServerSocket enterPassive() {
		final ServerSocket ss;
		final Session obj = this;
		try {
			ss = new ServerSocket(0);
			new Thread(new Runnable() {
				public void run() {
					try {
						setData(new FTPConnection(ss.accept(), obj));
						ss.close();
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		return ss;
	}
	public void resetLogin() {
		user = new User();
	}
	public FTPConnection getControl() {
		return control;
	}
	public void setActive(boolean b) {
		active = b;
	}
	public String getWorkingDirectory() {
		return dir;
	}
}
