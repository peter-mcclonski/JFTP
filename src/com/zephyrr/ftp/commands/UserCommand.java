package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.main.FTPConnection;

public class UserCommand extends Command {
	public void execute(FTPConnection conn,
			    String[] args) {
		if(args.length != 2) {
			conn.sendMessage(getCodeMsg(500));
			return;
		}
		Session s = conn.getSession();
		s.resetLogin();
		s.setUser(username);
		conn.sendMessage(getCodeMsg(331));
	}
}
