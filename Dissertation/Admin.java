import java.sql.*;
public class Admin {
	String firstName, lastName, email, schoolName;
	int adminID, userID;

	public Admin(){
		//default
		
	}

	public String getFirstName(){
		return firstName;
	}
	
	public void setFirstName(String fn){
		this.firstName=fn;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public void setLastName(String ln){
		this.lastName=ln;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String e){
		this.email=e;
	}
	
	public int getID(){
		return adminID;
	}
	
	public void setAdminID(int ID){
		this.adminID=ID;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public void setUserID(int id){
		this.userID=id;
	}

	public String getSchoolName(){
		return schoolName;
	}
	
	public void setSchoolName(String sc){
		this.schoolName=sc;
	}
	
	
}
