package de.heinfricke.countriesmapper.fileoperations;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.net.ftp.FTPClient;

import au.com.bytecode.opencsv.CSVWriter;
import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;
import de.heinfricke.countriesmapper.preparer.PrepareForXML;
import de.heinfricke.countriesmapper.utils.FTPConnection;

/**
 * This class contains methods to create files and directories.
 * 
 * @author mateusz
 *
 */
public class FTPFileMaker extends XMLMaker implements Maker {

	FTPConnection ftpConnection;
	String host;
	String port;
	String username;
	String password;

	public FTPFileMaker(FTPConnection ftpConnection) {
		this.ftpConnection = ftpConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.heinfricke.countriesmapper.fileoperations.Maker#createDirectories(java
	 * .util.List, java.lang.String)
	 */
	public void createFiles(List<GroupOfCountries> listOfGroupedCountriesClasses, String path) throws IOException {
		for (GroupOfCountries groupedCountries : listOfGroupedCountriesClasses) {
			checkDirectoryExists(path);

			String pathToGroupFolder = (path + File.separator + groupedCountries.getName());
			createDirectory(pathToGroupFolder);
			for (Country countries : groupedCountries.getCountriesList()) {
				String pathToSingleFile = (pathToGroupFolder + File.separator + countries.getName());
				createDirectory(pathToSingleFile);
			}
		}
	}

	/**
	 * This method checks if directory exists at FTP server.
	 * 
	 * @param dirPath
	 *            As first parameter it takes path to directory.
	 * @return It returns boolean if directory exists and throws IOExceptio id
	 *         doesn't exist.
	 * @throws IOException
	 */
	private boolean checkDirectoryExists(String dirPath) throws IOException {
		FTPClient client = ftpConnection.getClient();
		client.changeWorkingDirectory(dirPath);
		int returnCode = client.getReplyCode();
		if (returnCode == 550) {
			throw new IOException();
		}
		return true;
	}

	/**
	 * This method create new directory on FTP Server.
	 * 
	 * @param pathToSingleFile
	 * @throws IOException
	 */
	private void createDirectory(String pathToSingleFile) throws IOException {
		ftpConnection.makeDirectory(pathToSingleFile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.heinfricke.countriesmapper.fileoperations.Maker#createCSVFile(java.
	 * util.List, java.lang.String,
	 * de.heinfricke.countriesmapper.fileoperations.CSVMaker)
	 */
	public void createCSVFile(List<GroupOfCountries> listOfGroupedCountriesClasses, String path, CSVMaker csvMaker)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer writer = new BufferedWriter(new OutputStreamWriter(baos));
		CSVWriter csvWriter = new CSVWriter(writer);

		csvMaker.prepareInformations(listOfGroupedCountriesClasses, csvWriter);

		FTPClient client = ftpConnection.getClient();
		byte[] bytes = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		client.storeFile(path + File.separator + "Informations.csv", bais);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.heinfricke.countriesmapper.fileoperations.XMLMaker#createXMLFile(de.
	 * heinfricke.countriesmapper.preparer.PrepareForXML, java.lang.String,
	 * javax.xml.bind.Marshaller)
	 */
	public void createXMLFile(PrepareForXML prepareForXml, String path, Marshaller marshaller)
			throws JAXBException, IOException {
		FTPClient client = ftpConnection.getClient();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer writer = new BufferedWriter(new OutputStreamWriter(baos));
		marshaller.marshal(prepareForXml, writer);
		byte[] bytes = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		client.storeFile(path + File.separator + "Informations.xml", bais);
	}
}