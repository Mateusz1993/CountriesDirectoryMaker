package de.heinfricke.countriesmapper.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

public class CountriesReader
{	
	public SortedSet<String> readCountries(String userPath) throws FileNotFoundException, IOException
	{
		String path = userPath;
		System.out.println("Your path to .txt file is: " + path);			
		
		SortedSet<String> namesOfCountries = new TreeSet<String>();
		
		File file = new File(path);
		String line;
		
		BufferedReader bufferedReader = null;
		try {
			FileReader fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null)
			{				
				if(!line.isEmpty())
				{
					namesOfCountries.add(line);
				}
			}
		} 
		finally 
		{
			if (bufferedReader != null) 
			{
				bufferedReader.close();
			}
		}
		return namesOfCountries;
	}	
}
