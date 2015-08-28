package de.heinfricke.countriesmapper.fileoperations;

import java.io.IOException;
import java.util.List;
import au.com.bytecode.opencsv.CSVWriter;
import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;

/**
 * This class contains methods which are used to prepare CSV files.
 * 
 * @author mateusz
 *
 */
public class CSVMaker {
	/**
	 * This method prepare read all informations about countries from Country
	 * objects.
	 * 
	 * @param listOfGroupedCountriesClasses
	 *            As first parameter it takes list of grouped Country objects.
	 * @param writer
	 *            As second parameter it takes CSVWriter object.
	 * @throws IOException
	 */
	 void prepareInformations(List<GroupOfCountries> listOfGroupedCountriesClasses, CSVWriter writer)
			throws IOException {
		String[] titles = new String[]{"Name:", "Capital:", "Native name:", "Borders:"};
		writer.writeNext(titles);
		for (GroupOfCountries groupedCountries : listOfGroupedCountriesClasses) {
			for (Country countries : groupedCountries.getCountriesList()) {
				writer.writeNext(countries.returnInfromations());
			}
		}
		writer.close();
	}
}
