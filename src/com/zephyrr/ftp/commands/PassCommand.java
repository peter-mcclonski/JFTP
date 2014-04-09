package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.etc.Config;

public class PassCommand extends Command {
	public void execute(Session sess, String[] args) {
		if (sess.getLastCommand() != CommandList.USER) {
			sess.getControl().sendMessage(getCodeMsg(202));
			return;
		}
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(500));
			return;
		}
		if (sess.getUser().attemptAuthorization(args[0])) {
			sess.setWorkingDirectory(sess.getUser().getHome());
			sess.getControl().sendMessage(getCodeMsg(230));
		} else {
			sess.getControl().sendMessage(getCodeMsg(530));
		}
	}
}
