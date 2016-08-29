package main;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import gui.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class StudentQueries {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private Statement st = null;
	Queries q = Queries.getQueries();
	List<CourseResult> cr = q.getDetails();
	List<CourseDegree> coursed = new ArrayList<CourseDegree>();
	CourseDegree cd;

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
		//int total = 0;
		int total = q.getCourseDetails(s).getCredit();
		try{
			for(int i = 0;i<cr.size();i++){
				int credit = cr.get(i).getCourseName().getCredit();
				total+=credit;
			}

			
			if(total>180){
				sb.append("Full");
			}else {
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

		for(int i = 0;i<cr.size();i++){
			String t1 = String.format(" %-40.40s %-10d\n", cr.get(i).getCourseName().getCourse(), cr.get(i).getCourseName().getCredit());
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

	public String getScore(int id){
		String s = "";
		String[] score = {"A1","A2","A3","A4","A5","B1","B2","B3","C1","C2","C3","D1","D2","D3","E1","E2","E3","F1","F2","F3","G1","G2","H"};
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
			String query = "SELECT * FROM COURSEDEGREE WHERE COURSE_NAME NOT IN (SELECT COURSE FROM COURSERESULT WHERE STUDENTID = '"+s.getStudentID()+"') AND DEGREE_ID = '"+s.getDegree().getDegree().getDegreeID()+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				String course = rs.getString("COURSE_NAME");
				int degree = rs.getInt("DEGREE_ID");
				cd = new CourseDegree(q.getCourseDetails(course), q.getInfo(degree));
				coursed.add(cd);
			}
			rs.close();
			st.close();
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

	public String displayResult(){
		StringBuilder sb = new StringBuilder("");
		int totalcred = 0, totalpoints = 0;
		double average = 0;
		int count = 0;
		String display = String.format("%-50.50s %-10s %-10s %-10s\n", "Course", "Credit", "Mark", "Total");
		sb.append(display);
		sb.append("\n");
		if(cr.size() !=0){
			for(int i = 0; i<cr.size();i++){
				int res = cr.get(i).getResult();
				int credit = cr.get(i).getCourseName().getCredit();
				String course = cr.get(i).getCourseName().getCourse();
				String mark = getScore(res);
				int total = res*credit;
				String p;
				if(res ==0){
					count+=1;
					mark = "";
					p = "";
				}else{
					p = ""+total+"";
					totalpoints+=total;
					totalcred+=credit;
				}
				String s = String.format("%-50.50s %-10d %-10s %-10s\n", course, credit, mark, p);
				sb.append(s);
				sb.append("\n");

			}
			if(count>0){
				sb.append("* Not all results have been entered *\n");
			}
			average+=((double)totalpoints/totalcred);
		}else{
			return "Not Available";
		}



		String s2 = String.format(" %65s:%5d\n", "Total", totalpoints);
		sb.append(s2);
		sb.append("\n");
		String s1 = String.format("Your overall result is %d/%d : %.3f", totalpoints, totalcred, average);
		sb.append(s1);

		return sb.toString();

	}

	public List<CourseDegree> getCD(){
		return coursed;
	}

}
