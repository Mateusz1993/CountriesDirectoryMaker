package de.heinfricke.countriesmapper.preparer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.utils.StringUtils;

public class GroupsPreparer {
	
	private static final int NUMBER_OF_GROUPED_CHARACTERS = 3;
	
	private static Map<String, Integer> returnLettersOfAlphabet()
	{
		int counter = 0;
		int counterForHashtable = 0;	
		Map<String, Integer> justLettersOfAlphabet = new HashMap<String, Integer>();
		
		for(char alphabet = 'A'; alphabet <= 'Z'; alphabet++)
		{		
			String oneLetter = "";
			oneLetter += alphabet;
			Integer position = new Integer(counterForHashtable);
			justLettersOfAlphabet.put(oneLetter, position);
			counter++;
			
			if(counter % NUMBER_OF_GROUPED_CHARACTERS == 0 || alphabet == 'Z')
			{
				counterForHashtable++;
			}
		}
		return justLettersOfAlphabet;
	}
	

	public static List<String> returnThreeLettersGroups()
	{
		List<String> threeLettersGroup = new ArrayList<String>();
		Map<String, Integer> justLettersOfAlphabet = returnLettersOfAlphabet();
		int counterForValueOfMap = 0;
		int counterToCheckHowManyTimesForWasDone = 0;
		String threeLetters = "";
		
		for(Map.Entry<String, Integer> set : justLettersOfAlphabet.entrySet())
		{
			counterToCheckHowManyTimesForWasDone++;
			if(set.getValue() == counterForValueOfMap)
			{
				threeLetters += set.getKey();
				if(counterToCheckHowManyTimesForWasDone == justLettersOfAlphabet.size())
				{
					threeLettersGroup.add(threeLetters);
				}
			}
			else
			{
				threeLettersGroup.add(threeLetters);
				counterForValueOfMap++;
				threeLetters = "";
				threeLetters += set.getKey();
			}
		}	
		return threeLettersGroup;
	}

	
	public Map<String, List<Country>> organiesCountriesInGroups(Set<Country> countriesFromUser)
	{
		Set<Country> namesOfCountries = countriesFromUser;
		Map<String, Integer> justLettersOfAlphabet = returnLettersOfAlphabet();
		List<String> threeLettersGroups = returnThreeLettersGroups();
		List<Country> listOfCountriesForEachGroup = new ArrayList<Country>();
		Map<String, List<Country>> countriesOrganisedInGroups = new HashMap<String, List<Country>>();
		
		int numberOfAddedCountries = 0;
		String nameOfFirstCountry = namesOfCountries.iterator().next().getName();
		String numberOfGroupPositionOfFirstCountry = StringUtils.getFirstLetter(nameOfFirstCountry);
		int counter = justLettersOfAlphabet.get(numberOfGroupPositionOfFirstCountry);
		
		for(Country country : namesOfCountries)
		{
			String countryName = country.getName();
			String firstLetterOfCountry = StringUtils.getFirstLetter(countryName);
			int positionInList = justLettersOfAlphabet.get(firstLetterOfCountry);
			
			Country newCountry = new Country(countryName);
			
			if(positionInList == counter)
			{
				listOfCountriesForEachGroup.add(newCountry);
				numberOfAddedCountries++;
				
				if(numberOfAddedCountries >= namesOfCountries.size())
				{
					countriesOrganisedInGroups.put(threeLettersGroups.get(positionInList), listOfCountriesForEachGroup);
				}
			}
			else
			{
				String firstLetterOfLastCountry = StringUtils.getFirstLetter(listOfCountriesForEachGroup.get(listOfCountriesForEachGroup.size() - 1).getName());
				int firstLetterOfLastCountryPosition = justLettersOfAlphabet.get(firstLetterOfLastCountry);	
				countriesOrganisedInGroups.put(threeLettersGroups.get(firstLetterOfLastCountryPosition), listOfCountriesForEachGroup);
				listOfCountriesForEachGroup = new ArrayList<Country>();
				listOfCountriesForEachGroup.add(newCountry);
				numberOfAddedCountries++;
				counter = positionInList;
				
				if(numberOfAddedCountries >= namesOfCountries.size())
				{
					countriesOrganisedInGroups.put(threeLettersGroups.get(positionInList), listOfCountriesForEachGroup);
				}
			}			
		}	
		return countriesOrganisedInGroups;
	}
}
