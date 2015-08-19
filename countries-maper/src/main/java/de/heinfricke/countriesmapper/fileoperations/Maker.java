package de.heinfricke.countriesmapper.fileoperations;

import java.util.List;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;

/**
 * This interface contains methods for making files.
 * 
 * @author mateusz
 *
 */
public interface Maker {
	/**
	 * This method creates new 'groups' and 'country names' directories.
	 * 
	 * @param organizedCountries
	 *            This method takes as parameter Map where keys are names of
	 *            letter's group and values are Lists of Country objects.
	 * @param path
	 *            Second parameter is path to directory where new directories
	 *            will be created.
	 */
	public void createFiles(List<GroupOfCountries> organizedCountries, String path);
}
