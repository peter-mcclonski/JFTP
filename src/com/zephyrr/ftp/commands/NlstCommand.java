package com.zephyrr.ftp.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

/*
 * The command executor class for the NLST (Name List) command.  This
 * command sends a list of all files in the given directory.
 *
 * Known caveats:
 * 	- Cannot distinguish absolute paths.
 */

public class NlstCommand extends Command {
	public void execute(final Session sess, final String[] args) {
		// Check that we have no more than one argument (the path)
		if (args.length != 1) {
			// 501 Invalid argument
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Get the absolute path
		String path = sess.getWorkingDirectory() + args[0];
		// Check that it is a directory
		if (!FileManager.getFile(path).isDirectory()) {
			// 450 Invalid
			sess.getControl().sendMessage(getCodeMsg(450));
			return;
		}
		// Get the unsuitably ordered list of file names.
		String[] rawContents = FileManager.getFile(path).list();
		// And add them all to an ArrayList for easier sorting.
		ArrayList<String> contents = new ArrayList<String>();
		for (String s : rawContents) {
			contents.add(s);
		}

		// Sort the arraylist alphabetically, with directories 
		// all coming before files.
		Collections.sort(contents, new Comparator<String>() {
			public int compare(String s1, String s2) {
				String s1Path = sess.getWorkingDirectory() + "/" + args[0]
						+ "/" + s1;
				String s2Path = sess.getWorkingDirectory() + "/" + args[0]
						+ "/" + s2;
				System.out.println(s1Path);
				if (FileManager.getFile(s1Path).isDirectory()
						&& !FileManager.getFile(s2Path).isDirectory())
					return -1;
				if ((FileManager.getFile(s1Path).isDirectory() && FileManager
						.getFile(s2Path).isDirectory())
						|| (FileManager.getFile(s1Path).isFile() && FileManager
								.getFile(s2Path).isFile()))
					return s1.compareTo(s2);
				return 1;
			}
		});
		
		// Check that our data connection exists.
		if (sess.getData() == null) {
			// 425 No data connection
			sess.getControl().sendMessage(getCodeMsg(425));
			return;
		}
		// 125 Starting transfer
		sess.getControl().sendMessage(getCodeMsg(125));
		// Send each file entry as its own line.
		for (String s : contents)
			sess.getData().sendMessage(s);
		// 226 Success
		sess.getControl().sendMessage(getCodeMsg(226));
		// And tidy up by closing our connection.
		sess.closeDataConnection();
	}
}
