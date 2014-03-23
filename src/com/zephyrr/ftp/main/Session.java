package com.zephyrr.ftp.main;

import com.zephyrr.ftp.users.User;
import com.zephyrr.ftp.commands.Command;
import com.zephyrr.ftp.commands.CommandList;
import java.net.Socket;
import java.io.IOException;

public class Session implements Runnable {
	private FTPConnection control, data;
	private User user;
	private CommandList last;
	public Session(Socket sock) {
		try {
			control = new FTPConnection(sock, this);
		} catch(IOException e) {
			e.printStackTrace();
		}
		data = null;
		user = new User();
		new Thread(this).start();
	}
	public void run() {
		control.sendMessage("220 Service ready for new user.");
		String line;
		while((line = control.getMessage().trim()) != null) {
			String cmdString = line.split(" ")[0];
			String[] args = line.replaceFirst(cmdString, "").split(" ");
			Command comm = CommandList.valueOf(cmdString.toUpperCase()).getCommand();
			comm.execute(control, args);
			last = CommandList.valueOf(cmdString.toUpperCase());
		}
	}
	public CommandList getLastCommand() {
		return last;
	}
	public User getUser() {
		return user;
	}
	public void setUsername(String username) {
		user.setName(username);
	}
	public void resetLogin() {
		user = new User();
	}
}
