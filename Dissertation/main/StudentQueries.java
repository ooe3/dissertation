package main;
import java.sql.*;
import java.util.ArrayList;
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
	private Statement st;
	
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
		ResultSet rs;
		int total = 0;
		try{
			String query = "SELECT c.CREDIT FROM COURSERESULT AS cr INNER JOIN COURSES AS c ON c.COURSENAME = cr.COURSE WHERE cr.STUDENTID = '"+d+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				int credit = rs.getInt("CREDIT");

				total+=credit;

			}
			rs.close();
			st.close();

			total+=getCredit(s);
			if(total>180){
				sb.append("Full");
			}else {
				st = conn.createStatement();
				String sql = "INSERT INTO COURSERESULT (COURSE, STUDENTID) VALUES ('"+s+"','"+d+"')";
				st.executeUpdate(sql);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
//gets the student courses to be displayed on the GUI
	//String s supplies the lastname of the student
	public String displayStudentCourses(String s){
		StringBuilder sb = new StringBuilder("");

		try{
			String query = "SELECT c.COURSE FROM COURSERESULT AS c INNER JOIN STUDENT AS st ON c.STUDENTID = st.STUDENTID INNER JOIN USER AS us ON us.ID = st.USERID WHERE us.MATRICNO = '"+s+"'";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			int check = 0;
			while(rs.next()){
				check+=1;
				String chosen = rs.getString("COURSE");
				String t1 = String.format(" %s\n", chosen);
				sb.append(t1);
				sb.append("\n");
			}
			//returns a string to show that no courses have been selected
			if(check == 0){
				String t = String.format(" %s\n", "No courses enrolled in. Enroll in a course below");
				sb.append(t);
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
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
	
//display courses that can be removed for student
	public String removeSelection(String s, String s1){
		StringBuilder sb = new StringBuilder("");
		ResultSet rs;
		String m = matric(s,s1);
		try{
			String query = "SELECT c.COURSE FROM COURSERESULT AS c INNER JOIN STUDENT AS st ON c.STUDENTID = st.STUDENTID INNER JOIN USER AS us ON us.ID = st.USERID WHERE us.MATRICNO = '"+m+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while(rs.next()){
				String name = rs.getString("COURSE");
				sb.append(name);
				sb.append(",");
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
//Display courses available for selection
	//int s takes the studentID
	public String displayCourses(int s){
		StringBuilder sb = new StringBuilder("");
		ResultSet rs;
		try{
			String query = "SELECT COURSE_NAME FROM COURSEDEGREE WHERE COURSE_NAME NOT IN (SELECT COURSE FROM COURSERESULT WHERE STUDENTID = '"+s+"') AND DEGREE_ID = (SELECT DEGREE FROM STUDENT_DEGREE WHERE STUDENT = '"+s+"')";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				String name = rs.getString("COURSE_NAME");
				sb.append(name);
				sb.append(",");
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return sb.toString();


	}
	
	public String matric(String s, String s1){
		String m = "";
		ResultSet rs;
		try{
			String query = "SELECT us.MATRICNO FROM USER AS us INNER JOIN STUDENT AS s ON s.USERID = us.ID WHERE s.FIRSTNAME = '"+s+"' AND s.LASTNAME = '"+s1+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){

				m+=rs.getString("MATRICNO");
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;
	}
	
//Remove course selected by student
	//String s passes the course to be removed
	//int d passes the studentID
	public String removeChoice(String s, int d){
		String get = "";
		ResultSet rs;
		try{
			String query = "SELECT * FROM COURSERESULT WHERE COURSE = '"+s+"' AND STUDENTID = '"+d+"' AND RESULT IS NOT NULL";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				get+="Exists";
			}else{
				String sql = "DELETE FROM COURSERESULT WHERE COURSE = '"+s+"' AND STUDENTID = '"+d+"'";
				st.executeUpdate(sql);
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		return get;
	}
	
	public String displayResult(int studentID){
		StringBuilder sb = new StringBuilder("");
		int totalcred = 0, totalpoints = 0;
		double average = 0;
		int count = 0;
		String display = String.format("%-50.50s %-10s %-10s %-10s\n", "Course", "Credit", "Overall Mark", "Credit x Overall");
		sb.append(display);
		sb.append("\n");
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
				String mark = getScore(res);
				String s = String.format("%-50.50s %-10d %-10s %-10d\n", course, credit, mark, (res*credit));
				sb.append(s);
				sb.append("\n");
				totalcred+=credit;

			}
			average+=((double)totalpoints/totalcred);

			if (count == 0){
				return "Not Available";
			}
			String s2 = String.format(" %65s:%5d\n", "Total", totalpoints);
			sb.append(s2);
			sb.append("\n");
			String s1 = String.format("Your overall result is %d/%d : %.3f", totalpoints, totalcred, average);
			sb.append(s1);

			rs.close();
			st.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();

	}
	
	public int getCredit(String course){
		int cred = 0;
		ResultSet rs;
		try{
			String query = "SELECT CREDIT FROM COURSES WHERE COURSENAME = '"+course+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				int credit = rs.getInt("CREDIT");
				cred+=credit;
			}
			rs.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cred;
	}

}
