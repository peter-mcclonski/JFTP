package com.zephyrr.ftp.commands;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

public class MkdCommand extends Command {
	public void execute(Session sess, String[] args) {
		if(args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if(FileManager.getFile(sess.getWorkingDirectory() + "/" + args[0]).exists()) {
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		File f = FileManager.getFile(sess.getWorkingDirectory() + "/" + args[0]);
		f.mkdirs();
		sess.getControl().sendMessage(getCodeMsg(257).replaceFirst("$PATHNAME", FileManager.convertPath(f.getAbsolutePath())));
	}
}
