package de.heinfricke.countriesmapper.country;

import java.util.List;

import javax.xml.bind.annotation.*;

/**
 * Country are objects which contains information about countries. For now it
 * contains only countries's names.
 * 
 * @author mateusz
 *
 */
@XmlRootElement(name = "country")
@XmlType(propOrder = {"name", "capital", "nativeName", "borderCountries"})
public class Country implements Comparable<Country> {
	private String name;
	private String capital;
	private String nativeName;
	private List<String> borders;
	private String allBordersInOneString;
	private List<Country> borderCountries;
	
	public Country(){
	
	}

	public Country(String name, String capital, String nativeName, List<String> borders){
		this.name = name;
		this.capital = capital;
		this.nativeName = nativeName;
		this.borders = borders;
	}
	
	public Country(String name) {
		this.name = name;
	}

	@XmlElementWrapper(name="border-countries")
	@XmlElement(name="border-country")
	public List<Country> getBorderCountries(){
		return borderCountries;
	}
	
	public void setBorderCountries(List<Country> borderCountries){
		this.borderCountries = borderCountries;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getCapital() {
		return capital;
	}
	
	public void setCapital(String capital){
		this.capital = capital;
	}
	
	@XmlElement(name="native-name")
	public String getNativeName() {
		return nativeName;
	}
	
	public void setNativeName(String nativeName){
		this.nativeName = nativeName;
	}

	@XmlTransient
	public List<String> getBorders(){
		return borders;
	}
	
	public void setBorders(List<String> borders){
		this.borders = borders;
	}
	
	public void setAllBordersInOneString(String allBordersInOneString){
		this.allBordersInOneString = allBordersInOneString;
	}

	@XmlTransient
	public String getAllBordersInOneString(){
		return allBordersInOneString;
	}
	
	@Override
	public int compareTo(Country o) {
		return name.compareTo(o.name);
	}
	
	@Override
	public String toString()
	{
		return "Country [Name = " + name + ", capital = " + capital + ", nativeName = " + nativeName + ", borders = " + borders + "]";
	}	
	
	public String[] returnInfromations(){
		return (name + "#" + capital + "#" + nativeName + "#" + allBordersInOneString).split("#");
	}
}
