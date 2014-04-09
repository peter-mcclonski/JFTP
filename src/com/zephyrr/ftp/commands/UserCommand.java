package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

public class UserCommand extends Command {
	public void execute(Session sess, String[] args) {
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(500));
			return;
		}
		sess.resetLogin();
		sess.getUser().setName(args[0]);
		sess.getControl().sendMessage(getCodeMsg(331));
	}
}
