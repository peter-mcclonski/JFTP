package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

public class RntoCommand extends Command {
	public void execute(Session sess, String[] args) {
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if (sess.getLastCommand() != CommandList.RNFR) {
			sess.getControl().sendMessage(getCodeMsg(503));
			return;
		}
		String old = FileManager.dequeueRename(sess.getUser());
		if (FileManager.getFile(sess.getWorkingDirectory() + "/" + args[0])
				.exists()) {
			sess.getControl().sendMessage(getCodeMsg(553));
		} else {
			FileManager.rename(old, sess.getWorkingDirectory() + "/" + args[0]);
			sess.getControl().sendMessage(getCodeMsg(250));
		}
		FileManager.unlock(old);
	}
}
