package de.heinfricke.countriesmapper.fileoperations;

import java.util.List;
import java.util.Map;

import de.heinfricke.countriesmapper.country.Country;

/**
 * This interface contains methods for deleting files.
 * 
 * @author mateusz
 *
 */
public interface DeleterInterface {
    /**
     * This method is used to delete directories. In body of this method we run
     * other method which return user's decision about future of directories.
     * User can delete all his directories, replace only existing groups of
     * directories or don't delete any directories. As parameter this method
     * needs Map of Country object organized in groups so if user only need to
     * replace old group's directories, it will be known which directories
     * methods must to delete.
     * 
     * @param organizedCountriesMap
     *            Map of Country objects organized in groups. Keys are names of
     *            groups and values are Lists of Country objects.
     * @param userPath
     *            Path to directory where new directories will be created so
     *            also here old ones will be deleted.
     */
    void deleteDirectories(Map<String, List<Country>> organizedCountriesMap, String userPath);
}
