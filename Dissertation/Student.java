import java.sql.*;
public class Student {
	//private String matric;
	String firstName, lastName, email;
	int studentID, userID;
	


	public Student(){
	
		//default
	}

	public int getStudentID(){
		return studentID;
	}
	
	public void setStudentID(int ID){
		this.studentID=ID;
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
	
	public int getUserID(){
		return userID;
	}
	
	public void setUserID(int id){
		this.userID=id;
	}
}
