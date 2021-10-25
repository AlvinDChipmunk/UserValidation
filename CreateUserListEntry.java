package com.hubgitalvin.wk5;

public class CreateUserListEntry {
	public User createListEntry (String eUserName, String ePassword, String eName) { 
		User user = new User(); 
		
		user.setUser_userName(eUserName); 
		user.setUser_password(ePassword); 
		user.setUser_name(eName); 
		
		return user; 
	}
}
