package de.heinfricke.countriesmapper.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import de.heinfricke.countriesmapper.country.*;

/**
 * This class contains methods which are used to read files's contents.
 * 
 * @author mateusz
 *
 */
public class CountriesReader {
    /**
     * This method reads list of countries from file, makes new 'Country'
     * objects where each one contains name of country and returns these objects
     * as Set.
     * 
     * @param path
     *            Path to file with list of countries.
     * @return Set of 'Country' objects.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Set<Country> readCountries(String path) throws FileNotFoundException, IOException {
        System.out.println("\nYour path to .txt file is: " + path);

        Set<Country> namesOfCountries = new TreeSet<Country>();

        File file = new File(path);
        String line;

        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    Country test = new Country(line);
                    namesOfCountries.add(test);
                }
            }
        } 
        finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return namesOfCountries;
    }
}
