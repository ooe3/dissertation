package main;
import gui.*;
import java.awt.*;
import java.sql.*;
public class AddQueries {
	private Connection conn = null;
	private Statement st;
	private PreparedStatement ps;
	Course cs;
	
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
	
	public void insertCourseScore(int overall, String coursename, String fname, String lname){
		try{
			int id = getStudent(fname, lname);
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
	
	public int getStudent(String fname, String lname){
		ResultSet rs = null;
		int id = 0;
		try{
			String query = "SELECT STUDENTID FROM STUDENT WHERE FIRSTNAME = ? AND LASTNAME = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, fname);
			ps.setString(2, lname);
			rs = ps.executeQuery();
			if(rs.next()){
				id += (rs.getInt("STUDENTID"));
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}
	
	public String getResult(String fname, String lname){
		StringBuilder sb = new StringBuilder("");
		int totalcred = 0, totalpoints = 0;
		double average = 0;
		ResultSet rs;
		try{
			int id = getStudent(fname, lname);
			String query = "SELECT cr.RESULT, c.CREDIT FROM COURSERESULT AS cr INNER JOIN COURSES AS c ON c.COURSENAME = cr.COURSE WHERE cr.STUDENTID = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
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
			ps.close();


		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public void insertOverall(String mark, String fname, String lname){
		try{
			int id = getStudent(fname, lname);
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

public void getCourseDetails(String course){
	ResultSet rs = null;

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
}

public String matric(String s, String s1){
	String m = "";
	ResultSet rs = null;
	try{
		String query = "SELECT us.MATRICNO FROM USER AS us INNER JOIN STUDENT AS s ON s.USERID = us.ID WHERE s.FIRSTNAME = ? AND s.LASTNAME = ?";
		ps = conn.prepareStatement(query);
		ps.setString(1, s);
		ps.setString(2, s1);
		rs = ps.executeQuery();
		if(rs.next()){

			m+=rs.getString("MATRICNO");
		}
		rs.close();
		ps.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	return m;
}
//display courses that can be removed for student
public String removeSelection(String s, String s1){
	StringBuilder sb = new StringBuilder("");
	ResultSet rs = null;
	String m = matric(s,s1);
	try{
		String query = "SELECT c.COURSE, c.RESULT FROM COURSERESULT AS c INNER JOIN STUDENT AS st ON c.STUDENTID = st.STUDENTID INNER JOIN USER AS us ON us.ID = st.USERID WHERE us.MATRICNO = ?";
		ps = conn.prepareStatement(query);
		ps.setString(1, m);
		rs = ps.executeQuery();

		while(rs.next()){
			String name = rs.getString("COURSE");
			int res = rs.getInt("RESULT");
			sb.append(name);
			if(res!=0){
				sb.append("(R)");
			}
			
			sb.append(",");
		}
		rs.close();
		ps.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	return sb.toString();
}

public int getCwPercentage(){
	return cs.getCoursework();
}

public int getExamPercentage(){
	return cs.getExamPercentage();
}
}
