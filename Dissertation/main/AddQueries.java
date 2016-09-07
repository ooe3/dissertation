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
/*
 * AddQueries class which performs the methods below when it's called
 */
public class AddQueries {
	private Connection conn = null;//Connection object
	private PreparedStatement ps;//PreparedStatement object
	Student sdt;//Student object
	Course cs;//Course object
	CourseResult crt;//CourseResult object
	List<CourseResult> getCourses = new ArrayList<CourseResult>();//list containing the courseresult objects
	Queries q = Queries.getQueries();//Queries method to get the 

	private static AddQueries aq;

	private AddQueries(){
		conn = DatabaseConnection.getConnection();//to get the connection to execute the queries
	}
	//prevents the AddQueries from being instantiated by just calling this method
	public static AddQueries getMain(){
		if(aq==null)
			aq = new AddQueries();

		return aq;

	}

	//Calculate the score of a course
	//Method to be called in the GUI and int result returned to pass as one
	//of the parameters in insertCourseScore
	public int calculateScore(String exam, String coursework, int courseMark, int examMark){
		//exam and courseworks are strings which contain bands
		//getMark method converts the band to the score represented by it
		int e = getMark(exam);
		int c = getMark(coursework);
		int finalscore = 0;
		//divides the percentages by 100
		double e1 = (double)examMark/100;
		double c1 = (double)courseMark/100;
		
		//multiply the marks by their respective percentages and sums them
		e1*=e;
		c1*=c;
		double score = e1+c1;
		
		//Math.round returns the int closest to the argument
		finalscore+=Math.round(score);
		return finalscore;
	}
	//method to get the mark which represents the band
	public int getMark(String s){
		int score = 0;
		int[] scores = {22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0};//array of marks
		//array of bands
		String[] bands = {"A1","A2","A3","A4","A5","B1","B2","B3","C1","C2","C3","D1","D2","D3","E1","E2","E3","F1","F2","F3","G1","G2","H"};
		
		//for loops to match the bands to the scores
		for(int i = 0; i<scores.length;i++){
			if(s.equals(bands[i])){
				score=scores[i];
			}
		}
		return score;
	}
	
	//method to check if the string s entered is one of the bands
	//returns error if otherwise
	public String checkMark(String s){
		StringBuilder sb = new StringBuilder("");
		int count = 0;
		String[] marks = {"A1","A2","A3","A4","A5","B1","B2","B3","C1","C2","C3","D1","D2","D3","E1","E2","E3","F1","F2","F3","G1","G2","H"};
		for(int i = 0;i<marks.length;i++){
			if(!s.equals(marks[i])){
				count+=1;//increase count for every mark not equal to string s
			}
		}
		//if count equals the length of the array, it means string s wasn't in the array
		if(count==marks.length){
			sb.append("Error");
		}
		return sb.toString();
	}
	//method to return a student object based on the firstname and lastname
	public Student getSelected(String firstName, String lastName){
		sdt = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM STUDENT WHERE FIRSTNAME = ? AND LASTNAME = ?";//query to be executed
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
				sdt.setDegree(q.getSDInfo(sdt));//set the degree based on the students selection
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sdt;
	}

	//method to insert the overall into courseresult table where coursename and studentid match
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

	//method to check for the courses that don't have mark and for the students that have no courses registered yet
	//method used to check if all courses have a result to allow another method to calculate the average
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
	//method to return the result as a string in order to display it with 3 decimal places
	public String getResult(){
		StringBuilder sb = new StringBuilder("");
		int totalcred = 0, totalpoints = 0;
		double average = 0;
		for(int i = 0;i<getCourses.size();i++){
			int res = getCourses.get(i).getResult();
			int credit = getCourses.get(i).getCourseName().getCredit();
			totalpoints+=(res*credit);//calculate the total marks
			totalcred+=credit;//calculate the total number of credits
		}
		average+=((double)totalpoints/totalcred);//divide the total marks by total credits
		String s1 = String.format("%.3f", average);
		sb.append(s1);
		return sb.toString();
	}
	//method to insert the overall mark
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
	//method that returns a Course object of the requested course
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
				cs = new Course(coursename, credit, exam, cw);//create new object
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cs;
	}
	//method to return the course results object of a student with that studentid
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

				crt = new CourseResult(getCourseDetails(name),sdt,result);//create new object
				getCourses.add(crt);//add object to list

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
	
	public Connection testConn() throws SQLException{
		conn.setAutoCommit(false);
		return conn;
	}
}
