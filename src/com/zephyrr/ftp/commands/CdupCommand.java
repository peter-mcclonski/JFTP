package com.zephyrr.ftp.commands;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

public class CdupCommand extends Command {
	public void execute(Session sess, String[] args) {
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if (sess.getWorkingDirectory().equals("/")) {
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		File f = FileManager.getFile(sess.getWorkingDirectory());
		String parent = f.getParent();
		parent = FileManager.convertPath(parent);
		sess.setWorkingDirectory(parent);
		sess.getControl().sendMessage(getCodeMsg(200));
	}
}
