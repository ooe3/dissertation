package main;
import gui.*;
import java.awt.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
public class AddQueries {
	private Connection conn = null;
	private Statement st;
	private PreparedStatement ps;
	Student sdt;
	Course cs;
	CourseResult crt;
	List<CourseResult> getCourses = new ArrayList<CourseResult>();
	Queries q = Queries.getQueries();

	private static AddQueries aq;

	private AddQueries(){
		conn = DatabaseConnection.getConnection();
	}

	public static AddQueries getMain(){
		if(aq==null)
			aq = new AddQueries();

		return aq;

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

	public Student getSelected(String firstName, String lastName){
		sdt = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM STUDENT WHERE FIRSTNAME = ? AND LASTNAME = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			rs = ps.executeQuery();
			while(rs.next()){
				String fName = rs.getString("FIRSTNAME");
				String lName = rs.getString("LASTNAME");
				int studentID = rs.getInt("STUDENTID");
				String studentE = rs.getString("EMAIL");
				int userid = rs.getInt("USERID");
				sdt = new Student(userid, studentID, fName, lName, studentE);
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sdt;
	}



	public void insertCourseScore(int overall, String coursename, int id){
		try{
			String sql = "UPDATE COURSERESULT SET RESULT = ? WHERE COURSE = ? AND STUDENTID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, overall);
			ps.setString(2, coursename);
			ps.setInt(3,id);
			ps.executeUpdate();

			ps.close();
		}catch(Exception e){ 
			e.printStackTrace();
		}

	}
	
	public String checkResults(){
		StringBuilder sb = new StringBuilder("");
		int count = 0;
			for(int i = 0; i<getCourses.size();i++){
				if(getCourses.get(i).getResult() == 0){
					count+=1;
				}
			}
			if(count > 0 || getCourses.size() == 0){
				sb.append("No");
			}
		return sb.toString();
	}

	public String getResult(){
		StringBuilder sb = new StringBuilder("");
		int totalcred = 0, totalpoints = 0;
		double average = 0;
			for(int i = 0;i<getCourses.size();i++){
				int res = getCourses.get(i).getResult();
				int credit = getCourses.get(i).getCourseName().getCredit();
				totalpoints+=(res*credit);
				totalcred+=credit;
			}
			average+=((double)totalpoints/totalcred);
			String s1 = String.format("%.3f", average);
			sb.append(s1);
		return sb.toString();
	}

	public void insertOverall(String mark, int id){
		try{
			String sql = "UPDATE STUDENT_DEGREE SET RESULT = ? WHERE STUDENT = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, mark);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}


	}

	public Course getCourseDetails(String course){
		ResultSet rs = null;
		cs = null;

		try{
			String query = "SELECT * FROM COURSES WHERE COURSENAME = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, course);
			rs = ps.executeQuery();

			if(rs.next()){

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

	public CourseResult getDetails(int id){
		crt = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM COURSERESULT WHERE STUDENTID = ? ORDER BY COURSE";
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while(rs.next()){
				String name = rs.getString("COURSE");
				int result = rs.getInt("RESULT");

				crt = new CourseResult(getCourseDetails(name),sdt,result);
				getCourses.add(crt);

			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return crt;
	}

	public Course getDetails(){
		return cs;
	}

	public Student getStudent(){
		return sdt;
	}
	
	public List<CourseResult> getInfo(){
		return getCourses;
	}
}
