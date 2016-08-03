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
	DatabaseConnection dc;
	private Connection conn = null;
	private Statement st;
	private Users us;
	private Course cs;
	private Student stu;
	private Admin ad;
	private Degree dg;
	private School sc;
	private CourseDegree cd;
	private CourseResult cr;
	private StudentDegree sd;
	public Queries(DatabaseConnection d, Users u, Student sts, Admin adm,  Course course, School school, Degree dge, CourseResult crs, StudentDegree sds, CourseDegree cds){
		dc = d;
		us = u;
		stu = sts;
		ad = adm;
		this.cs = course;
		this.dg = dge;
		this.sc = school;
		this.cd = cds;
		this.cr = crs;
		this.sd = sds;
		conn = d.connectToDatabase();

	}

	public String LogIn(String matric, String password){
		String check = "";
		try{
			String query = "SELECT * FROM USER WHERE MATRICNO = '"+matric+"' AND PASSWORD = '"+password+"'";

			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);	

			while(rs.next()){
				check +="Success";
				String type = rs.getString("USERTYPE");
				int id = rs.getInt("ID");
				String matricno = rs.getString("MATRICNO");
				String pass = rs.getString("PASSWORD");


				us.setType(type);
				us.setMatric(matricno);
				us.setPassword(pass);
				us.setID(id);

				if(type.equals("Student")){
					String query1 = "SELECT * FROM STUDENT INNER JOIN USER ON USER.ID = STUDENT.USERID WHERE USER.MATRICNO = '"+us.getMatric()+"'";
					rs = st.executeQuery(query1);

					while(rs.next()){
						int studentID = rs.getInt("STUDENTID");
						String studentf = rs.getString("FIRSTNAME");
						String studentl = rs.getString("LASTNAME");
						String studentE = rs.getString("EMAIL");
						int userid = rs.getInt("USERID");



						stu.setStudentID(studentID);
						stu.setFirstName(studentf);
						stu.setLastName(studentl);
						stu.setEmail(studentE);
						stu.setUserID(userid);

					}

				}else {
					String query2 = "SELECT * FROM ADMIN INNER JOIN USER ON USER.ID = ADMIN.USERID WHERE USER.MATRICNO = '"+us.getMatric()+"'";
					rs = st.executeQuery(query2);

					while(rs.next()){
						int adminID = rs.getInt("ADMINID");
						String adminf = rs.getString("FIRSTNAME");
						String adminl = rs.getString("LASTNAME");
						String adminE = rs.getString("EMAIL");
						int userid = rs.getInt("USERID");
						String schoolref = rs.getString("SCHOOLREF");


						ad.setFirstName(adminf);
						ad.setLastName(adminl);
						ad.setEmail(adminE);
						ad.setAdminID(adminID);
						ad.setSchoolName(schoolref);
						ad.setUserID(userid);

					}

				}
			}

			rs.close();
			st.close();
		}catch(Exception e1){
			e1.printStackTrace();
		} 
		return check;
	}

	public String displayStudentCourses(String s){
		String courses = "";
		String display = String.format(" %s\n", "Chosen Courses");
		courses+=display;
		courses+="\n";
		String t = "", t1 = "";
		try{
			String query = "SELECT c.COURSE FROM COURSERESULT AS c INNER JOIN STUDENT AS st ON c.STUDENTID = st.STUDENTID WHERE st.LASTNAME = '"+s+"'";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			int check = 0;
			while(rs.next()){
				check+=1;
				String chosen = rs.getString("COURSE");
				t1 = String.format(" %s\n", chosen);
				courses+=t1;
				courses+="\n";
			}
			if(check == 0){
				t = String.format(" %s\n", "No Courses Selected yet. Add a course below");
				courses+=t;
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		return courses;
	}

	public String displayAvailableCourses(String s){
		String courses = "";
		String display = String.format(" %-50.50s %-10s %-10s %-10s\n", "Courses","Credit","Exam","Coursework");
		courses+=display;
		courses+="\n";
		String t = "", t1 = "";
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

				t1 = String.format(" %-50.50s %-10d %-10d %-10d\n", course,credit,exam,cw);
				courses+=t1;
				courses+="\n";
			}
			if(check == 0){
				t = String.format(" %s\n", "No courses available for selection Add a course below");
				courses+=t;
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return courses;
	}

	public String displayDetails(String s, String s1){
		String school = "";
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

					school+=degreetype;
					school+=" ";
					school+=degree;
				}

			}else{
				String query1 = "SELECT ad.SCHOOLREF FROM ADMIN AS ad INNER JOIN USER AS us ON us.ID = ad.USERID WHERE us.MATRICNO = '"+s1+"'";
				st = conn.createStatement();
				rs = st.executeQuery(query1);
				while(rs.next()){
					String schoolref = rs.getString("SCHOOLREF");
					if(schoolref.equals("Dental") || schoolref.equals("Adam Smith Business")){
						school+=schoolref;
						school+=" School";
					}else{
						school+="School of ";
						school+= schoolref;
					}
				}

			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		} 
		return school;
	}

	public void insertChoice(String s, int d){
		try{
			st = conn.createStatement();
			String sql = "INSERT INTO COURSERESULT (COURSE, STUDENTID) VALUES ('"+s+"','"+d+"')";
			st.executeUpdate(sql);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public void removeChoice(String s, int d){
		try{
			st = conn.createStatement();
			String sql = "DELETE FROM COURSERESULT WHERE COURSE = '"+s+"' AND STUDENTID = '"+d+"'";
			st.executeUpdate(sql);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

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

	public String displayCourses(int s){

		String choice="";
		ResultSet rs;
		try{
			String query = "SELECT COURSE_NAME FROM COURSEDEGREE WHERE COURSE_NAME NOT IN (SELECT COURSE FROM COURSERESULT WHERE STUDENTID = '"+s+"') AND DEGREE_ID = (SELECT DEGREE FROM STUDENT_DEGREE WHERE STUDENT = '"+s+"')";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				String name = rs.getString("COURSE_NAME");
				choice+=name;
				choice+=",";
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return choice;


	}

	public String removeSelection(String s){
		String choice="";
		ResultSet rs;

		try{
			String query = "SELECT c.COURSE FROM COURSERESULT AS c INNER JOIN STUDENT AS st ON c.STUDENTID = st.STUDENTID WHERE st.LASTNAME = '"+s+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while(rs.next()){
				String name = rs.getString("COURSE");
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



}
