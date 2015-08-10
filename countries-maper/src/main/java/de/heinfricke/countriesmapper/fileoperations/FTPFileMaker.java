package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.heinfricke.countriesmapper.CountriesDirectoryMake;
import de.heinfricke.countriesmapper.country.Country;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPFileMaker implements MakerInterface {
	private static final Logger LOGGER = Logger.getLogger(CountriesDirectoryMake.class.getCanonicalName());

	String host;
	int port;
	String username;
	String password;

	public FTPFileMaker(String host, String port, String username, String password) {
		int userPort = Integer.parseInt(port);
		this.host = host;
		this.port = userPort;
		this.username = username;
		this.password = password;
	}

	public void createDirectories(Map<String, List<Country>> organizedCountriesMap, String userPath) {
		FTPClient ftpClient = new FTPClient();
		try {
			Map<String, List<Country>> organizedCountries = organizedCountriesMap;
			String path = userPath;
			ftpClient.connect(host, port);

			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				System.out.println("It was some problem with connection. Please run application again.");
				return;
			}

			boolean success = ftpClient.login(username, password);
			if (!success) {
				System.out.println("Could not login to the server.");
				return;
			}

			for (Map.Entry<String, List<Country>> set : organizedCountries.entrySet()) {
				String groupDirectory = set.getKey();
				List<Country> listOfCountriesInEachGroup = set.getValue();

				String pathToGroupFolder = (path + File.separator + groupDirectory);
				ftpClient.makeDirectory(pathToGroupFolder);
				for (Country countries : listOfCountriesInEachGroup) {
					String pathToSingleFile = (pathToGroupFolder + File.separator + countries.getName());
					ftpClient.makeDirectory(pathToSingleFile);
				}
			}

			ftpClient.logout();
			ftpClient.disconnect();

		} catch (SocketException e) {
			System.out.println(
					"There was an error while creating or accessing a socket. Please make sure given host, port, username and password are correct and run application again");
			LOGGER.log(Level.FINE, "There was an error while creating or accesing a socket.", e);
		} catch (IOException e) {
			System.out.println(
					"There was a problem while connecting to server. Please make sure given host, port, username and password are correct and run application again");
			LOGGER.log(Level.FINE, "There was a problem while connecting to server.", e);
		}
	}
}