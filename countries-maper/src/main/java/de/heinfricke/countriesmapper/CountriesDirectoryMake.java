package de.heinfricke.countriesmapper;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.heinfricke.countriesmapper.reader.*;
import de.heinfricke.countriesmapper.utils.FTPConnection;
import de.heinfricke.countriesmapper.utils.UserInputs;
import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.fileoperations.*;
import de.heinfricke.countriesmapper.preparer.*;
import org.apache.commons.cli.*;
import org.codehaus.jettison.json.JSONException;

public class CountriesDirectoryMake {
	private static final Logger LOGGER = Logger.getLogger(CountriesDirectoryMake.class.getCanonicalName());

	public static void main(String[] args) {
		try {
			CountriesDirectoryMake countriesDirectoryMake = new CountriesDirectoryMake();
			CommandLine cmd = null;
			Options options = new Options();
			cmd = countriesDirectoryMake.readFromCommandLine(args, options);
			ProgramTask programTask = countriesDirectoryMake.returnProgramTask(cmd);
			countriesDirectoryMake.handleHelp(options, programTask);

			// Read all countries to "Set".
			CountriesReader countriesReader = new CountriesReader();
			Set<Country> sortedCountries = countriesReader.readCountries(cmd.getOptionValue("i"), cmd.hasOption("restCountriesFetch"));

			// Prepare groups of countries (for example: ABC = (Albania,
			// Czech Republic), PQR = (Poland, Qatar)).
			GroupOfCountries groupOfCountries = new GroupOfCountries();
			List<GroupOfCountries> listOfGroupedCountriesClasses = groupOfCountries
					.organizeCountriesInGroups(sortedCountries);

			// Delete and create directories.
			countriesDirectoryMake.executeTask(cmd, options, programTask, listOfGroupedCountriesClasses);

		} catch (FileNotFoundException e) {
			System.out.println("Provided file path is wrong. Please provide correct file path.");
			LOGGER.log(Level.FINE, "Provided file path is wrong. Please provide correct file path.", e);
		} catch (IOException e) {
			System.out.println(
					"There was a problem while reading a file. Please make sure given file is correct and run application again");
			LOGGER.log(Level.FINE, "There was a problem while reading a file.", e);
		} catch (ParseException e) {
			System.out.println("There was invalid expression. Please use '-H' for help.");
			LOGGER.log(Level.FINE, "Invalid expression.", e);
		} catch (JSONException e) {
			System.out.println("There was something invalid in JSON. You propably wrote country which doesn't exist or is not supported by our application.");
			LOGGER.log(Level.FINE, "Invalid expression.", e);
		} catch (RuntimeException e) {
			System.out.println("You propably wrote country which doesn't exists or is not supported by our application. Please make sure that country's name is correct and run application again.");
			LOGGER.log(Level.FINE, "HTTP error.", e);
		}
	}

	/**
	 * This method executes tasks. It can delete directories, create
	 * directories, show help etc.
	 * 
	 * @param cmd
	 *            As first parameter it takes CommandLine object.
	 * @param options
	 *            As second parameter it takes Options object.
	 * @param programTask
	 *            As third parameter it takes program task (decision about what
	 *            program have to do).
	 * @param listOfGroupedCountriesClasses
	 *            As fourth parameter it takes list of GroupOfCountries objects.
	 * @throws IOException
	 */
	private void executeTask(CommandLine cmd, Options options, ProgramTask programTask,
			List<GroupOfCountries> listOfGroupedCountriesClasses) throws IOException {
		if (programTask == ProgramTask.ERROR_INFO) {
			System.out.println("You wrote something wrong. Please use '-H' for help.");
		} else {
			UserInputs userInputs = new UserInputs();
			
			FileDeleter deleter = new FileDeleter(userInputs);
			Maker maker = new FileMaker();
			FTPConnection ftpConnection = new FTPConnection();
			if (programTask == ProgramTask.WORK_ON_FTP) {
				ftpConnection.makeConnection(cmd.getOptionValue("h"), cmd.getOptionValue("p"), cmd.getOptionValue("u"),
						cmd.getOptionValue("pw"));
				maker = new FTPFileMaker(ftpConnection);
				deleter = new FileDeleter(ftpConnection, userInputs);
			}
			deleter.deleteDirectories(listOfGroupedCountriesClasses, cmd.getOptionValue("o"));
			maker.createFiles(listOfGroupedCountriesClasses, cmd.getOptionValue("o"));
			
			if(cmd.hasOption("restCountriesFetch"))
			{
				CSVFileMaker csvFileMaker = new CSVFileMaker();
				if(programTask == ProgramTask.WORK_ON_FTP)
				{
					csvFileMaker = new CSVFileMaker(ftpConnection);
				}
				csvFileMaker.createFiles(listOfGroupedCountriesClasses, cmd.getOptionValue("o"));
			}
			
			if (programTask == ProgramTask.WORK_ON_FTP) {
				ftpConnection.makeDisconnection();
			}
		}
	}

