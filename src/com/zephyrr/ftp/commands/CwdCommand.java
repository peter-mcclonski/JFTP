package com.zephyrr.ftp.commands;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

/*
 * The executor class for the CWD command.  This command allows 
 * the user to switch their working directory to any descendant 
 * of the current directory.
 *
 * Known caveats: 
 * 	- Cannot move back up the directory tree without use of the
 * 		CDUP command.
 */

public class CwdCommand extends Command {
	public void execute(Session sess, String[] args) {
		// We require one argument: The relative path of the
		// directory to change to.
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Check that the target directory exists and is a 
		// directory at all.
		File f = FileManager.getFile(sess.getWorkingDirectory() + args[0]);
		if (!f.exists() || !f.isDirectory()) {
			// 550 File unavailable
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		// If we're all good, then we'll actually execute the move.
		sess.setWorkingDirectory((sess.getWorkingDirectory() + "/" + args[0])
				.replaceAll("//", "/"));
		// 250 success
		sess.getControl().sendMessage(getCodeMsg(250));
	}
}
