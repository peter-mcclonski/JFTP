package com.zephyrr.ftp.commands;

import java.io.File;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

public class StouCommand extends Command {
	public void execute(Session sess, String[] args) {
		File f;
		String fname;
		do {
			fname = "";
			for (int i = 0; i < 20; i++)
				fname += (char) (Math.random() * 10);
			f = FileManager.getFile(sess.getWorkingDirectory() + fname);
		} while (f.exists());
		CommandList.STOR.getCommand().execute(sess, new String[] { fname });
	}
}