	/**
	 * This method add options to Options object.
	 * 
	 * @param args
	 *            All arguments written in command line.
	 * @param options
	 *            Options object.
	 * @return It returns CommandLine object.
	 * @throws ParseException
	 */
	private CommandLine readFromCommandLine(String[] args, Options options) throws ParseException {
		CommandLine cmd;
		CommandLineParser parser = new DefaultParser();

		options.addOption("H", "help", false, "Show help.")
				.addOption("l", "localFileSystem", false, "Make directories in your local system.")
				.addOption("f", "ftp", false, "Make directories in your FTP Server.")
				.addOption("i", "inputFile", true, "Path to your input file.")
				.addOption("o", "outputPath", true, "Path to your output.").addOption("h", "host", true, "FTP host.")
				.addOption("p", "port", true, "FTP port.").addOption("u", "ftpUser", true, "FTP user name.")
				.addOption("pw", "ftpPassword", true, "FTP user password.")
				.addOption("restCountriesFetch", "r", false, "Make file with informations about each country.");

		cmd = parser.parse(options, args);
		return cmd;
	}

	/**
	 * This function takes as parameter CommandLine object and basing on user's
	 * parameters it return correct ProgramTask enum's option.
	 * 
	 * @param cmd
	 *            As parameter it takes CommandLine object.
	 * @return It return decision from ProgramTask enum.
	 */
	private ProgramTask returnProgramTask(CommandLine cmd) {
		if ((cmd.hasOption("l") && cmd.hasOption("i") && cmd.hasOption("o"))) {
			return ProgramTask.WORK_ON_LOCAL_FILES;
		} else if (cmd.hasOption("f") && cmd.hasOption("h") && cmd.hasOption("p") && cmd.hasOption("u")
				&& cmd.hasOption("pw") && cmd.hasOption("o")) {
			return ProgramTask.WORK_ON_FTP;
		} else if (cmd.hasOption("H")) {
			return ProgramTask.SHOW_HELP;
		} else {
			return ProgramTask.ERROR_INFO;
		}
	}

	/**
	 * This method just holds all outputs which user will see after choosing
	 * "Help".
	 * 
	 * @param options
	 *            It takes as parameter Options object.
	 */
	private void handleHelp(Options options, ProgramTask programTask) {
		if (programTask == ProgramTask.SHOW_HELP) {
			System.out.println("\nTo make directories on your local system please use: ");
			System.out.println(
					"./CountriesDirectoryMake -l -i path/to/your/local/file.txt -o /path/to/your/input/directory");
			System.out.println("\nTo make directories on your FTP server please use: ");
			System.out.println(
					"./CountriesDirectoryMake -f -i path/to/your/input/file.txt -h host -p port -u ftpUserName -pw FTPUserPasswrd -o /FTP/path \n");
			HelpFormatter formater = new HelpFormatter();
			formater.printHelp("Options:", options);
			System.exit(0);
		}
	}

	/**
	 * Enum to chose what program have to do.
	 * 
	 * @author Mateusz
	 *
	 */
	public enum ProgramTask {
		WORK_ON_FTP, WORK_ON_LOCAL_FILES, SHOW_HELP, ERROR_INFO
	}
}