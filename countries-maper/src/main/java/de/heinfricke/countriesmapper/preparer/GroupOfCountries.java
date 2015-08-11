package de.heinfricke.countriesmapper.preparer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.utils.StringUtils;

/**
 * @author mateusz
 *
 */
public class GroupOfCountries {
	/**
	 * 
	 */
	private static final int NUMBER_OF_GROUPED_CHARACTERS = 3;

	/**
	 * 
	 */
	String name;
	/**
	 * 
	 */
	List<Country> countriesList;

	/**
	 * @param name
	 * @param countriesList
	 */
	public GroupOfCountries(String name, List<Country> countriesList) {
		this.name = name;
		this.countriesList = countriesList;
	}

	public String getName() {
		return name;
	}

	public List<Country> getCountriesList() {
		return countriesList;
	}

	/**
	 * @return
	 */
	public static List<String> returnLettersGroups() {
		List<String> lettersGroups = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (char character = 'A'; character <= 'Z'; character++) {
			if ((character - 'A') % NUMBER_OF_GROUPED_CHARACTERS == 0 && character != 'A') {
				lettersGroups.add(sb.toString());
				sb.delete(0, sb.length());
			}
			sb.append(character);
		}
		if (sb.length() > 0) {
			lettersGroups.add(sb.toString());
		}
		return lettersGroups;
	}

	/**
	 * @param namesOfCountries
	 * @return
	 */
	public static List<GroupOfCountries> organizeCountriesInGroups(Set<Country> namesOfCountries) {
		List<String> lettersGroups = returnLettersGroups();
		List<ArrayList<Country>> listOfListsOfCountries = new ArrayList<ArrayList<Country>>();
		List<GroupOfCountries> listOfGroupedCountriesClasses = new ArrayList<GroupOfCountries>();

		createEmptyListForEachGroup(lettersGroups, listOfListsOfCountries);
		addCountryToListOfLists(namesOfCountries, lettersGroups, listOfListsOfCountries);
		
		for (List<Country> oneListOfCountries : listOfListsOfCountries) {
			if (!oneListOfCountries.isEmpty()) {
				String firstLetter = StringUtils.getFirstLetter(oneListOfCountries.get(0).getName());
				for (String oneGroup : lettersGroups) {
					if (oneGroup.contains(firstLetter.toUpperCase())) {
						GroupOfCountries organizedCountries = new GroupOfCountries(
								lettersGroups.get(lettersGroups.indexOf(oneGroup)), oneListOfCountries);
						listOfGroupedCountriesClasses.add(organizedCountries);
						break;
					}
				}
			}
		}
		return listOfGroupedCountriesClasses;
	}

	/**
	 * @param namesOfCountries
	 * @param lettersGroups
	 * @param listOfListsOfCountries
	 */
	private static void addCountryToListOfLists(Set<Country> namesOfCountries, List<String> lettersGroups,
			List<ArrayList<Country>> listOfListsOfCountries) {
		for (Country country : namesOfCountries) {
			String firstLetterOfCountry = StringUtils.getFirstLetter(country.getName());
			for (String oneGroup : lettersGroups) {
				if (oneGroup.contains(firstLetterOfCountry.toUpperCase())) {
					listOfListsOfCountries.get(lettersGroups.indexOf(oneGroup)).add(country);
					break;
				}
			}
		}
	}

	/**
	 * @param lettersGroups
	 * @param listOfListsOfCountries
	 */
	private static void createEmptyListForEachGroup(List<String> lettersGroups,
			List<ArrayList<Country>> listOfListsOfCountries) {
		ArrayList<Country> listOfGroupedCountries;
		int numberOfCreatedEmptyLists = 0;
		while (numberOfCreatedEmptyLists < lettersGroups.size()) {
			numberOfCreatedEmptyLists++;
			listOfGroupedCountries = new ArrayList<Country>();
			listOfListsOfCountries.add(listOfGroupedCountries);
		}
	}
}
