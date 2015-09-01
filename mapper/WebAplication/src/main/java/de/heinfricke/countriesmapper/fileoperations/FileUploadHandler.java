package de.heinfricke.countriesmapper.fileoperations;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.xml.bind.JAXBException;

import org.apache.commons.fileupload.servlet.*;
import org.codehaus.jettison.json.JSONException;

import de.heinfricke.countriesmapper.country.*;
import de.heinfricke.countriesmapper.preparer.*;
import de.heinfricke.countriesmapper.reader.*;
import de.heinfricke.countriesmapper.utils.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import javax.servlet.ServletException;

public class FileUploadHandler extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String port = null;
		String username = null;
		String password = null;
		String path = null;
		String host = null;
		InputStream stream = null;
		boolean csv = false;
		boolean xml = false;

		// process only if its multipart content
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

				for (FileItem item : multiparts) {
					if (item.isFormField()) {
						if (item.getFieldName().equals("port")) {
							port = item.getString();
						}
						if (item.getFieldName().equals("username")) {
							username = item.getString();
						}
						if (item.getFieldName().equals("password")) {
							password = item.getString();
						}
						if (item.getFieldName().equals("path")) {
							path = item.getString();
						}
						if (item.getFieldName().equals("host")) {
							host = item.getString();
						}

						if (item.getFieldName().equals("csv")) {
							csv = true;
						}

						if (item.getFieldName().equals("xml")) {
							xml = true;
						}
					}

					if (!item.isFormField()) {
						stream = item.getInputStream();
					}
				}

				// Read all countries to "Set".
				// InputStream stream = item.openStream();
				CountriesReader countriesReader = new CountriesReader();
				Set<Country> sortedCountries = countriesReader.readCountries(stream, true);

				// Prepare groups of countries (for example: ABC =
				// (Albania,
				// Czech Republic), PQR = (Poland, Qatar)).
				GroupOfCountries groupOfCountries = new GroupOfCountries();
				List<GroupOfCountries> listOfGroupedCountriesClasses = groupOfCountries
						.organizeCountriesInGroups(sortedCountries);

				FTPConnection ftpConnection = new FTPConnection();
				ftpConnection.makeConnection(host, port, username, password);

				UserInputs userInputs = new UserInputs();
				Deleter deleter = new FTPFileDeleter(ftpConnection, userInputs);
				deleter.deleteDirectories(listOfGroupedCountriesClasses, path);

				Maker maker = new FTPFileMaker(ftpConnection);
				maker.createFiles(listOfGroupedCountriesClasses, path);

				if (xml) {
					XMLMaker xmlMaker = new FTPFileMaker(ftpConnection);
					PrepareForXML preareForXml = new PrepareForXML();
					preareForXml.setCountries(sortedCountries);
					xmlMaker.countryObjectsToXML(preareForXml, path);
				}

				if (csv) {
					CSVMaker csvMaker = new CSVMaker();
					maker.createCSVFile(listOfGroupedCountriesClasses, path, csvMaker);
				}

				ftpConnection.makeDisconnection();

				// File uploaded successfully
				request.setAttribute("message", "File Uploaded Successfully");

			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			request.setAttribute("message", "Sorry this Servlet only handles file upload request");
		}
		request.getRequestDispatcher("/result.jsp").forward(request, response);
	}
}