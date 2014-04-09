package com.zephyrr.ftp.etc;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Config {
	private static HashMap<String, String> data;
	static {
		data = new HashMap<String, String>();
		readConfig();
	}

	private static void readConfig() {
		try {
			Scanner s = new Scanner(new File("config.dat"));
			while (s.hasNext()) {
				String line = s.nextLine();
				if (line.startsWith("//") || !line.contains("="))
					continue;
				data.put(line.substring(0, line.indexOf("=")).toUpperCase(),
						line.substring(line.indexOf("=") + 1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return data.get(key);
	}
}
