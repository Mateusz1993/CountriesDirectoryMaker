package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.heinfricke.countriesmapper.CountriesDirectoryMake;
import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.utils.FTPConnection;

/**
 * This class contains methods to create files and directories.
 * 
 * @author mateusz
 *
 */
public class FTPFileMaker implements Maker {
	private static final Logger LOGGER = Logger.getLogger(CountriesDirectoryMake.class.getCanonicalName());

	/* (non-Javadoc)
	 * @see de.heinfricke.countriesmapper.fileoperations.MakerInterface#createDirectories(java.util.Map, java.lang.String)
	 */
	public void createDirectories(Map<String, List<Country>> organizedCountries, String path) {
		try {
			for (Map.Entry<String, List<Country>> set : organizedCountries.entrySet()) {
				String groupDirectory = set.getKey();
				List<Country> listOfCountriesInEachGroup = set.getValue();

				String pathToGroupFolder = (path + File.separator + groupDirectory);
				FTPConnection.makeDirectory(pathToGroupFolder);
				for (Country countries : listOfCountriesInEachGroup) {
					String pathToSingleFile = (pathToGroupFolder + File.separator + countries.getName());
					FTPConnection.makeDirectory(pathToSingleFile);
				}
			}
		} catch (IOException e) {
			System.out.println(
					"There was a problem while connecting to server. Please make sure given host, port, username and password are correct and run application again");
			LOGGER.log(Level.FINE, "There was a problem while connecting to server.", e);
		}
	}
}