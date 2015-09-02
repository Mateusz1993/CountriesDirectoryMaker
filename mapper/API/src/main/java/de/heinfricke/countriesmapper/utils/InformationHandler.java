package de.heinfricke.countriesmapper.utils;

public class InformationHandler {
	private boolean csv = false;
	private boolean ftp = false;
	private boolean help = false;
	private String host;
	private String inputFile;
	private boolean localFileSystem = false;
	private String outputPath;
	private String port;
	private String ftpPassword;
	private boolean restCountriesFetch = false;
	private String ftpUser;
	private boolean xml = false;
	private ProgramTask programTask;

	/**
	 * This constructor is used when user want to work on local files.
	 * 
	 * @param inputFile
	 *            As first parameter it takes path to input file.
	 * @param outputPath
	 *            As second parameter it takes output's path.
	 * @param programTask
	 *            As third parameter it takes ProgramTask ENUM.
	 */
	public InformationHandler(String inputFile, String outputPath, ProgramTask programTask) {
		this.localFileSystem = true;
		this.inputFile = inputFile;
		this.outputPath = outputPath;
		this.programTask = programTask;
	}

	/**
	 * This constructor is used when user wants to work on FTP server.
	 * 
	 * @param inputFile
	 *            As first parameter it takes path to input file.
	 * @param outputPath
	 *            As second parameter it takes output's path.
	 * @param host
	 *            As third parameter it takes FTP server's host.
	 * @param port
	 *            As fourth parameter it takes FTP server's port.
	 * @param ftpUser
	 *            As fifth parameter it takes FTP server's user name.
	 * @param ftpPassword
	 *            As sixth parameter it takes FTP server's user's password.
	 * @param programTask
	 *            As seventh parameter it takes ProgramTask ENUM.
	 */
	public InformationHandler(String inputFile, String outputPath, String host, String port, String ftpUser,
			String ftpPassword, ProgramTask programTask) {
		this.ftp = true;
		this.inputFile = inputFile;
		this.outputPath = outputPath;
		this.programTask = programTask;
		this.host = host;
		this.port = port;
		this.ftpUser = ftpUser;
		this.ftpPassword = ftpPassword;
	}

	/**
	 * This constructor is used when user wants only see help.
	 * 
	 * @param programTask
	 *            As first parameter it takes ProgramTask ENUM.
	 */
	public InformationHandler(ProgramTask programTask) {
		this.programTask = programTask;
		this.help = true;
	}

	public void setCSV(boolean csv) {
		this.csv = csv;
	}

	public void setXML(boolean xml) {
		this.xml = xml;
	}

	public void setFTP(boolean ftp) {
		this.ftp = ftp;
	}

	public void setHelp(boolean help) {
		this.help = help;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void setLocalFileSystem(boolean localFileSystem) {
		this.localFileSystem = localFileSystem;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setFTPPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public void setRestCountriesFetch(boolean restCountriesFetch) {
		this.restCountriesFetch = restCountriesFetch;
	}

	public void setFTPUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public void setProgramTask(ProgramTask programTask) {
		this.programTask = programTask;
	}

	public boolean getCSV() {
		return csv;
	}

	public boolean getXML() {
		return xml;
	}

	public boolean getFTP() {
		return ftp;
	}

	public boolean getHelp() {
		return help;
	}

	public String getHost() {
		return host;
	}

	public String getInputFile() {
		return inputFile;
	}

	public boolean getLocalFileSystem() {
		return localFileSystem;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public String getPort() {
		return port;
	}

	public String getFTPPassword() {
		return ftpPassword;
	}

	public boolean getRestCountriesFetch() {
		return restCountriesFetch;
	}

	public String getFTPUser() {
		return ftpUser;
	}

	public ProgramTask getProgramTask() {
		return programTask;
	}
	
	/**
	 * Enum to chose what program have to do.
	 * 
	 * @author Mateusz
	 *
	 */
	public enum ProgramTask {
		WORK_ON_FTP, WORK_ON_LOCAL_FILES, SHOW_HELP
	}
}