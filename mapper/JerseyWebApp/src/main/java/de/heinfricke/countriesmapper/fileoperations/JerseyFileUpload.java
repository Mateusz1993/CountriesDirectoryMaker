package de.heinfricke.countriesmapper.fileoperations;

import java.io.*;
import java.net.SocketException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBException;
import com.sun.jersey.multipart.*;

import de.heinfricke.countriesmapper.utils.*;
import de.heinfricke.countriesmapper.utils.InformationHandler.ProgramTask;

@Path("/ftpaction")
public class JerseyFileUpload {

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postMsg(@HeaderParam("FTP-Host") String Host, @HeaderParam("FTP-Port") String Port,
			@HeaderParam("FTP-User") String User, @HeaderParam("FTP-Password") String Password,
			@HeaderParam("FTP-Path") String Path, @HeaderParam("FTP-CSV") String CSV,
			@HeaderParam("FTP-XML") String XML, @HeaderParam("FTP-ExistingFilesActivity") String ExistingFilesActivity,
			@FormDataParam("file") InputStream inputStream) {

		try {
			InformationHandler informationHandler = new InformationHandler(Path, Host, Port, User, Password,
					ProgramTask.WORK_ON_FTP, XMLcreate(XML), CSVcreate(CSV));

			DirectoriesActivity userDecision = returnUserDecision(ExistingFilesActivity);

			CountriesStructure worker = new CountriesStructure();
			worker.prepareCountriesStructure(inputStream, true, informationHandler, userDecision);

		} catch (UsernameOrPasswordException e) {
			return Response.status(401).build();
		} catch (SocketException e) {
			return Response.status(404).build();
		} catch (IOException e) {
			return Response.status(400).build();
		} catch (JAXBException e) {
			return Response.status(500).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}
		return Response.status(200).entity("Success!").build();
	}

	/**
	 * This method return user decision about destination of directories.
	 * 
	 * @param request
	 *            As first parameter it takes string.
	 * @return This method returns user decision about destination of
	 *         directories.
	 * @throws IOException
	 */
	private DirectoriesActivity returnUserDecision(String ExistingFilesActivity) throws IOException {
		DirectoriesActivity userDecision;
		if (ExistingFilesActivity.toLowerCase().equals("delete")) {
			userDecision = DirectoriesActivity.DELETE;
		} else if (ExistingFilesActivity.toLowerCase().equals("replace")) {
			userDecision = DirectoriesActivity.REPLACE;
		} else if (ExistingFilesActivity.toLowerCase().equals("add")) {
			userDecision = DirectoriesActivity.ADD_NEW_CONTENTS;
		} else {
			throw new IOException();
		}
		return userDecision;
	}

	/**
	 * This method returns true if user wants to have XML file with
	 * informations, false if user doesn't want to have XML file or it throws
	 * IOException if user writes wrong command.
	 * 
	 * @param request
	 *            As first parameter it takes string.
	 * @return It returns boolean or IOException.
	 * @throws IOException
	 */
	private boolean XMLcreate(String XML) throws IOException {
		if (XML.toLowerCase().equals("true")) {
			return true;
		} else if (XML.toLowerCase().equals("false")) {
			return false;
		} else {
			throw new IOException();
		}
	}

	/**
	 * This method returns true if user wants to have CSV file with
	 * informations, false if user doesn't want to have CSV file or it throws
	 * IOException if user writes wrong command.
	 * 
	 * @param request
	 *            As first parameter it takes string.
	 * @return It returns boolean or IOException.
	 * @throws IOException
	 */
	private boolean CSVcreate(String CSV) throws IOException {
		if (CSV.toLowerCase().equals("true")) {
			return true;
		} else if (CSV.toLowerCase().equals("false")) {
			return false;
		} else {
			throw new IOException();
		}
	}
}
