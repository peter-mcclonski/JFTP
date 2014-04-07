package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.TransmissionType;

public class StorCommand extends Command {
	public void execute(Session sess, String[] args) {
		if(args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if(sess.getData() == null) {
			sess.getControl().sendMessage(getCodeMsg(425));
			return;
		}
		if(!FileManager.write(sess.getWorkingDirectory() + args[0], new byte[0], sess.getUser())) {
			sess.getControl().sendMessage(getCodeMsg(550));
			sess.getData().close();
			return;
		}
		sess.getControl().sendMessage(getCodeMsg(125));
		byte[] bytes = null;
		switch(sess.getType()) {
			case ASCII:	String msg = "", line;
					while((line = sess.getData().getMessage()) != null)
						msg += line + "\n";
					bytes = msg.getBytes();
					break;
			case IMAGE:	bytes = sess.getData().getMessageBytes();	break;
		}
		FileManager.write(sess.getWorkingDirectory() + args[0], bytes, sess.getUser());
		FileManager.closeWriter(sess.getWorkingDirectory() + args[0], sess.getUser());
		sess.getControl().sendMessage(getCodeMsg(226));
		sess.getData().close();
	}
}
