package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.users.Permission;

/*
 * The executor class for the Append command.
 * The default file storage operation (STOR) will overwrite 
 * any existing file with the same name.  This command
 * will instead append the new data onto the end of that
 * pre-existing file.  If there is no file with the same
 * name, it will behave identically to the STOR command.
 */

public class AppeCommand extends Command {
	// args[0]: The filename/path to write.
	public void execute(Session sess, String[] args) {
		// We need one argument: The filename.
		if (args.length != 1) {
			// 501 Invalid arguments
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// For the sake of simplicity, we won't try to establish
		// the connection in here.  It is the responsibiity of the
		// client to send a PASV or PORT command before sending a
		// command requiring a data connection.  If they have failed
		// to do this, then we will simply break out.
		if (sess.getData() == null) {
			// 425 Can't open data
			sess.getControl().sendMessage(getCodeMsg(425));
			return;
		}
		// Check if this particular user is allowed to write new 
		// data.  If not, that's rather naughty, and we'll ignore 
		// the request.
		if (!sess.getUser().hasPermission(Permission.WRITEFILE)) {
			sess.getControl().sendMessage(getCodeMsg(450));
			return;
		}
		// Check if we can write to the given file.  If the file is
		// locked or is unavailable for any other reason, then
		// this will return false.
		if (!FileManager.write(sess.getWorkingDirectory() + args[0],
				new byte[0], sess.getUser(), true)) {
			// 550 File unavailable
			sess.getControl().sendMessage(getCodeMsg(550));
			// Tidy up and close the data connection...
			sess.getData().close();
			return;
		}
		// 125 Transfer ready
		sess.getControl().sendMessage(getCodeMsg(125));
		// We write everything as bytes, even if it's not received
		// in that form.
		byte[] bytes = null;
		switch (sess.getType()) {
		// Ascii mode just reads simple strings
		case ASCII:
			String msg = "",
			line;
			while ((line = sess.getData().getMessage()) != null)
				msg += line + "\n";
			bytes = msg.getBytes();
			break;
		// Whereas image (binary) mode is pure bytes
		case IMAGE:
			bytes = sess.getData().getMessageBytes();
			break;
		}
		// Write all of our data to the file, appending if necessary
		FileManager.write(sess.getWorkingDirectory() + args[0], bytes,
				sess.getUser(), true);
		// And tidy up by closing the writer, removing our lock.
		FileManager.closeWriter(sess.getWorkingDirectory() + args[0],
				sess.getUser());
		// 226 Transfer successful
		sess.getControl().sendMessage(getCodeMsg(226));
		// Again, tidy up and close the connection.
		sess.getData().close();
	}
}
