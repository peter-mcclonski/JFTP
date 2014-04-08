package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

public class DeleCommand extends Command {
	public void execute(Session sess, String[] args) {
		if(args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if(FileManager.isLocked(sess.getWorkingDirectory() + "/" + args[0])) {
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		FileManager.getFile(sess.getWorkingDirectory() + "/" + args[0]).delete();
		sess.getControl().sendMessage(getCodeMsg(250));
	}
}
