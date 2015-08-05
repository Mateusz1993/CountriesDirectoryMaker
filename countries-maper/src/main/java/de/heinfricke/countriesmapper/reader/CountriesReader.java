package de.heinfricke.countriesmapper.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import de.heinfricke.countriesmapper.country.*;

public class CountriesReader
{		
	public Set<Country> readCountries(String userPath) throws FileNotFoundException, IOException
	{
		String path = userPath;
		System.out.println("\nYour path to .txt file is: " + path);			
		
		List<Country> namesOfCountries = new ArrayList<Country>();
		
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
					Country test = new Country(line);
					namesOfCountries.add(test);
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
		
		Set<Country> treeSet = new TreeSet<Country>();
		treeSet.addAll(namesOfCountries);
		
		return treeSet;
	}
}
