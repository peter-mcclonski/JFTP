package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the RNTO (Rename To) command.  This
 * is the second in a pair of commands for renaming files.  This
 * one supplies the new name of the file.
 *
 * @author Peter Jablonski
 */

public class RntoCommand extends Command {
	public void execute(Session sess, String[] args) {
		// Check that we have one argument: The new name.
		if (args.length != 1) {
			// 501 illegal arguments
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Check that the last command issued was RNFR
		if (sess.getLastCommand() != CommandList.RNFR) {
			// 503 bad sequence
			sess.getControl().sendMessage(getCodeMsg(503));
			return;
		}
		// Get the file
		String old = FileManager.dequeueRename(sess.getUser());
		// Check that it exists
		if (FileManager.getFile(sess.getWorkingDirectory() + "/" + args[0])
				.exists()) {
			sess.getControl().sendMessage(getCodeMsg(553));
		} else {
			// And give it a new name.
			FileManager.rename(old, sess.getWorkingDirectory() + "/" + args[0]);
			sess.getControl().sendMessage(getCodeMsg(250));
		}
		// And clean up by getting rid of our lock.
		FileManager.unlock(old);
	}
}
