package com.zephyrr.ftp.users;

import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class AccountManager {
	private HashMap<String, RegisteredUser> accounts;
	public AccountManager() {
		accounts = new HashMap<String, RegisteredUser>();
		readAccounts();
	}
	private void readAccounts() {
		try {
			Scanner s = new Scanner(new File("db/accounts.dat"));
			String line;
			while((line = s.nextLine()) != null) {
				// TODO: parse
			}
			s.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
