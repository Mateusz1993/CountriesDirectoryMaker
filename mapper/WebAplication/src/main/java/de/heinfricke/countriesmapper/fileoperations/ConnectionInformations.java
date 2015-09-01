package de.heinfricke.countriesmapper.fileoperations;

public class ConnectionInformations {
	private String port;
	private String username;
	private String password;
	private String path;
	private String host;
	
	public ConnectionInformations(String port, String username, String password, String path, String host){
		this.port = port;
		this.username = username;
		this.password = password;
		this.path = path;
		this.host = host;
	}
	
	public void setPort(String port){
		this.port = port;
	}
	
	public String getPort(){
		return port;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public String getPath(){
		return path;
	}
	
	public void setHost(String host){
		this.host = host;
	}

	public String getHost(){
		return host;
	}
}
