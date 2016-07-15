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
			String query = "SELECT c.COURSE_NAME FROM COURSERESULT AS c INNER JOIN STUDENT AS st ON c.STUDENTID = st.STUDENTID WHERE st.LASTNAME = '"+s+"'";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			int check = 0;
			while(rs.next()){
				check+=1;
				String chosen = rs.getString("COURSE_NAME");
				t1 = String.format(" %s\n", chosen);
				courses+=t1;
			}
			if(check == 0){
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
			String query = "SELECT cd.COURSE_NAME FROM COURSEDEGREE AS cd INNER JOIN DEGREE AS d ON cd.DEGREE_ID = d.DEGREEID INNER JOIN "
				+ "SCHOOL AS s ON s.SCHOOLNAME = d.SCHOOL_REF INNER JOIN ADMIN AS a ON a.SCHOOLREF = s.SCHOOLNAME WHERE a.LASTNAME = '"+s+"'"; 
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			int check = 0;
			while(rs.next()){
				check+=1;
				//ResultSet rs
				String chosen = rs.getString("COURSE_NAME");
				t1 = String.format(" %s\n", chosen);
				courses+=t1;
			}
				if(check == 0){
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
