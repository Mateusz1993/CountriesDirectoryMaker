import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.reader.CountriesReader;

public class OrganizeCountriesInGroupsTest {

	@Test
	public void test() {
		
		
		
		try {
			//Correct version.
			CountriesReader correctCountriesReader = new CountriesReader();
			Set<Country> correctSortedCountries;
			correctSortedCountries = correctCountriesReader.readCountries("/home/mateusz/test/CountriesList.txt");
			//GroupsPreparer correctGroupPreparer = new GroupsPreparer();
			//Map<String, List<Country>> correctGroupsOfCountries = correctGroupPreparer.organizeCountriesInGroups(correctSortedCountries);
			//End of correct version.
			//assertThat(correctGroupsOfCountries, is(correctGroupsOfCountries));
			
			
			
			//Countries started on numbers.
			
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
