package other;

import java.sql.*;
//Student class
/**
 * Class which inherits Users class
 *  created to pass every student information
 * Gets the students firstname, lastname, email, StudentID.
 * userID gotten from the parent class
 * @author ooemuwa
 *
 */
public class Student extends Users {
	//private String matric;
	String firstName, lastName, email;
	int studentID, userID;
	StudentDegree sd;



	public Student(int userID, int ID, String fn, String ln, String em){
		super(userID, fn, ln, em);
		this.studentID = ID;
		//default
	}

	public int getStudentID(){
		return studentID;
	}
	
	public void setDegree(StudentDegree s){
		this.sd = s;
	}
	
	public StudentDegree getDegree(){
		return sd;
	}

}
