package de.heinfricke.countriesmapper;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import de.heinfricke.countriesmapper.utils.*;
import de.heinfricke.countriesmapper.utils.InformationHandler.ProgramTask;
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
			InformationHandler cliVariables = apacheCLI.returnCLIVariables(cmd);
			countriesDirectoryMake.handleHelp(options, cliVariables);
			UserInputs userInputs = new UserInputs();
			DirectoriesActivity userDecision = userInputs.userDecisionAboutDirectories();
			InputStream in = new FileInputStream(cmd.getOptionValue("i"));

			Worker worker = new Worker();
			worker.countryPreparerAndFileMakerRun(in, cliVariables.getRestCountriesFetch(), cliVariables, userDecision);

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
					"There was something invalid in JSON. You probably wrote country which doesn't exist or is not supported by our application.");
			LOGGER.log(Level.FINE, "Invalid expression.", e);
		} catch (RuntimeException e) {
			System.out.println(
					"You probably wrote country which doesn't exists or is not supported by our application. Please make sure that country's name is correct and run application again.");
			LOGGER.log(Level.FINE, "HTTP error.", e);
		}
	}

	/**
	 * This method just holds all outputs which user will see after choosing
	 * "Help".
	 * 
	 * @param options
	 *            It takes as parameter Options object.
	 */
	private void handleHelp(Options options, InformationHandler cliVariables) {
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