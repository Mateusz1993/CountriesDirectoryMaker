package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import javax.xml.bind.*;
import de.heinfricke.countriesmapper.preparer.PrepareForXML;
import de.heinfricke.countriesmapper.utils.FTPConnection;

public class XMLFileMaker {
	/**
	 * Object of FTPConnection. Important if we create files on FTP server.
	 */
	private FTPConnection ftpConnection = null;

	public XMLFileMaker() {
	}

	/**
	 * This constructor will be used when user will use working on FTP server
	 * version.
	 * 
	 * @param ftpConnection
	 *            As parameter it takes FTPConnection object.
	 */
	public XMLFileMaker(FTPConnection ftpConnection) {
		this.ftpConnection = ftpConnection;
	}
	
	
	
	
	
	public void countryObjectsToXML(PrepareForXML prepareForXml, String path) throws JAXBException {
			JAXBContext context = JAXBContext.newInstance(PrepareForXML.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			marshaller.marshal(prepareForXml, new File(path + File.separator + "Informations.xml"));
			
			
			
			
			
			
			
	}
}
