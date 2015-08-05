import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.heinfricke.countriesmapper.reader.*;
import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.fileoperations.*;
import de.heinfricke.countriesmapper.preparer.GroupsPreparer;

public class CountriesDirectoryMake
{	
	private static final Logger LOGGER = Logger.getLogger(CountriesDirectoryMake.class.getCanonicalName());
	
	public static void main(String[] args) 
	{
		if(args.length < 2)
		{
			System.out.println("You must to write minimum two arguments.");
		}
		else
		{
			CountriesReader countriesReader = new CountriesReader();
			try
			{
				//Read all countries to "Set".
				Set<Country> sortedCountries = countriesReader.readCountries(args[0]);
				
				//Prepare groups of countries (for example: ABC = (Albania, Czech Republic), PQR = (Poland, Qatar)).
				GroupsPreparer test = new GroupsPreparer();
				Map<String, List<Country>> groupsOfCountries = test.organiesCountriesInGroups(sortedCountries);
				
				//Delete old directories.
				FileDeleter fileDeleter = new FileDeleter();
				fileDeleter.deleteDirectories(groupsOfCountries, args[1]);
				
				//Make new directories.
				FileMaker fileMaker = new FileMaker();
				fileMaker.createDirectories(groupsOfCountries, args[1]);
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println("Provided file path is wrong. Please provide correct file path.");
				LOGGER.log(Level.FINE, "Provided file path is wrong. Please provide correct file path.", e);
			} 
			catch (IOException e) 
			{
				System.out.println("There was a problem while reading a file. Please make sure given file is correct and run application again");
				LOGGER.log(Level.FINE, "There was a problem while reading a file.", e);
			}
		}
	}
}