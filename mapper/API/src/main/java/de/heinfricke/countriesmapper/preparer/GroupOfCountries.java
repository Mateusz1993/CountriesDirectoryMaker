package de.heinfricke.countriesmapper.preparer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.utils.StringUtils;

/**
 * This method contains everything what is necessary to create groups of
 * countries.
 * 
 * @author mateusz
 *
 */
public class GroupOfCountries {
	private static final int NUMBER_OF_GROUPED_CHARACTERS = 3;

	/**
	 * Name of group.
	 */
	String name;
	/**
	 * List of countries assigned to the group.
	 */
	List<Country> countriesList;
	
	public GroupOfCountries(){
		//empty Constructor;
	};
	
	/**
	 * This constructor set name of group and countriesList for new object.
	 * 
	 * @param name
	 *            Name of countrie's group.
	 * @param countriesList
	 *            List of Country objects.
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
	 * This function divide alphabet for X letters and return List of these
	 * letters groups.
	 * 
	 * @return List of groups of letters.
	 */
	public List<String> returnLettersGroups() {
		List<String> lettersGroups = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (char character = 'A'; character <= 'Z'; character++) {
			if ((character - 'A') % NUMBER_OF_GROUPED_CHARACTERS == 0 && character != 'A') {
				lettersGroups.add(sb.toString());
				sb.delete(0, sb.length());
			}
			sb.append(character);
		}
			lettersGroups.add(sb.toString());
		return lettersGroups;
	}

	/**
	 * This function check for each list of Country objects if it isn't empty.
	 * If not, it check on what letter countries we have in our list. Later it
	 * creates GroupOfCountries object where name is the same like name of
	 * letters group and it also adds list of correct countries to new object.
	 * 
	 * @param namesOfCountries
	 *            As first parameter it takes set of all countries .
	 * @return It return List of GroupOfCountries objects.
	 */
	public List<GroupOfCountries> organizeCountriesInGroups(Set<Country> namesOfCountries) {
		List<String> lettersGroups = returnLettersGroups();
		List<ArrayList<Country>> listOfListsOfCountries = new ArrayList<ArrayList<Country>>();
		List<GroupOfCountries> listOfGroupedCountriesClasses = new ArrayList<GroupOfCountries>();
		
		createEmptyListForEachGroup(lettersGroups, listOfListsOfCountries);
		addCountryToListOfLists(namesOfCountries, lettersGroups, listOfListsOfCountries);
		
		for (List<Country> oneListOfCountries : listOfListsOfCountries) {
			if (!oneListOfCountries.isEmpty()) {
				StringUtils stringUtils = new StringUtils();
				String firstLetter = stringUtils.getFirstLetter(oneListOfCountries.get(0).getName());
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
	 * This method add countries to correct list.
	 * 
	 * @param namesOfCountries
	 *            As first parameter it takes set of countries.
	 * @param lettersGroups
	 *            As second parameter it takes List of X letters groups.
	 * @param listOfListsOfCountries
	 *            As third parameter it takes list of lists of countries.
	 */
	private void addCountryToListOfLists(Set<Country> namesOfCountries, List<String> lettersGroups,
			List<ArrayList<Country>> listOfListsOfCountries) {
		for (Country country : namesOfCountries) {
			StringUtils stringUtils = new StringUtils();
			String firstLetterOfCountry = stringUtils.getFirstLetter(country.getName());
			for (String oneGroup : lettersGroups) {
				if (oneGroup.contains(firstLetterOfCountry.toUpperCase())) {
					listOfListsOfCountries.get(lettersGroups.indexOf(oneGroup)).add(country);
					break;
				}
			}
		}
	}

	/**
	 * This method create empty list for each group of letters.
	 * 
	 * @param lettersGroups
	 *            As first parameter it takes List of X letters groups.
	 * @param listOfListsOfCountries
	 *            As second parameter it takes lists of lists of countries.
	 */
	private void createEmptyListForEachGroup(List<String> lettersGroups,
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
