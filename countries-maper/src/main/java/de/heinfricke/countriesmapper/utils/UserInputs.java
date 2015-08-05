package de.heinfricke.countriesmapper.utils;

import java.util.Scanner;

public class UserInputs {

	public static int userDecision()
	{
		Scanner scanner = null;
		String userDecision = "n";
		scanner = new Scanner (System.in);
		try
		{
			System.out.print("Do you want do delete all your directories? [Y/N]: " );
			userDecision = scanner.next();
			System.out.println();
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
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
