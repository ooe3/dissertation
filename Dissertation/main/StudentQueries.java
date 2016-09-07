package main;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import gui.*;
import other.CourseDegree;
import other.CourseResult;
import other.DatabaseConnection;
import other.Student;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
/*
 * Class the contains the main queries to be executed by the studentMain class
 */
public class StudentQueries {
	//Connection object and PreparedStatement object
	private Connection conn = null;
	private PreparedStatement ps = null;
	Queries q = Queries.getQueries();//getQueries method called on Queries object
	List<CourseResult> cr = q.getDetails();//getDetails method called on List object
	List<CourseDegree> coursed = new ArrayList<CourseDegree>();//new ArrayList created
	CourseDegree cd;

	//static to prevent class from being instantiated
	private static StudentQueries sq;

	private StudentQueries(){
		conn = DatabaseConnection.getConnection();
	}

	public static StudentQueries getMain(){
		if(sq==null)
			sq = new StudentQueries();

		return sq;

	}
	//Insert course selected by student
	public String insertChoice(String s, int d){
		StringBuilder sb = new StringBuilder("");
		int total = q.getCourseDetails(s).getCredit();
		try{
			//retrieve list containing courseresult objects
			for(int i = 0;i<cr.size();i++){
				int credit = cr.get(i).getCourseName().getCredit();
				total+=credit;
			}

			//check if total greater than 180
			if(total>180){
				sb.append("Full");
			}else {
				//String sql executed
				String sql = "INSERT INTO COURSERESULT (COURSE, STUDENTID) VALUES (?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, s);
				ps.setInt(2, d);
				ps.executeUpdate();
				ps.close();
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}

	//gets the student courses to be displayed on the GUI
	//String s supplies the lastname of the student
	public String displayStudentCourses(){
		StringBuilder sb = new StringBuilder("");
		//retrieve list containing courseresult objects
		for(int i = 0;i<cr.size();i++){
			String t1 = String.format(" %-40.40s %-10d\n", 
					cr.get(i).getCourseName().getCourse(), cr.get(i).getCourseName().getCredit());
			sb.append(t1);
			sb.append("\n");
		}

		//returns a string to show that no courses have been selected
		if(cr.size() == 0){
			String t = String.format(" %s\n", "No courses enrolled in. Enroll in a course below");
			sb.append(t);
		}


		return sb.toString();
	}
	//method to match score to band marks
	//returns the band mark
	public String getScore(int id){
		String s = "";
		String[] score = {"A1","A2","A3","A4","A5","B1","B2",
				"B3","C1","C2","C3","D1","D2","D3","E1","E2","E3","F1","F2","F3","G1","G2","H"};
		int[] mark = {22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0};
		for(int i = 0; i<score.length;i++){
			if(id == mark[i]){
				s+=score[i];
			}
		}

		return s;
	}

	//Display courses available for selection
	//int s takes the studentID
	public CourseDegree displayCourses(Student s){
		ResultSet rs = null;
		try{
			//String query executed
			String query = "SELECT * FROM COURSEDEGREE WHERE COURSE_NAME NOT "
					+ "IN (SELECT COURSE FROM COURSERESULT WHERE STUDENTID = ?) AND DEGREE_ID = ?";

			ps = conn.prepareStatement(query);
			ps.setInt(1, s.getStudentID());
			ps.setInt(2, s.getDegree().getDegree().getDegreeID());
			rs = ps.executeQuery();
			//retrieve data from the database
			while(rs.next()){
				String course = rs.getString("COURSE_NAME");//store in string course
				int degree = rs.getInt("DEGREE_ID");//store in degree id
				cd = new CourseDegree(q.getCourseDetails(course), q.getInfo(degree));//Course object created
				coursed.add(cd);//Course added to the list
			}
			rs.close();
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		return cd;


	}

	//Remove course selected by student
	//String s passes the course to be removed
	//int d passes the studentID
	public void removeChoice(String s, int d){
		//ResultSet rs = null;
		try{
			//sql that executes delete query
			String sql = "DELETE FROM COURSERESULT WHERE COURSE = ? AND STUDENTID = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, s);
			ps.setInt(2, d);
			ps.executeUpdate();

			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	//method to return string in specified format
	public String displayResult(){
		StringBuilder sb = new StringBuilder("");
		//int and double variables
		int totalcred = 0, totalpoints = 0;
		double average = 0;
		int count = 0;
		String display = String.format("%-50.50s %-10s %-10s %-10s\n", "Course", "Credit", "Mark", "Total");
		sb.append(display);
		sb.append("\n");
		//check list for courses
		//if 0, means no courses selected
		if(cr.size() !=0){
			//retrieve info from courseresult list
			for(int i = 0; i<cr.size();i++){
				//pass data into the respective variables
				int res = cr.get(i).getResult();
				int credit = cr.get(i).getCourseName().getCredit();
				String course = cr.get(i).getCourseName().getCourse();
				String mark = getScore(res);
				int total = res*credit;//perform multiplication on res and credit
				String p;
				if(res ==0){//if res == 0, display an empty string
					count+=1;//increase count by 1
					mark = "";
					p = "";
				}else{
					p = ""+total+"";//return the total
					totalpoints+=total;//calculate total pints
					totalcred+=credit;//calculate total credits
				}
				String s = String.format("%-50.50s %-10d %-10s %-10s\n", course, credit, mark, p);
				sb.append(s);
				sb.append("\n");

			}
			if(count>0){
				sb.append("* Not all results have been entered *\n");//add this string if count greater than zero
			}

		}else{
			return "No courses selected";//return string
		}

		//calculate the overall if all results are available
		if(count != cr.size()){
			average+=((double)totalpoints/totalcred);
			String s2 = String.format(" %65s:%5d\n", "Total", totalpoints);
			sb.append(s2);
			sb.append("\n");
			String s1 = String.format("Your overall result is %d/%d : %.3f", totalpoints, totalcred, average);
			sb.append(s1);
		}

		return sb.toString();

	}

	public List<CourseDegree> getCD(){
		return coursed;
	}

}
