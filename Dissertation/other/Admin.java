package other;
//Admin class that extends Users class
//inherits methods from Users class
import java.sql.*;
public class Admin extends Users {
	int adminID;
	School sc;

	public Admin(int userID, int ID, String fn, String ln, String email){
		super(userID, fn, ln, email);
		this.adminID = ID;
		//default
		
	}
	
	public int getID(){
		return adminID;
	}
	
	public void setSchool(School sc){
		this.sc = sc;
	}

	public School getSchoolName(){
		return sc;
	}
	
	
}
