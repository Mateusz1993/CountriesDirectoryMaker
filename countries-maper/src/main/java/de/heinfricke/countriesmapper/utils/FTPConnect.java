package de.heinfricke.countriesmapper.utils;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import de.heinfricke.countriesmapper.CountriesDirectoryMake;

/**
 * This class is used to connect to FTP Server.
 * 
 * @author mateusz
 *
 */
public class FTPConnect {
	private static final Logger LOGGER = Logger.getLogger(CountriesDirectoryMake.class.getCanonicalName());

	/**
	 * FTP server host.
	 */
	static String host;
	/**
	 * FTP server port.
	 */
	static int port;
	/**
	 * FTP server user name.
	 */
	static String username;
	/**
	 * FTP server user password.
	 */
	static String password;

	/**
	 * This constructor is used to declare all static variables.
	 * 
	 * @param host
	 *            FTP server host.
	 * @param port
	 *            FTP server port.
	 * @param username
	 *            FTP server user name.
	 * @param password
	 *            FTP user password.
	 */
	public FTPConnect(String host, String port, String username, String password) {
		int userPort = Integer.parseInt(port);
		FTPConnect.host = host;
		FTPConnect.port = userPort;
		FTPConnect.username = username;
		FTPConnect.password = password;
	}

	/**
	 * This method connect to FTP Server and also login user to FTP Server.
	 * 
	 * @return This method return FTPClient object.
	 */
	public static FTPClient connect() {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host, port);

			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				System.out.println("It was some problem with connection. Please run application again.");
			}

			boolean success = ftpClient.login(username, password);
			if (!success) {
				System.out.println("Could not login to the server.");
				ftpClient.logout();
				ftpClient.disconnect();
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
		return ftpClient;
	}
}
