package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.net.TransmissionType;

public class TypeCommand extends Command {
	public void execute(Session sess, String[] args) {
		if (args.length < 1 || args.length > 2) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		char type = args[0].toUpperCase().charAt(0);

		switch (type) {
		case 'A':
			sess.setType(TransmissionType.ASCII);
			break;
		case 'I':
			sess.setType(TransmissionType.IMAGE);
			break;
		case 'E':
			sess.getControl().sendMessage(getCodeMsg(504));
			return;
		case 'L':
			sess.getControl().sendMessage(getCodeMsg(504));
			return;
		default:
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if (args.length == 2 && args[1].equalsIgnoreCase("C")) {
			sess.getControl().sendMessage(getCodeMsg(504));
			return;
		}
		sess.getControl().sendMessage(getCodeMsg(200));
	}
}
