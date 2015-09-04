package de.heinfricke.countriesmapper.fileoperations;

import java.io.*;
import java.net.SocketException;
import java.util.*;
import javax.servlet.http.*;
import javax.xml.bind.JAXBException;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import de.heinfricke.countriesmapper.utils.DirectoriesActivity;
import de.heinfricke.countriesmapper.utils.InformationHandler;
import de.heinfricke.countriesmapper.utils.CountriesStructure;
import de.heinfricke.countriesmapper.utils.InformationHandler.ProgramTask;
import de.heinfricke.countriesmapper.utils.UsernameOrPasswordException;

/**
 * This class is representation of RESTClient with Java's HttpServlet.
 * 
 * @author mateusz
 *
 */
public class FileUploadHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			InformationHandler informationHandler = new InformationHandler(request.getHeader("FTP-Path"),
					request.getHeader("FTP-Host"), request.getHeader("FTP-Port"), request.getHeader("FTP-User"),
					request.getHeader("FTP-Password"), ProgramTask.WORK_ON_FTP, XMLcreate(request), CSVcreate(request));

			DirectoriesActivity userDecision = returnUserDecision(request);
			if (ServletFileUpload.isMultipartContent(request)) {
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				InputStream stream = null;

				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						stream = item.getInputStream();
					}
				}

				CountriesStructure worker = new CountriesStructure();
				worker.prepareCountriesStructure(stream, true, informationHandler, userDecision);
			}
		} catch (UsernameOrPasswordException e) {
			response.setStatus(401);
		} catch (SocketException e) {
			response.setStatus(404);
		} catch (IOException e) {
			response.setStatus(400);
		} catch (FileUploadException e) {
			response.setStatus(500);
		} catch (JAXBException e) {
			response.setStatus(500);
		} catch (Exception e) {
			response.setStatus(500);
		}
	}

	/**
	 * This method return user decision about destination of directories.
	 * 
	 * @param request
	 *            As first parameter it takes HttpServletRequest.
	 * @return This method returns user decision about destination of
	 *         directories.
	 * @throws IOException
	 */
	private DirectoriesActivity returnUserDecision(HttpServletRequest request) throws IOException {
		DirectoriesActivity userDecision;
		if (request.getHeader("FTP-ExistingFilesActivity").toLowerCase().equals("delete")) {
			userDecision = DirectoriesActivity.DELETE;
		} else if (request.getHeader("FTP-ExistingFilesActivity").toLowerCase().equals("replace")) {
			userDecision = DirectoriesActivity.REPLACE;
		} else if (request.getHeader("FTP-ExistingFilesActivity").toLowerCase().equals("add")) {
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
	 *            As first parameter it takes HttpServletRequest object.
	 * @return It returns boolean or IOException.
	 * @throws IOException
	 */
	private boolean XMLcreate(HttpServletRequest request) throws IOException {
		if (request.getHeader("FTP-XML").toLowerCase().equals("true")) {
			return true;
		} else if (request.getHeader("FTP-XML").toLowerCase().equals("false")) {
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
	 *            As first parameter it takes HttpServletRequest object.
	 * @return It returns boolean or IOException.
	 * @throws IOException
	 */
	private boolean CSVcreate(HttpServletRequest request) throws IOException {
		if (request.getHeader("FTP-CSV").toLowerCase().equals("true")) {
			return true;
		} else if (request.getHeader("FTP-CSV").toLowerCase().equals("false")) {
			return false;
		} else {
			throw new IOException();
		}
	}
}