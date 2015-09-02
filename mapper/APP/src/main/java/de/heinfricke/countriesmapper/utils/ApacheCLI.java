package de.heinfricke.countriesmapper.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.heinfricke.countriesmapper.utils.InformationHandler.ProgramTask;

public class ApacheCLI {
	
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
	public CommandLine readFromCommandLine(String[] args, Options options) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		options.addOption("H", "help", false, "Show help.")
				.addOption("l", "localFileSystem", false, "Make directories in your local system.")
				.addOption("f", "ftp", false, "Make directories in your FTP Server.")
				.addOption("i", "inputFile", true, "Path to your input file.")
				.addOption("o", "outputPath", true, "Path to your output.").addOption("h", "host", true, "FTP host.")
				.addOption("p", "port", true, "FTP port.").addOption("u", "ftpUser", true, "FTP user name.")
				.addOption("pw", "ftpPassword", true, "FTP user password.")
				.addOption("restCountriesFetch", "r", false, "Make file with informations about each country.")
				.addOption("csv", "c", false, "Create CSV file with informations. (default)")
				.addOption("xml", "x", false, "Create XML file with informations.");
		return parser.parse(options, args);
	}

	
	/**
	 * This function takes as parameter CommandLine object and basing on user's
	 * parameters it uses correct constructor to create CLIVariables object.
	 * 
	 * @param cmd
	 *            As parameter it takes CommandLine object.
	 * @return It return decision from ProgramTask enum.
	 * @throws OwnExceptions
	 */
	public InformationHandler returnCLIVariables(CommandLine cmd) throws ParseException {
		InformationHandler cliVariables;
		if ((cmd.hasOption("l") && cmd.hasOption("i") && cmd.hasOption("o"))) {
			cliVariables = new InformationHandler(cmd.getOptionValue("i"), cmd.getOptionValue("o"),
					ProgramTask.WORK_ON_LOCAL_FILES);
		} else if (cmd.hasOption("f") && cmd.hasOption("h") && cmd.hasOption("p") && cmd.hasOption("u")
				&& cmd.hasOption("pw") && cmd.hasOption("o")) {
			cliVariables = new InformationHandler(cmd.getOptionValue("i"), cmd.getOptionValue("o"), cmd.getOptionValue("h"),
					cmd.getOptionValue("p"), cmd.getOptionValue("u"), cmd.getOptionValue("pw"),
					ProgramTask.WORK_ON_FTP);
		} else if (cmd.hasOption("H")) {
			cliVariables = new InformationHandler(ProgramTask.SHOW_HELP);
		} else {
			throw new ParseException("There was invalid expression. Please use '-H' for help.");
		}

		if (cmd.hasOption("restCountriesFetch")) {
			cliVariables.setRestCountriesFetch(true);
			if (cmd.hasOption("xml")) {
				cliVariables.setXML(true);
			} else {
				cliVariables.setCSV(true);
			}
		}
		return cliVariables;
	}
}
