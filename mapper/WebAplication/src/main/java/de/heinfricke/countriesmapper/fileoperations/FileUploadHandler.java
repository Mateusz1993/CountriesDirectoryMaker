package de.heinfricke.countriesmapper.fileoperations;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.xml.bind.JAXBException;

import org.apache.commons.fileupload.servlet.*;
import de.heinfricke.countriesmapper.utils.*;
import de.heinfricke.countriesmapper.utils.InformationHandler.ProgramTask;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import javax.servlet.ServletException;

/**
 * This class contains important methods to use the applicationthorugh the
 * website.
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String directoriesDecision = null;
		InputStream stream = null;

		if (response.getStatus() == 200) {
			if (ServletFileUpload.isMultipartContent(request)) {
				try {
					@SuppressWarnings("unchecked")
					List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
					InformationHandler informationHandler = new InformationHandler(ProgramTask.WORK_ON_FTP);

					for (FileItem item : multiparts) {
						if (item.isFormField()) {
							if (item.getFieldName().equals("port")) {
								informationHandler.setPort(item.getString());
							} else if (item.getFieldName().equals("username")) {
								informationHandler.setFTPUser(item.getString());
							} else if (item.getFieldName().equals("password")) {
								informationHandler.setFTPPassword(item.getString());
							} else if (item.getFieldName().equals("path")) {
								informationHandler.setOutputPath(item.getString());
							} else if (item.getFieldName().equals("host")) {
								informationHandler.setHost(item.getString());
							} else if (item.getFieldName().equals("csv")) {
								informationHandler.setCSV(true);
							} else if (item.getFieldName().equals("xml")) {
								informationHandler.setXML(true);
							} else if (item.getFieldName().equals("directories")) {
								directoriesDecision = item.getString();
							}
						}

						if (!item.isFormField()) {
							stream = item.getInputStream();
						}
					}

					DirectoriesActivity userDecision = returnUserDecision(directoriesDecision);

					Worker worker = new Worker();
					worker.countryPreparerAndFileMakerRun(stream, true, informationHandler, userDecision);

					request.getRequestDispatcher("/result.jsp").forward(request, response);
				} catch (IOException e) {
					request.getRequestDispatcher("/IOException.jsp").forward(request, response);
				} catch (FileUploadException e) {
					request.getRequestDispatcher("/FileUploadException.jsp").forward(request, response);
				} catch (JAXBException e) {
					request.getRequestDispatcher("/JAXBException.jsp").forward(request, response);
				} catch (Exception e) {
					request.getRequestDispatcher("/JSONException.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("message", "Sorry this Servlet only handles file upload request");
			}
		} else {
			request.getRequestDispatcher("/IOException.jsp").forward(request, response);
		}
	}

	/**
	 * This method return user decision about destination of directories.
	 * 
	 * @param directoriesDecision
	 *            As first parameter it takes directoriesDecision String.
	 * @return This method returns user decision about destination of
	 *         directories.
	 */
	private DirectoriesActivity returnUserDecision(String directoriesDecision) {
		DirectoriesActivity userDecision;
		if (directoriesDecision.equals("add")) {
			userDecision = DirectoriesActivity.ADD_NEW_CONTENTS;
		} else if (directoriesDecision.equals("replace")) {
			userDecision = DirectoriesActivity.REPLACE;
		} else {
			userDecision = DirectoriesActivity.DELETE;
		}
		return userDecision;
	}
}