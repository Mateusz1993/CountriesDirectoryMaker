package de.heinfricke.countriesmapper.utils;

public enum DirectoriesActivity {
	DELETE("D"), REPLACE("R"), ADD_NEW_CONTENTS("A");

	private String directoriesActivityCode;

	private DirectoriesActivity(String directoriesActivityCode) {
		this.directoriesActivityCode = directoriesActivityCode;
	}

	/**
	 * This method return user decision about destination of files.
	 * 
	 * @param userInput
	 *            As first parameter it takes string which is user input.
	 * @return This method returns DirectoriesActivity ENUM.
	 * @throws IllegalArgumentException
	 */
	public static DirectoriesActivity getEnum(String userInput) throws IllegalArgumentException {
		for (DirectoriesActivity directoriesActivity : values()) {
			if (directoriesActivity.directoriesActivityCode.equals(userInput)) {
				return directoriesActivity;
			}
		}
		throw new IllegalArgumentException();
	}

	/**
	 * This method returns true, if user want to deleter/replace his files, or
	 * false if user only want to add new files to existing directories.
	 * 
	 * @param directoriesActivity
	 *            As first parameter it takes DirectoriesActivity ENUM.
	 * @return It returns true or false.
	 */
	public boolean requiresDirectoryRemove(DirectoriesActivity directoriesActivity) {
		String[] lettersWhichDecideAboutDeletion = new String[] { "D", "R" };
		for (String letter : lettersWhichDecideAboutDeletion) {
			if (letter == directoriesActivity.directoriesActivityCode) {
				return true;
			}
		}
		return false;
	}
}