package main;
import gui.*;
import java.awt.*;
import java.sql.*;

public class ViewResultQueries {
	private Connection conn = null;
	private Statement st;
	
	private static ViewResultQueries vrq;
	
	private ViewResultQueries(){
		conn = DatabaseConnection.getConnection();
	}

	public static ViewResultQueries getMain(){
		if(vrq==null)
			vrq = new ViewResultQueries();
		
		return vrq;

	}
	
	public String showResult(String fname, String lname){
		StringBuilder sb = new StringBuilder("");
		int totalcred = 0, totalpoints = 0;
		double average = 0;
		int count = 0;
		int p = 0;
		String score = "";
		String total = "";
		int id = getStudent(fname, lname);
		String names = String.format("%s %s\n", fname, lname);
		sb.append(names+"\n");
		String display = String.format(" %-50.50s %-10s %-10s %-10s\n", "Course", "Credit", "Overall Mark", "Credit x Overall");
		sb.append(display);
		sb.append("\n");
		ResultSet rs;

		try{
			String query = "SELECT cr.COURSE, cr.RESULT, c.CREDIT FROM COURSERESULT AS cr INNER JOIN COURSES AS c ON c.COURSENAME = cr.COURSE WHERE cr.STUDENTID = '"+id+"' ORDER BY cr.COURSE ASC";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				count+=1;

				String course = rs.getString("COURSE");
				int res = rs.getInt("RESULT");
				int credit = rs.getInt("CREDIT"); 
				int r = res * credit;
				p+=res;
				if(res == 0){
					score = "";
					total = "";
				}else{
					score = getScore(res);
					total = ""+(r)+"";
					totalcred+=credit;
				}
				String s = String.format(" %-50.50s %-10d %-10s %-10s\n", course, credit, score, total);
				sb.append(s);
				sb.append("\n");
				totalpoints+=(r);


			}
			average+=((double)totalpoints/totalcred);

			if (count == 0){
				return "No courses selected.";
			}

			if(p != 0){
				String s2 = String.format(" %65s:%5d\n", "Total", totalpoints);
				sb.append(s2);
				sb.append("\n");
				String s1 = String.format("Your overall result is %d/%d : %.3f", totalpoints, totalcred, average);
				sb.append(s1);
			}

			rs.close();
			st.close();

		}catch(Exception e){
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
	
	public String degreeResult(String s){
		Double score = 0.0;
		StringBuilder sb = new StringBuilder("");
		String degree = String.format("%s\n", s);
		sb.append(degree + "\n");
		String display = String.format(" %-50.50s %-10s\n", "Name", "Result");
		sb.append(display);
		int count = 0;
		ResultSet rs;
		try{
			String query = "SELECT s.FIRSTNAME, s.LASTNAME, sd.RESULT FROM STUDENT_DEGREE AS sd INNER JOIN DEGREE AS d ON d.DEGREEID = sd.DEGREE INNER JOIN STUDENT AS s ON s.STUDENTID = sd.STUDENT WHERE d.DEGREENAME = '"+s+"' AND sd.RESULT IS NOT NULL";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				count+=1;
				String fname = rs.getString("FIRSTNAME");
				String lname = rs.getString("LASTNAME");
				String result = rs.getString("RESULT");

				String name = fname +" "+ lname;
				Double res = Double.parseDouble(result);
				String names = String.format(" %-50.50s %-10.3f\n", name, res);
				sb.append(names+"\n");
				score+=res;

			}
			String average = String.format("The average score for this degree is %.3f", score/count);
			sb.append(average);

			if(count == 0){
				return "No results for that degree";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public String overallCourse(String s){
		StringBuilder sb = new StringBuilder("");
		int totalpoints = 0;
		double average = 0;
		int count = 0;
		int numOfStudents = 0;
		String degree = String.format("%s\n", s);
		sb.append(degree + "\n");
		String display = String.format(" %-40.40s %-10s\n", "Name", "Overall Mark");
		sb.append(display);
		sb.append("\n");
		ResultSet rs;

		try{
			String query = "SELECT s.FIRSTNAME, s.LASTNAME, cr.RESULT FROM COURSERESULT AS cr INNER JOIN STUDENT AS s ON s.STUDENTID = cr.STUDENTID WHERE cr.COURSE = '"+s+"'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){

				String fname = rs.getString("FIRSTNAME");
				String lname = rs.getString("LASTNAME");
				int res = rs.getInt("RESULT");
				count+=res;
				if(res != 0){
					String name = fname +" "+ lname;
					numOfStudents+=1;
					String mark = getScore(res);
					String s1 = String.format(" %-40.40s %-10s\n", name, mark);
					sb.append(s1);
					sb.append("\n");
					totalpoints+=res;
				}

			}

			if(count !=0){
				average+=((double)totalpoints/numOfStudents);
				String s1 = String.format("The overall average for this course is %.3f", average);
				sb.append(s1);
			}else{
				sb.append("No marks available yet.");
			}

			rs.close();
			st.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public String overallSchool(int id){
		Double score = 0.0;
		StringBuilder sb = new StringBuilder("");
		String degree = String.format("%s\n", "Overall average for School");
		sb.append(degree + "\n");
		String display = String.format(" %-40.40s %-30.30s %-10s\n", "Name","Degree" ,"Result");
		sb.append(display);
		int count = 0;
		ResultSet rs;
		try{
			String query = "SELECT s.FIRSTNAME, s.LASTNAME, d.DEGREENAME, sd.RESULT FROM STUDENT_DEGREE AS sd INNER JOIN DEGREE AS d "
					+ "ON d.DEGREEID = sd.DEGREE INNER JOIN ADMIN AS a ON a.SCHOOLREF = d.SCHOOL_REF INNER JOIN STUDENT AS s ON s.STUDENTID = sd.STUDENT WHERE a.ADMINID = '"+id+"' AND sd.RESULT IS NOT NULL";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				count+=1;
				String fname = rs.getString("FIRSTNAME");
				String lname = rs.getString("LASTNAME");
				String degreename = rs.getString("DEGREENAME");
				String result = rs.getString("RESULT");

				String name = fname +" "+ lname;
				Double res = Double.parseDouble(result);
				String names = String.format(" %-40.40s %-30.30s %-10.3f\n", name, degreename, res);
				sb.append(names+"\n");
				score+=res;

			}
			String average = String.format("The average score for this school is %.3f", score/count);
			sb.append(average);

			if(count == 0){
				return "No results for this school";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}

}
