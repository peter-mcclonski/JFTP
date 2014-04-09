package com.zephyrr.ftp.commands;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.users.Permission;

public class RmdCommand extends Command {
	public void execute(Session sess, String[] args) {
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if (!sess.getUser().hasPermission(Permission.DELETEDIR)) {
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		File base = FileManager.getFile(sess.getWorkingDirectory() + "/"
				+ args[0]);
		if (!base.exists() || !base.isDirectory()) {
			sess.getControl().sendMessage(getCodeMsg(550));
		}
		if (recursiveDelete(base)) {
			sess.getControl().sendMessage(getCodeMsg(250));
		} else {
			sess.getControl().sendMessage(getCodeMsg(550));
		}
	}

	private boolean recursiveDelete(File f) {
		File[] children = f.listFiles();
		for (File c : children) {
			if (c.isDirectory())
				recursiveDelete(c);
			else if (!FileManager.isLocked(FileManager.convertPath(c
					.getAbsolutePath()))) {
				f.delete();
			}
		}
		return !FileManager.isLocked(FileManager.convertPath(f
				.getAbsolutePath())) && f.delete();
	}
}
