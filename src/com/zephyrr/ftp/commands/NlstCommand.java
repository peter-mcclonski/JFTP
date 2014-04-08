package com.zephyrr.ftp.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.zephyrr.ftp.io.FileManager;
import com.zephyrr.ftp.main.Session;

public class NlstCommand extends Command {
	public void execute(final Session sess, final String[] args) {
		if(args.length != 1) {
			sess.getControl().sendMessage(getCodeMsg(501));
			return;
		}
		String path = sess.getWorkingDirectory() + args[0];
		if(!FileManager.getFile(path).isDirectory()) {
			sess.getControl().sendMessage(getCodeMsg(450));
			return;
		}
		String[] rawContents = FileManager.getFile(path).list();
		ArrayList<String> contents = new ArrayList<String>();
		for(String s : rawContents) {
			// TODO: Add additional file data.
			contents.add(s);
		}

		Collections.sort(contents, new Comparator<String>() {
			public int compare(String s1, String s2) {
				String s1Path = sess.getWorkingDirectory() + "/" + args[0] + "/" + s1;
				String s2Path = sess.getWorkingDirectory() + "/" + args[0] + "/" + s2;
				System.out.println(s1Path);
				if(FileManager.getFile(s1Path).isDirectory() &&
					!FileManager.getFile(s2Path).isDirectory())
					return -1;
				if((FileManager.getFile(s1Path).isDirectory() && FileManager.getFile(s2Path).isDirectory()) || (FileManager.getFile(s1Path).isFile() && FileManager.getFile(s2Path).isFile()))
					return s1.compareTo(s2);
				return 1;
			}
		});

		if(sess.getData() == null) {
			sess.getControl().sendMessage(getCodeMsg(425));
			return;
		}
		sess.getControl().sendMessage(getCodeMsg(125));
		for(String s : contents)
			sess.getData().sendMessage(s);
		sess.getControl().sendMessage(getCodeMsg(226));
		sess.closeDataConnection();
	}
}
