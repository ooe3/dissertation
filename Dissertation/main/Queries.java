package main;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import gui.StartGUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
public class Queries {
	//	DatabaseConnection dc;//Databaseconnection object
	private Connection conn = null;
	private Statement st;
	Users us;

	private static Queries q;
	/**
	 * The class which has all queries all possible methods
	 *  to be used in the functioning of the system
	 * @param d
	 */

	//provides the access to the database
	private Queries(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					conn = DatabaseConnection.getConnection();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


	}
	//as in she was giving me two options because she’s still following me

	public static Queries getQueries(){
		if(q==null)
			q = new Queries();
		return q;

	}

	//Log in method using the matric number and password
	//Matric gotten from textfield
	public Users LogIn(String matric, String password){
		us = null;
		ResultSet rs;
		//checks to see if matric & password exists
		try{
			String query = "SELECT * FROM USER WHERE MATRICNO = '"+matric+"' AND PASSWORD = '"+password+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);	

			while(rs.next()){
				//stores user details in the following variables
				String type = rs.getString("USERTYPE");//type stores 
				int id = rs.getInt("ID");//id stores
				String matricno = rs.getString("MATRICNO");


				//type is checked to see whether we need to query the student or admin table
				if(type.equals("Student")){
					String query1 = "SELECT * FROM STUDENT INNER JOIN USER ON USER.ID = STUDENT.USERID WHERE USER.MATRICNO = '"+matric+"'";
					rs = st.executeQuery(query1);

					while(rs.next()){

						int studentID = rs.getInt("STUDENTID");
						String studentf = rs.getString("FIRSTNAME");
						String studentl = rs.getString("LASTNAME");
						String studentE = rs.getString("EMAIL");
						us = new Student(id, studentID, studentf, studentl, studentE);
						us.setMatric(matricno);
						us.setType(type);
					}

				}else {
					String query2 = "SELECT * FROM ADMIN INNER JOIN USER ON USER.ID = ADMIN.USERID WHERE USER.MATRICNO = '"+matric+"'";
					rs = st.executeQuery(query2);

					while(rs.next()){

						int adminID = rs.getInt("ADMINID");
						String adminf = rs.getString("FIRSTNAME");
						String adminl = rs.getString("LASTNAME");
						String adminE = rs.getString("EMAIL");
						String schoolref = rs.getString("SCHOOLREF");
						us = new Admin(id, adminID, adminf, adminl, adminE, schoolref);
						us.setType(type);
						us.setMatric(matricno);
					}

				}
			}

			rs.close();
			st.close();
		}catch(Exception e1){
			e1.printStackTrace();
		} 
		return us;
	}
	

	//To return user details for display
	public String displayDetails(String s, String s1){
		StringBuilder sb = new StringBuilder("");
		ResultSet rs;
		try{
			if(s.equals("Student")){
				String query = "SELECT d.DEGREENAME, d.DEGREETYPE FROM DEGREE AS d INNER JOIN STUDENT_DEGREE AS sd ON sd.DEGREE = d.DEGREEID "
						+ "INNER JOIN STUDENT AS s ON s.STUDENTID = sd.STUDENT INNER JOIN USER AS us ON us.ID = s.USERID WHERE us.MATRICNO = '"+s1+"'";
				st = conn.createStatement();
				rs = st.executeQuery(query);
				while(rs.next()){
					String degree = rs.getString("DEGREENAME");
					String degreetype = rs.getString("DEGREETYPE");

					sb.append(degreetype);
					sb.append(" ");
					sb.append(degree);
				}

			}else{
				String query1 = "SELECT ad.SCHOOLREF FROM ADMIN AS ad INNER JOIN USER AS us ON us.ID = ad.USERID WHERE us.MATRICNO = '"+s1+"'";
				st = conn.createStatement();
				rs = st.executeQuery(query1);
				while(rs.next()){
					String schoolref = rs.getString("SCHOOLREF");
					if(schoolref.equals("Dental") || schoolref.equals("Adam Smith Business")){
						sb.append(schoolref);
						sb.append(" School");
					}else{
						sb.append("School of ");
						sb.append(schoolref);
					}
				}

			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		} 
		return sb.toString();
	}

	public String displayStudents(int id){
		StringBuilder sb = new StringBuilder("");
		ResultSet rs;

		try{
			String query = "SELECT st.FIRSTNAME, st.LASTNAME FROM STUDENT AS st INNER JOIN STUDENT_DEGREE AS sd ON sd.STUDENT = st.STUDENTID INNER JOIN DEGREE AS d ON d.DEGREEID = sd.DEGREE INNER JOIN ADMIN AS ad ON ad.SCHOOLREF = d.SCHOOL_REF WHERE ad.ADMINID = '"+id+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while(rs.next()){
				String firstname = rs.getString("FIRSTNAME");
				String lastname = rs.getString("LASTNAME");

				sb.append(firstname +" "+ lastname);
				sb.append(",");
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	

	public String checkResults(String fname, String lname){
		StringBuilder sb = new StringBuilder("");
		int count = 0;
		int check = 0;
		ResultSet rs;
		try{
			int id = getStudent(fname, lname);
			String query1 = "SELECT cr.RESULT FROM COURSERESULT AS cr INNER JOIN COURSES AS c ON c.COURSENAME = cr.COURSE INNER JOIN STUDENT AS s ON s.STUDENTID = cr.STUDENTID WHERE s.STUDENTID = '"+id+"' AND cr.result IS NULL";
			st = conn.createStatement();
			rs = st.executeQuery(query1);
			while(rs.next()){
				count+=1;

			}
			if(count > 0){
				sb.append("No");
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}

	public int getStudent(String fname, String lname){
		ResultSet rs;
		int id = 0;
		try{
			String query = "SELECT STUDENTID FROM STUDENT WHERE FIRSTNAME = '"+fname+"' AND LASTNAME = '"+lname+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				id += (rs.getInt("STUDENTID"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public String allStudents(int id){
		ResultSet rs;
		StringBuilder sb = new StringBuilder("");
		String display = String.format(" %-40.40s %-20s %-40.40s %-10s\n", "Name","Matric Number","Degree" ,"Email");
		sb.append(display);

		try{
			String query = "SELECT s.FIRSTNAME, s.LASTNAME, d.DEGREENAME, s.EMAIL FROM STUDENT AS s INNER JOIN STUDENT_DEGREE AS sd "
					+ "ON sd.STUDENT = s.STUDENTID INNER JOIN DEGREE AS d ON d.DEGREEID = sd.DEGREE INNER JOIN ADMIN AS ad ON ad.SCHOOLREF = d.SCHOOL_REF WHERE ad.ADMINID = '"+id+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while(rs.next()){
				String fname = rs.getString("FIRSTNAME");
				String lname = rs.getString("LASTNAME");
				String degree = rs.getString("DEGREENAME");
				String email = rs.getString("EMAIL");

				String matric = matric(fname, lname);
				String name = fname+" "+lname;

				String area = String.format(" %-40.40s %-20s %-40.40s %-10s\n", name, matric, degree, email);
				sb.append(area+"\n");
			}
			rs.close();
			st.close();
		}catch(Exception e){

		}
		return sb.toString();
	}

	public String insertStudent(String name, String lname, String email, String address, String matric, String degree){
		StringBuilder sb = new StringBuilder("");
		int count = 0;
		ResultSet rs;
		try{
			String query ="SELECT s.FIRSTNAME, s.LASTNAME, us.MATRICNO FROM USER AS us INNER JOIN STUDENT AS s ON s.USERID = us.ID";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while(rs.next()){
				String fname = rs.getString("FIRSTNAME").toLowerCase();
				String lstname = rs.getString("LASTNAME").toLowerCase();
				String mat = rs.getString("MATRICNO").toLowerCase();
				if((name.toLowerCase().equals(fname) && lstname.toLowerCase().equals(lstname)) || matric.toLowerCase().equals(mat)){
					count+=1;
					sb.append("Error");
				}
			}
			if(count == 0){
				String sql = "INSERT INTO USER VALUES ('"+countUsers()+"', '"+matric+"', '"+matric+"', 'Student')";
				st.executeUpdate(sql);

				String sql1 = "INSERT INTO STUDENT VALUES ('"+countStudents()+"', '"+name+"', '"+lname+"', '"+email+"', '"+address+"', (SELECT ID FROM USER WHERE MATRICNO = '"+matric+"'))";
				st.executeUpdate(sql1);

				String sql2 = "INSERT INTO STUDENT_DEGREE (STUDENT, DEGREE) VALUES ((SELECT s.STUDENTID FROM STUDENT AS s INNER JOIN USER AS us ON us.ID = s.USERID WHERE us.MATRICNO = '"+matric+"'), (SELECT DEGREEID FROM DEGREE WHERE DEGREENAME = '"+degree+"'))";
				st.executeUpdate(sql2);
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}

	public int countUsers(){
		ResultSet rs;
		try{
			String query = "SELECT COUNT (*) FROM USER";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while(rs.next()){
				return rs.getInt(1)+1;
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	public int countStudents(){
		ResultSet rs;
		try{
			String query = "SELECT COUNT (*) FROM STUDENT";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while(rs.next()){
				return rs.getInt(1)+1;
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public String getUnique(){
		StringBuilder sb = new StringBuilder("");
		final String initial = "2";
		sb.append(initial);

		Random r = new Random();
		int val = r.nextInt(999999) + 100000;
		sb.append(val);
		return sb.toString();
	}

	public String matric(String s, String s1){
		String m = "";
		ResultSet rs;
		try{
			String query = "SELECT us.MATRICNO FROM USER AS us INNER JOIN STUDENT AS s ON s.USERID = us.ID WHERE s.FIRSTNAME = '"+s+"' AND s.LASTNAME = '"+s1+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){

				m+=rs.getString("MATRICNO");
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;
	}

	public String checkString(String s){
		String s1 = "";
		Pattern pattern = Pattern.compile("[a-zA-Z\\s-]*");
		Matcher matcher = pattern.matcher(s);
		boolean check = matcher.matches();
		if(!check){
			s1+="Exists";
		}
		return s1;
	}

	public String checkLastName(String s){
		String s1 = "";
		Pattern pattern = Pattern.compile("[a-zA-Z\\-]*");
		Matcher matcher = pattern.matcher(s);
		boolean check = matcher.matches();
		if(!check){
			s1+="Exists";
		}
		return s1;
	}

	public String checkAddress(String s){
		String s1 = "";
		Pattern pattern = Pattern.compile("[a-zA-Z0-9\\s.,]*");
		Matcher matcher = pattern.matcher(s);
		boolean check = matcher.matches();
		if(!check){
			s1+="Exists";
		}
		return s1;
	}

	public String checkEmail(String s){
		String s1 = "";
		Pattern pattern = Pattern.compile("[a-zA-Z0-9\\@._-]*");
		Matcher matcher = pattern.matcher(s);
		boolean check = matcher.matches();
		if(!check){
			s1+="Exists";
		}
		return s1;
	}

	public Users getUser(){
		return us;
	}
	



}
