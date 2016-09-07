package main;
import gui.*;
import other.CourseResult;
import other.DatabaseConnection;
import other.School;
import other.Student;
import other.StudentDegree;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewResultQueries {
	private Connection conn = null;
	private Statement st;
	private PreparedStatement ps = null;
	private static ViewResultQueries vrq;
	AddQueries aq = AddQueries.getMain();//getMain method called
	Queries q = Queries.getQueries();//getQueries method called
	List<CourseResult> getCourses = aq.getInfo();//getInfo called to get list
	//StudentDegree and student objects
	StudentDegree sd;
	Student sdt;
	List<StudentDegree> getResults = new ArrayList<StudentDegree>();//getResults list
	CourseResult cr;
	List<CourseResult> getOverall = new ArrayList<CourseResult>();//getOverall list

	private ViewResultQueries(){
		conn = DatabaseConnection.getConnection();
	}

	public static ViewResultQueries getMain(){
		if(vrq==null)
			vrq = new ViewResultQueries();

		return vrq;

	}
	//method to return students total marks
	public String showResult(){
		StringBuilder sb = new StringBuilder("");
		int totalcred = 0, totalpoints = 0;
		double average = 0;
		int count = 0;
		int p = 0;
		String score = "";
		String total = "";

		String names = String.format("%s %s\n", aq.getStudent().getFirstName(), aq.getStudent().getLastName());//display firstname and lastname
		sb.append(names+"\n");
		String display = String.format(" %-50.50s %-10s %-10s %-10s\n", "Course", "Credit", "Mark", "Total");
		sb.append(display);
		sb.append("\n");
		//loop to get CourseResult objects
		for(int i = 0;i<getCourses.size();i++){
			String course = getCourses.get(i).getCourseName().getCourse();
			int res = getCourses.get(i).getResult();
			int credit = getCourses.get(i).getCourseName().getCredit();
			int r = res * credit;//multiplication performed with res and credit
			p+=res;
			//display empty string were res = 0
			if(res == 0){
				score = "";
				total = "";
			}else{
				
				score = getScore(res);//getScore method to get the band of the score
				//total of the results and total of the credit
				total = ""+(r)+"";
				totalcred+=credit;
			}
			String s = String.format(" %-50.50s %-10d %-10s %-10s\n", course, credit, score, total);
			sb.append(s);
			sb.append("\n");
			totalpoints+=(r);
		}
		average+=((double)totalpoints/totalcred);//calculate average
		//check to see if no courses selected
		if (getCourses.size() == 0){
			return "No courses selected.";
		}
		//display method once at least one result is available
		if(p != 0){
			String s2 = String.format(" %65s:%5d\n", "Total", totalpoints);
			sb.append(s2);
			sb.append("\n");
			String s1 = String.format("Your overall result is %d/%d : %.3f", totalpoints, totalcred, average);
			sb.append(s1);
		}else{
			sb.append("*Overall result will show when at least one mark has been entered*");
		}

		return sb.toString();
	}
	//method to get band for a score
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
	// method to return StudentDegree object based on the id
	public StudentDegree getDegreeInfo(int id){
		sd = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM STUDENT_DEGREE AS sd INNER JOIN DEGREE AS d ON d.DEGREEID = sd.DEGREE WHERE d.DEGREEID = ? AND sd.RESULT IS NOT NULL";
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				//store results in the variable
				int student = rs.getInt("STUDENT");
				int degreeid = rs.getInt("DEGREE");
				String result = rs.getString("RESULT");
				sd = new StudentDegree(getInfo(student),q.getInfo(degreeid),result);
				getResults.add(sd);//add studentdegree objects to the list
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sd;
	}
	//method to return student object based on the id
	public Student getInfo(int id){
		sdt = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM STUDENT WHERE STUDENTID = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				String fName = rs.getString("FIRSTNAME");
				String lName = rs.getString("LASTNAME");
				int studentID = rs.getInt("STUDENTID");
				String studentE = rs.getString("EMAIL");
				int userid = rs.getInt("USERID");
				sdt = new Student(userid, studentID, fName, lName, studentE);//object created
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sdt;
	}
	//method to return a string to display the students and overall result for the degree
	public String degreeResult(){
		Double score = 0.0;
		StringBuilder sb = new StringBuilder("");
		if(getResults.size() != 0){
			sb.append(String.format("%s\n", sd.getDegree().getDegreeName()));//get degreename and type
			sb.append("\n");
		}
		String display = String.format(" %-50.50s %-10s\n", "Name", "Result");
		sb.append(display);
		//retrieve objects from the list
		for(int i = 0;i<getResults.size();i++){
			String fname = getResults.get(i).getStudent().getFirstName();
			String lname = getResults.get(i).getStudent().getLastName();
			String result = getResults.get(i).getResult();

			String name = fname +" "+ lname;//combine the firstname and lastname
			Double res = Double.parseDouble(result);//convert result string to double
			String names = String.format(" %-50.50s %-10.3f\n", name, res);
			sb.append(names+"\n");
			score+=res;//total the result of all students
		}
		String average = String.format(" The average score for this degree is %.3f", score/getResults.size());//display and get the average
		sb.append(average);
		
		//check if no objects in the list
		if(getResults.size() == 0){
			return "No results for that degree";
		}
		return sb.toString();
	}
	//method to return courseresult objects
	public CourseResult getCourses(String s){
		cr = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM COURSERESULT WHERE COURSE = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, s);
			rs = ps.executeQuery();

			while(rs.next()){
				int student = rs.getInt("STUDENTID");
				String course = rs.getString("COURSE");
				int res = rs.getInt("RESULT");
				cr = new CourseResult(aq.getCourseDetails(course), getInfo(student),res);
				getOverall.add(cr);//add courseresult objects to the list
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cr;
	}
	//method to display the student and the overall mark for a course
	public String overallCourse(){
		StringBuilder sb = new StringBuilder("");
		int totalpoints = 0;
		double average = 0;
		int count = 0;
		int numOfStudents = 0;
		//check size of list
		if(getOverall.size() != 0){
			String degree = String.format("%s\n", cr.getCourseName().getCourse());//return coursename and format it
			sb.append(degree + "\n");
		}
		//format string
		String display = String.format(" %-40.40s %-10s\n", "Name", "Overall Mark");
		sb.append(display);
		sb.append("\n");
		//retrieve courseresult objects
		for(int i = 0; i<getOverall.size(); i++){
			String fname = getOverall.get(i).getStudentID().getFirstName();
			String lname = getOverall.get(i).getStudentID().getLastName();
			int res = getOverall.get(i).getResult();
			count+=res;
			//display and calculate average if there are objects
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
		//calculate average for all students
		if(count !=0){
			average+=((double)totalpoints/numOfStudents);
			String s1 = String.format("The overall average for this course is %.3f", average);
			sb.append(s1);
		}else{
			sb.append("No marks available yet.");
		}
		return sb.toString();
	}
	//method to return StudentDegree objects
	public StudentDegree getOveralSchool(School sc){
		sd = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM STUDENT_DEGREE AS sd INNER JOIN DEGREE AS d ON d.DEGREEID = sd.DEGREE WHERE d.SCHOOL_REF = ? AND sd.RESULT IS NOT NULL";
			ps = conn.prepareStatement(query);
			ps.setString(1, sc.getName());
			rs = ps.executeQuery();
			while(rs.next()){
				int student = rs.getInt("STUDENT");
				int degreeid = rs.getInt("DEGREE");
				String result = rs.getString("RESULT");
				sd = new StudentDegree(getInfo(student),q.getInfo(degreeid),result);
				getResults.add(sd);//add the objects to the list
			}
			rs.close();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sd;
	}
	
//method to return string to display overall for school
	public String overallSchool(){
		Double score = 0.0;
		StringBuilder sb = new StringBuilder("");
		String degree = String.format("%s\n", "Overall average for School");
		sb.append(degree + "\n");
		String display = String.format(" %-40.40s %-30.30s %-10s\n", "Name","Degree" ,"Result");
		sb.append(display);
		//retrieve objects from list
			for(int i = 0;i<getResults.size();i++){
				String fname = getResults.get(i).getStudent().getFirstName();
				String lname = getResults.get(i).getStudent().getLastName();
				String degreename = getResults.get(i).getDegree().getDegreeName();
				String result = getResults.get(i).getResult();
				
				String name = fname +" "+ lname;
				Double res = Double.parseDouble(result);
				String names = String.format(" %-40.40s %-30.30s %-10.3f\n", name, degreename, res);//format string
				sb.append(names+"\n");
				score+=res;
			}
			String average = String.format("The average score for this school is %.3f", score/getResults.size());//format string
			sb.append(average);
			//if no objects
			if(getResults.size() == 0){
				return "No results for this school";
			}
		return sb.toString();
	}

	public List<StudentDegree> getStudentDegree(){
		return getResults;
	}

	public StudentDegree getName(){
		return sd;
	}

	public CourseResult getCourseName(){
		return cr;
	}

	public List<CourseResult> getCourseResults(){
		return getOverall;
	}
}
