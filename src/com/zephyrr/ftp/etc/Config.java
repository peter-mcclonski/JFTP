package com.zephyrr.ftp.etc;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/*
 * An accessor responsible for handling config information.  All
 * configurable data/settings are accessed through here.
 *
 * @author Peter Jablonski
 */

public class Config {
	// Map holding config info
	private static HashMap<String, String> data;
	// The relative path to the file holding config data.
	private static String CONFIGFILE = "config.dat";
	static {
		// Initialize the map
		data = new HashMap<String, String>();
		// And load the data
		readConfig();
	}

	private static void readConfig() {
		try {
			Scanner s = new Scanner(new File(CONFIGFILE));
			// Read every line in the file
			while (s.hasNext()) {
				String line = s.nextLine();
				// We'll ignore any line not containing '=',
				// or any line starting with '//'
				if (line.startsWith("//") || !line.contains("="))
					continue;
				// We use substring to divide on the first '=',
				// because there could be more than one '=' in
				// the string.  What comes before the '=' is 
				// our key, and what comes after is the value.
				data.put(line.substring(0, line.indexOf("=")).toUpperCase(),
						line.substring(line.indexOf("=") + 1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Returns the requested config value.
	public static String get(String key) {
		return data.get(key);
	}
}
