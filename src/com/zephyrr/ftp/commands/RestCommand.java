package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

public class RestCommand extends Command {
	public void execute(Session sess, String[] args) {
		sess.getControl().sendMessage(getCodeMsg(502));
	}
}
