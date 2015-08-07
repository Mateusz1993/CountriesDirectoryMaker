package de.heinfricke.countriesmapper;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.heinfricke.countriesmapper.reader.*;
import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.fileoperations.*;
import de.heinfricke.countriesmapper.preparer.*;
import org.apache.commons.cli.*;

public class CountriesDirectoryMake {
    private static final Logger LOGGER = Logger.getLogger(CountriesDirectoryMake.class.getCanonicalName());

    public static void main(String[] args) {

        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        CommandLine cmd = null;

        options.addOption("H", "help", false, "Show help.")
                .addOption("l", "localFileSystem", false, "Make directories in your local system.")
                .addOption("f", "ftp", false, "Make directories in your FTP Server.")
                .addOption("i", "inputFile", true, "Path to your input file.")
                .addOption("o", "outputDir", true, "Path to your output directory.")
                .addOption("h", "host", true, "FTP host.").addOption("p", "port", true, "FTP port.")
                .addOption("u", "ftpUser", true, "FTP user name.")
                .addOption("pw", "ftpPassword", true, "FTP user password.")
                .addOption("fp", "ftpPath", true, "Path on FTP.");

        boolean correctInputInCommandLine = false;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("l") && cmd.hasOption("i") && cmd.hasOption("o")) {
                correctInputInCommandLine = true;
            } else if (cmd.hasOption("f") && cmd.hasOption("h") && cmd.hasOption("p") && cmd.hasOption("u")
                    && cmd.hasOption("pw") && cmd.hasOption("fp")) {
                correctInputInCommandLine = true;
            } else if (cmd.hasOption("H")) {
                System.out.println("\nTo make directories on your local system please use: ");
                System.out.println(
                        "./CountriesDirectoryMake -l -i path/to/your/local/file.txt -o /path/to/your/input/directory");
                System.out.println("\nTo make directories on your FTP server please use: ");
                System.out.println(
                        "./CountriesDirectoryMake -f -i path/to/your/input/file.txt -h host -p port -u ftpUserName -pw FTPUserPasswrd -fp /FTP/path \n");
                HelpFormatter formater = new HelpFormatter();
                formater.printHelp("Options:", options);
                System.exit(0);
            } else {
                System.out.println("You wrote something wrong. Please use '-H' for help.");
                System.exit(0);
            }

        } catch (ParseException e) {
            System.out.println("There was invalid expression. Please use '-H' for help.");
            LOGGER.log(Level.FINE, "Invalid expression.", e);
        }

        if (correctInputInCommandLine) {
            CountriesReader countriesReader = new CountriesReader();
            try {
                // Read all countries to "Set".
                Set<Country> sortedCountries = countriesReader.readCountries(cmd.getOptionValue("i"));

                // Prepare groups of countries (for example: ABC = (Albania,
                // Czech Republic), PQR = (Poland, Qatar)).
                GroupsPreparer groupsPreparer = new GroupsPreparer();
                Map<String, List<Country>> groupsOfCountries = groupsPreparer
                        .organizeCountriesInGroups(sortedCountries);

                if (cmd.hasOption("l")) {
                    // Delete old directories.
                    FileDeleter fileDeleter = new FileDeleter();
                    fileDeleter.deleteDirectories(groupsOfCountries, cmd.getOptionValue("o"));

                    // Make new directories.
                    FileMaker fileMaker = new FileMaker();
                    fileMaker.createDirectories(groupsOfCountries, cmd.getOptionValue("o"));
                }
            } catch (FileNotFoundException e) {
                System.out.println("Provided file path is wrong. Please provide correct file path.");
                LOGGER.log(Level.FINE, "Provided file path is wrong. Please provide correct file path.", e);
            } catch (IOException e) {
                System.out.println(
                        "There was a problem while reading a file. Please make sure given file is correct and run application again");
                LOGGER.log(Level.FINE, "There was a problem while reading a file.", e);
            }
        }
    }
}