package com.zephyrr.ftp.commands;

public class UserCommand extends Command {
	private String username;
	public void execute(FTPConnection c,
			    String[] args) {
		if(args.length != 2) {
			c.sendMessage("
			return;
		}
		username = args[0];
	}
}
