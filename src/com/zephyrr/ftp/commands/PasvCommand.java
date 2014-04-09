package com.zephyrr.ftp.commands;

import java.net.ServerSocket;

import com.zephyrr.ftp.etc.Config;
import com.zephyrr.ftp.main.Session;

public class PasvCommand extends Command {
	public void execute(Session sess, String[] args) {
		if (args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		ServerSocket ss = sess.enterPassive();
		String msg = getCodeMsg(227);
		String[] ip = Config.get("PASSIVE_IP").split("\\.");
		System.out.println(ip.length);
		for (int i = 0; i < ip.length; i++)
			msg = msg.replace("h" + (i + 1), ip[i]);
		msg = msg.replace("p1", "" + ss.getLocalPort() / 256);
		msg = msg.replace("p2", "" + ss.getLocalPort() % 256);
		sess.getControl().sendMessage(msg);
	}
}
