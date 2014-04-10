package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the STRU command.  This command
 * sets the data structure in use.
 *
 * Known caveats:
 * 	- Only currently supported structure is File (F)
 *
 * @author Peter Jablonski
 */

public class StruCommand extends Command {
	public void execute(Session sess, String[] args) {
		// Check that there are two arguments
		if (args.length != 2) {
			// Illegal arguments
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Check that it is our one and only supported struct
		if (!args[1].equalsIgnoreCase("F")) {
			sess.getControl().sendMessage(getCodeMsg(504));
			return;
		}
		// 200 OK
		sess.getControl().sendMessage(getCodeMsg(200));
	}
}
