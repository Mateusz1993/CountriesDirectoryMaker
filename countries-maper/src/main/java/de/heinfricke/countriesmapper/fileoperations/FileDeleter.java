package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupsPreparer;
import de.heinfricke.countriesmapper.utils.UserInputs;
import de.heinfricke.countriesmapper.utils.UserInputs.whatWantUserDoWithHisDirectories;

public class FileDeleter {
	
	public void deleteDirectories(Map<String, List<Country>> organizedCountriesMap, String userPath)
	{
		whatWantUserDoWithHisDirectories userDecizion = UserInputs.userDecizionAboutDirectories();
		Map<String, List<Country>> organizedCountries = organizedCountriesMap;
		String path = userPath;
		List<String> collectionOfThreeLetters = new ArrayList<String>();
		
		for(Map.Entry<String, List<Country>> set : organizedCountries.entrySet())
		{
			collectionOfThreeLetters.add(set.getKey());
		}
		
		if(userDecizion == whatWantUserDoWithHisDirectories.DELETE)
		{
			for(String directoryToDelete : GroupsPreparer.returnThreeLettersGroups())
			{
				String pathOfGorupDirectory = (path + File.separator + directoryToDelete);
				File tempfile = new File(pathOfGorupDirectory);
				
				deleteDirectory(tempfile);
			}
		}
		else if (userDecizion == whatWantUserDoWithHisDirectories.REPLACE)
		{
			for(String directoryToDelete : collectionOfThreeLetters)
			{
				String pathOfGorupDirectory = (path + File.separator + directoryToDelete);
				File tempfile = new File(pathOfGorupDirectory);
				
				deleteDirectory(tempfile);
			}
		}
		
	}

	
	private static boolean deleteDirectory(File dir) {
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
}
