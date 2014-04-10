JFTP
====

Author: Peter Jablonski

Compiling:
The included build.sh should work (sh build.sh).  It has been tested
only on Fedora 20.  It works by generating a list of all source files,
then executing javac on that list and deleting the list file.  

Running:
The server can be started with a simple java command:
	java com.zephyrr.ftp.main.FTP
Note that the file config.dat must be present in the executing directory.

Config:
You must properly set the values in config.dat for your system.  The HOMEDIR
attribute is particularly important.

Accounts:
The accounts.dat file included should provide a fairly clear understanding
of how to create accounts.  Note that any account not in here, and any
account not marked as enabled, will not be able to log in or issue commands.

Features:
- Fully functional user account and permission system
- Logging of commands
- Multithreading for supporting multiple users
- Almost the entirety of RFC 959

Known caveats:
- A few command specific deficiencies, noted in their source files
- The data connection runs in the same thread as the control connection
- Some particularly irksome commands and settings are not implemented
- For some reason, a zero-argument command still has an argument array length of 1

Unknown situations:
This code has been developed and tested solely on Fedora 20, using the system comand line FTP client.  No guarantees are made as to how it will perform on other systems or with different clients.  It SHOULD work, because it follows the RFC, but there are no guarantees at present.

Further reading:
For more information on any setting or command, please check RFC 959
http://tools.ietf.org/html/rfc959

Notes:
I think this warrants some insane extra credit.  I also think that having helped a whole bunch of people warrants even more.



Have fun grading.  There are about 1650 lines of code, not including comments.
