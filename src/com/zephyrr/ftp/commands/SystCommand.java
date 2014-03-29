package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.FTPConnection;

public class SystCommand extends Command {
	public void execute(FTPConnection ftcp, String[] args) {
		ftcp.sendMessage(getCodeMsg(215));
	}
}
