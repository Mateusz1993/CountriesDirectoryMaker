package de.heinfricke.countriesmapper.country;

import java.util.List;

/**
 * Country are objects which contains information about countries. For now it
 * contains only countries's names.
 * 
 * @author mateusz
 *
 */
public class Country implements Comparable<Country> {
	private String name;
	private String capital;
	private String nativeName;
	private List<String> borders;
	private String allBordersInOneString;
	
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
	
	public String getNativeName() {
		return nativeName;
	}
	
	public void setNativeName(String nativeName){
		this.nativeName = nativeName;
	}
	
	public List<String> getBorders(){
		return borders;
	}
	
	public void setBorders(List<String> borders){
		this.borders = borders;
	}
	
	public void setAllBordersInOneString(String allBordersInOneString){
		this.allBordersInOneString = allBordersInOneString;
	}

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
