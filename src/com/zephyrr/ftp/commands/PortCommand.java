package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the Port command.  This command puts
 * the server in active mode to attempt a data connection to the
 * specified IP and port.
 *
 * @author Peter Jablonski
 */

public class PortCommand extends Command {
	public void execute(Session sess, String[] args) {
		// We need one argument: The IP/Port.
		if (args.length != 1) {
			// 501 illegal arguments
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Divide the message for easier handling
		String[] info = args[0].trim().split(",");
		// Build the IP/port, and tell the Session to connect to it.
		sess.enterActive(info[0] + "." + info[1] + "." + info[2] + "."
				+ info[3],
				Integer.parseInt(info[4]) * 256 + Integer.parseInt(info[5]));
		// 200 OK
		sess.getControl().sendMessage(getCodeMsg(200));
	}
}
