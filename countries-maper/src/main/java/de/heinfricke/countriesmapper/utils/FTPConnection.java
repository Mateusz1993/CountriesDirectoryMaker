package de.heinfricke.countriesmapper.utils;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import de.heinfricke.countriesmapper.CountriesDirectoryMake;

/**
 * This class is used to make all tasks connected with FTP Server.
 * 
 * @author mateusz
 *
 */
public class FTPConnection {
	private static final Logger LOGGER = Logger.getLogger(CountriesDirectoryMake.class.getCanonicalName());

	/**
	 * FTP Server host.
	 */
	static String host;
	/**
	 * FTP server port.
	 */
	static int port;
	/**
	 * FTP user name.
	 */
	static String username;
	/**
	 * FTP user password.
	 */
	static String password;
	/**
	 * FPTClient object.
	 */
	static FTPClient client;

	/**
	 * This method just set all static variables.
	 * 
	 * @param userHost
	 *            FTP server host.
	 * @param userPort
	 *            FTP server port.
	 * @param userName
	 *            FTP user name.
	 * @param userPassword
	 *            FTP user password.
	 */
	public static void setAllVariables(String userHost, String userPort, String userName, String userPassword) {
		int usersPort = Integer.parseInt(userPort);
		host = userHost;
		port = usersPort;
		username = userName;
		password = userPassword;
		client = new FTPClient();
	}

	/**
	 * This method include method which set all variables and it connects and
	 * logins user to FTP server.
	 * 
	 * @param userHost
	 *            FTP server host.
	 * @param userPort
	 *            FTP server port.
	 * @param userName
	 *            FTP user name.
	 * @param userPassword
	 *            FTP user password.
	 */
	public static void makeConnection(String userHost, String userPort, String userName, String userPassword) {
		try {

			setAllVariables(userHost, userPort, userName, userPassword);

			client.connect(host, port);

			int replyCode = client.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				System.out.println("It was some problem with connection. Please run application again.");
			}

			boolean success = client.login(username, password);
			if (!success) {
				System.out.println("Could not login to the server.");
				client.logout();
				client.disconnect();
				System.exit(0);
			}
		} catch (SocketException e) {
			System.out.println(
					"There was an error while creating or accessing a socket. Please make sure given host, port, username and password are correct and run application again");
			LOGGER.log(Level.FINE, "There was an error while creating or accesing a socket.", e);
			System.exit(0);
		} catch (IOException e) {
			System.out.println(
					"There was a problem while connecting to server. Please make sure given host, port, username and password are correct and run application again");
			LOGGER.log(Level.FINE, "There was a problem while connecting to server.", e);
			System.exit(0);
		}
	}

	/**
	 * This method makes new directories.
	 * 
	 * @param pathToDirectory
	 *            Path where new directory must to be created.
	 * @throws IOException
	 */
	public static void makeDirectory(String pathToDirectory) throws IOException {
		client.makeDirectory(pathToDirectory);
	}

	/**
	 * This method removes old directories.
	 * 
	 * @param pathToDirectory
	 *            Path to directory which must be deleted.
	 * @throws IOException
	 */
	public static void removeDirectory(String pathToDirectory) throws IOException {
		client.removeDirectory(pathToDirectory);
	}

	/**
	 * This method returns list of directories which include directory given as
	 * pathToDirectory parameter.
	 * 
	 * @param pathToDirectory
	 *            Path to directory where we want to check the contents.
	 * @return It returns array of FTOFiles.
	 * @throws IOException
	 */
	public static FTPFile[] listDirectories(String pathToDirectory) throws IOException {
		FTPFile[] files = client.listDirectories(pathToDirectory);
		return files;
	}

	/**
	 * This method logout and disconnects user from FTP server.
	 * 
	 * @throws IOException
	 */
	public static void makeDisconnection() throws IOException {
		client.logout();
		client.disconnect();
	}
}