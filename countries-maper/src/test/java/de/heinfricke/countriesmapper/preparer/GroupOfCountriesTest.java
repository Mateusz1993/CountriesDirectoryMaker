package de.heinfricke.countriesmapper.preparer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;

public class GroupOfCountriesTest {
	@Test
	public void test() {
		GroupOfCountries groupOfCountries = new GroupOfCountries();
		Set<Country> countries = new TreeSet<Country>();

		countries.add(new Country("Albania"));
		countries.add(new Country("Czech Republic"));
		countries.add(new Country("Argentina"));
		countries.add(new Country("Portugal"));
		countries.add(new Country("Poland"));
		countries.add(new Country("Denmark"));

		List<GroupOfCountries> groups = groupOfCountries.organizeCountriesInGroups(countries);
		assertThat(groups.size(), is(3));
		assertThat(groups.get(0), is(notNullValue()));
		assertThat(groups.get(0).getName(), is("ABC"));
		assertThat(groups.get(0).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().size(), is(3));
		assertThat(groups.get(0).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(0).getName(), is("Albania"));
		assertThat(groups.get(0).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(1).getName(), is("Argentina"));
		assertThat(groups.get(0).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(2).getName(), is("Czech Republic"));

		assertThat(groups.get(1), is(notNullValue()));
		assertThat(groups.get(1).getName(), is("DEF"));
		assertThat(groups.get(1).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().size(), is(1));
		assertThat(groups.get(1).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().get(0).getName(), is("Denmark"));

		assertThat(groups.get(2), is(notNullValue()));
		assertThat(groups.get(2).getName(), is("PQR"));
		assertThat(groups.get(2).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().size(), is(2));
		assertThat(groups.get(2).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().get(0).getName(), is("Poland"));
		assertThat(groups.get(2).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().get(1).getName(), is("Portugal"));
	}

	@Test
	public void testWithSingleCountries() {
		GroupOfCountries groupOfCountries = new GroupOfCountries();
		Set<Country> countries = new TreeSet<Country>();
		// Latvia Somalia Sudan Lithuania Algeria Honduras Iran Vietnam

		countries.add(new Country("Latvia"));
		countries.add(new Country("Somalia"));
		countries.add(new Country("Sudan"));
		countries.add(new Country("Lithuania"));
		countries.add(new Country("Algeria"));
		countries.add(new Country("Honduras"));
		countries.add(new Country("Iran"));
		countries.add(new Country("Vietnam"));

		List<GroupOfCountries> groups = groupOfCountries.organizeCountriesInGroups(countries);
		assertThat(groups.size(), is(5));

		assertThat(groups.get(0), is(notNullValue()));
		assertThat(groups.get(0).getName(), is("ABC"));
		assertThat(groups.get(0).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().size(), is(1));
		assertThat(groups.get(0).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(0).getName(), is("Algeria"));

		assertThat(groups.get(1), is(notNullValue()));
		assertThat(groups.get(1).getName(), is("GHI"));
		assertThat(groups.get(1).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().size(), is(2));
		assertThat(groups.get(1).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().get(0).getName(), is("Honduras"));
		assertThat(groups.get(1).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().get(1).getName(), is("Iran"));

		assertThat(groups.get(2), is(notNullValue()));
		assertThat(groups.get(2).getName(), is("JKL"));
		assertThat(groups.get(2).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().size(), is(2));
		assertThat(groups.get(2).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().get(0).getName(), is("Latvia"));
		assertThat(groups.get(2).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().get(1).getName(), is("Lithuania"));

		assertThat(groups.get(3), is(notNullValue()));
		assertThat(groups.get(3).getName(), is("STU"));
		assertThat(groups.get(3).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().size(), is(2));
		assertThat(groups.get(3).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().get(0).getName(), is("Somalia"));
		assertThat(groups.get(3).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().get(1).getName(), is("Sudan"));

		assertThat(groups.get(4), is(notNullValue()));
		assertThat(groups.get(4).getName(), is("VWX"));
		assertThat(groups.get(4).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().size(), is(1));
		assertThat(groups.get(4).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().get(0).getName(), is("Vietnam"));
	}

