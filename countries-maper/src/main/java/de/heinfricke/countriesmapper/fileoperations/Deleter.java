package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.heinfricke.countriesmapper.preparer.GroupOfCountries;
import de.heinfricke.countriesmapper.utils.UserInputs;
import de.heinfricke.countriesmapper.utils.DirectoriesActivity;

/**
 * This class has methods used for preparing files to deletion.
 * 
 * @author mateusz
 *
 */
public abstract class Deleter {

	/**
	 * User decision about destination of files.
	 */
	private UserInputs userInputs;

	/**
	 * This method is used for deleting files on local system, FTP server etc.
	 * 
	 * @param pathOfGorupDirectory
	 *            As parameter it takes path to files.
	 * @throws IOException
	 */
	abstract void deleteFiles(String pathOfGorupDirectory) throws IOException;

	/**
	 * This constructor is used because we need to know if user want to delete
	 * all files or only replace existing.
	 * 
	 * @param userInputs
	 */
	public Deleter(UserInputs userInputs) {
		this.userInputs = userInputs;
	}

	/**
	 * This method is used to delete directories. In body of this method we run
	 * other method which return user's decision about future of directories.
	 * User can delete all his directories, replace only existing groups of
	 * directories or don't delete any directories. As parameter this method
	 * needs List of GroupOfCountries objects so if user only need to replace
	 * old group's directories, it will be known which directories methods must
	 * to delete.
	 * 
	 * @param organizedCountries
	 *            List of GroupOfCountries objects.
	 * @param path
	 *            Path to directory where new directories will be created so
	 *            also here old ones will be deleted.
	 * @throws IOException
	 */
	public void deleteDirectories(List<GroupOfCountries> organizedCountries, String path)
			throws IOException, IllegalArgumentException {
		DirectoriesActivity userDecision = userInputs.userDecisionAboutDirectories();
		List<String> listOfThreeLettersGroups;

		listOfThreeLettersGroups = prepareThreeLettersGroups(organizedCountries, userDecision);

		if (userDecision.decisionAboutDeletingFiles(userDecision)) {
			for (String directoryToDelete : listOfThreeLettersGroups) {
				String pathOfGorupDirectory = (path + File.separator + directoryToDelete);

				deleteFiles(pathOfGorupDirectory);
			}
		}
	}

	/**
	 * This method prepare three letters groups.
	 * 
	 * @param organizedCountries
	 *            List of GroupOfCountries objects.
	 * @param userDecision
	 *            User decision about what to do with old files.
	 * @return This method return list of three letters groups.
	 */
	private List<String> prepareThreeLettersGroups(List<GroupOfCountries> organizedCountries,
			DirectoriesActivity userDecision) {
		List<String> listOfThreeLettersGroups = new ArrayList<String>();
		if (userDecision == DirectoriesActivity.DELETE) {
			GroupOfCountries groupOfCountries = new GroupOfCountries();
			listOfThreeLettersGroups = groupOfCountries.returnLettersGroups();
		} else if (userDecision == DirectoriesActivity.REPLACE) {
			for (GroupOfCountries groupedCountries : organizedCountries) {
				listOfThreeLettersGroups.add(groupedCountries.getName());
			}
		}
		return listOfThreeLettersGroups;
	}
}