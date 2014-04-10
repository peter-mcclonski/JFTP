package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the User command.  This command is the 
 * first of the login commands, and is ideally the first command the
 * user will enter upon attempting to connect.  It must be followed
 * by a PASS command.
 *
 * @author Peter Jablonski
 */

public class UserCommand extends Command {
	public void execute(Session sess, String[] args) {
		// Must have exactly 1 argument (name)
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(500));
			return;
		}
		// Clear any prior login info
		sess.resetLogin();
		// Set the default user's name
		sess.getUser().setName(args[0]);
		// Request password
		sess.getControl().sendMessage(getCodeMsg(331));
	}
}
