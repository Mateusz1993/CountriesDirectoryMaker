package de.heinfricke.countriesmapper.creator;

import java.io.File;
import java.util.SortedSet;

public abstract class Creator
{
	public abstract void createFiles(String userPath, SortedSet<String> countriesFromUser);
	
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
}

