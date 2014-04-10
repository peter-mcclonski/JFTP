package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the MODE command.  Sets the current
 * transfer mode.  
 *
 * Known caveats:
 * 	- Currently only supports Stream mode.
 *
 * @author Peter Jablonski
 */

public class ModeCommand extends Command {
	public void execute(Session sess, String[] args) {
		// We need one argument: The mode character.
		if (args.length != 1) {
			// 501 invalid argument
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Check that it's the one mode we support (S)
		if (!args[0].toUpperCase().equals("S")) {
			// 504 Argument not implemented
			sess.getControl().sendMessage(getCodeMsg(504));
			return;
		}
		// 200 OK
		sess.getControl().sendMessage(getCodeMsg(200));
	}
}
