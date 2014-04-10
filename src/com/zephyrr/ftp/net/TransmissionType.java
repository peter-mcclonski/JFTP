package com.zephyrr.ftp.net;

/*
 * A simple enum for tracking transmission types.
 * Currently only ASCII and IMAGE are supported.
 *
 * @author Peter Jablonski
 */

public enum TransmissionType {
	ASCII, 
	IMAGE, 
	EBCDIC, 
	LOCAL;
}
