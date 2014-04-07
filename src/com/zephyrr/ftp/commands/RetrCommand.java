package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;
import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.io.ReadResult;
import com.zephyrr.ftp.main.TransmissionType;

public class RetrCommand extends Command {
	public void execute(Session sess, String[] args) {
		if(args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		if(sess.getData() == null) {
			sess.getControl().sendMessage(getCodeMsg(425));
			return;
		}
		if(!FileManager.getFile(sess.getWorkingDirectory() + args[0]).exists()) {
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		ReadResult rr = FileManager.read(sess.getWorkingDirectory() + args[0], 0, sess.getUser());
		if(rr.isLocked()) {
			sess.getControl().sendMessage(getCodeMsg(550));
			return;
		}
		sess.getControl().sendMessage(getCodeMsg(125));
		do {
			rr = FileManager.read(sess.getWorkingDirectory() + args[0], 512, sess.getUser());
			switch(sess.getType()) {
				case ASCII:	sess.getData().sendMessage(new String(rr.getData()).substring(0, rr.getTrueLength()));	break;
				case IMAGE:	sess.getData().sendPartialMessageBytes(rr.getData(), 0, rr.getTrueLength());	break;
			}
		} while(!rr.isFinished());
		sess.getControl().sendMessage(getCodeMsg(226));
		sess.getData().close();
	}
}
