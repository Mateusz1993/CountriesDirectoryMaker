package de.heinfricke.countriesmapper.fileoperations;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import de.heinfricke.countriesmapper.CountriesDirectoryMake;
import de.heinfricke.countriesmapper.country.Country;
import de.heinfricke.countriesmapper.preparer.GroupsPreparer;
import de.heinfricke.countriesmapper.utils.UserInputs;
import de.heinfricke.countriesmapper.utils.UserInputs.DirectoriesActivity;

public class FTPFileDeleter implements DeleterInterface {
	private static final Logger LOGGER = Logger.getLogger(CountriesDirectoryMake.class.getCanonicalName());

	String host;
	int port;
	String username;
	String password;
	
    public FTPFileDeleter(String host, String port, String username, String password)
    {
    	int userPort = Integer.parseInt(port);
    	this.host = host;
    	this.port = userPort;
    	this.username = username;
    	this.password = password;
    }
	
    public void deleteDirectories(Map<String, List<Country>> organizedCountriesMap, String userPath) {        
    	FTPClient ftpClient = new FTPClient();
    	
    	try {
    	    Map<String, List<Country>> organizedCountries = organizedCountriesMap;
	        String path = userPath;
			ftpClient.connect(host, port);			
	    	DirectoriesActivity userDecision = UserInputs.userDecisionAboutDirectories();
	        List<String> listOfThreeLettersGroups = new ArrayList<String>();
				
			int replyCode = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(replyCode)){
				System.out.println("It was some problem with connection. Please run application again.");
				System.exit(0);
			}
			
			boolean success = ftpClient.login(username, password);
			if(!success){
				System.out.println("Could not login to the server.");
				System.exit(0);
			}

	        if (userDecision == DirectoriesActivity.DELETE) {
	            listOfThreeLettersGroups = GroupsPreparer.returnLettersGroups();
	        } 
	        else if (userDecision == DirectoriesActivity.REPLACE) {
	            for (Map.Entry<String, List<Country>> set : organizedCountries.entrySet()) {
	                listOfThreeLettersGroups.add(set.getKey());
	            }
	        }

	        if ((userDecision == DirectoriesActivity.DELETE)
	                || (userDecision == DirectoriesActivity.REPLACE)) {
	            for (String directoryToDelete : listOfThreeLettersGroups) {
	                String pathOfGorupDirectory = (path + File.separator + directoryToDelete);
	    			FTPFile[] files = ftpClient.listDirectories(pathOfGorupDirectory);	    			
	    			for(FTPFile file : files){
	    				ftpClient.removeDirectory(pathOfGorupDirectory + File.separator + file.getName());
	    			}
	                ftpClient.removeDirectory(pathOfGorupDirectory);
	            }
	        }

			ftpClient.logout();
			ftpClient.disconnect();
			
		} catch (SocketException e) {
			 System.out.println(
                     "There was an error while creating or accessing a socket. Please make sure given host, port, username and password are correct and run application again");
             LOGGER.log(Level.FINE, "There was an error while creating or accesing a socket.", e);
             System.exit(0);
		} catch (IOException e) {
			System.out.println(
					"There was a problem while connecting to server. Please make sure given host, port, username and password are correct and run application again");
            LOGGER.log(Level.FINE, "There was a problem while connecting to server.", e);
            System.exit(0);
		}   
    }
}