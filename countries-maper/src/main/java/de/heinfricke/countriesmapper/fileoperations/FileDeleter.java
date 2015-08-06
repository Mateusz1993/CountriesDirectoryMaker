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
public class FileDeleter {

    /**
     * This method is used to delete directories. In body of this method we run
     * other method which return user's decision about future of directories.
     * User can delete all his directories, replace only existing groups of
     * directories or don't delete any directories. As parameter this method
     * needs Map of Country object organized in groups so if user only need to
     * replace old group's directories, it will be known which directories
     * methods must to delete.
     * 
     * @param organizedCountriesMap
     *            Map of Country objects organized in groups. Keys are names of
     *            groups and values are Lists of Country objects.
     * @param userPath
     *            Path to directory where new directories will be created so
     *            also here old ones will be deleted.
     */
    public void deleteDirectories(Map<String, List<Country>> organizedCountriesMap, String userPath) {
        DirectoriesActivity userDecision = UserInputs.userDecisionAboutDirectories();
        Map<String, List<Country>> organizedCountries = organizedCountriesMap;
        String path = userPath;
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