package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import au.com.bytecode.opencsv.CSVWriter;
import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupOfCountries;
import de.heinfricke.countriesmapper.preparer.PrepareForXML;

/**
 * This class contains methods which are used to make new files and directories.
 * 
 * @author mateusz
 *
 */
public class FileMaker extends XMLMaker implements Maker {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.heinfricke.countriesmapper.fileoperations.Maker#createDirectories(java
	 * .util.List, java.lang.String)
	 */
	public void createFiles(List<GroupOfCountries> listOfGroupedCountriesClasses, String path) {
		for (GroupOfCountries groupedCountries : listOfGroupedCountriesClasses) {
			String pathToGroupFolder = (path + File.separator + groupedCountries.getName());
			File groupFile = createFile(pathToGroupFolder);
			groupFile.mkdirs();
			for (Country countries : groupedCountries.getCountriesList()) {
				String pathToSingleFile = (pathToGroupFolder + File.separator + countries.getName());
				File singleFile = createFile(pathToSingleFile);
				singleFile.mkdirs();
			}
		}
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
		CSVWriter csvWriter = new CSVWriter(new FileWriter(path + File.separator + "Informations.csv"));
		csvMaker.prepareInformations(listOfGroupedCountriesClasses, csvWriter);
	}

	protected File createFile(String pathToSingleFile) {
		return new File(pathToSingleFile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.heinfricke.countriesmapper.fileoperations.XMLMaker#createXMLFile(de.
	 * heinfricke.countriesmapper.preparer.PrepareForXML, java.lang.String,
	 * javax.xml.bind.Marshaller)
	 */
	public void createXMLFile(PrepareForXML prepareForXml, String path, Marshaller marshaller) throws JAXBException {
		marshaller.marshal(prepareForXml, new File(path + File.separator + "Informations.xml"));
	}
}
