package com.zephyrr.ftp.users;

import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class AccountManager {
	private static HashMap<String, RegisteredUser> accounts;
	static {
		accounts = new HashMap<String, RegisteredUser>();
		readAccounts();
	}
	private static void readAccounts() {
		try {
			Scanner s = new Scanner(new File("db/accounts.dat"));
			while(s.hasNext()) {
				String line = s.nextLine();
				if(line.startsWith("//"))
					continue;
				String[] data = line.split(" ");
				accounts.put(data[0], new RegisteredUser(data[0], data[1], new String[]{data[2], data[3]}));
			}
			s.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean isUser(String name) {
		return accounts.containsKey(name);
	}
	public static RegisteredUser getUser(String name) {
		return accounts.get(name);
	}
}
