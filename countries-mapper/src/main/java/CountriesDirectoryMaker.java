import java.io.*;
import java.lang.*;
import java.util.*;


class CountriesReader
{
	
	private SortedSet<String> namesOfCountries = new TreeSet<String>();
	private String path;
	
	void readCountries()
	{
		Scanner scanner = new Scanner (System.in);
		System.out.print("Enter path to list of countries: ");
		path = scanner.next();
		System.out.println("Your path is: " + path);
			
		//Delete this later.
		//path = "//home//mateusz//workspace//countries-mapper";
		
		try 
		{
			File file = new File(path + "//CountriesList.txt");
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void createFiles()
	{
		for(String i : namesOfCountries)
		{
			String letters = (path + "//" + i.substring(0,1));
			File fileLetters = new File(letters);
			fileLetters.mkdirs();
			
			String countries = (letters + "//" + i);
			File fileCountries = new File(countries);
			fileCountries.mkdirs();		
		}
	}	
}


public class CountriesDirectoryMaker 
{
	
	public static void main(String[] args) 
	{
		CountriesReader test = new CountriesReader();
		test.readCountries();
		test.createFiles();

	}
}