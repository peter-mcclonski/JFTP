package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the RNFR (Rename From) command.  This command
 * is the first in a sequence of two commands to rename a file.  This 
 * particular command identifies the original name/path.
 *
 * @author Peter Jablonski
 */

public class RnfrCommand extends Command {
	public void execute(Session sess, String[] args) {
		// We need one argument: The path.
		if (args.length != 1) {
			// 501 illegal arguments
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Check that the file exists
		if (!FileManager.getFile(sess.getWorkingDirectory() + "/" + args[0])
				.exists()) {
			// 550 file unavailable
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		// Check that the file is available
		if (FileManager.isLocked(sess.getWorkingDirectory() + "/" + args[0])) {
			// 450 unavailable
			sess.getControl().sendMessage(getCodeMsg(450));
			return;
		}
		// Lock the file so that it doesn't get accessed before 
		// we finish
		FileManager.lock(args[0], sess.getUser());
		// Get rid of any prior rename attempts.
		FileManager.dequeueRename(sess.getUser());
		// And mark this as our rename attempt
		FileManager.queueRename(sess.getWorkingDirectory() + "/" + args[0],
				sess.getUser());
		// 350 waiting for further info
		sess.getControl().sendMessage(getCodeMsg(350));
	}
}
