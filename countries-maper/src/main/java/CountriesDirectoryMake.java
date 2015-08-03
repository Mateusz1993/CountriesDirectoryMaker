import java.io.*;
import java.util.*;

class CountriesReader
{	
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
		
		for(String i : namesOfCountries)
		{
			if(!firstLetters.contains(i.substring(0,1)))
			{
				counter++;
				firstLetters.add(i.substring(0,1));
				threeLetters += i.substring(0,1);
				
				if(counter%3 == 0)
				{
					String letters = (path + File.separator + threeLetters);
					File fileLetters = new File(letters);
					fileLetters.mkdirs();
					
					for(String j : namesOfCountries)
					{
						if(threeLetters.contains(j.substring(0,1)))
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