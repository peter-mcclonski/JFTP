package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * Executor class for the Help command.  Not currently
 * implemented.
 *
 * @author Peter Jablonski
 */

public class HelpCommand extends Command {
	public void execute(Session sess, String[] args) {
		// 502 not implemented
		sess.getControl().sendMessage(getCodeMsg(502));
	}
}
