package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.users.Permission;

/*
 * The executor class for the DELE (delete) command.  Removes
 * the file requested from the server.
 *
 * @author Peter Jablonski
 */

public class DeleCommand extends Command {
	public void execute(Session sess, String[] args) {
		// We need one argument: The file to delete
		if (args.length != 1) {
			// 501 Invalid arguments
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Check if the user is allowed to delete stuff
		if (!sess.getUser().hasPermission(Permission.DELETEFILE)) {
			// If not, refuse the operation
			sess.getControl().sendMessage(getCodeMsg(450));
			return;
		}
		// Make sure the file isn't in use.
		if (FileManager.isLocked(sess.getWorkingDirectory() + "/" + args[0])) {
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		// Get the file and delete it.
		FileManager.getFile(sess.getWorkingDirectory() + "/" + args[0])
				.delete();
		// 250 success
		sess.getControl().sendMessage(getCodeMsg(250));
	}
}
