package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.Session;

/*
 * Every command executor extends this class.  It provides easy access to
 * all implemented reply codes, with the requirement of implementing the
 * execute(...) method.
 *
 * @author Peter Jablonski
 */

public abstract class Command {
	// Not implemented
	private String code110() {
		// TODO
		return "";
	}
	
	// Not implemented
	private String code120() {
		// TODO
		return "";
	}

	// Not implemented
	private String code211() {
		// TODO
		return "";
	}

	// Not implemented
	private String code212() {
		// TODO
		return "";
	}

	// Not implemented
	private String code213() {
		// TODO
		return "";
	}

	// Not implemented
	private String code214() {
		// TODO
		return "";
	}

	// Lookup function for getting the message associated with a
	// given reply code number.
	public String getCodeMsg(int code) {
		switch (code) {
		case 110:
			return code110();
		case 120:
			return code120();
		case 125:
			return "125 Data connection already open; transfer starting.";
		case 150:
			return "150 File status okay; about to open data connection.";
		case 200:
			return "200 Command okay.";
		case 202:
			return "202 Command not implemented, superfluous at this site.";
		case 211:
			return code211();
		case 212:
			return code212();
		case 213:
			return code213();
		case 214:
			return code214();
		case 215:
			return "UNIX Type: L8";
		case 220:
			return "220 Service ready for new user.";
		case 221:
			return "221 Service closing control connection.  Logged out.";
		case 225:
			return "225 Data connection open; no transfer in progress.";
		case 226:
			return "226 Closing data connection.  Requested file action successful.";
		case 227:
			return "227 Entering Passive Mode (h1,h2,h3,h4,p1,p2)";
		case 230:
			return "230 User logged in, proceed.";
		case 250:
			return "250 Requested file action okay, completed.";
		case 257:
			return "257 \"$PATHNAME\" created.";
		case 331:
			return "331 User name okay, need password.";
		case 332:
			return "332 Need account for login.";
		case 350:
			return "350 Requested file action pending further information.";
		case 421:
			return "421 Service not available, closing control connection.";
		case 425:
			return "425 Can't open data connection.";
		case 426:
			return "426 Connection closed; transfer aborted.";
		case 450:
			return "450 Requested file action not taken.  File unavailable.";
		case 451:
			return "451 Requested action aborted: local error in processing.";
		case 452:
			return "452 Requested action not taken.  Insufficient storage space in system.";
		case 500:
			return "500 Syntax error, command unrecognized.";
		case 501:
			return "501 Syntax error in parameters or arguments.";
		case 502:
			return "502 Command not implemented.";
		case 503:
			return "503 Bad sequence of commands.";
		case 504:
			return "504 Command not implemented for that parameter.";
		case 530:
			return "530 Bad login.";
		case 532:
			return "532 Need account for storing files.";
		case 550:
			return "550 Requested action not taken.  File unavailable.";
		case 551:
			return "551 Requested action aborted: page type unknown.";
		case 552:
			return "552 Requested file action aborted.  Exceeded storage allocation.";
		case 553:
			return "553 Requested action not taken.  File name not allowed.";
		}
		return "";
	}

	// The method responsible for executing a given command.
	public abstract void execute(Session sess, String[] args);
}
