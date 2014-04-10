package com.zephyrr.ftp.commands;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.users.Permission;

/*
 * The executor class for the MKD command.  Creates a new
 * directory with a given path/name, if possible. 
 *
 * @author Peter Jablonski
 */

public class MkdCommand extends Command {
	public void execute(Session sess, String[] args) {
		// We take one argument: The path/name of the new 
		// directory that we wish to create.
		if (args.length != 1) {
			// 501 Invalid argument
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Check that the user is allowed to make new directories
		if (!sess.getUser().hasPermission(Permission.MAKEDIR)) {
			// If not, send them away.
			sess.getControl().sendMessage(getCodeMsg(450));
			return;
		}
		// Check that the directory doesn't already exist.
		if (FileManager.getFile(sess.getWorkingDirectory() + "/" + args[0])
				.exists()) {
			// 550 File unavailable
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		// Create the requested directory, and any additional
		// required parent directories.
		File f = FileManager
				.getFile(sess.getWorkingDirectory() + "/" + args[0]);
		f.mkdirs();
		// 257 successfully created
		sess.getControl().sendMessage(
				getCodeMsg(257).replace("$PATHNAME",
					args[0]));
					
	}
}
