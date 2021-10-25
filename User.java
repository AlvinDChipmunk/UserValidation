package com.hubgitalvin.wk5;

public class User {

	private String userName; 
	private String password; 
	private String name; 
	
	public void setUser_userName (String newUName)  { this.userName = newUName; } 
	public void setUser_password (String new_pWord) { this.password = new_pWord; } 
	public void setUser_name (String newName)       { this.name = newName; } 
	
	public String getUser_userName () { return this.userName; } 
	public String getUser_password () { return this.password; } 
	public String getUser_name ()     { return this.name; } 
	
}
