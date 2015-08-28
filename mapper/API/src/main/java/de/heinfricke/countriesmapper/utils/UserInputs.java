package de.heinfricke.countriesmapper.utils;

import java.util.Scanner;

/**
 * This class contains everything what requires user's interaction.
 * 
 * @author Mateusz
 *
 */
public class UserInputs {

	/**
	 * In this method user makes decision about his directories. He can delete
	 * all his group directories, replace only these which have new contents or
	 * add new contents to existing directories.
	 * 
	 * @return User decision about future of his directories.
	 */
	public DirectoriesActivity userDecisionAboutDirectories() throws IllegalArgumentException  {
		Scanner scanner = null;
		String userDecision;
		scanner = new Scanner(System.in);
		try {
			System.out.println("\nWhat do you want to do with your directories?");
			System.out.println("D - Delete all my group directories.");
			System.out.println("R - Replace only these group directories which have new contents.");
			System.out.println("A - Add new contents to my old directories.");
			System.out.print("Decision [D/R/A]: ");
			userDecision = scanner.next();
			System.out.println();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return DirectoriesActivity.getEnum(userDecision.toUpperCase());
	}
}