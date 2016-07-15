import java.sql.*;
public class Queries {
	DatabaseConnection dc;
	private Connection conn = null;
	private Statement st;
	public Queries(DatabaseConnection d){
		dc = d;
		conn = d.connectToDatabase();
	}
	
	public String displayStudentCourses(String s){
		String courses = "";
		String display = String.format(" %s\n", "Chosen Courses");
		courses+=display;
		String t = "", t1 = "";
		try{
			String query = "SELECT * FROM COURSERESULT WHERE STUDENTID IN (SELECT STUDENTID FROM STUDENT WHERE LASTNAME = '"+s+"')";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			if(rs.next()){
				String chosen = rs.getString("COURSE_NAME");
				t1 = String.format(" %s\n", chosen);
				courses+=t1;
			}else{
				t = String.format(" %s\n", "No Courses Selected yet. Add a course below");
				courses+=t;
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return courses;
	}
	
	public String displayAvailableCourses(String s){
		String courses = "";
		String display = String.format(" %s\n", "Courses Available To Students");
		courses+=display;
		String t = "", t1 = "";
		try{
			String query = "SELECT COURSE_NAME FROM COURSEDEGREE ";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			if(rs.next()){
				String chosen = rs.getString("COURSE_NAME");
				t1 = String.format(" %s\n", chosen);
				courses+=t1;
			}else{
				t = String.format(" %s\n", "No courses available for selection Add a course below");
				courses+=t;
			}
			rs.close();
			st.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return courses;
	}
	
	

}
