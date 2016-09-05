package main;
import gui.*;
import other.Course;
import other.CourseResult;
import other.DatabaseConnection;
import other.Student;
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
	public int calculateScore(String exam, String coursework, int courseMark, int examMark){
		int e = getMark(exam);
		int c = getMark(coursework);
		int finalscore = 0;

		double e1 = (double)examMark/100;
		double c1 = (double)courseMark/100;

		e1*=e;
		c1*=c;
		double score = e1+c1;

		finalscore+=Math.round(score);
		return finalscore;
	}

	public int getMark(String s){
		int score = 0;
		int[] scores = {22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0};
		String[] bands = {"A1","A2","A3","A4","A5","B1","B2","B3","C1","C2","C3","D1","D2","D3","E1","E2","E3","F1","F2","F3","G1","G2","H"};

		for(int i = 0; i<scores.length;i++){
			if(s.equals(bands[i])){
				score+=scores[i];
			}
		}
		return score;
	}

	public String checkMark(String s){
		StringBuilder sb = new StringBuilder("");
		int count = 0;
		String[] marks = {"A1","A2","A3","A4","A5","B1","B2","B3","C1","C2","C3","D1","D2","D3","E1","E2","E3","F1","F2","F3","G1","G2","H"};
		for(int i = 0;i<marks.length;i++){
			if(!s.equals(marks[i])){
				count+=1;
			}
		}
		if(count==marks.length){
			sb.append("Error");
		}
		return sb.toString();
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
				sdt.setDegree(q.getSDInfo(sdt));
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
