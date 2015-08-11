package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupsPreparer;
import de.heinfricke.countriesmapper.utils.UserInputs;
import de.heinfricke.countriesmapper.utils.UserInputs.DirectoriesActivity;

/**
 * This class contains methods which are used to delete files and directories.
 * 
 * @author mateusz
 *
 */
public class FileDeleter implements Deleter {

	/* (non-Javadoc)
     * @see de.heinfricke.countriesmapper.fileoperations.DeleterInterface#deleteDirectories(java.util.Map, java.lang.String)
     */
    public void deleteDirectories(Map<String, List<Country>> organizedCountries, String path) {
        DirectoriesActivity userDecision = UserInputs.userDecisionAboutDirectories();
        List<String> listOfThreeLettersGroups = new ArrayList<String>();

        if (userDecision == DirectoriesActivity.DELETE) {
            listOfThreeLettersGroups = GroupsPreparer.returnLettersGroups();
        } 
        else if (userDecision == DirectoriesActivity.REPLACE) {
            for (Map.Entry<String, List<Country>> set : organizedCountries.entrySet()) {
                listOfThreeLettersGroups.add(set.getKey());
            }
        }

        if ((userDecision == DirectoriesActivity.DELETE)
                || (userDecision == DirectoriesActivity.REPLACE)) {
            for (String directoryToDelete : listOfThreeLettersGroups) {
                String pathOfGorupDirectory = (path + File.separator + directoryToDelete);
                File tempfile = new File(pathOfGorupDirectory);
                deleteDirectory(tempfile);
            }
        }
    }

    /**
     * This method delete main directory and all included directories.
     * 
     * @param dir
     *            As parameter it takes File object.
     * @return It returns dir.delete().
     */
    private static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}