	@Test
	public void multipleCountriesTest() {
		GroupOfCountries groupOfCountries = new GroupOfCountries();
		Set<Country> countries = new TreeSet<Country>();
		// Bostwana x2, Cuba x5, Senegal x1, Uganda x1, Kenya x7, Switzerland
		// x1, Finland x3, Iraq x1, Austria x2

		countries.add(new Country("Kenya"));
		countries.add(new Country("Bostwana"));
		countries.add(new Country("Cuba"));
		countries.add(new Country("Kenya"));
		countries.add(new Country("Finland"));
		countries.add(new Country("Senegal"));
		countries.add(new Country("Cuba"));
		countries.add(new Country("Austria"));
		countries.add(new Country("Finland"));
		countries.add(new Country("Uganda"));
		countries.add(new Country("Kenya"));
		countries.add(new Country("Kenya"));
		countries.add(new Country("Bostwana"));
		countries.add(new Country("Kenya"));
		countries.add(new Country("Cuba"));
		countries.add(new Country("Cuba"));
		countries.add(new Country("Switzerland"));
		countries.add(new Country("Finland"));
		countries.add(new Country("Kenya"));
		countries.add(new Country("Cuba"));
		countries.add(new Country("Kenya"));
		countries.add(new Country("Iraq"));
		countries.add(new Country("Austria"));

		List<GroupOfCountries> groups = groupOfCountries.organizeCountriesInGroups(countries);
		assertThat(groups.size(), is(5));

		assertThat(groups.get(0), is(notNullValue()));
		assertThat(groups.get(0).getName(), is("ABC"));
		assertThat(groups.get(0).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().size(), is(3));
		assertThat(groups.get(0).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(0).getName(), is("Austria"));
		assertThat(groups.get(0).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(1).getName(), is("Bostwana"));
		assertThat(groups.get(0).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(2).getName(), is("Cuba"));

		assertThat(groups.get(1), is(notNullValue()));
		assertThat(groups.get(1).getName(), is("DEF"));
		assertThat(groups.get(1).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().size(), is(1));
		assertThat(groups.get(1).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().get(0).getName(), is("Finland"));

		assertThat(groups.get(2), is(notNullValue()));
		assertThat(groups.get(2).getName(), is("GHI"));
		assertThat(groups.get(2).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().size(), is(1));
		assertThat(groups.get(2).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().get(0).getName(), is("Iraq"));

		assertThat(groups.get(3), is(notNullValue()));
		assertThat(groups.get(3).getName(), is("JKL"));
		assertThat(groups.get(3).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().size(), is(1));
		assertThat(groups.get(3).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().get(0).getName(), is("Kenya"));

		assertThat(groups.get(4), is(notNullValue()));
		assertThat(groups.get(4).getName(), is("STU"));
		assertThat(groups.get(4).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().size(), is(3));
		assertThat(groups.get(4).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().get(0).getName(), is("Senegal"));
		assertThat(groups.get(4).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().get(1).getName(), is("Switzerland"));
		assertThat(groups.get(4).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().get(2).getName(), is("Uganda"));
	}

