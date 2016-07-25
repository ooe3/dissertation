
public class Course {
	private String courseName;
	private int credit, exam, courseWork;
	
	public Course(){
		
		
	}
	
	public String getCourse(){
		return courseName;
		
	}
	
	public int getCredit(){
		return credit;
	}
	
	public int getExamPercentage(){
		return exam;
	}
	
	public int getCoursework(){
		return courseWork;
	}
	
	public void setCourse(String s){
		courseName = s;
	}
	
	public void setCredit(int c){
		credit = c;
	}
	
	public void setExam(int e){
		exam = e;
	}
	
	public void setCoursework(int cw){
		courseWork = cw;
	}
}
