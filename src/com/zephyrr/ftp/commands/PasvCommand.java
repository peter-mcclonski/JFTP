package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import java.net.ServerSocket;

public class PasvCommand extends Command {
	public void execute(Session sess, String[] args) {
		System.out.println(":" + args[0] + ":");
		if(args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		ServerSocket ss = sess.enterPassive();
		String msg = getCodeMsg(227);
		//String[] ip = ss.getInetAddress().getHostAddress().split(".");
		String[] ip = new String[] {"127", "0", "0", "1"};
		System.out.println(ss.getInetAddress().getHostAddress());
		for(int i = 0; i < ip.length; i++) 
			msg = msg.replace("h" + (i + 1), ip[i]);
		System.out.println(ss.getLocalPort());
		msg = msg.replace("p1", "" + ss.getLocalPort() / 256);
		msg = msg.replace("p2", "" + ss.getLocalPort() % 256);
		sess.getControl().sendMessage(msg);
	}
}
