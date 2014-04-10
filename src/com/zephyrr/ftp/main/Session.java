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

/*
 * The workhorse of the server.  A session maintains control over
 * interpreting incoming messages, distributing commands to their
 * executors, and just generally being the core of the FTP system.
 * Each Session runs in its own thread.
 *
 * @author Peter Jablonski
 */

public class Session implements Runnable {
	// Wrappers for handling connections
	private volatile FTPConnection control, data;
	// The user logged into this session
	private User user;
	// Whether this session is active
	private boolean active;
	// The most recently executed command
	private CommandList last;
	// Current working directory
	private String dir;
	// Current transmission type
	private TransmissionType type;
	// The spawning FTP instance
	private FTP parent;

	public Session(Socket sock, FTP parent) {
		// Set defaults
		this.parent = parent;
		try {
			// Create the control connection
			control = new FTPConnection(sock, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		type = TransmissionType.ASCII;
		dir = "/";
		active = true;
		data = null;
		user = new User();
		// And start the run thread
		new Thread(this).start();
	}

	public void run() {
		// Initial welcome message
		control.sendMessage("220 Service ready for new user.");
		// Keep reading new commands until we drop
		String line;
		while (active && (line = control.getMessage()) != null) {
			// Log every command we receive, along with 
			// the date, time, and username.
			Logger.getInstance().enqueue(new Date() + "\t\t" + user.getName() + "\t\t" + line + "\n");
			// Trim off spaces
			line = line.trim();
			// Isolate the command
			String cmdString = line.split(" ")[0];
			// Get rid of the actual command and instead have
			// only the arguments.
			String[] args = line.replaceFirst(cmdString, "").trim().split(" ");
			try {
				// Get the associated CommandList entry
				CommandList cl = CommandList.valueOf(cmdString.toUpperCase());
				// If you aren't logged in, you can only use
				// login related commands.
				if (!user.isAuthorized()
						&& !(cl == CommandList.USER || cl == CommandList.PASS)) {
					getControl().sendMessage("530 Not logged in.");
					continue;
				}
				// Execute the command
				cl.getCommand().execute(this, args);
				// And set the most recent command.
				last = CommandList.valueOf(cmdString.toUpperCase());
			} catch (IllegalArgumentException e) {
				// If something's wrong, then the command
				// must not be implemented.
				control.sendMessage("502 Command not implemented");
			}
		}
		// If we're out of the loop, close shop.
		if (control != null)
			control.close();
		if (data != null)
			data.close();
		parent.removeSession(this);
	}

	// Log out a user, resetting everything related to them.
	public void logout() {
		user = new User();
		closeDataConnection();
	}

	// Returns the last issued command
	public CommandList getLastCommand() {
		return last;
	}

	// Returns the currently connected user
	public User getUser() {
		return user;
	}

	// Sets the data connection.  Just used for thread conflict
	// avoidance.
	private synchronized void setData(FTPConnection ftpc) {
		data = ftpc;
	}

	// Establishes a connection in active mode (PORT command)
	public void enterActive(final String remote, final int port) {
		// If we've got an existing data connection, get rid of it.
		closeDataConnection();
		// Making this session object available to our inner class
		final Session obj = this;
		// For the sake of efficiency, we'll delegate to a separate
		// thread for establishing the connection.
		new Thread(new Runnable() {
			public void run() {
				try {
					// Make the connection to the given
					// address.
					Socket sock = new Socket(remote, port);
					// And mark our data connection
					setData(new FTPConnection(sock, obj));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	// Establishes the data connection in passive (PASV) mode
	public ServerSocket enterPassive() {
		// If we've got a data connection already, close it.
		closeDataConnection();
		// Make a few things available to the inner class
		final ServerSocket ss;
		final Session obj = this;
		try {
			// Automatically allocate a port
			ss = new ServerSocket(0);
			// And for the sake of efficiency, establish the 
			// connection in a separate thread.
			new Thread(new Runnable() {
				public void run() {
					try {
						// Mark the new data connection
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

	// Close an active data connection
	public void closeDataConnection() {
		if (data != null)
			data.close();
		data = null;
	}

	// Reset a user
	public void resetLogin() {
		user = new User();
	}

	// Retrieves the control connection
	public FTPConnection getControl() {
		return control;
	}

	// Retrieves the data connection
	public FTPConnection getData() {
		return data;
	}

	// Sets whether this session is enabled
	public void setActive(boolean b) {
		active = b;
	}

	// Retrieves the current working directory
	public String getWorkingDirectory() {
		return dir;
	}

	// Changes the current working directory, making sure there are
	// no issues with separaters
	public void setWorkingDirectory(String s) {
		dir = (s + "/").replaceAll("//", "/").replaceAll("//", "/");
	}

	// Retrieves the current transmission type
	public TransmissionType getType() {
		return type;
	}

	// Sets the current transmission type
	public void setType(TransmissionType t) {
		type = t;
	}
}
