package com.zephyrr.ftp.commands;

import com.zephyrr.ftp.main.FTPConnection;

public abstract class Command {
	private String code110() {
		// TODO
	}
	private String code120() {
		// TODO
	}
	private String code211() {
		// TODO
	}
	private String code212() {
		// TODO
	}
	private String code213() {
		// TODO
	}
	private String code214() {
		// TODO
	}
	private String code227() {
		// TODO
	}
	private String code257() {
		// TODO
	}
	public String getCodeMsg(int code) {
		switch(code) {
		case 110: return code110();	break;
		case 120: return code120();	break;
		case 125: return "125 Data connection already open; transfer starting.";	break;
		case 150: return "150 File status okay; about to open data connection.";	break;
		case 200: return "200 Command okay.";	break;
		case 202: return "202 Command not implemented, superfluous at this site.";	break;
		case 211: return code211();	break;
		case 212: return code212();	break;
		case 213: return code213();	break;
		case 214: return code214();	break;
		case 215: return "UNIX Type: L8";	break;
		case 220: return "220 Service ready for new user.";	break;
		case 221: return "221 Service closing control connection.\nLogged out if appropriate.";	break;
		case 225: return "225 Data connection open; no transfer in progress.";	break;
		case 226: return "226 Closing data connection.\nRequested file action successful.";	break;
		case 227: return code227();	break;
		case 230: return "230 User logged in, proceed.";	break;
		case 250: return "250 Requested file action okay, completed.";	break;
		case 257: return code257();	break;
		case 331: return "331 User name okay, need password.";	break;
		case 332: return "332 Need account for login.";	break;
		case 350: return "350 Requested file action pending further information.";	break;
		case 421: return "421 Service not available, closing control connection.";	break;
		case 425: return "425 Can't open data connection.";	break;
		case 426: return "426 Connection closed; transfer aborted.";	break;
		case 450: return "450 Requested file action not taken.\nFile unavailable.";	break;
		case 451: return "451 Requested action aborted: local error in processing.";	break;
		case 452: return "452 Requested action not taken.\nInsufficient storage space in system.";	break;
		case 500: return "500 Syntax error, command unrecognized.";	break;
		case 501: return "501 Syntax error in parameters or arguments.";	break;
		case 502: return "502 Command not implemented.";	break;
		case 503: return "503 Bad sequence of commands.";	break;
		case 504: return "504 Command not implemented for that parameter.";	break;
		case 530: return "530 Not logged in.";	break;
		case 532: return "532 Need account for storing files.";	break;
		case 550: return "550 Requested action not taken.\nFile unavailable.";	break;
		case 551: return "551 Requested action aborted: page type unknown.";	break;
		case 552: return "552 Requested file action aborted.\nExceeded storage allocation.";	break;
		case 553: return "553 Requested action not taken.\nFile name not allowed.";	break;
		}
	}

	public abstract void execute(FTPConnection ftpc, String[] args);
}
