package main;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import gui.*;
import other.Course;
import other.CourseDegree;
import other.DatabaseConnection;
import other.Degree;
import other.School;
import other.Student;
import other.Users;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainQueries {
	private Connection conn = null;
	private PreparedStatement ps = null;

	private static MainQueries mq;
	Degree dg;
	CourseDegree cd;
	Course c;
	List<Degree> getDg = new ArrayList<Degree>();
	List<Course> getCd = new ArrayList<Course>();
	List<CourseDegree> getCdg = new ArrayList<CourseDegree>();
	Student st;
	List<Student> students = new ArrayList<Student>();
	Queries q = Queries.getQueries();
	Users us;
	int count = 0;

	private MainQueries(){
		conn = DatabaseConnection.getConnection();
	}

	public static MainQueries getMain(){
		if(mq==null)
			mq = new MainQueries();

		return mq;

	}

	//return strings that contains courses available
	public String displayAvailableCourses(){
		StringBuilder sb = new StringBuilder("");
		String display = String.format(" %-50.50s %-10s %-10s %-10s\n", "Courses","Credit","Exam","Coursework");
		sb.append(display);
		sb.append("\n");

		for(int i = 0;i<getCd.size();i++){
			String course = getCd.get(i).getCourse();
			int credit = getCd.get(i).getCredit();
			int exam = getCd.get(i).getExamPercentage();
			int cw = getCd.get(i).getCoursework();
			String t1 = String.format(" %-50.50s %-10d %-10d %-10d\n", course,credit,exam,cw);
			sb.append(t1);
			sb.append("\n");
		}

		if(getCd.size() == 0){
			String t = String.format(" %s\n", "No courses available for selection.Create a course below");
			sb.append(t);
		}

		return sb.toString();
	}

	public Course getCourses(School sc){
		c = null;
		ResultSet rs = null;
		try{
			String query = "SELECT DISTINCT c.COURSENAME, c.CREDIT, c.EXAM, c.COURSEWORK FROM COURSES AS c INNER JOIN COURSEDEGREE AS cd ON cd.COURSE_NAME = c.COURSENAME INNER JOIN DEGREE AS d ON d.DEGREEID = cd.DEGREE_ID WHERE d.SCHOOL_REF = ? ORDER BY c.COURSENAME";
			ps = conn.prepareStatement(query);
			ps.setString(1, sc.getName());
			rs = ps.executeQuery();

			while(rs.next()){
				String course = rs.getString("COURSENAME");
				int credit = rs.getInt("CREDIT");
				int exam = rs.getInt("EXAM");
				int cw = rs.getInt("COURSEWORK");
				c = new Course(course, credit, exam, cw);
				getCd.add(c);

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return c;

	}

	public Degree displayDegree(School sc){
		ResultSet rs = null;
		dg = null;
		try{
			String query = "SELECT * FROM DEGREE WHERE SCHOOL_REF = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, sc.getName());
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("DEGREEID");
				String name = rs.getString("DEGREENAME");
				String type = rs.getString("DEGREETYPE");
				dg = new Degree(id,name,type,sc);
				getDg.add(dg);
			}
			rs.close();
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		return dg;
	}

	public String insertDegree(String name, String type, School sc){
		String check = "";
		int counted = 0;
		try{
			for(int i = 0; i<getDg.size();i++){
				String degreename = getDg.get(i).getDegreeName();
				String degreetype = getDg.get(i).getDegreeType();
				if((degreename.toLowerCase().equals(name.toLowerCase()))&&(degreetype.toLowerCase().equals(type.toLowerCase()))){
					counted+=1;
					check+="Error";
				}

			}
			if(counted == 0){
				String sql = "INSERT INTO DEGREE (DEGREENAME, DEGREETYPE, SCHOOL_REF) VALUES (?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, type);
				ps.setString(3, sc.getName());
				ps.executeUpdate();
			}
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return check;
	}

	//Insert new courses into the system by admin
	public String insertCourse(String s, int d, int d1, int d2){
		String check = "";
		int count = 0;
		try{
			for(int i = 0; i<getCd.size();i++){
				String name = getCd.get(i).getCourse().toLowerCase();
				if(s.toLowerCase().equals(name)){
					count+=1;
					check+="Error";
				}

			}
			if(count == 0){
				String sql = "INSERT INTO COURSES VALUES (?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, s);
				ps.setInt(2, d);
				ps.setInt(3, d1);
				ps.setInt(4, d2);
				ps.executeUpdate();
			}
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return check;
	}

	public CourseDegree getInserted(School sc){
		cd = null;
		ResultSet rs = null;
		try{
			String query = "select * from coursedegree inner join degree on degree.degreeid = coursedegree.degree_id where school_ref = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, sc.getName());
			rs = ps.executeQuery();
			while(rs.next()){
				String course = rs.getString("COURSE_NAME");
				int id = rs.getInt("DEGREE_ID");
				cd = new CourseDegree(q.getCourseDetails(course), q.getInfo(id));
				getCdg.add(cd);

			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cd;
	}

	//method which stores all the students in the student object
	public Student getAll(){
		ResultSet rs = null;
		try{
			String query1 = "SELECT * FROM STUDENT AS s INNER JOIN USER AS us on us.ID = s.USERID";
			ps = conn.prepareStatement(query1);
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
				st.setDegree(q.getSDInfo(st));
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
	//insertStudent for adding a student
	//takes the firstname, lastname, email, address, matric, and degreeid
	public String insertStudent(String name, String lname, String email, String address, String matric, int degree){
		StringBuilder sb = new StringBuilder("");
		int count = 0;
		final String type = "Student";
		try{
			//check to see if the student exists which have already been stored in the objetcts
			for(int i = 0;i<students.size();i++){
				String fname = students.get(i).getFirstName().toLowerCase();
				String lstname = students.get(i).getLastName().toLowerCase();
				String mat = students.get(i).getMatric().toLowerCase();
				String mail = students.get(i).getEmail().toLowerCase();
				//first converted to lower case before checking to see if the student exists
				//firstname, lastname, matric and email are checked to see if they exist
				if((name.toLowerCase().equals(fname) && lstname.toLowerCase().equals(lname))&& matric.toLowerCase().equals(mat)){
					count+=1;//count increased if found
					sb.append("Error");

				}else if(matric.toLowerCase().equals(mat)){
					count+=1;//count increased if found
					sb.append("Exists");
				}else if(email.toLowerCase().equals(mail)){
					count+=1;//count increased if found
					sb.append("Already");
				}
			}
			//if count is still 0, then the queries below can be inserted
			if(count == 0){

				String sql = "INSERT INTO USER (MATRICNO, PASSWORD, USERTYPE) VALUES (?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, matric);
				ps.setString(2, matric);
				ps.setString(3, type);
				ps.executeUpdate();

				String sql1 = "INSERT INTO STUDENT  (FIRSTNAME, LASTNAME, EMAIL, USERID) "
					+ "VALUES (?,?,?,(SELECT ID FROM USER WHERE MATRICNO = ?))";
				ps = conn.prepareStatement(sql1);
				ps.setString(1, name);
				ps.setString(2, lname);
				ps.setString(3, email);
				ps.setString(4, matric);
				ps.executeUpdate();

				String sql2 = "INSERT INTO STUDENT_DEGREE (STUDENT, DEGREE) VALUES ((SELECT s.STUDENTID FROM STUDENT AS s INNER JOIN USER AS us ON us.ID = s.USERID WHERE us.MATRICNO = ?), ?)";
				ps = conn.prepareStatement(sql2);
				ps.setString(1, matric);
				ps.setInt(2, degree);
				ps.executeUpdate();
			}
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}

	//To insert the specific course and the degree it belongs to
	public String addCourseDegree(String course, int degree){
		StringBuilder sb = new StringBuilder("");
		ResultSet rs = null;
		int count = 0;
		try{
			for(int i = 0;i<getCdg.size();i++){
				String courseName = getCdg.get(i).getName().getCourse();
				int degreeid = getCdg.get(i).getDegreeID().getDegreeID();
				if(courseName.equals(course) && (degreeid == degree)){
					count+=1;
					sb.append("Error");
				}
			}
			if(count == 0){

				String sql = "INSERT INTO COURSEDEGREE VALUES (?, ?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, course);
				ps.setInt(2, degree);
				ps.executeUpdate();

			}

			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String removeCourseFromDegree(String course, int degree){
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder("");
		int id = 0;
		int count = 0;
		try{

			for(int i = 0;i<getCdg.size();i++){
				int d = getCdg.get(i).getDegreeID().getDegreeID();
				String c = getCdg.get(i).getName().getCourse();
				if(d == degree && c.equals(course)){
					id=getCdg.get(i).getDegreeID().getDegreeID();
				}else{
					count+=1;
				}
			}
			if(count != getCdg.size()){
				String sql1 = "DELETE FROM COURSEDEGREE WHERE COURSE_NAME = ? AND DEGREE_ID = ?";
				ps = conn.prepareStatement(sql1);
				ps.setString(1, course);
				ps.setInt(2, id);
				ps.executeUpdate();
				ps.close();
			}else{
				sb.append("Error");
			}


		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}

	//Remove course from the system
	public void removeCourse(String s){
		try{
			String sql1 = "DELETE FROM COURSEDEGREE WHERE COURSE_NAME = ?";
			ps = conn.prepareStatement(sql1);
			ps.setString(1, s);
			ps.executeUpdate();
			String sql2 = "DELETE FROM COURSERESULT WHERE COURSE = ?";
			ps = conn.prepareStatement(sql2);
			ps.setString(1, s);
			ps.executeUpdate();
			String sql = "DELETE FROM COURSES WHERE COURSENAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, s);
			ps.executeUpdate();
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}

	}



	public List<Degree> getList(){
		return getDg;
	}

	public List<Course> getCourseList(){
		return getCd;
	}

	public List<CourseDegree> getCourseDegreeList(){
		return getCdg;
	}

	public int getCount(){
		return count;
	}

	public List<Student> getStudents(){
		return students;
	}
}

