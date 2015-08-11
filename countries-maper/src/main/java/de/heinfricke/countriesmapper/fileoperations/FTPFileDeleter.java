package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPFile;

import de.heinfricke.countriesmapper.CountriesDirectoryMake;
import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupsPreparer;
import de.heinfricke.countriesmapper.utils.FTPConnection;
import de.heinfricke.countriesmapper.utils.UserInputs;
import de.heinfricke.countriesmapper.utils.UserInputs.DirectoriesActivity;

/**
 * This class contains methods to delete files and directories.
 *
 * @author mateusz
 *
 */
public class FTPFileDeleter implements Deleter {
	private static final Logger LOGGER = Logger.getLogger(CountriesDirectoryMake.class.getCanonicalName());

	/* (non-Javadoc)
	 * @see de.heinfricke.countriesmapper.fileoperations.DeleterInterface#deleteDirectories(java.util.Map, java.lang.String)
	 */
	public void deleteDirectories(Map<String, List<Country>> organizedCountries, String path) {
		try {
			DirectoriesActivity userDecision = UserInputs.userDecisionAboutDirectories();
			List<String> listOfThreeLettersGroups = new ArrayList<String>();

			if (userDecision == DirectoriesActivity.DELETE) {
				listOfThreeLettersGroups = GroupsPreparer.returnLettersGroups();
			} else if (userDecision == DirectoriesActivity.REPLACE) {
				for (Map.Entry<String, List<Country>> set : organizedCountries.entrySet()) {
					listOfThreeLettersGroups.add(set.getKey());
				}
			}

			if ((userDecision == DirectoriesActivity.DELETE) || (userDecision == DirectoriesActivity.REPLACE)) {
				for (String directoryToDelete : listOfThreeLettersGroups) {
					String pathOfGorupDirectory = (path + File.separator + directoryToDelete);
					FTPFile[] files = FTPConnection.listDirectories(pathOfGorupDirectory);
					for (FTPFile file : files) {
						FTPConnection.removeDirectory(pathOfGorupDirectory + File.separator + file.getName());
					}
					FTPConnection.removeDirectory(pathOfGorupDirectory);
				}
			}
		} catch (IOException e) {
			System.out.println(
					"There was a problem while connecting to server. Please make sure given host, port, username and password are correct and run application again");
			LOGGER.log(Level.FINE, "There was a problem while connecting to server.", e);
			System.exit(0);
		}
	}
}