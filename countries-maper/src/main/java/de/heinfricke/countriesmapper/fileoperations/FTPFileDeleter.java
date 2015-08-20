package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPFile;
import de.heinfricke.countriesmapper.utils.FTPConnection;
import de.heinfricke.countriesmapper.utils.UserInputs;

/**
 * This class has methods used for deleting files on FTP server.
 * 
 * @author mateusz
 *
 */
public class FTPFileDeleter extends Deleter {
	private FTPConnection ftpConnection;

	/**
	 * This constructor is used because we need to know if user want to delete
	 * all files or only replace existing. Also it takes object important for
	 * connection with FTP server.
	 * 
	 * @param ftpConnection
	 *            As first parameter it takes FTPConnection object.
	 * @param userInputs
	 *            As second parameter it takes UserInputs Object.
	 */
	public FTPFileDeleter(FTPConnection ftpConnection, UserInputs userInputs) {
		super(userInputs);
		this.ftpConnection = ftpConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.heinfricke.countriesmapper.fileoperations.Deleter#deleteFiles(java.
	 * lang.String)
	 */
	void deleteFiles(String pathOfGorupDirectory) throws IOException {
		FTPFile[] files;
		files = ftpConnection.listDirectories(pathOfGorupDirectory);
		for (FTPFile file : files) {
			ftpConnection.removeDirectory(pathOfGorupDirectory + File.separator + file.getName());
		}
		ftpConnection.removeDirectory(pathOfGorupDirectory);
	}
}
