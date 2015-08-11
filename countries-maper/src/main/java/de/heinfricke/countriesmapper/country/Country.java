package de.heinfricke.countriesmapper.country;

/**
 * Country are objects which contains information about countries. For now it
 * contains only countries's names.
 * 
 * @author mateusz
 *
 */
public class Country implements Comparable<Country> {
	String name;

	public Country(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Country o) {
		return name.compareTo(o.name);
	}
}
