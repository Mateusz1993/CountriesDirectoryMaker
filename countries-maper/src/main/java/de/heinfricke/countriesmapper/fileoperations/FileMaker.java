package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.util.List;
import java.util.Map;

import de.heinfricke.countriesmapper.country.Country;

/**
 * This class contains methods which are used to make new files and directories.
 * 
 * @author mateusz
 *
 */
public class FileMaker implements Maker {

	/* (non-Javadoc)
	 * @see de.heinfricke.countriesmapper.fileoperations.MakerInterface#createDirectories(java.util.Map, java.lang.String)
	 */
    public void createDirectories(Map<String, List<Country>> organizedCountries, String path) {
        for (Map.Entry<String, List<Country>> set : organizedCountries.entrySet()) {
            String groupDirectory = set.getKey();
            List<Country> listOfCountriesInEachGroup = set.getValue();

            String pathToGroupFolder = (path + File.separator + groupDirectory);
            File groupFile = new File(pathToGroupFolder);
            groupFile.mkdirs();
            for (Country countries : listOfCountriesInEachGroup) {
                String pathToSingleFile = (pathToGroupFolder + File.separator + countries.getName());
                File singleFile = new File(pathToSingleFile);
                singleFile.mkdirs();
            }
        }
    }
}
