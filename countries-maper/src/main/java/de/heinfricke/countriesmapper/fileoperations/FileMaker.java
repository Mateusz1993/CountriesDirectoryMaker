package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.util.List;
import java.util.Map;

import de.heinfricke.countriesmapper.country.Country;

public class FileMaker {

    public void createDirectories(Map<String, List<Country>> organizedCountriesMap, String userPath) {
        Map<String, List<Country>> organizedCountries = organizedCountriesMap;
        String path = userPath;

        for (Map.Entry<String, List<Country>> set : organizedCountries.entrySet()) {
            String groupDirectory = set.getKey();
            List<Country> listOfCountriesInEachGroup = set.getValue();

            String pathToGroupFolder = (path + File.separator + groupDirectory);
            File groupFile = new File(pathToGroupFolder);
            groupFile.mkdirs();
            for (Country countries : listOfCountriesInEachGroup) {
                String pathToSingleFile = (pathToGroupFolder + File.separator + countries.getName());
                File singleFile = new File(pathToSingleFile);
                singleFile.mkdirs();
            }
        }
    }
}
