package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * The executor class for the LIST command.  Retrieves a list
 * of all files, with information about each.
 *
 * Known caveats:
 * 	- Currently doesn't include any extra information.  
 * 		Merely calls the NLST command.
 *
 * @author Peter Jablonski
 */

public class ListCommand extends Command {
	public void execute(Session sess, String[] args) {
		// Functions as an NLST alias.
		CommandList.NLST.getCommand().execute(sess, args);
	}
}
