package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * Command executor for the SMNT command.  This command is 
 * considered superflous in this implementation, and thus
 * is not included.
 */

public class SmntCommand extends Command {
	public void execute(Session sess, String[] args) {
		sess.getControl().sendMessage(getCodeMsg(202));
	}
}
