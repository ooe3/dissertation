import java.sql.*;
import java.awt.*;
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
		String display = String.format(" %-10s %-10s %-10s %-10s\n", "Courses","Credit","Exam","Coursework");
		courses+=display;
		courses+="\n";
		String t = "", t1 = "";
		try{
			String queryx = "SELECT * FROM COURSES INNER JOIN COURSEDEGREE ON COURSES.COURSENAME = COURSEDEGREE.COURSE_NAME INNER JOIN "
					+ "DEGREE ON DEGREE.DEGREEID = COURSEDEGREE.DEGREE_ID INNER JOIN SCHOOL ON SCHOOL.SCHOOLNAME = DEGREE.SCHOOL_REF INNER JOIN "
					+ "ADMIN ON ADMIN.SCHOOLREF = SCHOOL.SCHOOLNAME WHERE ADMIN.LASTNAME = '"+s+"'";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryx);

			int check = 0;
			while(rs.next()){
				check+=1;
				//ResultSet rs
				String course = rs.getString("COURSE_NAME");
				int credit = rs.getInt("CREDIT");
				int exam = rs.getInt("EXAM");
				int cw = rs.getInt("COURSEWORK");
				t1 = String.format(" %-10s %-10d %-10d %-10d\n", course,credit,exam,cw);
				courses+=t1;
				courses+="\n";
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

	public String displayDetails(String s, String s1){
		String school = "";
		try{
			if(s.equals("Student")){
				String query = "SELECT d.DEGREENAME, d.DEGREETYPE FROM DEGREE AS d INNER JOIN STUDENT_DEGREE AS sd ON sd.DEGREE = d.DEGREEID "
						+ "INNER JOIN STUDENT AS s ON s.STUDENTID = sd.STUDENT INNER JOIN USER AS us ON us.ID = s.USERID WHERE us.MATRICNO = '"+s1+"'";
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while(rs.next()){
					String degree = rs.getString("DEGREENAME");
					String degreetype = rs.getString("DEGREETYPE");
					school+=degreetype;
					school+=" ";
					school+=degree;
				}
				rs.close();
			}else{
				String query1 = "SELECT ad.SCHOOLREF FROM ADMIN AS ad INNER JOIN USER AS us ON us.ID = ad.USERID WHERE us.MATRICNO = '"+s1+"'";
				st = conn.createStatement();
				ResultSet rs1 = st.executeQuery(query1);
				while(rs1.next()){
				String schoolref = rs1.getString("SCHOOLREF");
				if(schoolref.equals("Dental") || schoolref.equals("Adam Smith Business")){
					school+=schoolref;
					school+=" School";
					}else{
				school+="School of ";
				school+= schoolref;
				}
				}
				rs1.close();
			}

			st.close();
		}catch (Exception e){
			e.printStackTrace();
		} 
		return school;
	}



}