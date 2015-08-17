package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

import de.heinfricke.countriesmapper.preparer.GroupOfCountries;
import de.heinfricke.countriesmapper.utils.FTPConnection;
import de.heinfricke.countriesmapper.utils.UserInputs;
import de.heinfricke.countriesmapper.utils.UserInputs.DirectoriesActivity;

public class FileDeleter {

	private UserInputs userInputs;
	private FilesLocalization filesLocalization;
	private FTPConnection ftpConnection;

	public FileDeleter(UserInputs userInputs) {
		this.userInputs = userInputs;
		this.filesLocalization = FilesLocalization.LOCAL;
	}

	public FileDeleter(FTPConnection ftpConnection, UserInputs userInputs) {
		this.userInputs = userInputs;
		this.filesLocalization = FilesLocalization.FTPSERVER;
		this.ftpConnection = ftpConnection;
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
	 */
	public void deleteDirectories(List<GroupOfCountries> organizedCountries, String path) throws IOException {
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

				if (filesLocalization == FilesLocalization.FTPSERVER) {
					FTPFile[] files = ftpConnection.listDirectories(pathOfGorupDirectory);
					for (FTPFile file : files) {
						ftpConnection.removeDirectory(pathOfGorupDirectory + File.separator + file.getName());
					}
					ftpConnection.removeDirectory(pathOfGorupDirectory);
				}

				if (filesLocalization == FilesLocalization.LOCAL) {
					File tempfile = createFile(pathOfGorupDirectory);
					deleteDirectory(tempfile);
				}
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

	public enum FilesLocalization {
		LOCAL, FTPSERVER;
	}
}