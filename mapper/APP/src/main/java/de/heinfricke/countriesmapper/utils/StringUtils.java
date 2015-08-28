package de.heinfricke.countriesmapper.utils;

/**
 * This class contains some helpful methods to prevent code duplication.
 * 
 * @author mateusz
 *
 */
public class StringUtils {
	/**
	 * This method returns first letter of word given as parameter.
	 * 
	 * @param country
	 *            From this word will be taken first letter.
	 * @return First letter of word given as parameter.
	 */
	public String getFirstLetter(String country) {
		return country.substring(0, 1);
	}
}
