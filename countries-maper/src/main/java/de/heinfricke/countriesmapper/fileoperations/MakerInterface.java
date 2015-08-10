package de.heinfricke.countriesmapper.fileoperations;

import java.util.List;
import java.util.Map;

import de.heinfricke.countriesmapper.country.Country;

/**
 * This interface contains methods for making files.
 * 
 * @author mateusz
 *
 */
public interface MakerInterface {
    /**
     * This method creates new 'groups' and 'country names' directories.
     * 
     * @param organizedCountriesMap
     *            This method takes as parameter Map where keys are names of
     *            letter's group and values are Lists of Country objects.
     * @param userPath
     *            Second parameter is path to directory where new directories
     *            will be created.
     */
    public void createDirectories(Map<String, List<Country>> organizedCountriesMap, String userPath);
}
