import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import org.junit.Test;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;
import de.heinfricke.countriesmapper.reader.CountriesReader;

public class GroupOfCountriesTest {
	@Test
	public void test() {
		try {
			CountriesReader countriesReader = new CountriesReader();
			Set<Country> sortedCountries;
			//File with: Albania, Czech Republic, Argentina, Portugal, Poland, Denmark
			sortedCountries = countriesReader.readCountries("/home/mateusz/test/CountriesList.txt");

			Country countryForTest = new Country("123");
			sortedCountries.add(countryForTest);
			GroupOfCountries.organizeCountriesInGroups(sortedCountries);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