	@Test
	public void countriesFromEachGroup() {
		GroupOfCountries groupOfCountries = new GroupOfCountries();
		Set<Country> countries = new TreeSet<Country>();

		// Afghanistan, Albania, Chile, Egypt, Gabon, Indonesia, Japan, Kenay,
		// Madagascar, Oman, Poland, Qatar, Samoa, San Marino, Serbia, Togo,
		// Vietnam, Yemen
		countries.add(new Country("Afghanistan"));
		countries.add(new Country("Chile"));
		countries.add(new Country("Indonesia"));
		countries.add(new Country("Gabon"));
		countries.add(new Country("Egypt"));
		countries.add(new Country("Madagascar"));
		countries.add(new Country("Kenya"));
		countries.add(new Country("Oman"));
		countries.add(new Country("Qatar"));
		countries.add(new Country("Yemen"));
		countries.add(new Country("Vietnam"));
		countries.add(new Country("Samoa"));
		countries.add(new Country("San Marino"));
		countries.add(new Country("Albania"));
		countries.add(new Country("Poland"));
		countries.add(new Country("Togo"));
		countries.add(new Country("Japan"));
		countries.add(new Country("Serbia"));

		List<GroupOfCountries> groups = groupOfCountries.organizeCountriesInGroups(countries);
		assertThat(groups.size(), is(9));

		assertThat(groups.get(0), is(notNullValue()));
		assertThat(groups.get(0).getName(), is("ABC"));
		assertThat(groups.get(0).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().size(), is(3));
		assertThat(groups.get(0).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(0).getName(), is("Afghanistan"));
		assertThat(groups.get(0).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(1).getName(), is("Albania"));
		assertThat(groups.get(0).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(2).getName(), is("Chile"));

		assertThat(groups.get(1), is(notNullValue()));
		assertThat(groups.get(1).getName(), is("DEF"));
		assertThat(groups.get(1).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().size(), is(1));
		assertThat(groups.get(1).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().get(0).getName(), is("Egypt"));

		assertThat(groups.get(2), is(notNullValue()));
		assertThat(groups.get(2).getName(), is("GHI"));
		assertThat(groups.get(2).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().size(), is(2));
		assertThat(groups.get(2).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().get(0).getName(), is("Gabon"));
		assertThat(groups.get(2).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().get(1).getName(), is("Indonesia"));

		assertThat(groups.get(3), is(notNullValue()));
		assertThat(groups.get(3).getName(), is("JKL"));
		assertThat(groups.get(3).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().size(), is(2));
		assertThat(groups.get(3).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().get(0).getName(), is("Japan"));
		assertThat(groups.get(3).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().get(1).getName(), is("Kenya"));

		assertThat(groups.get(4), is(notNullValue()));
		assertThat(groups.get(4).getName(), is("MNO"));
		assertThat(groups.get(4).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().size(), is(2));
		assertThat(groups.get(4).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().get(0).getName(), is("Madagascar"));
		assertThat(groups.get(4).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().get(1).getName(), is("Oman"));

		assertThat(groups.get(5), is(notNullValue()));
		assertThat(groups.get(5).getName(), is("PQR"));
		assertThat(groups.get(5).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(5).getCountriesList().size(), is(2));
		assertThat(groups.get(5).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(5).getCountriesList().get(0).getName(), is("Poland"));
		assertThat(groups.get(5).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(5).getCountriesList().get(1).getName(), is("Qatar"));

		assertThat(groups.get(6), is(notNullValue()));
		assertThat(groups.get(6).getName(), is("STU"));
		assertThat(groups.get(6).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(6).getCountriesList().size(), is(4));
		assertThat(groups.get(6).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(6).getCountriesList().get(0).getName(), is("Samoa"));
		assertThat(groups.get(6).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(6).getCountriesList().get(1).getName(), is("San Marino"));
		assertThat(groups.get(6).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(6).getCountriesList().get(2).getName(), is("Serbia"));
		assertThat(groups.get(6).getCountriesList().get(3), is(notNullValue()));
		assertThat(groups.get(6).getCountriesList().get(3).getName(), is("Togo"));

		assertThat(groups.get(7), is(notNullValue()));
		assertThat(groups.get(7).getName(), is("VWX"));
		assertThat(groups.get(7).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(7).getCountriesList().size(), is(1));
		assertThat(groups.get(7).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(7).getCountriesList().get(0).getName(), is("Vietnam"));

		assertThat(groups.get(8), is(notNullValue()));
		assertThat(groups.get(8).getName(), is("YZ"));
		assertThat(groups.get(8).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(8).getCountriesList().size(), is(1));
		assertThat(groups.get(8).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(8).getCountriesList().get(0).getName(), is("Yemen"));
	}

