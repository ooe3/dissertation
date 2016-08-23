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
	Course cs;

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

	public String showResult(String fname, String lname){
		StringBuilder sb = new StringBuilder("");
		int totalcred = 0, totalpoints = 0;
		double average = 0;
		int count = 0;
		int p = 0;
		String score = "";
		String total = "";
		int id = getStudent(fname, lname);
		String names = String.format("%s %s\n", fname, lname);
		sb.append(names+"\n");
		String display = String.format(" %-50.50s %-10s %-10s %-10s\n", "Course", "Credit", "Overall Mark", "Credit x Overall");
		sb.append(display);
		sb.append("\n");
		ResultSet rs;

		try{
			String query = "SELECT cr.COURSE, cr.RESULT, c.CREDIT FROM COURSERESULT AS cr INNER JOIN COURSES AS c ON c.COURSENAME = cr.COURSE WHERE cr.STUDENTID = '"+id+"' ORDER BY cr.COURSE ASC";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				count+=1;

				String course = rs.getString("COURSE");
				int res = rs.getInt("RESULT");
				int credit = rs.getInt("CREDIT"); 
				int r = res * credit;
				p+=res;
				if(res == 0){
					score = "";
					total = "";
				}else{
					score = getScore(res);
					total = ""+(r)+"";
					totalcred+=credit;
				}
				String s = String.format(" %-50.50s %-10d %-10s %-10s\n", course, credit, score, total);
				sb.append(s);
				sb.append("\n");
				totalpoints+=(r);


			}
			average+=((double)totalpoints/totalcred);

			if (count == 0){
				return "No courses selected.";
			}

			if(p != 0){
				String s2 = String.format(" %65s:%5d\n", "Total", totalpoints);
				sb.append(s2);
				sb.append("\n");
				String s1 = String.format("Your overall result is %d/%d : %.3f", totalpoints, totalcred, average);
				sb.append(s1);
			}

			rs.close();
			st.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}

	public void insertOverall(String mark, String fname, String lname){
		ResultSet rs;
		try{
			int id = getStudent(fname, lname);
			st = conn.createStatement();
			String sql = "UPDATE STUDENT_DEGREE SET RESULT = '"+mark+"' WHERE STUDENT = '"+id+"'";
			st.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}

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

	public void getCourseDetails(String course){
		ResultSet rs;

		try{
			String query = "SELECT * FROM COURSES WHERE COURSENAME = '"+course+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				cs = new Course();
				String coursename = rs.getString("COURSENAME");
				int credit = rs.getInt("CREDIT");
				int exam = rs.getInt("EXAM");
				int cw = rs.getInt("COURSEWORK");

				cs.setCourse(coursename);
				cs.setCredit(credit);
				cs.setExam(exam);
				cs.setCoursework(cw);
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void insertCourseScore(int overall, String coursename, String fname, String lname){
		ResultSet rs;
		try{
			int id = getStudent(fname, lname);
			String sql = "UPDATE COURSERESULT SET RESULT = '"+overall+"' WHERE COURSE = '"+coursename+"' AND STUDENTID = '"+id+"'";
			st.executeUpdate(sql);
		}catch(Exception e){ 
			e.printStackTrace();
		}

	}
	//Calculate the score of a course
	//Method to be called in the GUI and int result returned to pass as one
	//of the parameters in insertCourseScore
	public int calculateScore(int exam, int coursework, int courseMark, int examMark){
		double e = (double)exam/100;
		double c = (double)coursework/100;
		final int maxcredit = 22;
		int finalscore = 0;

		e*=examMark;
		c*=courseMark;
		double score = e+c;
		score/=100;
		score*=maxcredit;

		finalscore+=Math.round(score);
		return finalscore;
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

	public String getResult(String fname, String lname){
		StringBuilder sb = new StringBuilder("");
		int totalcred = 0, totalpoints = 0;
		double average = 0;
		ResultSet rs;
		try{
			int id = getStudent(fname, lname);
			String query = "SELECT cr.RESULT, c.CREDIT FROM COURSERESULT AS cr INNER JOIN COURSES AS c ON c.COURSENAME = cr.COURSE WHERE cr.STUDENTID = '"+id+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				int res = rs.getInt("RESULT");
				int credit = rs.getInt("CREDIT"); 
				totalpoints+=(res*credit);
				totalcred+=credit;


			}
			average+=((double)totalpoints/totalcred);
			String s1 = String.format("%.3f", average);
			sb.append(s1);
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

	public String overallCourse(String s){
		StringBuilder sb = new StringBuilder("");
		int totalpoints = 0;
		double average = 0;
		int count = 0;
		int numOfStudents = 0;
		String degree = String.format("%s\n", s);
		sb.append(degree + "\n");
		String display = String.format(" %-40.40s %-10s\n", "Name", "Overall Mark");
		sb.append(display);
		sb.append("\n");
		ResultSet rs;

		try{
			String query = "SELECT s.FIRSTNAME, s.LASTNAME, cr.RESULT FROM COURSERESULT AS cr INNER JOIN STUDENT AS s ON s.STUDENTID = cr.STUDENTID WHERE cr.COURSE = '"+s+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){

				String fname = rs.getString("FIRSTNAME");
				String lname = rs.getString("LASTNAME");
				int res = rs.getInt("RESULT");
				count+=res;
				if(res != 0){
					String name = fname +" "+ lname;
					numOfStudents+=1;
					String mark = getScore(res);
					String s1 = String.format(" %-40.40s %-10s\n", name, mark);
					sb.append(s1);
					sb.append("\n");
					totalpoints+=res;
				}

			}

			if(count !=0){
				average+=((double)totalpoints/numOfStudents);
				String s1 = String.format("The overall average for this course is %.3f", average);
				sb.append(s1);
			}else{
				sb.append("No marks available yet.");
			}

			rs.close();
			st.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
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

	public String overallSchool(int id){
		Double score = 0.0;
		StringBuilder sb = new StringBuilder("");
		String degree = String.format("%s\n", "Overall average for School");
		sb.append(degree + "\n");
		String display = String.format(" %-40.40s %-30.30s %-10s\n", "Name","Degree" ,"Result");
		sb.append(display);
		int count = 0;
		ResultSet rs;
		try{
			String query = "SELECT s.FIRSTNAME, s.LASTNAME, d.DEGREENAME, sd.RESULT FROM STUDENT_DEGREE AS sd INNER JOIN DEGREE AS d "
					+ "ON d.DEGREEID = sd.DEGREE INNER JOIN ADMIN AS a ON a.SCHOOLREF = d.SCHOOL_REF INNER JOIN STUDENT AS s ON s.STUDENTID = sd.STUDENT WHERE a.ADMINID = '"+id+"' AND sd.RESULT IS NOT NULL";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				count+=1;
				String fname = rs.getString("FIRSTNAME");
				String lname = rs.getString("LASTNAME");
				String degreename = rs.getString("DEGREENAME");
				String result = rs.getString("RESULT");

				String name = fname +" "+ lname;
				Double res = Double.parseDouble(result);
				String names = String.format(" %-40.40s %-30.30s %-10.3f\n", name, degreename, res);
				sb.append(names+"\n");
				score+=res;

			}
			String average = String.format("The average score for this school is %.3f", score/count);
			sb.append(average);

			if(count == 0){
				return "No results for this school";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String degreeResult(String s){
		Double score = 0.0;
		StringBuilder sb = new StringBuilder("");
		String degree = String.format("%s\n", s);
		sb.append(degree + "\n");
		String display = String.format(" %-50.50s %-10s\n", "Name", "Result");
		sb.append(display);
		int count = 0;
		ResultSet rs;
		try{
			String query = "SELECT s.FIRSTNAME, s.LASTNAME, sd.RESULT FROM STUDENT_DEGREE AS sd INNER JOIN DEGREE AS d ON d.DEGREEID = sd.DEGREE INNER JOIN STUDENT AS s ON s.STUDENTID = sd.STUDENT WHERE d.DEGREENAME = '"+s+"' AND sd.RESULT IS NOT NULL";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				count+=1;
				String fname = rs.getString("FIRSTNAME");
				String lname = rs.getString("LASTNAME");
				String result = rs.getString("RESULT");

				String name = fname +" "+ lname;
				Double res = Double.parseDouble(result);
				String names = String.format(" %-50.50s %-10.3f\n", name, res);
				sb.append(names+"\n");
				score+=res;

			}
			String average = String.format("The average score for this degree is %.3f", score/count);
			sb.append(average);

			if(count == 0){
				return "No results for that degree";
			}
		}catch(Exception e){
			e.printStackTrace();
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

	public String getScore(int id){
		String s = "";
		String[] score = {"A1","A2","A3","A4","A5","B1","B2","B3","C1","C2","C3","D1","D2","D3","E1","E2","E3","F1","F2","F3","G1","G2","H"};
		int[] mark = {22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0};
		for(int i = 0; i<score.length;i++){
			if(id == mark[i]){
				s+=score[i];
			}
		}

		return s;
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

	public int getCwPercentage(){
		return cs.getCoursework();
	}

	public int getExamPercentage(){
		return cs.getExamPercentage();
	}
	



}
