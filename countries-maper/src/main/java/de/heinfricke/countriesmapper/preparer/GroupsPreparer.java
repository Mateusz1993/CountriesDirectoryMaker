package de.heinfricke.countriesmapper.preparer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.utils.StringUtils;

/**
 * This class contains all methods which are used to prepare groups of countries
 * for the future creation/deletion of files.
 * 
 * @author mateusz
 *
 */
public class GroupsPreparer {

    /**
     * This int determines how much characters will have each group directory.
     * (For example if this int equals three, directories will be named 'ABC',
     * 'DEF', etc.).
     */
    private static final int NUMBER_OF_GROUPED_CHARACTERS = 3;

    /**
     * This method maps letters on iDs.
     * 
     * @return Map of letters and their iDs. 
     */
    private static Map<Character, Integer> mapLettersOnIds() {
        Map<Character, Integer> justLettersOfAlphabet = new HashMap<Character, Integer>();

        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            Character oneLetter = alphabet;
            int position = (alphabet - 'A') / NUMBER_OF_GROUPED_CHARACTERS;
            justLettersOfAlphabet.put(oneLetter, position);
        }
        return justLettersOfAlphabet;
    }

    /**
     * This method returns list of strings. Each string is group of letters and
     * these letters will be names of future group directories.
     * 
     * @return List of names of group directories.
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
     * This method maps list of Country objects in groups. In this method's body
     * for each Country object's name is taken first letter. Then for this
     * letter is taken iD from Map of letters. ID is also position of name of
     * group directory in List so now it is known name of group for this
     * country. Each group of letter (name of group directory) has own List of
     * Country objects. Next Country objects are added to right List. At the end
     * each group of letter is added to Map as key and as value they have List
     * of their Country objects.
     * 
     * @param countriesFromUser
     *            As parameter it takes Set of Country objects.
     * @return Map where groups of countries are keys and List of Country
     *         objects are values.
     */
    public Map<String, List<Country>> organizeCountriesInGroups(Set<Country> countriesFromUser) {
        Set<Country> namesOfCountries = countriesFromUser;
        Map<Character, Integer> justLettersOfAlphabet = mapLettersOnIds();
        List<String> threeLettersGroups = returnLettersGroups();
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