	@Test
	public void addCountriesAndRandomThings() {
		GroupOfCountries groupOfCountries = new GroupOfCountries();
		Set<Country> countries = new TreeSet<Country>();

		// Brazil, Costa Rica, Columbia, Croata, France, Germany, Portugal,
		// Russia, Spain, Wales, 6.03.1902, " ", 1-24, 92:48, \n, " ", 2*4=8
		countries.add(new Country("6.03.1902"));
		countries.add(new Country("Costa Rica"));
		countries.add(new Country("1"));
		countries.add(new Country("France"));
		countries.add(new Country("2"));
		countries.add(new Country("Portugal"));
		countries.add(new Country("3"));
		countries.add(new Country("Spain"));
		countries.add(new Country("92:48"));
		countries.add(new Country("4"));
		countries.add(new Country("Portugal"));
		countries.add(new Country("5"));
		countries.add(new Country("Spain"));
		countries.add(new Country("6"));
		countries.add(new Country("Portugal"));
		countries.add(new Country("7"));
		countries.add(new Country("\n"));
		countries.add(new Country("Germany"));
		countries.add(new Country("8"));
		countries.add(new Country("France"));
		countries.add(new Country("9"));
		countries.add(new Country("Columbia"));
		countries.add(new Country("10"));
		countries.add(new Country("Wales"));
		countries.add(new Country("11"));
		countries.add(new Country("Brazil"));
		countries.add(new Country("12"));
		countries.add(new Country("     "));
		countries.add(new Country("Spain"));
		countries.add(new Country("13"));
		countries.add(new Country("Brazil"));
		countries.add(new Country("14"));
		countries.add(new Country("Spain"));
		countries.add(new Country("15"));
		countries.add(new Country("Brazil"));
		countries.add(new Country("16"));
		countries.add(new Country("Spain"));
		countries.add(new Country("17"));
		countries.add(new Country("Spain"));
		countries.add(new Country("18"));
		countries.add(new Country("Croatia"));
		countries.add(new Country("19"));
		countries.add(new Country("Spain"));
		countries.add(new Country("20"));
		countries.add(new Country("Russia"));
		countries.add(new Country("21"));
		countries.add(new Country("Spain"));
		countries.add(new Country("22"));
		countries.add(new Country("Brazil"));
		countries.add(new Country("23"));
		countries.add(new Country("Spain"));
		countries.add(new Country("24"));
		countries.add(new Country("2*4=8"));

		List<GroupOfCountries> groups = groupOfCountries.organizeCountriesInGroups(countries);
		assertThat(groups.size(), is(6));

		assertThat(groups.get(0), is(notNullValue()));
		assertThat(groups.get(0).getName(), is("ABC"));
		assertThat(groups.get(0).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().size(), is(4));
		assertThat(groups.get(0).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(0).getName(), is("Brazil"));
		assertThat(groups.get(0).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(1).getName(), is("Columbia"));
		assertThat(groups.get(0).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(2).getName(), is("Costa Rica"));
		assertThat(groups.get(0).getCountriesList().get(3), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(3).getName(), is("Croatia"));

		assertThat(groups.get(1), is(notNullValue()));
		assertThat(groups.get(1).getName(), is("DEF"));
		assertThat(groups.get(1).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().size(), is(1));
		assertThat(groups.get(1).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().get(0).getName(), is("France"));

		assertThat(groups.get(2), is(notNullValue()));
		assertThat(groups.get(2).getName(), is("GHI"));
		assertThat(groups.get(2).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().size(), is(1));
		assertThat(groups.get(2).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().get(0).getName(), is("Germany"));

		assertThat(groups.get(3), is(notNullValue()));
		assertThat(groups.get(3).getName(), is("PQR"));
		assertThat(groups.get(3).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().size(), is(2));
		assertThat(groups.get(3).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().get(0).getName(), is("Portugal"));
		assertThat(groups.get(3).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().get(1).getName(), is("Russia"));

		assertThat(groups.get(4), is(notNullValue()));
		assertThat(groups.get(4).getName(), is("STU"));
		assertThat(groups.get(4).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().size(), is(1));
		assertThat(groups.get(4).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().get(0).getName(), is("Spain"));

		assertThat(groups.get(5), is(notNullValue()));
		assertThat(groups.get(5).getName(), is("VWX"));
		assertThat(groups.get(5).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(5).getCountriesList().size(), is(1));
		assertThat(groups.get(5).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(5).getCountriesList().get(0).getName(), is("Wales"));
	}

