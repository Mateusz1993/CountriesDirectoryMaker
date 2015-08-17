package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.heinfricke.countriesmapper.CountriesDirectoryMake;
import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;
import de.heinfricke.countriesmapper.utils.FTPConnection;

/**
 * This class contains methods to create files and directories.
 * 
 * @author mateusz
 *
 */
public class FTPFileMaker implements Maker {
	private static final Logger LOGGER = Logger.getLogger(CountriesDirectoryMake.class.getCanonicalName());

	FTPConnection ftpConnection;
		
	public FTPFileMaker(FTPConnection ftpConnection){
		this.ftpConnection = ftpConnection;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.heinfricke.countriesmapper.fileoperations.Maker#createDirectories(java
	 * .util.List, java.lang.String)
	 */
	public void createDirectories(List<GroupOfCountries> listOfGroupedCountriesClasses, String path) {
		try {
			for (GroupOfCountries groupedCountries : listOfGroupedCountriesClasses) {
				String pathToGroupFolder = (path + File.separator + groupedCountries.getName());
				createDirectory(pathToGroupFolder);
				for (Country countries : groupedCountries.getCountriesList()) {
					String pathToSingleFile = (pathToGroupFolder + File.separator + countries.getName());
					createDirectory(pathToSingleFile);
				}
			}
		} catch (IOException e) {
			System.out.println(
					"There was a problem while connecting to server. Please make sure given host, port, username and password are correct and run application again");
			LOGGER.log(Level.FINE, "There was a problem while connecting to server.", e);
		}
	}

	/**
	 * This method create new directory on FTP Server.
	 * 
	 * @param pathToSingleFile
	 * @throws IOException
	 */
	private void createDirectory(String pathToSingleFile) throws IOException {
		ftpConnection.makeDirectory(pathToSingleFile);
	}
}