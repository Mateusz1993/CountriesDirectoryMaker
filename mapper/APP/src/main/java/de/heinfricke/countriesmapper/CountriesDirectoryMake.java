package de.heinfricke.countriesmapper;

import java.io.*;
import java.net.SocketException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import de.heinfricke.countriesmapper.reader.*;
import de.heinfricke.countriesmapper.utils.ApacheCLI;
import de.heinfricke.countriesmapper.utils.CLIVariables;
import de.heinfricke.countriesmapper.utils.CLIVariables.ProgramTask;
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
			ApacheCLI apacheCLI = new ApacheCLI();
			cmd = apacheCLI.readFromCommandLine(args, options);
			CLIVariables cliVariables = apacheCLI.returnCLIVariables(cmd);
			countriesDirectoryMake.handleHelp(options, cliVariables);

			// Read all countries to "Set".
			CountriesReader countriesReader = new CountriesReader();
			Set<Country> sortedCountries = countriesReader.readCountries(cmd.getOptionValue("i"),
					cmd.hasOption("restCountriesFetch"));

			// Prepare groups of countries (for example: ABC = (Albania,
			// Czech Republic), PQR = (Poland, Qatar)).
			GroupOfCountries groupOfCountries = new GroupOfCountries();
			List<GroupOfCountries> listOfGroupedCountriesClasses = groupOfCountries
					.organizeCountriesInGroups(sortedCountries);

			// Delete and create directories.
			countriesDirectoryMake.executeTask(cmd, options, cliVariables, listOfGroupedCountriesClasses,
					sortedCountries);
		} catch (IllegalArgumentException e) {
			System.out.println("Unknown value. Please use D, R or A as your decision.");
			LOGGER.log(Level.FINE, "Unknown value.", e);
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
		} catch (JAXBException e) {
			System.out.println(
					"There was something invalid during preparing XML file. Please make sure that all countries and path to directories are correct.");
			LOGGER.log(Level.FINE, "JAXB Exception.", e);
		} catch (JSONException e) {
			System.out.println(
					"There was something invalid in JSON. You propably wrote country which doesn't exist or is not supported by our application.");
			LOGGER.log(Level.FINE, "Invalid expression.", e);
		} catch (RuntimeException e) {
			System.out.println(
					"You propably wrote country which doesn't exists or is not supported by our application. Please make sure that country's name is correct and run application again.");
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
	 * @param cliVariables
	 *            As third parameter it takes program task (decision about what
	 *            program have to do).
	 * @param listOfGroupedCountriesClasses
	 *            As fourth parameter it takes list of GroupOfCountries objects.
	 * @throws IOException
	 * @throws JAXBException
	 */
	private void executeTask(CommandLine cmd, Options options, CLIVariables cliVariables,
			List<GroupOfCountries> listOfGroupedCountriesClasses, Set<Country> sortedCountries)
					throws IOException, JAXBException, IllegalArgumentException {

		FTPConnection ftpConnection = new FTPConnection();
		Deleter deleter = createDeleter(cliVariables, ftpConnection);
		Maker maker = createMaker(cliVariables, ftpConnection);

		deleter.deleteDirectories(listOfGroupedCountriesClasses, cliVariables.getOutputPath());
		maker.createFiles(listOfGroupedCountriesClasses, cliVariables.getOutputPath());

		createOutputCSV(cliVariables, listOfGroupedCountriesClasses, maker);
		createOutputXML(cliVariables, sortedCountries, ftpConnection);

		if (ftpConnection.checkFTPConnection()) {
			ftpConnection.makeDisconnection();
		}
	}

	/**
	 * This method create new XML file.
	 * 
	 * @param cliVariables
	 *            As first parameter it takes CLIVariables object.
	 * @param sortedCountries
	 *            As second parameter it takes set of sorted Countries.
	 * @param ftpConnection
	 *            As third parameter it takes FPTConnection object.
	 * @throws JAXBException
	 * @throws IOException
	 */
	private void createOutputXML(CLIVariables cliVariables, Set<Country> sortedCountries, FTPConnection ftpConnection)
			throws JAXBException, IOException {
		if (cliVariables.getXML()) {
			XMLMaker xmlMaker = new FileMaker();
			if (ftpConnection.checkFTPConnection()) {
				xmlMaker = new FTPFileMaker(ftpConnection);
			}
			PrepareForXML preareForXml = new PrepareForXML();
			preareForXml.setCountries(sortedCountries);
			xmlMaker.countryObjectsToXML(preareForXml, cliVariables.getOutputPath());
		}
	}

	/**
	 * This method create new CSV file.
	 * 
	 * @param cliVariables
	 *            As first parameter it takes CLIVariables object.
	 * @param listOfGroupedCountriesClasses
	 *            As second parameter it take List of GroupOfCountries objects.
	 * @param maker
	 *            As third parameter it takes Maker object.
	 * @throws IOException
	 */
	private void createOutputCSV(CLIVariables cliVariables, List<GroupOfCountries> listOfGroupedCountriesClasses,
			Maker maker) throws IOException {
		if (cliVariables.getCSV()) {
			CSVMaker csvMaker = new CSVMaker();
			maker.createCSVFile(listOfGroupedCountriesClasses, cliVariables.getOutputPath(), csvMaker);
		}
	}

	/**
	 * This method create Deleter object.
	 * 
	 * @param cliVariables
	 *            As first parameter it takes CLIVariables object.
	 * @param ftpConnection
	 *            As second parameter it takes FTPConnection object.
	 * @return It returns Deleter object.
	 * @throws SocketException
	 * @throws IOException
	 */
	private Deleter createDeleter(CLIVariables cliVariables, FTPConnection ftpConnection)
			throws SocketException, IOException {
		UserInputs userInputs = new UserInputs();
		Deleter deleter = new LocalFileDeleter(userInputs);
		if (cliVariables.getProgramTask() == ProgramTask.WORK_ON_FTP) {
			ftpConnection.makeConnection(cliVariables.getHost(), cliVariables.getPort(), cliVariables.getFTPUser(),
					cliVariables.getFTPPassword());
			deleter = new FTPFileDeleter(ftpConnection, userInputs);
		}
		return deleter;
	}

	/**
	 * This method create Maker object.
	 * 
	 * @param cliVariables
	 *            As first parameter it takes CLIVariables object.
	 * @param ftpConnection
	 *            As second parameter it takes FTPConnection object.
	 * @return It returns Maker object.
	 */
	private Maker createMaker(CLIVariables cliVariables, FTPConnection ftpConnection) {
		Maker maker = new FileMaker();
		if (ftpConnection.checkFTPConnection()) {
			maker = new FTPFileMaker(ftpConnection);
		}
		return maker;
	}

	/**
	 * This method just holds all outputs which user will see after choosing
	 * "Help".
	 * 
	 * @param options
	 *            It takes as parameter Options object.
	 */
	private void handleHelp(Options options, CLIVariables cliVariables) {
		if (cliVariables.getProgramTask() == ProgramTask.SHOW_HELP) {
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
}