package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.net.TransmissionType;

/*
 * The command executor for the Rein (Reinitialize) command.  This
 * command will reset all session data, including logging out the 
 * current user and reverting to default settings.
 *
 * @author Peter Jablonski
 */

public class ReinCommand extends Command {
	public void execute(Session sess, String[] args) {
		// Default directory
		sess.setWorkingDirectory("/");
		// Default user
		sess.logout();
		// Default transmission type
		sess.setType(TransmissionType.ASCII);
		// 220 success
		sess.getControl().sendMessage(getCodeMsg(220));
	}
}
