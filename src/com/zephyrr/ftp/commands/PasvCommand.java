package com.zephyrr.ftp.commands;

import java.net.ServerSocket;

import com.zephyrr.ftp.etc.Config;
import com.zephyrr.ftp.main.Session;

/*
 * Command executor for the Pasv (passive) command.  This
 * command puts the server in passive mode to await a data
 * connection from the client.
 *
 * @author Peter Jablonski
 */

public class PasvCommand extends Command {
	public void execute(Session sess, String[] args) {
		// We need no arguments.
		if (args.length != 1) {
			// 501 invalid argument
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Tell the Session to go into passive mode.
		ServerSocket ss = sess.enterPassive();
		// Get the placeholder code 227, then replace the 
		// placeholder values with the desired IP and port.
		String msg = getCodeMsg(227);
		String[] ip = Config.get("PASSIVE_IP").split("\\.");
		System.out.println(ip.length);
		for (int i = 0; i < ip.length; i++)
			msg = msg.replace("h" + (i + 1), ip[i]);
		msg = msg.replace("p1", "" + ss.getLocalPort() / 256);
		msg = msg.replace("p2", "" + ss.getLocalPort() % 256);
		// And send.
		sess.getControl().sendMessage(msg);
	}
}
