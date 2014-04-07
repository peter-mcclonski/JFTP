package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

public class CdupCommand extends Command {
	public void execute(Session sess, String[] args) {
		if(args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if(sess.getWorkingDirectory().equals("/")) {
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		File f = FileManager.getFile(sess.getWorkingDirectory());
		File parent = f.getParent();
		
	}
}
