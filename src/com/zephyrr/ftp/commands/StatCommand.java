package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the STAT command.  This command
 * would optimally return status information, but is not
 * implemented.
 */

public class StatCommand extends Command {
	public void execute(Session sess, String[] args) {
		// 502 not implemented
		sess.getControl().sendMessage(getCodeMsg(502));
	}
}
