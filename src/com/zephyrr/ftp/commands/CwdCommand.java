package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.io.FileManager;

public class CwdCommand extends Command {
	public void execute(Session sess, String[] args) {
		if(args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		File f = FileManager.getFile(sess.getWorkingDirectory() + args[0]);
		if(!f.exists() || !f.isDirectory()) {
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		sess.setWorkingDirectory((sess.getWorkingDirectory() + "/" + args[0]).replaceAll("//", "/"));
		sess.getControl().sendMessage(getCodeMsg(250));
	}
}
