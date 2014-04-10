package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * Command executor for the NOOP command.  Specifically intended
 * to do nothing but return an OK code.
 *
 * @author Peter Jablonski
 */

public class NoopCommand extends Command {
	public void execute(Session sess, String[] args) {
		// 200 OK
		sess.getControl().sendMessage(getCodeMsg(200));
	}
}
