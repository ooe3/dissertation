package main;
import java.sql.*;
public class Admin extends Users {
	String schoolName;
	int adminID;

	public Admin(int userID, int ID, String fn, String ln, String email, String schoolName){
		super(userID, fn, ln, email);
		this.adminID = ID;
		this.schoolName = schoolName;
		//default
		
	}
	
	public int getID(){
		return adminID;
	}

	public String getSchoolName(){
		return schoolName;
	}
	
	
}
