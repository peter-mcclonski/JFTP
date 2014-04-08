package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.main.TransmissionType;

public class ReinCommand extends Command {
	public void execute(Session sess, String[] args) {
		sess.setWorkingDirectory("/");
		sess.logout();
		sess.setType(TransmissionType.ASCII);
		sess.getControl().sendMessage(getCodeMsg(220));
	}
}
