package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.util.List;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;

/**
 * This class contains methods which are used to make new files and directories.
 * 
 * @author mateusz
 *
 */
public class FileMaker implements Maker {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.heinfricke.countriesmapper.fileoperations.Maker#createDirectories(java
	 * .util.List, java.lang.String)
	 */
	public void createDirectories(List<GroupOfCountries> listOfGroupedCountriesClasses, String path) {
		for (GroupOfCountries groupedCountries : listOfGroupedCountriesClasses) {
			String pathToGroupFolder = (path + File.separator + groupedCountries.getName());
			File groupFile = createFile(pathToGroupFolder);
			groupFile.mkdirs();
			for (Country countries : groupedCountries.getCountriesList()) {
				String pathToSingleFile = (pathToGroupFolder + File.separator + countries.getName());
				File singleFile = createFile(pathToSingleFile);
				singleFile.mkdirs();
			}
		}
	}

	protected File createFile(String pathToSingleFile) {
		return new File(pathToSingleFile);
	}
}
