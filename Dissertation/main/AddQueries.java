package main;
import gui.*;
import java.awt.*;
import java.sql.*;
public class AddQueries {
	private Connection conn = null;
	private Statement st;
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
		ResultSet rs;
		try{
			int id = getStudent(fname, lname);
			String sql = "UPDATE COURSERESULT SET RESULT = '"+overall+"' WHERE COURSE = '"+coursename+"' AND STUDENTID = '"+id+"'";
			st.executeUpdate(sql);
		}catch(Exception e){ 
			e.printStackTrace();
		}

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

public int getCwPercentage(){
	return cs.getCoursework();
}

public int getExamPercentage(){
	return cs.getExamPercentage();
}
}
