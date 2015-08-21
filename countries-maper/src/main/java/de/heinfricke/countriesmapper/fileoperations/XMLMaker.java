package de.heinfricke.countriesmapper.fileoperations;

import java.io.*;
import javax.xml.bind.*;
import org.apache.commons.net.ftp.FTPClient;

import de.heinfricke.countriesmapper.preparer.PrepareForXML;
import de.heinfricke.countriesmapper.utils.FTPConnection;

/**
 * @author mateusz
 *
 */
public class XMLMaker {
	/**
	 * Object of FTPConnection. Important if we create files on FTP server.
	 */
	private FTPConnection ftpConnection = null;

	public XMLMaker() {
	}

	/**
	 * This constructor will be used when user will use working on FTP server
	 * version.
	 * 
	 * @param ftpConnection
	 *            As parameter it takes FTPConnection object.
	 */
	public XMLMaker(FTPConnection ftpConnection) {
		this.ftpConnection = ftpConnection;
	}

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

		if (ftpConnection == null) {
			marshaller.marshal(prepareForXml, new File(path + File.separator + "Informations.xml"));
		} else {
			FTPClient client = ftpConnection.getClient();
			File xmlFile = new File("Informations.xml");
			marshaller.marshal(prepareForXml, xmlFile);
			InputStream inputStream = new FileInputStream(xmlFile);
			client.storeFile(path + File.separator + "Informations.xml", inputStream);
			xmlFile.delete();
			inputStream.close();
		}
	}
}