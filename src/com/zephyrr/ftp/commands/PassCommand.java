package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.etc.Config;

/*
 * Command executor for the PASS command.  Allows the user to 
 * complete the login process by providing a password.
 *
 * @author Peter Jablonski
 */

public class PassCommand extends Command {
	public void execute(Session sess, String[] args) {
		// Check that this command was immediately preceded
		// by the USER command.
		if (sess.getLastCommand() != CommandList.USER) {
			// 202 invalid sequence
			sess.getControl().sendMessage(getCodeMsg(202));
			return;
		}
		// Check that we have one argument: The password.
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(500));
			return;
		}
		// Attempt to log in with the given information.
		if (sess.getUser().attemptAuthorization(args[0])) {
			// Set the working directory to that user's home.
			sess.setWorkingDirectory(sess.getUser().getHome());
			// 230 Logged in
			sess.getControl().sendMessage(getCodeMsg(230));
		} else {
			// 530 failed
			sess.getControl().sendMessage(getCodeMsg(530));
		}
	}
}
