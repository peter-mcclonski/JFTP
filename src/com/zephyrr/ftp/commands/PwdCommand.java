package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the PWD (Print Working Directory) 
 * command.  This command prints the path of the working directory,
 * relative to the server's HOMEDIR.
 *
 * @author Peter Jablonski
 */

public class PwdCommand extends Command {
	public void execute(Session sess, String[] args) {
		// Check that we have no arguments
		if (args.length != 1) {
			// 501 Illegal argument
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// And send the path, replacing the placeholder with
		// the correct value.
		sess.getControl().sendMessage(
				getCodeMsg(257)
						.replace("$PATHNAME", sess.getWorkingDirectory()));
	}
}
