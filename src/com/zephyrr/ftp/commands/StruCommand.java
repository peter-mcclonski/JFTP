package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

public class StruCommand extends Command {
	public void execute(Session sess, String[] args) {
		if(args.length != 2) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if(!args[1].equalsIgnoreCase("F")) {
			sess.getControl().sendMessage(getCodeMsg(504));
			return;
		}
		sess.getControl().sendMessage(getCodeMsg(200));
	}
}
