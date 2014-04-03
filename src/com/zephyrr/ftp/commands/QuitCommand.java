package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

public class QuitCommand extends Command {
	public void execute(Session sess, String[] args) {
		sess.setActive(false);
		sess.getControl().sendMessage(getCodeMsg(221));
	}
}
