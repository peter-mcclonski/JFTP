package com.zephyrr.ftp.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;

import com.zephyrr.ftp.commands.CommandList;
import com.zephyrr.ftp.net.FTPConnection;
import com.zephyrr.ftp.net.TransmissionType;
import com.zephyrr.ftp.users.User;
import com.zephyrr.ftp.etc.Logger;

public class Session implements Runnable {
	private volatile FTPConnection control, data;
	private User user;
	private boolean active;
	private CommandList last;
	private String dir;
	private TransmissionType type;
	private FTP parent;

	public Session(Socket sock, FTP parent) {
		this.parent = parent;
		try {
			control = new FTPConnection(sock, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		type = TransmissionType.ASCII;
		dir = "/";
		active = true;
		data = null;
		user = new User();
		new Thread(this).start();
	}

	public void run() {
		control.sendMessage("220 Service ready for new user.");
		String line;
		while (active && (line = control.getMessage()) != null) {
			Logger.getInstance().enqueue(new Date() + "\t\t" + user.getName() + "\t\t" + line + "\n");
			line = line.trim();
			System.out.println(line);
			String cmdString = line.split(" ")[0];
			String[] args = line.replaceFirst(cmdString, "").trim().split(" ");
			System.out.println(Arrays.toString(args));
			try {
				CommandList cl = CommandList.valueOf(cmdString.toUpperCase());
				if (!user.isAuthorized()
						&& !(cl == CommandList.USER || cl == CommandList.PASS)) {
					getControl().sendMessage("530 Not logged in.");
					continue;
				}
				cl.getCommand().execute(this, args);
				last = CommandList.valueOf(cmdString.toUpperCase());
			} catch (IllegalArgumentException e) {
				control.sendMessage("502 Command not implemented");
			}
		}
		if (control != null)
			control.close();
		if (data != null)
			data.close();
		parent.removeSession(this);
	}

	public void logout() {
		user = new User();
		closeDataConnection();
	}

	public CommandList getLastCommand() {
		return last;
	}

	public User getUser() {
		return user;
	}

	private synchronized void setData(FTPConnection ftpc) {
		data = ftpc;
	}

	public void enterActive(final String remote, final int port) {
		final Session obj = this;
		new Thread(new Runnable() {
			public void run() {
				try {
					Socket sock = new Socket(remote, port);
					setData(new FTPConnection(sock, obj));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public ServerSocket enterPassive() {
		closeDataConnection();
		final ServerSocket ss;
		final Session obj = this;
		try {
			ss = new ServerSocket(0);
			new Thread(new Runnable() {
				public void run() {
					try {
						setData(new FTPConnection(ss.accept(), obj));
						ss.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return ss;
	}

	public void closeDataConnection() {
		if (data != null)
			data.close();
		data = null;
	}

	public void resetLogin() {
		user = new User();
	}

	public FTPConnection getControl() {
		return control;
	}

	public FTPConnection getData() {
		return data;
	}

	public void setActive(boolean b) {
		active = b;
	}

	public String getWorkingDirectory() {
		return dir;
	}

	public void setWorkingDirectory(String s) {
		dir = (s + "/").replaceAll("//", "/").replaceAll("//", "/");
		System.out.println("Directory: " + dir);
	}

	public TransmissionType getType() {
		return type;
	}

	public void setType(TransmissionType t) {
		type = t;
	}
}
