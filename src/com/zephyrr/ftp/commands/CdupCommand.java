package com.zephyrr.ftp.commands;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

/*
 * Executor class for the Cdup command.  This command
 * will change the working directory to the parent of the 
 * current directory.
 *
 * @author Peter Jablonski
 */

public class CdupCommand extends Command {
	public void execute(Session sess, String[] args) {
		// We want no arguments.  Why take what we do not need?
		if (args.length != 1) {
			// 501 Invalid arguments
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// We want to restrict the user to their home directory, so
		// if they're already there, we won't let them go further up.
		if (sess.getWorkingDirectory().equals(sess.getUser().getHome())) {
			// 550 File unavailable
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		// And we get the internal path of the parent, relative to 
		// the server's home directory.
		File f = FileManager.getFile(sess.getWorkingDirectory());
		String parent = f.getParent();
		parent = FileManager.convertPath(parent);
		System.out.println(parent);
		// And set that to our working directory.
		sess.setWorkingDirectory(parent);
		sess.getControl().sendMessage(getCodeMsg(200));
	}
}
