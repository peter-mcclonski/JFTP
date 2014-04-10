package com.zephyrr.ftp.commands;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.users.Permission;

/*
 * The command executor for the RMD (Remove Directory) command.  This
 * command will attempt to delete the given directory and, if necessary,
 * recursively delete all descendants of that directory.
 *
 * @author Peter Jablonski
 */

public class RmdCommand extends Command {
	public void execute(Session sess, String[] args) {
		// One argument: Path to delete.
		if (args.length != 1) {
			// 501 illegal arguments
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Check that the user is allowed to delete directories.
		if (!sess.getUser().hasPermission(Permission.DELETEDIR)) {
			// 550 unavailable
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		// Start recursing at the given directory, if it's a real
		// directory at all.
		File base = FileManager.getFile(sess.getWorkingDirectory() + "/"
				+ args[0]);
		if (!base.exists() || !base.isDirectory()) {
			// 550 file unavailable
			sess.getControl().sendMessage(getCodeMsg(550));
		}
		if (recursiveDelete(base)) {
			// If we deleted absolutely everything, then 
			// we've succeeded.
			sess.getControl().sendMessage(getCodeMsg(250));
		} else {
			// Otherwise, 550 file unavailable.
			sess.getControl().sendMessage(getCodeMsg(550));
		}
	}

	private boolean recursiveDelete(File f) {
		// Get all the files in a directory.
		File[] children = f.listFiles();
		for (File c : children) {
			// If a file is a directory, recurse into it
			// to delete all of its children.
			if (c.isDirectory())
				recursiveDelete(c);
			// Otherwise, delete the file.
			else c.delete();
		}
		// And then delete the current directory.
		return f.delete();
	}
}
