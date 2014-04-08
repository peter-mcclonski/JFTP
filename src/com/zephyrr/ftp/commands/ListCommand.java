package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

public class ListCommand extends Command {
	public void execute(Session sess, String[] args) {
		CommandList.NLST.getCommand().execute(sess, args);
	}
}
