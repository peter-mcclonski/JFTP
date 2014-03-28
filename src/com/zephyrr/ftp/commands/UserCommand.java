package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.main.FTPConnection;

public class UserCommand extends Command {
	public void execute(FTPConnection conn,
			    String[] args) {
		if(args.length != 1) {
			conn.sendMessage(getCodeMsg(500));
			return;
		}
		Session s = conn.getSession();
		s.resetLogin();
		s.getUser().setName(args[0]);
		conn.sendMessage(getCodeMsg(331));
	}
}
