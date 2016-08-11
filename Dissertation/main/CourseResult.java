package main;

public class CourseResult {
	private String course;
	private int result, student;
	
	public CourseResult(){
		//default
	}
	
	public int getResult(){
		return result;
	}
	
	public void setResult(int results){
		result = results;
	}
	
	public String getCourseName(){
		return course;
	}
	
	public void setCourseName(String courseName){
		this.course= courseName;
	}
	
	public int getStudentID(){
		return student;
	}

	public void setStudentID(int id){
		this.student=id;
	}
}
