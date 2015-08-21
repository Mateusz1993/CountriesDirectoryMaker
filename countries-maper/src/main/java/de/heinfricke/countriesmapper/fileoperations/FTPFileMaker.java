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
			String pathToGroupFolder = (path + File.separator + groupedCountries.getName());
			createDirectory(pathToGroupFolder);
			for (Country countries : groupedCountries.getCountriesList()) {
				String pathToSingleFile = (pathToGroupFolder + File.separator + countries.getName());
				createDirectory(pathToSingleFile);
			}
		}
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
	 * de.heinfricke.countriesmapper.fileoperations.XMLMaker#createXMLFile(de.
	 * heinfricke.countriesmapper.preparer.PrepareForXML, java.lang.String,
	 * javax.xml.bind.Marshaller)
	 */
	public void createXMLFile(PrepareForXML prepareForXml, String path, Marshaller marshaller)
			throws JAXBException, IOException {
		FTPClient client = ftpConnection.getClient();
		ByteArrayOutputStream baos = null;
		baos = new ByteArrayOutputStream();
		Writer writer = new BufferedWriter(new OutputStreamWriter(baos));
		marshaller.marshal(prepareForXml, writer);
		byte[] bytes = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		client.storeFile(path + File.separator + "Informations.xml", bais);
	}
}