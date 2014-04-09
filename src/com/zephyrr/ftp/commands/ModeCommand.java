package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

public class ModeCommand extends Command {
	public void execute(Session sess, String[] args) {
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if (!args[0].toUpperCase().equals("S")) {
			sess.getControl().sendMessage(getCodeMsg(504));
			return;
		}
		sess.getControl().sendMessage(getCodeMsg(200));
	}
}
