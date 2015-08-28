package de.heinfricke.countriesmapper.reader;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

import org.codehaus.jettison.json.JSONException;
import de.heinfricke.countriesmapper.country.*;
import de.heinfricke.countriesmapper.preparer.InformationPreparer;

/**
 * This class contains methods which are used to read files's contents.
 * 
 * @author mateusz
 *
 */
public class CountriesReader {
	/**
	 * This method reads list of countries from file, makes new 'Country'
	 * objects where each one contains name of country and returns these objects
	 * as Set.
	 * 
	 * @param path
	 *            Path to file with list of countries.
	 * @return Set of 'Country' objects.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws JSONException
	 */
	public Set<Country> readCountries(String path, boolean prepareInfromationFile)
			throws FileNotFoundException, IOException, JSONException, RuntimeException {
		System.out.println("\nYour path to .txt file is: " + path);

		Set<Country> namesOfCountries = new TreeSet<Country>();
		String line;

		if (prepareInfromationFile) {
			System.out.println("Loading...");
		}

		BufferedReader bufferedReader = null;
		try {
			InputStream in = new FileInputStream(path);
			bufferedReader = new BufferedReader(new InputStreamReader(in));
			
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.isEmpty()) {
					Country newCountry;
					if (prepareInfromationFile) {
						InformationPreparer informationPreparer = new InformationPreparer();
						newCountry = informationPreparer.collectCountryData(line);
					} else {
						newCountry = new Country(line);
					}
					namesOfCountries.add(newCountry);
				}
			}
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		return namesOfCountries;
	}
}