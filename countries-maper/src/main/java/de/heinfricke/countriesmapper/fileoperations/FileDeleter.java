package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.heinfricke.countriesmapper.preparer.GroupOfCountries;
import de.heinfricke.countriesmapper.utils.UserInputs;
import de.heinfricke.countriesmapper.utils.UserInputs.DirectoriesActivity;

/**
 * This class contains methods which are used to delete files and directories.
 * 
 * @author mateusz
 *
 */
public class FileDeleter implements Deleter {

	private UserInputs userInputs;
	
	public FileDeleter(UserInputs userInputs) {
		this.userInputs = userInputs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.heinfricke.countriesmapper.fileoperations.Deleter#deleteDirectories(
	 * java.util.List, java.lang.String)
	 */
	public void deleteDirectories(List<GroupOfCountries> organizedCountries, String path) {
		DirectoriesActivity userDecision = userInputs.userDecisionAboutDirectories();
		List<String> listOfThreeLettersGroups = new ArrayList<String>();

		if (userDecision == DirectoriesActivity.DELETE) {
			GroupOfCountries groupOfCountries = new GroupOfCountries();
			listOfThreeLettersGroups = groupOfCountries.returnLettersGroups();
		} else if (userDecision == DirectoriesActivity.REPLACE) {
			for (GroupOfCountries groupedCountries : organizedCountries) {
				listOfThreeLettersGroups.add(groupedCountries.getName());
			}
		}

		if ((userDecision == DirectoriesActivity.DELETE) || (userDecision == DirectoriesActivity.REPLACE)) {
			for (String directoryToDelete : listOfThreeLettersGroups) {
				String pathOfGorupDirectory = (path + File.separator + directoryToDelete);
				File tempfile = createFile(pathOfGorupDirectory);
				deleteDirectory(tempfile);
			}
		}
	}

	protected File createFile(String pathOfGorupDirectory) {
		return new File(pathOfGorupDirectory);
	}

	/**
	 * This method delete main directory and all included directories.
	 * 
	 * @param dir
	 *            As parameter it takes File object.
	 * @return It returns dir.delete().
	 */
	private boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(children[i]);
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}