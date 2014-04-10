package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the SYST command.  This command
 * retrieves basic system information.
 *
 * @author Peter Jablonski
 */

public class SystCommand extends Command {
	public void execute(Session sess, String[] args) {
		// 215 UNIX L8
		sess.getControl().sendMessage(getCodeMsg(215));
	}
}
