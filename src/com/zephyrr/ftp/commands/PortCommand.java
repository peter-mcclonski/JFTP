package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

public class PortCommand extends Command {
	public void execute(Session sess, String[] args) {
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		String[] info = args[0].trim().split(",");
		sess.enterActive(info[0] + "." + info[1] + "." + info[2] + "."
				+ info[3],
				Integer.parseInt(info[4]) * 256 + Integer.parseInt(info[5]));
		sess.getControl().sendMessage(getCodeMsg(200));
	}
}
