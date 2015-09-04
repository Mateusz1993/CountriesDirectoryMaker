package de.heinfricke.countriesmapper.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;

import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.fileoperations.CSVMaker;
import de.heinfricke.countriesmapper.fileoperations.Deleter;
import de.heinfricke.countriesmapper.fileoperations.FTPFileDeleter;
import de.heinfricke.countriesmapper.fileoperations.FTPFileMaker;
import de.heinfricke.countriesmapper.fileoperations.FileMaker;
import de.heinfricke.countriesmapper.fileoperations.LocalFileDeleter;
import de.heinfricke.countriesmapper.fileoperations.Maker;
import de.heinfricke.countriesmapper.fileoperations.XMLMaker;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;
import de.heinfricke.countriesmapper.preparer.PrepareForXML;
import de.heinfricke.countriesmapper.reader.CountriesReader;
import de.heinfricke.countriesmapper.utils.InformationHandler.ProgramTask;

public class CountriesStructure {

	public void prepareCountriesStructure(InputStream in, boolean useRestCountriesFetch, InformationHandler informationsHandler,
			DirectoriesActivity userDecision) throws IOException, JAXBException, JSONException, RuntimeException, UsernameOrPasswordException {

		// Read all countries to "Set".
		CountriesReader countriesReader = new CountriesReader();
		Set<Country> sortedCountries = countriesReader.readCountries(in, useRestCountriesFetch);

		// Prepare groups of countries (for example: ABC = (Albania,
		// Czech Republic), PQR = (Poland, Qatar)).
		GroupOfCountries groupOfCountries = new GroupOfCountries();
		List<GroupOfCountries> listOfGroupedCountriesClasses = groupOfCountries
				.organizeCountriesInGroups(sortedCountries);

		// Delete and create directories.
		CountriesStructure worker = new CountriesStructure();
		worker.executeTask(informationsHandler, listOfGroupedCountriesClasses, sortedCountries, userDecision);
	}

	/**
	 * This method executes tasks. It can delete directories, create
	 * directories, show help etc.
	 * 
	 * @param informationsHandler
	 *            As second parameter it takes program task (decision about what
	 *            program have to do).
	 * @param listOfGroupedCountriesClasses
	 *            As third parameter it takes list of GroupOfCountries objects.
	 * @throws IOException
	 * @throws JAXBException
	 * @throws UsernameOrPasswordException 
	 */
	private void executeTask(InformationHandler informationsHandler,
			List<GroupOfCountries> listOfGroupedCountriesClasses, Set<Country> sortedCountries,
			DirectoriesActivity userDecision) throws IOException, JAXBException, IllegalArgumentException, UsernameOrPasswordException {

		FTPConnection ftpConnection = new FTPConnection();
		Deleter deleter = createDeleter(informationsHandler, ftpConnection, userDecision);
		Maker maker = createMaker(ftpConnection);

		deleter.deleteDirectories(listOfGroupedCountriesClasses, informationsHandler.getOutputPath());
		maker.createFiles(listOfGroupedCountriesClasses, informationsHandler.getOutputPath());

		createOutputCSV(informationsHandler, listOfGroupedCountriesClasses, maker);
		createOutputXML(informationsHandler, sortedCountries, ftpConnection);

		if (ftpConnection.checkFTPConnection()) {
			ftpConnection.makeDisconnection();
		}
	}

	/**
	 * This method create new XML file.
	 * 
	 * @param informationsHandler
	 *            As first parameter it takes InformationHandler object.
	 * @param sortedCountries
	 *            As second parameter it takes set of sorted Countries.
	 * @param ftpConnection
	 *            As third parameter it takes FPTConnection object.
	 * @throws JAXBException
	 * @throws IOException
	 */
	private void createOutputXML(InformationHandler informationsHandler, Set<Country> sortedCountries,
			FTPConnection ftpConnection) throws JAXBException, IOException {
		if (informationsHandler.getXML()) {
			XMLMaker xmlMaker = new FileMaker();
			if (ftpConnection.checkFTPConnection()) {
				xmlMaker = new FTPFileMaker(ftpConnection);
			}
			PrepareForXML preareForXml = new PrepareForXML();
			preareForXml.setCountries(sortedCountries);
			xmlMaker.countryObjectsToXML(preareForXml, informationsHandler.getOutputPath());
		}
	}

	/**
	 * This method create new CSV file.
	 * 
	 * @param informationsHandler
	 *            As first parameter it takes InformationHandler object.
	 * @param listOfGroupedCountriesClasses
	 *            As second parameter it take List of GroupOfCountries objects.
	 * @param maker
	 *            As third parameter it takes Maker object.
	 * @throws IOException
	 */
	private void createOutputCSV(InformationHandler informationsHandler,
			List<GroupOfCountries> listOfGroupedCountriesClasses, Maker maker) throws IOException {
		if (informationsHandler.getCSV()) {
			CSVMaker csvMaker = new CSVMaker();
			maker.createCSVFile(listOfGroupedCountriesClasses, informationsHandler.getOutputPath(), csvMaker);
		}
	}

	/**
	 * This method create Deleter object.
	 * 
	 * @param informationsHandler
	 *            As first parameter it takes InformationHandler object.
	 * @param ftpConnection
	 *            As second parameter it takes FTPConnection object.
	 * @return It returns Deleter object.
	 * @throws SocketException
	 * @throws IOException
	 * @throws UsernameOrPasswordException 
	 */
	private Deleter createDeleter(InformationHandler informationsHandler, FTPConnection ftpConnection,
			DirectoriesActivity userDecision) throws SocketException, IOException, UsernameOrPasswordException {
		Deleter deleter = new LocalFileDeleter(userDecision);
		if (informationsHandler.getProgramTask() == ProgramTask.WORK_ON_FTP) {
			ftpConnection.makeConnection(informationsHandler.getHost(), informationsHandler.getPort(),
					informationsHandler.getFTPUser(), informationsHandler.getFTPPassword());
			deleter = new FTPFileDeleter(ftpConnection, userDecision);
		}
		if (userDecision == DirectoriesActivity.DELETE){
			deleter.deleteCSVFile(informationsHandler.getOutputPath());
			deleter.deleteXMLFile(informationsHandler.getOutputPath());
		}
		return deleter;
	}

	/**
	 * This method create Maker object.
	 * 
	 * @param ftpConnection
	 *            As first parameter it takes FTPConnection object.
	 * @return It returns Maker object.
	 */
	private Maker createMaker(FTPConnection ftpConnection) {
		Maker maker = new FileMaker();
		if (ftpConnection.checkFTPConnection()) {
			maker = new FTPFileMaker(ftpConnection);
		}
		return maker;
	}

}
