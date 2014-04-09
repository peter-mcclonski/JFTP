package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

public class RnfrCommand extends Command {
	public void execute(Session sess, String[] args) {
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if (!FileManager.getFile(sess.getWorkingDirectory() + "/" + args[0])
				.exists()) {
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		if (FileManager.isLocked(sess.getWorkingDirectory() + "/" + args[0])) {
			sess.getControl().sendMessage(getCodeMsg(450));
			return;
		}
		FileManager.lock(args[0], sess.getUser());
		FileManager.dequeueRename(sess.getUser());
		FileManager.queueRename(sess.getWorkingDirectory() + "/" + args[0],
				sess.getUser());
		sess.getControl().sendMessage(getCodeMsg(350));
	}
}
