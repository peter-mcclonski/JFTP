package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.net.TransmissionType;

/*
 * The command executor for the TYPE command.  This command sets
 * the transmission type for the server (ie: Image, Ascii)
 *
 * Known caveats:
 * 	- Currently only supports Ascii and Image
 *
 * @author Peter Jablonski
 */

public class TypeCommand extends Command {
	public void execute(Session sess, String[] args) {
		// Takes either 1 or 2 arguments.
		if (args.length < 1 || args.length > 2) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Get the type character
		char type = args[0].toUpperCase().charAt(0);

		switch (type) {
		// A = Ascii
		case 'A':
			sess.setType(TransmissionType.ASCII);
			break;
		// I = Image
		case 'I':
			sess.setType(TransmissionType.IMAGE);
			break;
		// E = EBCDIC
		case 'E':
			sess.getControl().sendMessage(getCodeMsg(504));
			return;
		// L = Local
		case 'L':
			sess.getControl().sendMessage(getCodeMsg(504));
			return;
		// Anything else is bogus
		default:
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		// Secondary params are not supported
		if (args.length == 2 && args[1].equalsIgnoreCase("C")) {
			sess.getControl().sendMessage(getCodeMsg(504));
			return;
		}
		// 200 OK
		sess.getControl().sendMessage(getCodeMsg(200));
	}
}
