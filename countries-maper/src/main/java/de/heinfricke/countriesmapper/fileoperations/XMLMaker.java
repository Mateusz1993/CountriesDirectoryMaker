package de.heinfricke.countriesmapper.fileoperations;

import java.io.*;
import javax.xml.bind.*;
import de.heinfricke.countriesmapper.preparer.PrepareForXML;

/**
 * @author mateusz
 *
 */
public abstract class XMLMaker {
	public abstract void createXMLFile(PrepareForXML prepareForXml, String path, Marshaller marshaller) throws JAXBException, IOException;
	
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
		marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
		marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		createXMLFile(prepareForXml, path, marshaller);
	}
}