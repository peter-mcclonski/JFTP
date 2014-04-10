package com.zephyrr.ftp.users;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import com.zephyrr.ftp.etc.Config;

/*
 * Simple lookup system for user accounts.
 *
 * @author Peter Jablonski
 */

public class AccountManager {
	// Holds registered accounts, loaded from the accounts file
	private static HashMap<String, RegisteredUser> accounts;
	static {
		// Initialize
		accounts = new HashMap<String, RegisteredUser>();
		// And read
		readAccounts();
	}

	// Reads accounts from the account file
	private static void readAccounts() {
		try {
			// Open a reader on the configured account file
			Scanner s = new Scanner(new File(Config.get("USERFILE")));
			// Read every line
			while (s.hasNext()) {
				String line = s.nextLine();
				// Ignoring commented lines
				if (line.startsWith("//"))
					continue;
				// Split on the space
				String[] data = line.split(" ");
				// See the included accounts.dat for an
				// explanation of parameters.
				accounts.put(data[0], new RegisteredUser(data[0], data[1],
						new String[] { data[2], data[3] }));
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Checks if the given username exists
	public static boolean isUser(String name) {
		return accounts.containsKey(name);
	}

	// Retrieves the registereduser instance associated with
	// a username.
	public static RegisteredUser getUser(String name) {
		return accounts.get(name);
	}
}
