import java.io.*;
import java.util.*;

class CountriesReader
{	
	/**
	 * This is a constant that describes how many characters are grouped in level one directory.
	 */
	private static final int NUMBER_OF_GROUPED_CHARACTERS = 3;
	
	
	private SortedSet<String> namesOfCountries = new TreeSet<String>();
	private SortedSet<String> firstLetters = new TreeSet<String>();
	private String path;	
	
	void readCountries()
	{
		Scanner scanner = new Scanner (System.in);
		System.out.print("Enter path to list of countries: ");
		path = scanner.next();
		System.out.println("Your path is: " + path);			
		
		try 
		{
			File file = new File(path + File.separator + "CountriesList.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			
			while((line = bufferedReader.readLine()) != null)
			{
				namesOfCountries.add(line);
			}
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	void createFiles()
	{
		int counter = 0;
		String threeLetters = "";
		
		for(String country : namesOfCountries)
		{
			if(!firstLetters.contains(getFirstLetter(country)))
			{
				counter++;
				firstLetters.add(getFirstLetter(country));
				threeLetters += getFirstLetter(country);
				
				if(counter % NUMBER_OF_GROUPED_CHARACTERS == 0)
				{
					String letters = (path + File.separator + threeLetters);
					File fileLetters = new File(letters);
					fileLetters.mkdirs();
					
					for(String j : namesOfCountries)
					{
						if(threeLetters.contains(getFirstLetter(j)))
						{
							
							String countries = (letters + File.separator + j);
							File fileCountries = new File(countries);
							fileCountries.mkdirs();		
						}
					}			
					threeLetters = "";
				}
				
			}
		}
	}


	private String getFirstLetter(String country) {
		return country.substring(0,1);
	}
	
}


public class CountriesDirectoryMake
{
	
	public static void main(String[] args) 
	{
		CountriesReader test = new CountriesReader();
		test.readCountries();
		test.createFiles();

	}
}