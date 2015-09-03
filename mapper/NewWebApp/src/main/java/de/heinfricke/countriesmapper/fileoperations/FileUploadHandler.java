package de.heinfricke.countriesmapper.fileoperations;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.xml.bind.JAXBException;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import de.heinfricke.countriesmapper.utils.DirectoriesActivity;
import de.heinfricke.countriesmapper.utils.InformationHandler;
import de.heinfricke.countriesmapper.utils.Worker;
import de.heinfricke.countriesmapper.utils.InformationHandler.ProgramTask;

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

		InformationHandler informationHandler = new InformationHandler(request.getHeader("FTP-Path"),
				request.getHeader("FTP-Host"), request.getHeader("FTP-Port"), request.getHeader("FTP-User"),
				request.getHeader("FTP-Password"), ProgramTask.WORK_ON_FTP,
				Boolean.valueOf(request.getHeader("FTP-XML")), Boolean.valueOf(request.getHeader("FTP-CSV")));

		DirectoriesActivity userDecision = returnUserDecision(request);

		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				InputStream stream = null;

				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						stream = item.getInputStream();
					}
				}

				Worker worker = new Worker();
				worker.countryPreparerAndFileMakerrRun(stream, true, informationHandler, userDecision);
			}
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
	 */
	private DirectoriesActivity returnUserDecision(HttpServletRequest request) {
		DirectoriesActivity userDecision;
		if (request.getHeader("FTP-DirActiv").toLowerCase().equals("delete")) {
			userDecision = DirectoriesActivity.DELETE;
		} else if (request.getHeader("FTP-DirActiv").toLowerCase().equals("replace")) {
			userDecision = DirectoriesActivity.REPLACE;
		} else {
			userDecision = DirectoriesActivity.ADD_NEW_CONTENTS;
		}
		return userDecision;
	}
}