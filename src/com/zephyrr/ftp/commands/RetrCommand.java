package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.io.ReadResult;
import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.users.Permission;

/*
 * Command executor for the RETR (Retrieve) command.  This command
 * gets a file from the server and transfers it to the client in 
 * whatever transmission mode is currently set.
 *
 * @author Peter Jablonski
 */

public class RetrCommand extends Command {
	public void execute(Session sess, String[] args) {
		// We need one argument: The file to get.
		if (args.length != 1) {
			// 501 illegal argument
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Check that we have an active data connection already.
		if (sess.getData() == null) {
			// 425 no connection
			sess.getControl().sendMessage(getCodeMsg(425));
			return;
		}
		// Check that the file exists
		if (!FileManager.getFile(sess.getWorkingDirectory() + args[0]).exists()) {
			// 550 file unavailable
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		// Check that the user is allowed to retrieve files.
		if (!sess.getUser().hasPermission(Permission.READFILE)) {
			// 450 unavailable
			sess.getControl().sendMessage(getCodeMsg(450));
			return;
		}
		// Do a test read to see if the file is available.
		ReadResult rr = FileManager.read(sess.getWorkingDirectory() + args[0],
				0, sess.getUser());
		if (rr.isLocked()) {
			// 550 file unavailable
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		// 125 transfer starting
		sess.getControl().sendMessage(getCodeMsg(125));
		// Read and send the file in 512 byte chunks.
		do {
			rr = FileManager.read(sess.getWorkingDirectory() + args[0], 512,
					sess.getUser());
			// In ASCII mode, just send a string.
			switch (sess.getType()) {
			case ASCII:
				sess.getData().sendMessage(
						new String(rr.getData()).substring(0,
								rr.getTrueLength()));
				break;
			// In IMAGE mode, just send raw bytes.
			case IMAGE:
				sess.getData().sendPartialMessageBytes(rr.getData(), 0,
						rr.getTrueLength());
				break;
			}
		} while (!rr.isFinished());
		// 226 success
		sess.getControl().sendMessage(getCodeMsg(226));
		// And clean up after ourselves.
		sess.getData().close();
	}
}
