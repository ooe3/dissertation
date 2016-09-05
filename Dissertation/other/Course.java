package other;


public class Course {
	private String courseName;
	private int credit, exam, courseWork;
	private Admin ad;
	
	public Course(String cs, int cred, int exam, int cw){
		this.courseName = cs;
		this.credit = cred;
		this.exam = exam;
		this.courseWork = cw;
		
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
	
	public Admin getAdmin(){
		return ad;
	}
}
