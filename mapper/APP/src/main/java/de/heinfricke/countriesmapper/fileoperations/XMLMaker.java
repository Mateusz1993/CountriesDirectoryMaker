package de.heinfricke.countriesmapper.fileoperations;

import java.io.*;
import javax.xml.bind.*;
import de.heinfricke.countriesmapper.preparer.PrepareForXML;

/**
 * @author mateusz
 *
 */
public abstract class XMLMaker {
	/**
	 * This method create XML files.
	 * 
	 * @param prepareForXml
	 *            As first parameter it takes Country objects prepared to
	 *            creating XML.
	 * @param path
	 *            As second parameter it takes output path where XML file will
	 *            be created.
	 * @param marshaller
	 *            As third parameter it takes Marshaller object.
	 * @throws JAXBException
	 * @throws IOException
	 */
	public abstract void createXMLFile(PrepareForXML prepareForXml, String path, Marshaller marshaller)
			throws JAXBException, IOException;

	/**
	 * This method convert Country Objects to XML representation and save it to
	 * file.
	 * 
	 * @param prepareForXml
	 * @param path
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void countryObjectsToXML(PrepareForXML prepareForXml, String path) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(PrepareForXML.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		createXMLFile(prepareForXml, path, marshaller);
	}
}