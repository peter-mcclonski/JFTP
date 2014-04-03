package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

public class ListCommand extends Command {
	public void execute(Session sess, String[] args) {
		if(args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		
	}
}