	@Test
	public void countryOnEachLetter() {
		GroupOfCountries groupOfCountries = new GroupOfCountries();
		Set<Country> countries = new TreeSet<Country>();

		// Albania, Austria, Belgium, Chile, Denmark, Estonia, Finland, Gabon,
		// Honduras, Istrael, Japan, Kenya, Liechtenstein, Monaco, Nepal, Oman,
		// Poland, Portugal, Qatar, Romania, Slovakia, Switzerland, Togo,
		// Uzbekistan, Vatican, Venezuela, Wales, Yemen, Zambia
		countries.add(new Country("Albania"));
		countries.add(new Country("Finland"));
		countries.add(new Country("Switzerland"));
		countries.add(new Country("Togo"));
		countries.add(new Country("Wales"));
		countries.add(new Country("Yemen"));
		countries.add(new Country("Kenya"));
		countries.add(new Country("Liechtenstein"));
		countries.add(new Country("Austria"));
		countries.add(new Country("Nepal"));
		countries.add(new Country("Monaco"));
		countries.add(new Country("Qatar"));
		countries.add(new Country("Vatican"));
		countries.add(new Country("Venezuela"));
		countries.add(new Country("Israel"));
		countries.add(new Country("Slovakia"));
		countries.add(new Country("Zambia"));
		countries.add(new Country("Romania"));
		countries.add(new Country("Uzbekistan"));
		countries.add(new Country("Poland"));
		countries.add(new Country("Japan"));
		countries.add(new Country("Portugal"));
		countries.add(new Country("Honduras"));
		countries.add(new Country("Gabon"));
		countries.add(new Country("Estonia"));
		countries.add(new Country("Denmark"));
		countries.add(new Country("Chile"));
		countries.add(new Country("Belgium"));
		countries.add(new Country("Oman"));
		countries.add(new Country("Uruguay"));

		List<GroupOfCountries> groups = groupOfCountries.organizeCountriesInGroups(countries);
		assertThat(groups.size(), is(9));

		assertThat(groups.get(0), is(notNullValue()));
		assertThat(groups.get(0).getName(), is("ABC"));
		assertThat(groups.get(0).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().size(), is(4));
		assertThat(groups.get(0).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(0).getName(), is("Albania"));
		assertThat(groups.get(0).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(1).getName(), is("Austria"));
		assertThat(groups.get(0).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(2).getName(), is("Belgium"));
		assertThat(groups.get(0).getCountriesList().get(3), is(notNullValue()));
		assertThat(groups.get(0).getCountriesList().get(3).getName(), is("Chile"));

		assertThat(groups.get(1), is(notNullValue()));
		assertThat(groups.get(1).getName(), is("DEF"));
		assertThat(groups.get(1).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().size(), is(3));
		assertThat(groups.get(1).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().get(0).getName(), is("Denmark"));
		assertThat(groups.get(1).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().get(1).getName(), is("Estonia"));
		assertThat(groups.get(1).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(1).getCountriesList().get(2).getName(), is("Finland"));

		assertThat(groups.get(2), is(notNullValue()));
		assertThat(groups.get(2).getName(), is("GHI"));
		assertThat(groups.get(2).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().size(), is(3));
		assertThat(groups.get(2).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().get(0).getName(), is("Gabon"));
		assertThat(groups.get(2).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().get(1).getName(), is("Honduras"));
		assertThat(groups.get(2).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(2).getCountriesList().get(2).getName(), is("Israel"));

		assertThat(groups.get(3), is(notNullValue()));
		assertThat(groups.get(3).getName(), is("JKL"));
		assertThat(groups.get(3).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().size(), is(3));
		assertThat(groups.get(3).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().get(0).getName(), is("Japan"));
		assertThat(groups.get(3).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().get(1).getName(), is("Kenya"));
		assertThat(groups.get(3).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(3).getCountriesList().get(2).getName(), is("Liechtenstein"));

		assertThat(groups.get(4), is(notNullValue()));
		assertThat(groups.get(4).getName(), is("MNO"));
		assertThat(groups.get(4).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().size(), is(3));
		assertThat(groups.get(4).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().get(0).getName(), is("Monaco"));
		assertThat(groups.get(4).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().get(1).getName(), is("Nepal"));
		assertThat(groups.get(4).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(4).getCountriesList().get(2).getName(), is("Oman"));

		assertThat(groups.get(5), is(notNullValue()));
		assertThat(groups.get(5).getName(), is("PQR"));
		assertThat(groups.get(5).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(5).getCountriesList().size(), is(4));
		assertThat(groups.get(5).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(5).getCountriesList().get(0).getName(), is("Poland"));
		assertThat(groups.get(5).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(5).getCountriesList().get(1).getName(), is("Portugal"));
		assertThat(groups.get(5).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(5).getCountriesList().get(2).getName(), is("Qatar"));
		assertThat(groups.get(5).getCountriesList().get(3), is(notNullValue()));
		assertThat(groups.get(5).getCountriesList().get(3).getName(), is("Romania"));

		assertThat(groups.get(6), is(notNullValue()));
		assertThat(groups.get(6).getName(), is("STU"));
		assertThat(groups.get(6).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(6).getCountriesList().size(), is(5));
		assertThat(groups.get(6).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(6).getCountriesList().get(0).getName(), is("Slovakia"));
		assertThat(groups.get(6).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(6).getCountriesList().get(1).getName(), is("Switzerland"));
		assertThat(groups.get(6).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(6).getCountriesList().get(2).getName(), is("Togo"));
		assertThat(groups.get(6).getCountriesList().get(3), is(notNullValue()));
		assertThat(groups.get(6).getCountriesList().get(3).getName(), is("Uruguay"));
		assertThat(groups.get(6).getCountriesList().get(4), is(notNullValue()));
		assertThat(groups.get(6).getCountriesList().get(4).getName(), is("Uzbekistan"));

		assertThat(groups.get(7), is(notNullValue()));
		assertThat(groups.get(7).getName(), is("VWX"));
		assertThat(groups.get(7).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(7).getCountriesList().size(), is(3));
		assertThat(groups.get(7).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(7).getCountriesList().get(0).getName(), is("Vatican"));
		assertThat(groups.get(7).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(7).getCountriesList().get(1).getName(), is("Venezuela"));
		assertThat(groups.get(7).getCountriesList().get(2), is(notNullValue()));
		assertThat(groups.get(7).getCountriesList().get(2).getName(), is("Wales"));

		assertThat(groups.get(8), is(notNullValue()));
		assertThat(groups.get(8).getName(), is("YZ"));
		assertThat(groups.get(8).getCountriesList(), is(notNullValue()));
		assertThat(groups.get(8).getCountriesList().size(), is(2));
		assertThat(groups.get(8).getCountriesList().get(0), is(notNullValue()));
		assertThat(groups.get(8).getCountriesList().get(0).getName(), is("Yemen"));
		assertThat(groups.get(8).getCountriesList().get(1), is(notNullValue()));
		assertThat(groups.get(8).getCountriesList().get(1).getName(), is("Zambia"));
	}
}