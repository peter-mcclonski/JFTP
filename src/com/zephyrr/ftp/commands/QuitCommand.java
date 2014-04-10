package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the Quit command.  This command
 * will terminate the session and disconnect the user.
 *
 * @author Peter Jablonski
 */

public class QuitCommand extends Command {
	public void execute(Session sess, String[] args) {
		// Mark the session as inactive, breaking the loop.
		sess.setActive(false);
		// 221 Logged out.
		sess.getControl().sendMessage(getCodeMsg(221));
	}
}
