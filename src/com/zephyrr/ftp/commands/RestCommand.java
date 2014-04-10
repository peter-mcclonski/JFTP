package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * Command executor for the REST (Restart) command.  This
 * command would optimally be able to restart a file transfer
 * at a given point, had it been interrupted previously.  
 * It is, however, unimplemented.
 *
 * @author Peter Jablonski
 */

public class RestCommand extends Command {
	public void execute(Session sess, String[] args) {
		// 502 not implemented
		sess.getControl().sendMessage(getCodeMsg(502));
	}
}
