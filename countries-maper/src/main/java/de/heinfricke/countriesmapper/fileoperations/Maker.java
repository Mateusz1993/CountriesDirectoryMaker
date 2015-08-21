package de.heinfricke.countriesmapper.fileoperations;

import java.io.IOException;
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
	 * @throws IOException
	 */
	public void createFiles(List<GroupOfCountries> organizedCountries, String path) throws IOException;

	/**
	 * This method creates CSV Files.
	 * 
	 * @param listOfGroupedCountriesClasses
	 *            As first parameter it takes list of grouped Countries objects.
	 * @param path
	 *            As second parameter it takes path to output directory, where
	 *            CSV file will be created.
	 * @param csvMaker
	 *            As third parameter it takes CSVMaker object.
	 * @throws IOException
	 */
	public void createCSVFile(List<GroupOfCountries> listOfGroupedCountriesClasses, String path, CSVMaker csvMaker)
			throws IOException;
}