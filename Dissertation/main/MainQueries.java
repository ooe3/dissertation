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

public class MainQueries {
	private Connection conn = null;
	private PreparedStatement ps = null;

	private static MainQueries mq;
	Degree dg;
	CourseDegree cd;
	Course c;
	List<Degree> getDg = new ArrayList<Degree>();
	List<Course> getCd = new ArrayList<Course>();
	List<CourseDegree> getCdg = new ArrayList<CourseDegree>();
	Queries q = Queries.getQueries();
	Users us;
	int count = 0;

	private MainQueries(){
		conn = DatabaseConnection.getConnection();
	}

	public static MainQueries getMain(){
		if(mq==null)
			mq = new MainQueries();

		return mq;

	}

	//return strings that contains courses available
	public String displayAvailableCourses(){
		StringBuilder sb = new StringBuilder("");
		String display = String.format(" %-50.50s %-10s %-10s %-10s\n", "Courses","Credit","Exam","Coursework");
		sb.append(display);
		sb.append("\n");

		for(int i = 0;i<getCd.size();i++){
			String course = getCd.get(i).getCourse();
			int credit = getCd.get(i).getCredit();
			int exam = getCd.get(i).getExamPercentage();
			int cw = getCd.get(i).getCoursework();
			String t1 = String.format(" %-50.50s %-10d %-10d %-10d\n", course,credit,exam,cw);
			sb.append(t1);
			sb.append("\n");
		}

		if(getCd.size() == 0){
			String t = String.format(" %s\n", "No courses available for selection.Create a course below");
			sb.append(t);
		}

		return sb.toString();
	}

	public Course getCourses(School sc){
		c = null;
		ResultSet rs = null;
		try{
			String query = "SELECT DISTINCT c.COURSENAME, c.CREDIT, c.EXAM, c.COURSEWORK FROM COURSES AS c INNER JOIN COURSEDEGREE AS cd ON cd.COURSE_NAME = c.COURSENAME INNER JOIN DEGREE AS d ON d.DEGREEID = cd.DEGREE_ID WHERE d.SCHOOL_REF = ? ORDER BY c.COURSENAME";
			ps = conn.prepareStatement(query);
			ps.setString(1, sc.getName());
			rs = ps.executeQuery();

			while(rs.next()){
				String course = rs.getString("COURSENAME");
				int credit = rs.getInt("CREDIT");
				int exam = rs.getInt("EXAM");
				int cw = rs.getInt("COURSEWORK");
				c = new Course(course, credit, exam, cw);
				getCd.add(c);

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return c;

	}

	public Degree displayDegree(School sc){
		ResultSet rs = null;
		dg = null;
		try{
			String query = "SELECT * FROM DEGREE WHERE SCHOOL_REF = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, sc.getName());
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("DEGREEID");
				String name = rs.getString("DEGREENAME");
				String type = rs.getString("DEGREETYPE");
				dg = new Degree(id,name,type,sc);
				getDg.add(dg);
			}
			rs.close();
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		return dg;
	}

	//Insert new courses into the system by admin
	public String insertCourse(String s, int d, int d1, int d2){
		String check = "";
		int count = 0;
		try{
			for(int i = 0; i<getCd.size();i++){
				String name = getCd.get(i).getCourse().toLowerCase();
				if(s.toLowerCase().equals(name)){
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
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return check;
	}

	public CourseDegree getInserted(School sc){
		cd = null;
		ResultSet rs = null;
		try{
			String query = "select * from coursedegree inner join degree on degree.degreeid = coursedegree.degree_id where school_ref = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, sc.getName());
			rs = ps.executeQuery();
			while(rs.next()){
				String course = rs.getString("COURSE_NAME");
				int id = rs.getInt("DEGREE_ID");
				cd = new CourseDegree(q.getCourseDetails(course), q.getInfo(id));
				getCdg.add(cd);

			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cd;
	}

	//To insert the specific course and the degree it belongs to
	public String addCourseDegree(String course, String degree){
		StringBuilder sb = new StringBuilder("");
		ResultSet rs = null;
		int count = 0;
		try{
			for(int i = 0;i<getCdg.size();i++){
				String courseName = getCdg.get(i).getName().getCourse();
				String degreename = getCdg.get(i).getDegreeID().getDegreeName();
				if(courseName.equals(course) && degreename.equals(degree)){
					count+=1;
					sb.append("Error");
				}
			}
			if(count == 0){
				String query = "SELECT DEGREEID FROM DEGREE WHERE DEGREENAME = ?";
				ps = conn.prepareStatement(query);
				ps.setString(1, degree);
				rs = ps.executeQuery();
				if(rs.next()){
					int degreeid = rs.getInt("DEGREEID");
					String sql = "INSERT INTO COURSEDEGREE VALUES (?, ?)";
					ps = conn.prepareStatement(sql);
					ps.setString(1, course);
					ps.setInt(2, degreeid);
					ps.executeUpdate();
				}
				rs.close();
			}

			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String removeCourseFromDegree(String course, String degree){
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder("");
		int id = 0;
		int count = 0;
		try{

			for(int i = 0;i<getCdg.size();i++){
				String d = getCdg.get(i).getDegreeID().getDegreeName();
				String c = getCdg.get(i).getName().getCourse();
				if(d.equals(degree) && c.equals(course)){
					id+=getCdg.get(i).getDegreeID().getDegreeID();
				}else{
					count+=1;
				}
			}
			if(count != getCdg.size()){
				String sql1 = "DELETE FROM COURSEDEGREE WHERE COURSE_NAME = ? AND DEGREE_ID = ?";
				ps = conn.prepareStatement(sql1);
				ps.setString(1, course);
				ps.setInt(2, id);
				ps.executeUpdate();
				ps.close();
			}else{
				sb.append("Error");
			}


		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}

	//Remove course from the system
	public void removeCourse(String s){
		try{
			String sql1 = "DELETE FROM COURSEDEGREE WHERE COURSE_NAME = ?";
			ps = conn.prepareStatement(sql1);
			ps.setString(1, s);
			ps.executeUpdate();
			String sql2 = "DELETE FROM COURSERESULT WHERE COURSE = ?";
			ps = conn.prepareStatement(sql2);
			ps.setString(1, s);
			ps.executeUpdate();
			String sql = "DELETE FROM COURSES WHERE COURSENAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, s);
			ps.executeUpdate();
			ps.close();
		}catch (Exception e){
			e.printStackTrace();
		}

	}



	public List<Degree> getList(){
		return getDg;
	}

	public List<Course> getCourseList(){
		return getCd;
	}

	public List<CourseDegree> getCourseDegreeList(){
		return getCdg;
	}

	public int getCount(){
		return count;
	}
}

