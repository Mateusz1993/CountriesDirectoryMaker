package de.heinfricke.countriesmapper.preparer;

import java.util.*;

import javax.xml.bind.annotation.*;

import de.heinfricke.countriesmapper.country.Country;

@XmlRootElement(name = "countries")
public class PrepareForXML {
	private Set<Country> countries = new TreeSet<Country>();

	public void setCountries(Set<Country> countries) {
		this.countries = countries;
	}

	@XmlElement(name = "country")
	public Set<Country> getCountries() {
		return countries;
	}
}