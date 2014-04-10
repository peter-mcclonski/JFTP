package com.zephyrr.ftp.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import com.zephyrr.ftp.etc.Config;
import com.zephyrr.ftp.users.User;

/*
 * FileManager class, responsible for all file related operations
 * other than logging.
 *
 * @author Peter Jablonski
 */

public class FileManager {
	// Tracks which files are locked (in use) by each user
	private static HashMap<File, User> locks;
	// Holds readers, since it may take multiple calls to 
	// fully read a given file.
	private static HashMap<File, FileInputStream> readers;
	// Holds writers, since it may take multiple calls to
	// fully write a given file.
	private static HashMap<File, FileOutputStream> writers;
	// Tracks each user's most recent rename attempt.  
	// Specifically, this map holds the original file name
	// specified in a user's RNFR command.
	private static HashMap<User, String> renames;
	// The server's HOMEDIR (constant across users)
	private static String HOMEDIR = Config.get("HOMEDIR");
	static {
		// Initialize everything real quick
		locks = new HashMap<File, User>();
		renames = new HashMap<User, String>();
		readers = new HashMap<File, FileInputStream>();
		writers = new HashMap<File, FileOutputStream>();
	}

	// Returns the full system path to a file.
	private static String getTruePath(String rel) {
		return (HOMEDIR + rel).replaceAll("//", "/");
	}

	// Writes data to a file
	public static boolean write(String abstractPath, byte[] msg, User u,
			boolean append) {
		// Gets the file to write to
		File out = getFile(abstractPath);
		try {
			// And gets the parent directory
			File parent = out.getParentFile();
			// If necessary, all parent directories are created
			if (!parent.exists())
				parent.mkdirs();
			// Create the file if it doesn't exist
			out.createNewFile();
			// Lock the file, if it is unlocked
			if (!locks.containsKey(out))
				locks.put(out, u);
			// If it's already locked by someone else, or
			// someone is trying to read the file, back off
			else if (!locks.get(out).equals(u) || readers.containsKey(out))
				return false;
			// If we don't have a writer yet, make one
			if (!writers.containsKey(out))
				writers.put(out, new FileOutputStream(out, append));
			// And write our data
			writers.get(out).write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	// Clean up method after a write
	public static boolean closeWriter(String abstractPath, User u) {
		// Get the file
		File out = getFile(abstractPath);
		// If the file is in use by someone else, you have 
		// no business trying to kick them out.
		if (!locks.get(out).equals(u))
			return false;
		try {
			// Close the writer
			writers.get(out).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Remove the writer
		writers.remove(out);
		// Remove the lock
		locks.remove(out);
		return true;
	}

	// Read some portion of a file.
	public static ReadResult read(String abstractPath, int count, User u) {
		// Since we have a bunch of different things we could
		// be returning, we'll stick it in our handy ReadResult.
		ReadResult rr = new ReadResult();
		// Get the file
		File f = getFile(abstractPath);
		// If the file is unlocked, lock it.
		if (!locks.containsKey(f))
			locks.put(f, u);
		// If it's in use, mark that, and return.
		else if (!locks.get(f).equals(u) || writers.containsKey(f)) {
			rr.setLocked(true);
			return rr;
		}
		try {
			// Generate a new reader, if necessary
			if (!readers.containsKey(f))
				readers.put(f, new FileInputStream(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Put in a new byte array to hold our data
		rr.setData(new byte[count]);
		int result;
		try {
			// And read into that array
			result = readers.get(f).read(rr.getData());
		} catch (IOException e) {
			// If there was an issue, set result to -1
			result = -1;
			e.printStackTrace();
		}
		// The only case where result wouldn't equal count is
		// if we were to finish reading the file.
		if (result != count) {
			// So we mark it as finished and clean up
			rr.setFinished(true);
			locks.remove(f);
			readers.remove(f);
		}
		// The array may not be full, so we'll mark how much we
		// actually managed to read.
		rr.setTrueLength(result);
		// And return
		return rr;
	}

	// Mark that a user wants to rename a certain file
	public static void queueRename(String abstractPath, User u) {
		renames.put(u, abstractPath);
	}

	// Throw out a user's prior rename request
	public static String dequeueRename(User u) {
		if (renames.containsKey(u))
			return renames.get(u);
		return null;
	}

	// Actually perform a rename
	public static void rename(String oldName, String newName) {
		getFile(oldName).renameTo(getFile(newName));
	}

	// Checks if a given file is locked
	public static boolean isLocked(String abstractPath) {
		return locks.containsKey(getFile(abstractPath));
	}

	// Locks a file
	public static void lock(String abstractPath, User u) {
		if (!isLocked(abstractPath))
			locks.put(getFile(abstractPath), u);
	}

	// Unlocks a file
	public static void unlock(String abstractPath) {
		if (isLocked(abstractPath))
			locks.remove(getFile(abstractPath));
	}

	// Retrieves the file associated with an abstract path
	public static File getFile(String abstractPath) {
		return new File(getTruePath(abstractPath));
	}

	// Converts an absolute path to a relative path within the
	// data directory.
	public static String convertPath(String path) {
		return ("/" + (path + "//").substring(HOMEDIR.length()) + "/").replaceAll("//", "/");
	}
}
