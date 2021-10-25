package com.hubgitalvin.wk5;

/*
*
* Assignment #3
* User Validation with CSV File
* 
* Basic Requirements / Functions: 
* 
* The goal for this assignment will be to mimic a user login from a Java 
* console application.
* 
* The program will prompt a user for a username and a password, and then 
* use the inputs you receive to validate whether or not the username / 
* password combination is valid.
* 
* In order to validate this username / password combination, the program 
* will need to read this information from a file (called "data.txt") and 
* import the data into your Java application.
* 
* Possible Program Flow: 
* 
* - Set up internal data
* - Get user input
* - Validate input by comparing stored data with user input
* - Good result:  Allow "entrance" or show success
* - Bad result:  Try again, max 5 attempts or go to fail message
* 
* Possible Parts: 
* 
* - Highly suggested POJO for representing the user data 
* - separate method for reading and storing data from file 
* - separate method for collecting user input 
* - separate method for comparing user input to stored data 
* 
* 
*/

import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.util.Scanner;

public class UserValidator {

	// variable creation and relevant initialization 
	
	static User[]  userList                 = new User[4]; // would like to do a dynamic list size allocation to match the associated file's number of lines 
	static Scanner userInput                = new Scanner(System.in); 
	static String  uiUserName               = ""; 
	static String  uiPassword               = ""; 
	static String  goodName                 = ""; 
	static boolean doesNameAndPasswordMatch = false; // flag for knowing ultimately if there really is a match; avoiding implicit logic so I don't accidentally hide things from myself 

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	//--------------------section separator----------------------------------------
		
		// file handle for reading in data 
		BufferedReader userFileReader = null; 
		
		// temporary holders when individual parts of a User object is needed 
		String tmpUserName; 
		String tmpPassword; 
		String tmpName; 

		
	//--------------------section separator----------------------------------------	
		// start reading data from file and store it 
		userFileReader = createList(); 
			
	//--------------------section separator----------------------------------------
		// collect user input in a while loop 
			// break out of while loop if credentials are correct or... 
			// stay in while loop completely 
			// returning a boolean value can allow displaying a 
			// success message or a fail message 

		collectUserInput(); 
				
	//--------------------section separator----------------------------------------
		// display final result 
			// here, we simply display the consequences of succeeding 
			// or failing at logging in 
			// BOTH options cannot be displayed, only one or the other 
		
		if (doesNameAndPasswordMatch) { 
			displaySuccess(); 
		} else { 
			displayFailure(); 
		}
		
	//--------------------section separator----------------------------------------
		// end of program 
		
	} 
	
	// utility method to get String arrays 
	public static String[] parseLine(String input) {

		String outStringArray[] = input.split(","); 
		
		return outStringArray; 

	}
	
	// this method creates the hidden user list to match usernames and passwords that 
	// login attempts try to show they are allowed in 
	@SuppressWarnings("finally")
	public static BufferedReader createList () { 
		
		BufferedReader fileReader = null; 
		String[] tmpStringArray   = new String[3]; 
		String curLine            = "A-A-Ron";     // just need to populate the variable with something non-null to allow entry into the while loop 
		User tmpUser              = new User();    // temporary User for data insertion purposes 
		int userListLocation      = 0;             // for knowing which array location to insert the new object in 
		
		try { 
			
			// the following line needs data.txt file in the same file location as this source Java file
			fileReader = new BufferedReader( new FileReader("data.txt")); 
			
			while ( ( curLine = fileReader.readLine() ) != null) { 
				
				tmpStringArray = parseLine(curLine); 
				
				tmpUser.setUser_userName(tmpStringArray[0]); 
				tmpUser.setUser_password(tmpStringArray[1]); 
				tmpUser.setUser_name(tmpStringArray[2]); 
				
				userList[userListLocation] = tmpUser; 
				
				userListLocation ++; 
				
			} 
			
			// System.out.println("Closing file reader."); 
			fileReader.close(); 
		
		} catch (FileNotFoundException e) { 
		
			System.out.println("Oops!  The file wasn't found!"); 
			e.printStackTrace(); 
			
			return null; 

		}  catch (IOException e) { 
			
			e.printStackTrace(); 			
			return null; 

		} finally { 
			
			try { 
				//System.out.println("Closing file reader."); 
				fileReader.close(); 
				return fileReader; 
				
			} catch (IOException e) { 
				e.printStackTrace(); 
				return null; 
			}
			
		} 

	}
	
	// method for checking current input with current username and password 
	public static boolean doesItMatch ( 
			String uiUserName, 
			String uiPassword, 
			String storedUserName, 
			String storedPassword ) { 
		
		boolean goodMatch = false; 
		
		// diagnostic system outs 
		//System.out.println("Input Username: " + uiUserName); 
		//System.out.println("Input Password: " + uiPassword); 
		//System.out.println("Stored Username: " + storedUserName); 
		//System.out.println("Stored Password: " + storedPassword); 
		
		if ( (uiUserName.equals(storedUserName)) && (uiPassword.equals(storedPassword)) ) { 
			goodMatch = true; 
		}
		
		return goodMatch; 
	}
	
	// method for collecting input from UI 
	public static void collectUserInput() { 
		
		String inUserName = ""; 
		String inPassword = ""; 
		
		int numTries = 1; 
		int maxTries = 5; 
		
		int userListCounter = 1; 
		int userListLength  = 4; 

		boolean doesCurrentEntryMatch  = false; 
		
		while (!doesNameAndPasswordMatch && (numTries <= maxTries)) { 
			
			// the outer while loop takes care of the login attempts 
			
			System.out.print("Please enter your Username: "); 
			inUserName = userInput.nextLine(); 
			
			System.out.print("Now enter your Password   : "); 
			inPassword = userInput.nextLine(); 

			while (!doesCurrentEntryMatch && (userListCounter <= userListLength)) { 
				
				// the inner while loop takes care of traversing the list of usernames and matching passwords 
				doesCurrentEntryMatch = doesItMatch(
						inUserName, 
						inPassword, 
						userList[userListCounter - 1].getUser_userName(), 
						userList[userListCounter - 1].getUser_password()
						); 
				
				if (doesCurrentEntryMatch) { 
					doesNameAndPasswordMatch = true; 
					goodName = userList[userListCounter - 1].getUser_name(); 
				}
				
				userListCounter ++; 
				
			} 
			
			if (!doesNameAndPasswordMatch) System.out.println("Invalid login, please try again.");
			numTries ++; 
			
		}
		
	}

	public static void displaySuccess() { 
		System.out.println("Welcome " + goodName);
	}
	
	public static void displayFailure() { 
		System.out.println("Too many failed login attempts, you are now locked out.");	
	}
}
