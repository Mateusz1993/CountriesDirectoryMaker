package de.heinfricke.countriesmapper.creator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import de.heinfricke.countriesmapper.utils.*;


public class DirectoriesMaker extends Creator{
	
	/**
	 * This is a constant that describes how many characters are grouped in level one directory.
	 */
	private static final int NUMBER_OF_GROUPED_CHARACTERS = 3;
	
	
	
	public void createFiles(String userPath, SortedSet<String> countriesFromUser)
	{
		SortedSet<String> namesOfCountries = countriesFromUser;
		int counter = 0;
		int counterForHashtable = 0;
		String threeLetters = "";
		String path = userPath;
		ArrayList<String> collectionOfThreeLetters = new ArrayList<String>();
		HashMap<String, Integer> justLettersOfAlphabet = new HashMap<String, Integer>();
		SortedSet<String> directoriesToCreate = new TreeSet<String>();

		System.out.print("Your directories will be created in: ");
		System.out.println(path);
	
		//This 'for' takes all letters of alphabet to 'HashMap' and divide them into smaller parts to 'ArrayList'.
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
			String firstLetterOfCountry = StringUtils.getFirstLetter(country);
			int numberOfPositionInArrayList = justLettersOfAlphabet.get(firstLetterOfCountry);			
			directoriesToCreate.add(collectionOfThreeLetters.get(numberOfPositionInArrayList));
		}
		
		
		//Here user can chose if he want to delete all directories or just these, which he will make new one.
		Scanner scanner = null;
		String userDecision = "n";
		scanner = new Scanner (System.in);
		try
		{
			System.out.print("Do you want do delete all your directories? [Y/N]: " );
			userDecision = scanner.next();
		}
		finally
		{
			if(scanner != null)
			{
				scanner.close();
			}
		}
		
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
			String firstLetter = StringUtils.getFirstLetter(country);
			int positionOfLetterInArrayList = justLettersOfAlphabet.get(firstLetter);
			String x = collectionOfThreeLetters.get(positionOfLetterInArrayList);
	
			String pathForDirectoryToCreate = (path + File.separator + x + File.separator + country);
			File directoryForCountry = new File(pathForDirectoryToCreate);
			directoryForCountry.mkdirs();		
		}
	}
}