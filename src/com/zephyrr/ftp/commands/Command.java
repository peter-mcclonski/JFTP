package com.zephyrr.ftp.commands;

public interface Command {
	public void execute(FTPConnection ftpc, String[] args);
}
