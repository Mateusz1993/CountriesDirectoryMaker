package de.heinfricke.countriesmapper.utils;

import java.util.Scanner;

public class UserInputs {

	public static WhatWantUserToDoWithHisDirectories userDecizionAboutDirectories()
	{
		Scanner scanner = null;
		String userDecizion = "n";
		scanner = new Scanner (System.in);
		try
		{
			System.out.println("What do you want to do with your directories?");
			System.out.println("D - Delete all my group directories.");
			System.out.println("R - Repleace only these group directories which have new contents.");
			System.out.println("A - Add new contents to my old directories.");
			System.out.print("Decizion [D/R/A]: ");
			userDecizion = scanner.next();
			System.out.println();
		}
		finally
		{
			if(scanner != null)
			{
				scanner.close();
			}
		}
		
		if(userDecizion.equals("D") || userDecizion.equals("d"))
		{
			return WhatWantUserToDoWithHisDirectories.DELETE;
		}
		else if(userDecizion.equals("R") || userDecizion.equals("r"))
		{
			return WhatWantUserToDoWithHisDirectories.REPLACE;
		}
		else
		{
			return WhatWantUserToDoWithHisDirectories.ADD_NEW_CONTENTS;
		}
	}
	
	
	public enum WhatWantUserToDoWithHisDirectories
	{
		DELETE, REPLACE, ADD_NEW_CONTENTS;
	}
	
	
}
