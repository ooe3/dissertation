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

public class MainQueries {
	private Connection conn = null;
	private PreparedStatement ps = null;

	private static MainQueries mq;

	private MainQueries(){
		conn = DatabaseConnection.getConnection();
	}

	public static MainQueries getMain(){
		if(mq==null)
			mq = new MainQueries();
		
		return mq;

	}
	
//return strings that contains courses available
	//String s passes the admin lastname as a parameter
	public String displayAvailableCourses(String s){
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder("");
		String display = String.format(" %-50.50s %-10s %-10s %-10s\n", "Courses","Credit","Exam","Coursework");
		sb.append(display);
		sb.append("\n");
		try{
			String queryx = "SELECT DISTINCT COURSENAME, CREDIT, EXAM, COURSEWORK FROM COURSES AS c INNER JOIN COURSEDEGREE AS cd ON c.COURSENAME = cd.COURSE_NAME INNER JOIN "
					+ "DEGREE AS d ON d.DEGREEID = cd.DEGREE_ID INNER JOIN SCHOOL AS s ON s.SCHOOLNAME = d.SCHOOL_REF INNER JOIN "
					+ "ADMIN AS ad ON ad.SCHOOLREF = s.SCHOOLNAME INNER JOIN USER AS us ON us.ID = ad.USERID WHERE us.MATRICNO = ? ORDER BY COURSENAME ASC";
			ps = conn.prepareStatement(queryx);
			ps.setString(1, s);
			rs = ps.executeQuery();

			int check = 0;
			while(rs.next()){
				check+=1;
				//ResultSet rs
				String course = rs.getString("COURSENAME");
				int credit = rs.getInt("CREDIT");
				int exam = rs.getInt("EXAM");
				int cw = rs.getInt("COURSEWORK");

				String t1 = String.format(" %-50.50s %-10d %-10d %-10d\n", course,credit,exam,cw);
				sb.append(t1);
				sb.append("\n");
			}
			if(check == 0){
				String t = String.format(" %s\n", "No courses available for selection.Create a course below");
				sb.append(t);
			}
			rs.close();
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public String displayDegree(String s){
		StringBuilder sb = new StringBuilder("");
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM DEGREE WHERE SCHOOL_REF = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, s);
			rs = ps.executeQuery();
			while(rs.next()){
				String name = rs.getString("DEGREENAME");
				String type = rs.getString("DEGREETYPE");
				sb.append(name);
				sb.append("("+type+")");
				sb.append(",");
			}
			rs.close();
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return sb.toString();


	}
	
	//
	public String removeSelectionAdmin(String s){
		StringBuilder sb = new StringBuilder("");
		ResultSet rs = null;

		try{
			String queryx = "SELECT DISTINCT COURSENAME FROM COURSES AS c INNER JOIN COURSEDEGREE AS cd ON c.COURSENAME = cd.COURSE_NAME INNER JOIN "
					+ "DEGREE AS d ON d.DEGREEID = cd.DEGREE_ID INNER JOIN SCHOOL AS s ON s.SCHOOLNAME = d.SCHOOL_REF INNER JOIN "
					+ "ADMIN AS ad ON ad.SCHOOLREF = s.SCHOOLNAME INNER JOIN USER AS us ON us.ID = ad.USERID WHERE us.MATRICNO = ? ORDER BY COURSENAME ASC";

			ps = conn.prepareStatement(queryx);
			ps.setString(1, s);
			rs = ps.executeQuery();

			while(rs.next()){
				String name = rs.getString("COURSENAME");
				sb.append(name);
				sb.append(",");
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
//Insert new courses into the system by admin
	public String insertCourse(String s, int d, int d1, int d2){
		String check = "";
		int count = 0;
		ResultSet rs = null;

		try{
			
			String query = "SELECT COURSENAME FROM COURSES";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				String name = rs.getString("COURSENAME");
				if(s.toLowerCase().equals(name.toLowerCase())){
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



			rs.close();
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return check;
	}
	
//To insert the specific course and the degree it belongs to
	public String addCourseDegree(String course, String degree){
		StringBuilder sb = new StringBuilder("");
		ResultSet rs = null;
		try{
			
			String query = "SELECT DEGREEID FROM DEGREE WHERE DEGREENAME = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, degree);
			rs = ps.executeQuery();
			if(rs.next()){
				int degreeid = rs.getInt("DEGREEID");
				String query1 = "SELECT * FROM COURSEDEGREE WHERE COURSE_NAME = ? AND DEGREE_ID = ?";
				ps = conn.prepareStatement(query1);
				ps.setString(1, course);
				ps.setInt(2, degreeid);
				rs = ps.executeQuery();
				if(rs.next()){
					sb.append("Error");
				}else{
					String sql = "INSERT INTO COURSEDEGREE VALUES ('"+course+"', '"+degreeid+"')";
					ps = conn.prepareStatement(sql);
					ps.setString(1, course);
					ps.setInt(2, degreeid);
					ps.executeUpdate();
				}
			}
			rs.close();
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
//Remove course from the system
	public String removeCourse(String s){
		ResultSet rs;
		String get = "";
		try{
			String query = "SELECT * FROM COURSERESULT WHERE COURSE = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, s);
			rs = ps.executeQuery();
			if(rs.next()){
				get+="Exists";
			}else{
				String sql1 = "DELETE FROM COURSEDEGREE WHERE COURSE_NAME = ?";
				ps = conn.prepareStatement(sql1);
				ps.setString(1, s);
				ps.executeUpdate();
				String sql = "DELETE FROM COURSES WHERE COURSENAME = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, s);
				ps.executeUpdate();
			}
			rs.close();
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return get;
	}
}

