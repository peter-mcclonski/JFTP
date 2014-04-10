package com.zephyrr.ftp.commands;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

/*
 * The command executor for the STOU (Store Unique) command.  This
 * command is functionally identical to the STOR command, except 
 * that it generates a unique local filename.
 *
 * @author Peter Jablonski
 */

public class StouCommand extends Command {
	public void execute(Session sess, String[] args) {
		File f;
		String fname;
		// Make strings of 20 random digits until we find one 
		// that doesn't yet exist.
		do {
			fname = "";
			for (int i = 0; i < 20; i++)
				fname += (char) (Math.random() * 10);
			f = FileManager.getFile(sess.getWorkingDirectory() + fname);
		} while (f.exists());
		// And execute the STOR command, taking that filename
		// as an argument.
		CommandList.STOR.getCommand().execute(sess, new String[] { fname });
	}
}
