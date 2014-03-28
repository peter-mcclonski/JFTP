package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.FTPConnection;

public class PassCommand extends Command {
	public void execute(FTPConnection ftcp, String[] args) {
		if(ftcp.getSession().getLastCommand() != CommandList.USER) {
			ftcp.sendMessage(getCodeMsg(202));
			return;
		}
		if(args.length != 1) {
			ftcp.sendMessage(getCodeMsg(500));
			return;
		}
		if(ftcp.getSession().getUser().attemptAuthorization(args[0])) {
			ftcp.sendMessage(getCodeMsg(230));
		} else {
			ftcp.sendMessage(getCodeMsg(530));
		}
	}
}
