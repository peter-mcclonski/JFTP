package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the SITE command.  This command
 * returns basic information about the server.  It is 
 * superflous in this implementation, so it is not included.
 *
 * @author Peter Jablonski
 */

public class SiteCommand extends Command {
	public void execute(Session sess, String[] args) {
		sess.getControl().sendMessage(getCodeMsg(202));
	}
}
