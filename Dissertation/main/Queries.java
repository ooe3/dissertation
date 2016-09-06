package main;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import gui.StartGUI;
import other.Admin;
import other.Course;
import other.CourseResult;
import other.DatabaseConnection;
import other.Degree;
import other.School;
import other.Student;
import other.StudentDegree;
import other.Users;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
public class Queries {
	//	DatabaseConnection dc;//Databaseconnection object
	private Connection conn = null;
	private PreparedStatement ps;
	List<CourseResult> courseDetails = new ArrayList<CourseResult>();
	List<Student> students = new ArrayList<Student>();
	List<Admin> administrator = new ArrayList<Admin>();
	List<School> schools = new ArrayList<School>();
	Users us;
	Admin ad;
	Degree dg;
	School sc;
	StudentDegree sd;
	Course cs;
	CourseResult cr;
	Student st;
	Statement stt = null;
	int count = 0;
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
	//

	public static Queries getQueries(){
		if(q==null)
			q = new Queries();
		return q;

	}

	//Log in method using the matric number and password
	//Matric gotten from textfield
	@SuppressWarnings("resource")
	public Users LogIn(String matric, String password){
		us = null;
		ResultSet rs = null;
		//checks to see if matric & password exists
		try{
			String query = "SELECT * FROM USER WHERE MATRICNO = ? AND PASSWORD = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, matric);
			ps.setString(2, password);
			rs = ps.executeQuery();	

			while(rs.next()){
				//stores user details in the following variables
				String type = rs.getString("USERTYPE");//type stores 
				int id = rs.getInt("ID");//id stores
				String matricno = rs.getString("MATRICNO");


				//type is checked to see whether we need to query the student or admin table
				//if student, it performs the query from the student table and creates a new object
				if(type.equals("Student")){
					String query1 = "SELECT * FROM STUDENT INNER JOIN USER ON USER.ID = STUDENT.USERID WHERE USER.MATRICNO = ?";
					ps = conn.prepareStatement(query1);
					ps.setString(1, matricno);
					rs = ps.executeQuery();

					while(rs.next()){
						
						int studentID = rs.getInt("STUDENTID");
						String studentf = rs.getString("FIRSTNAME");
						String studentl = rs.getString("LASTNAME");
						String studentE = rs.getString("EMAIL");
						us = new Student(id, studentID, studentf, studentl, studentE);
						us.setMatric(matricno);
						us.setType(type);
						((Student)us).setDegree(getSDInfo((Student)us));
					}

				}else {
					//if admin, it performs the query from the admin table and creates a new object
					String query2 = "SELECT * FROM ADMIN INNER JOIN USER ON USER.ID = ADMIN.USERID WHERE USER.MATRICNO = ?";
					ps = conn.prepareStatement(query2);
					ps.setString(1, matricno);
					rs = ps.executeQuery();

					while(rs.next()){
						int adminID = rs.getInt("ADMINID");
						String adminf = rs.getString("FIRSTNAME");
						String adminl = rs.getString("LASTNAME");
						String adminE = rs.getString("EMAIL");
						String schoolref = rs.getString("SCHOOLREF");
						us = new Admin(id, adminID, adminf, adminl, adminE);
						us.setType(type);
						us.setMatric(matricno);
						((Admin)us).setSchool(getSchoolInfo(schoolref));
					}

				}
			}

			rs.close();
			ps.close();
		}catch(Exception e1){
			e1.printStackTrace();
		} 
		return us;
	}
	//method to return a string that consists of all students and their details
	//or a string of students in a particular degree
	public String allStudents(String s){
		StringBuilder sb = new StringBuilder("");
		String display = String.format(" %-40.40s %-20s %-50.50s %-10s\n", "Name","Matric Number","Degree" ,"Email");
		sb.append(display+"\n");
		//loop through the student list and get the information
		for(int i = 0; i<students.size(); i++){
			String name = students.get(i).getFirstName()+" "+students.get(i).getLastName();
			String matric = students.get(i).getMatric();
			String degree =students.get(i).getDegree().getDegree().getDegreeName()+"("+students.get(i).getDegree().getDegree().getDegreeType()+")";
			String email = students.get(i).getEmail();
			if(degree.equals(s)){//if a particular degree has been selected
			String area = String.format(" %-40.40s %-20s %-50.50s %-10s\n", name, matric, degree, email);
			sb.append(area+"\n");
			}else if(s.equals("All")){//if no particular degree has been selected
				String area = String.format(" %-40.40s %-20s %-50.50s %-10s\n", name, matric, degree, email);
				sb.append(area+"\n");
			}
		}
		if(students.size() == 0){//if no students exist in the school yet
			sb.append("No students registered to this school yet. Add a student using the Add student option");
		}

		return sb.toString();
	}
	//method which stores all the students in a school in the student object
	//School is passed as the parameter to know which School
	public Student getAll(School sc){
		ResultSet rs = null;
		try{
			String query1 = "SELECT * FROM STUDENT AS s INNER JOIN USER AS us on us.ID = s.USERID WHERE s.STUDENTID IN (SELECT STUDENT FROM STUDENT_DEGREE WHERE DEGREE IN (SELECT DEGREEID FROM DEGREE WHERE SCHOOL_REF = ?)) ORDER BY s.LASTNAME";
			ps = conn.prepareStatement(query1);
			ps.setString(1, sc.getName());
			rs = ps.executeQuery();

			while(rs.next()){
				int userid = rs.getInt("USERID");
				int studentid = rs.getInt("STUDENTID");
				String fname = rs.getString("FIRSTNAME");
				String lname = rs.getString("LASTNAME");
				String email = rs.getString("EMAIL");
				String matric = rs.getString("MATRICNO");
				//query1 executed, the variables above get the data from the columns in the table
				//and then passed to the student object below


				st = new Student(userid, studentid, fname, lname, email);
				st.setDegree(getSDInfo(st));
				st.setMatric(matric);
				students.add(st);//student objects added to student list
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		return st;
	}
	//method to return Admin objects
	public Admin getAll(){
		ResultSet rs = null;
		try{
			String query1 = "SELECT * FROM ADMIN AS ad INNER JOIN USER AS us on us.ID = ad.USERID";
			ps = conn.prepareStatement(query1);
			rs = ps.executeQuery();

			while(rs.next()){
				
				int userid = rs.getInt("USERID");
				int adminID = rs.getInt("ADMINID");
				String adminf = rs.getString("FIRSTNAME");
				String adminl = rs.getString("LASTNAME");
				String adminE = rs.getString("EMAIL");
				String matric = rs.getString("MATRICNO");
				//int and string variables passed as parameters
				//to the Admin Object
				ad = new Admin(userid, adminID, adminf, adminl, adminE);
				ad.setMatric(matric);
				administrator.add(ad);//admin object stored in the list
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		return ad;
	}
	
	public String insertAdmin(String name, String lname, String email, String address, String matric, String school){
		StringBuilder sb = new StringBuilder("");
		int count = 0;
		int check = 0;
		final String type = "Admin";
		try{
			for(int i = 0;i<administrator.size();i++){
				String fname = administrator.get(i).getFirstName().toLowerCase();
				String lstname = administrator.get(i).getLastName().toLowerCase();
				String mat = administrator.get(i).getMatric().toLowerCase();
				String mail = administrator.get(i).getEmail().toLowerCase();
				if((name.toLowerCase().equals(fname) && lstname.toLowerCase().equals(lname))|| matric.toLowerCase().equals(mat)){
					count+=1;
					sb.append("Error");

				}else if(matric.toLowerCase().equals(mat)){
					count+=1;
					sb.append("Exists");
				}else if(email.toLowerCase().equals(mail)){
					count+=1;
					sb.append("Already");
				}
			}
			for(int i = 0; i<schools.size();i++){
				String schl = schools.get(i).getName();
				if(school.equals(schl)){
					check+=1;
				}
			}
			if(count == 0){

				String sql = "INSERT INTO USER (MATRICNO, PASSWORD, USERTYPE) VALUES (?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, matric);
				ps.setString(2, matric);
				ps.setString(3, type);
				ps.executeUpdate();
				if(check == 0){
				String sql2 = "INSERT INTO SCHOOL (SCHOOLNAME) VALUES (?)";
				ps = conn.prepareStatement(sql2);
				ps.setString(1, school);
				ps.executeUpdate();
				}
				
				String sql1 = "INSERT INTO ADMIN  (FIRSTNAME, LASTNAME, EMAIL, USERID, SCHOOLREF) VALUES (?,?,?,(SELECT ID FROM USER WHERE MATRICNO = ?),(SELECT SCHOOLNAME FROM SCHOOL WHERE SCHOOLNAME = ?))";
				ps = conn.prepareStatement(sql1);
				ps.setString(1, name);
				ps.setString(2, lname);
				ps.setString(3, email);
				ps.setString(4, matric);
				ps.setString(5, school);
				ps.executeUpdate();

				
			}
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
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
	//
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

	public String checkFirstName(String s){
		String s1 = "";
		Pattern pattern = Pattern.compile("[a-zA-Z\\-]*");
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
		Pattern pattern = Pattern.compile("[a-zA-Z0-9\\s._-]{2,}+\\,[A-Za-z0-9\\s.,]{2,}");
		Matcher matcher = pattern.matcher(s);
		boolean check = matcher.matches();
		if(!check){
			s1+="Exists";
		}
		return s1;
	}

	public String checkEmail(String s){
		String s1 = "";
		Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[A-Za-z.]{2,9}");//Tank Tutorial 19.."Java Video Tutorial 19". Newthinktank.com. N.p., 2012. Web. 1 Sept. 2016.
		Matcher matcher = pattern.matcher(s);
		boolean check = matcher.matches();
		if(!check){
			s1+="Exists";
		}
		return s1;
	}

	public String checkUsername(String s){
		String s1 = "";
		Pattern pattern = Pattern.compile("[0-9]{7,7}+[a-z]{1,1}");//Tank Tutorial 19.."Java Video Tutorial 19". Newthinktank.com. N.p., 2012. Web. 1 Sept. 2016.
		Matcher matcher = pattern.matcher(s);
		boolean check = matcher.matches();
		if(!check){
			s1+="Exists";
		}
		return s1;
	}

	public School getSchoolInfo(String s){
		sc = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM SCHOOL WHERE SCHOOLNAME = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, s);
			rs = ps.executeQuery();
			while(rs.next()){
				String school = rs.getString("SCHOOLNAME");
				sc = new School(school);
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sc;
	}
	
	public School getList(){
		School s = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM SCHOOL";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				String school = rs.getString("SCHOOLNAME");
				s = new School(school);
				schools.add(s);
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return s;
	}

	public Degree getInfo(int ID){
		dg = null;
		ResultSet rs = null;

		try{
			String query = "SELECT * FROM DEGREE WHERE DEGREEID = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, ID);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("DEGREEID");
				String name = rs.getString("DEGREENAME");
				String type = rs.getString("DEGREETYPE");
				dg = new Degree(id,name,type,sc);
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return dg;
	}

	public Degree getDegree(){
		return dg;
	}

	public StudentDegree getSDInfo(Student s){
		sd = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM STUDENT_DEGREE WHERE STUDENT = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, s.getStudentID());
			rs = ps.executeQuery();
			while(rs.next()){
				String result = rs.getString("RESULT");
				int id = rs.getInt("DEGREE");
				if(us==null){
					sd = new StudentDegree((Student) us,getInfo(id),result);
				}else{
					sd = new StudentDegree(st,getInfo(id),result);
				}
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sd;
	}

	public CourseResult getDetails(Student s){
		cr = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM COURSERESULT WHERE STUDENTID = ? ORDER BY COURSE";
			ps = conn.prepareStatement(query);
			ps.setInt(1, s.getStudentID());
			rs = ps.executeQuery();

			while(rs.next()){
				String name = rs.getString("COURSE");
				int result = rs.getInt("RESULT");
				if(us == null){
				cr = new CourseResult(getCourseDetails(name),(Student)us,result);
				}else{
					cr = new CourseResult(getCourseDetails(name),st,result);
				}
				courseDetails.add(cr);

			}


			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cr;
	}

	public Course getCourseDetails(String course){
		ResultSet rs = null;
		cs = null;
		try{
			String query = "SELECT * FROM COURSES WHERE COURSENAME = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, course);
			rs = ps.executeQuery();

			while(rs.next()){

				String coursename = rs.getString("COURSENAME");
				int credit = rs.getInt("CREDIT");
				int exam = rs.getInt("EXAM");
				int cw = rs.getInt("COURSEWORK");
				cs = new Course(coursename, credit, exam, cw);
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cs;
	}

	public Users getUser(){
		return us;
	}

	public StudentDegree getStudentDegree(){
		return sd;
	}

	public School getSchool(){
		return sc;
	}

	public Course getCourse(){
		return cs;
	}

	public List<CourseResult> getDetails(){
		return courseDetails;
	}

	public List<Student> getStudents(){
		return students;
	}

	public void closeConnection(){
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) { e.printStackTrace();}
		}

	}
	
	public List<Admin> getAdmin(){
		return administrator;
	}
	
	public List<School> getSchools(){
		return schools;
	}

}
