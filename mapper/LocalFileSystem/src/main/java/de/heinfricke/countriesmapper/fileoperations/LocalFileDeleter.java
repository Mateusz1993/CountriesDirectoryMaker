package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;

import de.heinfricke.countriesmapper.utils.DirectoriesActivity;
import de.heinfricke.countriesmapper.utils.UserInputs;

/**
 * This class has methods used for deleting local files.
 * 
 * @author mateusz
 *
 */
public class LocalFileDeleter extends Deleter {

	/**
	 * This constructor is used because we need to know if user want to delete
	 * all files or only replace existing.
	 * 
	 * @param userDecision
	 */
	public LocalFileDeleter(DirectoriesActivity userDecision) {
		super(userDecision);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.heinfricke.countriesmapper.fileoperations.Deleter#deleteFiles(java.
	 * lang.String)
	 */
	void deleteFiles(String pathOfGorupDirectory) {
		File tempfile = createFile(pathOfGorupDirectory);
		deleteDirectory(tempfile);
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

	protected File createFile(String pathOfGorupDirectory) {
		return new File(pathOfGorupDirectory);
	}
}
