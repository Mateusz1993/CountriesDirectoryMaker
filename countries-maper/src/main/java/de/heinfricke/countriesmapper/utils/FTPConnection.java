package de.heinfricke.countriesmapper.utils;

import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * This class is used to make all tasks connected with FTP Server.
 * 
 * @author mateusz
 *
 */
public class FTPConnection {
	/**
	 * FTP Server host.
	 */
	String host;
	/**
	 * FTP server port.
	 */
	int port;
	/**
	 * FTP user name.
	 */
	String username;
	/**
	 * FTP user password.
	 */
	String password;
	/**
	 * FPTClient object.
	 */
	FTPClient client;

	/**
	 * This method just set all variables.
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
	public void setAllVariables(String userHost, String userPort, String userName, String userPassword) {
		int usersPort = Integer.parseInt(userPort);
		this.host = userHost;
		this.port = usersPort;
		this.username = userName;
		this.password = userPassword;
		this.client = new FTPClient();
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
	 * @throws IOException
	 * @throws SocketException
	 */
	public void makeConnection(String userHost, String userPort, String userName, String userPassword)
			throws SocketException, IOException {
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
		}
	}

	/**
	 * This method makes new directories.
	 * 
	 * @param pathToDirectory
	 *            Path where new directory must to be created.
	 * @throws IOException
	 */
	public void makeDirectory(String pathToDirectory) throws IOException {
		client.makeDirectory(pathToDirectory);
	}

	/**
	 * This method removes old directories.
	 * 
	 * @param pathToDirectory
	 *            Path to directory which must be deleted.
	 * @throws IOException
	 */
	public void removeDirectory(String pathToDirectory) throws IOException {
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
	public FTPFile[] listDirectories(String pathToDirectory) throws IOException {
		return client.listDirectories(pathToDirectory);
	}

	/**
	 * This method logout and disconnects user from FTP server.
	 * 
	 * @throws IOException
	 */
	public void makeDisconnection() throws IOException {
		client.logout();
		client.disconnect();
	}

	public FTPClient getClient() {
		return client;
	}
}