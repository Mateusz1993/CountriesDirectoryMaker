package de.heinfricke.countriesmapper.fileoperations;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import au.com.bytecode.opencsv.CSVWriter;
import de.heinfricke.countriesmapper.CountriesDirectoryMake;
import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;
import de.heinfricke.countriesmapper.utils.FTPConnection;

/**
 * This class contains methods which are used to make CSV files on local system and FTP server.
 * 
 * @author mateusz
 *
 */
public class CSVFileMaker implements Maker {
	private static final Logger LOGGER = Logger.getLogger(CountriesDirectoryMake.class.getCanonicalName());
	/**
	 * Object of FTPConnection. Important if we create files on FTP server.
	 */
	private FTPConnection ftpConnection = null;

	public CSVFileMaker() {
	}

	/**
	 * This constructor will be used when user will use working on FTP server
	 * version.
	 * 
	 * @param ftpConnection
	 *            As parameter it takes FTPConnection object.
	 */
	public CSVFileMaker(FTPConnection ftpConnection) {
		this.ftpConnection = ftpConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.heinfricke.countriesmapper.fileoperations.Maker#createFiles(java.util.
	 * List, java.lang.String)
	 */
	public void createFiles(List<GroupOfCountries> listOfGroupedCountriesClasses, String path) {
		try {
			CSVWriter csvWriter = null;
			ByteArrayOutputStream baos = null;
			final String fileName = "Information.csv";

			if (ftpConnection == null) {
				csvWriter = new CSVWriter(new FileWriter(path + File.separator + fileName));
			} else {
				baos = new ByteArrayOutputStream();
				Writer writer = new BufferedWriter(new OutputStreamWriter(baos));
				csvWriter = new CSVWriter(writer);
			}

			prepareInformations(listOfGroupedCountriesClasses, csvWriter);

			if (ftpConnection != null) {
				FTPClient client = ftpConnection.getClient();
				byte[] bytes = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				client.storeFile(path + File.separator + fileName, bais);
			}
		} catch (IOException e) {
			System.out.println(
					"There was a problem while connecting to server or during creating file. Please make sure given input path, output path, host, port, username and password are correct and run application again.");
			LOGGER.log(Level.FINE, "There was a problem while connecting to server or during creating file.", e);
		}
	}

	/**
	 * This method prepare read all informations about countries from Country
	 * objects.
	 * 
	 * @param listOfGroupedCountriesClasses
	 *            As first parameter it takes list of grouped Country objects.
	 * @param writer
	 *            As second parameter it takes CSVWriter object.
	 * @throws IOException
	 */
	private void prepareInformations(List<GroupOfCountries> listOfGroupedCountriesClasses, CSVWriter writer)
			throws IOException {
		String[] titles = new String[]{"Name:", "Capital:", "Native name:", "Borders:"};
		writer.writeNext(titles);
		for (GroupOfCountries groupedCountries : listOfGroupedCountriesClasses) {
			for (Country countries : groupedCountries.getCountriesList()) {
				writer.writeNext(countries.returnInfromations());
			}
		}
		writer.close();
	}
}
