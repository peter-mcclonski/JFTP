package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.users.Permission;

/*
 * The command executor for the STOR command.  This command will
 * receive a file from the client, storing it on the server.
 */

public class StorCommand extends Command {
	public void execute(Session sess, String[] args) {
		// One argument: The storage path.
		if (args.length != 1) {
			// 501 illegal arguments
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Check that we have an existing data connection
		if (sess.getData() == null) {
			sess.getControl().sendMessage(getCodeMsg(425));
			return;
		}
		// Check that the user is allowed to write new files.
		if (!sess.getUser().hasPermission(Permission.WRITEFILE)) {
			sess.getControl().sendMessage(getCodeMsg(450));
			return;
		}
		// Do a test write to see if the file is available. 
		if (!FileManager.write(sess.getWorkingDirectory() + args[0],
				new byte[0], sess.getUser(), false)) {
			sess.getControl().sendMessage(getCodeMsg(550));
			sess.getData().close();
			return;
		}
		// Begin the transfer
		sess.getControl().sendMessage(getCodeMsg(125));
		byte[] bytes = null;
		switch (sess.getType()) {
		// If we are in ASCII mode, read one line at a time.
		case ASCII:
			String msg = "",
			line;
			while ((line = sess.getData().getMessage()) != null)
				msg += line + "\n";
			bytes = msg.getBytes();
			break;
		// In IMAGE mode, read the raw bytes.
		case IMAGE:
			bytes = sess.getData().getMessageBytes();
			break;
		}
		// Write the received data to the file, overwriting any
		// existing data.
		FileManager.write(sess.getWorkingDirectory() + args[0], bytes,
				sess.getUser(), false);
		// And close the writer as we leave.
		FileManager.closeWriter(sess.getWorkingDirectory() + args[0],
				sess.getUser());
		// 226 success
		sess.getControl().sendMessage(getCodeMsg(226));
		// Tidy up a little more by closing the data connection
		sess.getData().close();
	}
}
