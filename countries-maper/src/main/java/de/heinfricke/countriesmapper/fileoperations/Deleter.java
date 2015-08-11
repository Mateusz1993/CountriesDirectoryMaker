package de.heinfricke.countriesmapper.fileoperations;

import java.util.List;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;

/**
 * This interface contains methods for deleting files.
 * 
 * @author mateusz
 *
 */
public interface Deleter {
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
	void deleteDirectories(List<GroupOfCountries> organizedCountries, String path);
}
