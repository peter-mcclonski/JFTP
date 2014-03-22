package com.zephyrr.ftp.main;

public class Session implements Runnable {
	private FTPConnection control, data;
	private User user;
	private CommandList last;
	public Session(Socket sock) {
		control = new FTPConnection(sock);
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
