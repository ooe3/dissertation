import java.sql.*;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
public class Queries {
	DatabaseConnection dc;//Databaseconnection object
	private Connection conn = null;
	private Statement st;
	/**
	 * The class which has all queries all possible methods
	 *  to be used in the functioning of the system
	 * @param d
	 */
	
	//provides the access to the database
	public Queries(DatabaseConnection d){
		dc = d;
		conn = d.connectToDatabase();

	}
	
	//Log in method using the matric number and password
	//Matric gotten from textfield
	public Users LogIn(String matric, String password){
		Users us = null;
		String check = "";
		//checks to see if matric & password exists
		try{
			String query = "SELECT * FROM USER WHERE MATRICNO = '"+matric+"' AND PASSWORD = '"+password+"'";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);	

			while(rs.next()){
				check +="Success";
				//stores user details in the following variables
				String type = rs.getString("USERTYPE");//type stores 
				int id = rs.getInt("ID");//id stores
				String matricno = rs.getString("MATRICNO");
//				String pass = rs.getString("PASSWORD");
//				us.setType(type);
//				us.setMatric(matricno);
//				us.setPassword(pass);
				
				
				//type is checked to see whether we need to query the student or admin table
				if(type.equals("Student")){
					String query1 = "SELECT * FROM STUDENT INNER JOIN USER ON USER.ID = STUDENT.USERID WHERE USER.MATRICNO = '"+matric+"'";
					rs = st.executeQuery(query1);
					
					while(rs.next()){
						
						int studentID = rs.getInt("STUDENTID");
						String studentf = rs.getString("FIRSTNAME");
						String studentl = rs.getString("LASTNAME");
						String studentE = rs.getString("EMAIL");
						//int userid = rs.getInt("USERID");
//						us = new Users(id, studentf, studentl, studentE);
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
						//int userid = rs.getInt("USERID");
						String schoolref = rs.getString("SCHOOLREF");
//						us = new Users(id, adminf, adminl, adminE);
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
	//gets the student courses to be displayed on the GUI
	//String s supplies the lastname of the student
	public String displayStudentCourses(String s){
		StringBuilder sb = new StringBuilder("");
		String courses = "";
		String display = String.format(" %s\n", "Chosen Courses");
		sb.append(display);
		sb.append("\n");
		
		try{
			String query = "SELECT c.COURSE FROM COURSERESULT AS c INNER JOIN STUDENT AS st ON c.STUDENTID = st.STUDENTID WHERE st.LASTNAME = '"+s+"'";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			int check = 0;
			while(rs.next()){
				check+=1;
				String chosen = rs.getString("COURSE");
				String t1 = String.format(" %s\n", chosen);
				sb.append(t1);
				sb.append("\n");
			}
			//returns a string to show that no courses have been selected
			if(check == 0){
				String t = String.format(" %s\n", "No Courses Selected yet. Add a course below");
				sb.append(t);
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		return sb.toString();
	}
	//return strings that contains courses available
	//String s passes the admin lastname as a parameter
	public String displayAvailableCourses(String s){
		StringBuilder sb = new StringBuilder("");
		String display = String.format(" %-50.50s %-10s %-10s %-10s\n", "Courses","Credit","Exam","Coursework");
		sb.append(display);
		sb.append("\n");
		try{
			String queryx = "SELECT * FROM COURSES INNER JOIN COURSEDEGREE ON COURSES.COURSENAME = COURSEDEGREE.COURSE_NAME INNER JOIN "
					+ "DEGREE ON DEGREE.DEGREEID = COURSEDEGREE.DEGREE_ID INNER JOIN SCHOOL ON SCHOOL.SCHOOLNAME = DEGREE.SCHOOL_REF INNER JOIN "
					+ "ADMIN ON ADMIN.SCHOOLREF = SCHOOL.SCHOOLNAME WHERE ADMIN.LASTNAME = '"+s+"'";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryx);

			int check = 0;
			while(rs.next()){
				check+=1;
				//ResultSet rs
				String course = rs.getString("COURSE_NAME");
				int credit = rs.getInt("CREDIT");
				int exam = rs.getInt("EXAM");
				int cw = rs.getInt("COURSEWORK");

				String t1 = String.format(" %-50.50s %-10d %-10d %-10d\n", course,credit,exam,cw);
				sb.append(t1);
				sb.append("\n");
			}
			if(check == 0){
				String t = String.format(" %s\n", "No courses available for selection Add a course below");
				sb.append(t);
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return sb.toString();
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
	//Insert course selected by student
	public void insertChoice(String s, int d){
		try{
			st = conn.createStatement();
			String sql = "INSERT INTO COURSERESULT (COURSE, STUDENTID) VALUES ('"+s+"','"+d+"')";
			st.executeUpdate(sql);
		}catch (Exception e){
			e.printStackTrace();
		}

	}
	//Remove course selected by student
	//String s passes the course to be removed
	//int d passes the studentID
	public void removeChoice(String s, int d){
		try{
			st = conn.createStatement();
			String sql = "DELETE FROM COURSERESULT WHERE COURSE = '"+s+"' AND STUDENTID = '"+d+"'";
			st.executeUpdate(sql);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	//Insert new courses into the system by admin
	public String insertCourse(String s, int d, int d1, int d2){
		String check = "";
		ResultSet rs;

		try{
			st = conn.createStatement();
			String query = "SELECT COURSENAME FROM COURSES WHERE COURSENAME = '"+s+"'";
			rs = st.executeQuery(query);
			if(rs.next()){
				check+="Error";

			}else{
				String sql = "INSERT INTO COURSES VALUES ('"+s+"','"+d+"','"+d1+"','"+d2+"')";
				st.executeUpdate(sql);

			}

			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return check;
	}
	
	//To insert the specific course and the degree it belongs to
	public void addCourseDegree(String course, String degree){
		ResultSet rs;
		try{
			st = conn.createStatement();
			String query = "SELECT DEGREEID FROM DEGREE WHERE DEGREENAME = '"+degree+"'";
			rs = st.executeQuery(query);
			if(rs.next()){
				int degreeid = rs.getInt("DEGREEID");
				String sql = "INSERT INTO COURSEDEGREE VALUES ('"+course+"', '"+degreeid+"')";
				st.executeUpdate(sql);
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	//Remove course from the system
	public void removeCourse(String s){
		try{
			st = conn.createStatement();
			String sql1 = "DELETE FROM COURSEDEGREE WHERE COURSE_NAME = '"+s+"'";
			st.executeUpdate(sql1);
			String sql = "DELETE FROM COURSES WHERE COURSENAME = '"+s+"'";
			st.executeUpdate(sql);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	//Display courses available for selection
	//int s takes the studentID
	public String displayCourses(int s){
		StringBuilder sb = new StringBuilder("");
		ResultSet rs;
		try{
			String query = "SELECT COURSE_NAME FROM COURSEDEGREE WHERE COURSE_NAME NOT IN (SELECT COURSE FROM COURSERESULT WHERE STUDENTID = '"+s+"') AND DEGREE_ID = (SELECT DEGREE FROM STUDENT_DEGREE WHERE STUDENT = '"+s+"')";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				String name = rs.getString("COURSE_NAME");
				sb.append(name);
				sb.append(",");
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return sb.toString();


	}
	//display courses that can be removed for student
	public String removeSelection(String s){
		StringBuilder sb = new StringBuilder("");
		ResultSet rs;

		try{
			String query = "SELECT c.COURSE FROM COURSERESULT AS c INNER JOIN STUDENT AS st ON c.STUDENTID = st.STUDENTID WHERE st.LASTNAME = '"+s+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while(rs.next()){
				String name = rs.getString("COURSE");
				sb.append(name);
				sb.append(",");
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	//
	public String removeSelectionAdmin(String s){
		String choice="";
		ResultSet rs;

		try{
			String queryx = "SELECT COURSENAME FROM COURSES INNER JOIN COURSEDEGREE ON COURSES.COURSENAME = COURSEDEGREE.COURSE_NAME INNER JOIN "
					+ "DEGREE ON DEGREE.DEGREEID = COURSEDEGREE.DEGREE_ID INNER JOIN SCHOOL ON SCHOOL.SCHOOLNAME = DEGREE.SCHOOL_REF INNER JOIN "
					+ "ADMIN ON ADMIN.SCHOOLREF = SCHOOL.SCHOOLNAME WHERE ADMIN.LASTNAME = '"+s+"'";
			st = conn.createStatement();
			rs = st.executeQuery(queryx);

			while(rs.next()){
				String name = rs.getString("COURSENAME");
				choice+=name;
				choice+=",";
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return choice;
	}

	public String displayDegree(String s){

		String choice="";
		ResultSet rs;
		try{
			String query = "SELECT * FROM DEGREE WHERE SCHOOL_REF = '"+s+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				String name = rs.getString("DEGREENAME");
				String type = rs.getString("DEGREETYPE");
				choice+=name;
				choice+="("+type+")";
				choice+=",";
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return choice;


	}

	public String displayResult(int studentID){
		String result = "", s = "";
		int totalcred = 0, totalpoints = 0, average = 0;
		int count = 0;
		String display = String.format("%-50.50s %-10s %-10s %-10s\n", "Course", "Credit", "Overall Mark", "Credit x Overall");
		result+=display;
		result+="\n";
		ResultSet rs;

		try{
			String query = "SELECT cr.COURSE, cr.RESULT, c.CREDIT FROM COURSERESULT AS cr INNER JOIN COURSES AS c ON c.COURSENAME = cr.COURSE WHERE cr.STUDENTID = '"+studentID+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				count+=1;

				String course = rs.getString("COURSE");
				int res = rs.getInt("RESULT");
				int credit = rs.getInt("CREDIT"); 
				if(res == 0){
					return "Not Available";
				}else
					totalpoints+=(res*credit);
				s = String.format("%-50.50s %-10d %-10d %-10d\n", course, credit, res, (res*credit));
				result+=s;
				totalcred+=credit;
				average+=(totalpoints/totalcred);
			}

			if (count == 0){
				return "Not Available";
			}
			String s2 = String.format(" %65s:%5d\n", "Total", totalpoints);
			result+=s2;
			result+="\n";
			String s1 = String.format("Your overall result is %d/%d : %.3f", totalpoints, totalcred, ((double)average));
			result+=s1;

			rs.close();
			st.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		return result;

	}

	public void insertOverall(int mark){
		try{

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public String displayStudents(int id){
		String result = "";
		ResultSet rs;

		try{
			String query = "SELECT st.FIRSTNAME, st.LASTNAME FROM STUDENT AS st INNER JOIN STUDENT_DEGREE AS sd ON sd.STUDENT = st.STUDENTID INNER JOIN DEGREE AS d ON d.DEGREEID = sd.DEGREE INNER JOIN ADMIN AS ad ON ad.SCHOOLREF = d.SCHOOL_REF WHERE ad.ADMINID = '"+id+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while(rs.next()){
				String firstname = rs.getString("FIRSTNAME");
				String lastname = rs.getString("LASTNAME");

				result+=(firstname +" "+ lastname);
				result+=",";
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public void getCourseDetails(String course){
		ResultSet rs;

		try{
			String query = "SELECT * FROM COURSES WHERE COURSENAME = '"+course+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while(rs.next()){
				Course cs = new Course();
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
			String query = "SELECT STUDENTID FROM STUDENT WHERE FIRSTNAME = '"+fname+"' AND LASTNAME = '"+lname+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				int id = rs.getInt("STUDENTID");

				String sql = "UPDATE COURSERESULT SET RESULT = '"+overall+"' WHERE COURSE = '"+coursename+"' AND STUDENTID = '"+id+"'";
				st.executeUpdate(sql);
			}
			rs.close();
			st.close();
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
	
	public void closeConnection(){
		if(conn != null){
			try{
				conn.close();
			}catch(Exception e){
				e.getMessage();
			}
		}
	}



}
