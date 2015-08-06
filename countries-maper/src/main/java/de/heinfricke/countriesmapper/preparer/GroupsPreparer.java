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

    private static Map<Character, Integer> mapLettersOnIds() {
        Map<Character, Integer> justLettersOfAlphabet = new HashMap<Character, Integer>();

        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            Character oneLetter = alphabet;
            int position = (alphabet - 'A') / NUMBER_OF_GROUPED_CHARACTERS;
            justLettersOfAlphabet.put(oneLetter, position);
        }
        return justLettersOfAlphabet;
    }

    public static List<String> returnThreeLettersGroups() {
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

    public Map<String, List<Country>> organizeCountriesInGroups(Set<Country> countriesFromUser) {
        Set<Country> namesOfCountries = countriesFromUser;
        Map<Character, Integer> justLettersOfAlphabet = mapLettersOnIds();
        List<String> threeLettersGroups = returnThreeLettersGroups();
        List<Country> listOfCountriesForEachGroup = new ArrayList<Country>();
        Map<String, List<Country>> countriesOrganizedInGroups = new HashMap<String, List<Country>>();

        int numberOfAddedCountries = 0;
        String nameOfFirstCountry = namesOfCountries.iterator().next().getName();
        String numberOfGroupPositionOfFirstCountry = StringUtils.getFirstLetter(nameOfFirstCountry);
        int counter = justLettersOfAlphabet.get(numberOfGroupPositionOfFirstCountry.charAt(0));

        for (Country country : namesOfCountries) {
            String countryName = country.getName();
            String firstLetterOfCountry = StringUtils.getFirstLetter(countryName);
            int positionInList = justLettersOfAlphabet.get(firstLetterOfCountry.charAt(0));

            Country newCountry = new Country(countryName);

            if (positionInList == counter) {
                listOfCountriesForEachGroup.add(newCountry);
                numberOfAddedCountries++;

                if (numberOfAddedCountries >= namesOfCountries.size()) {
                    countriesOrganizedInGroups.put(threeLettersGroups.get(positionInList), listOfCountriesForEachGroup);
                }
            } 
            else {
                String firstLetterOfLastCountry = StringUtils.getFirstLetter(
                        listOfCountriesForEachGroup.get(listOfCountriesForEachGroup.size() - 1).getName());
                int firstLetterOfLastCountryPosition = justLettersOfAlphabet.get(firstLetterOfLastCountry.charAt(0));
                countriesOrganizedInGroups.put(threeLettersGroups.get(firstLetterOfLastCountryPosition),
                        listOfCountriesForEachGroup);
                listOfCountriesForEachGroup = new ArrayList<Country>();
                listOfCountriesForEachGroup.add(newCountry);
                numberOfAddedCountries++;
                counter = positionInList;

                if (numberOfAddedCountries >= namesOfCountries.size()) {
                    countriesOrganizedInGroups.put(threeLettersGroups.get(positionInList), listOfCountriesForEachGroup);
                }
            }
        }
        return countriesOrganizedInGroups;
    }
}
