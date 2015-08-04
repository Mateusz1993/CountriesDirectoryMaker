import java.io.*;
import java.util.*;

public class CountriesDirectoryMake
{	
	public static void main(String[] args) 
	{

		if(args.length < 2)
		{
			System.out.println("You must to write minimum two arguments.");
		}
		else
		{
			CountriesReader test = new CountriesReader();
			SortedSet<String> test2 = test.readCountries(args[0]);
			test.createFiles(args[1], test2);
		}
	}
}



interface Creator
{
	void CreateDirectories(SortedSet<String> sortedListOfCountries);
}



class CountriesReader
{	
	/**
	 * This is a constant that describes how many characters are grouped in level one directory.
	 */
	private static final int NUMBER_OF_GROUPED_CHARACTERS = 3;
	
	
	//private SortedSet<String> namesOfCountries = new TreeSet<String>();
	
/*	void readCountries(String userPath)
	{
		String path = userPath;
		System.out.println("Your path to CountriesList.txt is: " + path);			
		
		
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
	}*/

	
	SortedSet<String> readCountries(String userPath)
	{
		String path = userPath;
		System.out.println("Your path to CountriesList.txt is: " + path);			
		
		SortedSet<String> namesOfCountries = new TreeSet<String>();
		
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
		return namesOfCountries;
	}
	
	
	
	
	
	void createFiles(String userPath, SortedSet<String> countriesFromUser)
	{
		
		SortedSet<String> namesOfCountries = countriesFromUser;
		SortedSet<String> firstLetters = new TreeSet<String>();
		int counter = 0;
		int counterForHashtable = 0;
		String threeLetters = "";
		String path = userPath;
		Vector<String> collectionOfThreeLetters = new Vector<String>();
		Hashtable<String, Integer> justLettersOfAlphabet = new Hashtable<String, Integer>();
		SortedSet<String> directoriesToCreate = new TreeSet<String>();

		
		System.out.print("Your directories will be created in: ");
		System.out.println(path);
	
		//This 'for' takes all letters of alphabet to 'Hashtable' and divide them into smaller parts to 'Vector'.
		for(char alphabet = 'A'; alphabet <= 'Z'; alphabet++)
		{		
			threeLetters += alphabet;
			counter++;
			String oneLetter = "";
			oneLetter += alphabet;
			Integer position = new Integer(counterForHashtable);
			justLettersOfAlphabet.put(oneLetter, position);

			if(counter % NUMBER_OF_GROUPED_CHARACTERS == 0 || alphabet == 'Z')
			{
				collectionOfThreeLetters.add(threeLetters);
				threeLetters = "";
				counterForHashtable++;
			}
		}
		
		
		//This 'for' checks on which letters countries we have in our 'txt' file and it also makes list of directories to create.
		for(String country : namesOfCountries)
		{
			String firstLetterOfCountry = getFirstLetter(country);
			int numberOfPositionInVector = justLettersOfAlphabet.get(firstLetterOfCountry);
			directoriesToCreate.add(collectionOfThreeLetters.elementAt(numberOfPositionInVector));
		}
		
		
		//Here user can chose if he want to delete all directories or just these, which he will make new one.
		Scanner scanner = new Scanner (System.in);
		System.out.print("Do you want do delete all your directories? [Y/N]: " );
		String userDecision = scanner.next();
		
		if(userDecision.equals("Y") || userDecision.equals("y"))
		{
			for(String directoryToDelete : collectionOfThreeLetters)
			{
				String pathOfGorupDirectory = (path + File.separator + directoryToDelete);
				File tempfile = new File(pathOfGorupDirectory);
				
				deleteDirectory(tempfile);
			}
		}
		else
		{
			for(String directoryToDelete : directoriesToCreate)
			{
				String pathOfGorupDirectory = (path + File.separator + directoryToDelete);
				File tempfile = new File(pathOfGorupDirectory);
				
				deleteDirectory(tempfile);
			}
		}
		
		//This 'for' create new directories for each group of countries.
		for(String dir : directoriesToCreate)
		{
			String nameOfDirectory = (path + File.separator + dir);
			File groupFile = new File(nameOfDirectory);
			groupFile.mkdirs();
		}
	
		//This 'for' make directory for each country which '.txt' file contains.
		for(String country : namesOfCountries)
		{
			String firstLetter = getFirstLetter(country);
			int positionOfLetterInVector = justLettersOfAlphabet.get(firstLetter);
			String x = collectionOfThreeLetters.elementAt(positionOfLetterInVector);
	
			String pathForDirectoryToCreate = (path + File.separator + x + File.separator + country);
			File directoryForCountry = new File(pathForDirectoryToCreate);
			directoryForCountry.mkdirs();		
		}
	}

	
	public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) 
        {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) 
            {
                boolean success = deleteDirectory(children[i]);
                if (!success) 
                {
                    return false;
                }
            }
        }
      return dir.delete();
    }
	
	
	
	private String getFirstLetter(String country) {
		return country.substring(0,1);
	}
}


