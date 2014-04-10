package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * Executor class for the Allocate command.
 * This command is unnecessary in this implementation,
 * and thus has not been implemented.
 *
 * @author Peter Jablonski
 */

public class AlloCommand extends Command {
	public void execute(Session sess, String[] args) {
		// 202 Command superfluous
		sess.getControl().sendMessage(getCodeMsg(202));
	}
}
