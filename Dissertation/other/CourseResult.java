package other;
public class CourseResult {
	Course cs;
	Student st;
	private int result;
	//CourseResult object
	//takes course and student objects as parameters
	
	public CourseResult(Course c, Student s, int res){
		//default
		this.cs = c;
		this.st = s;
		this.result = res;
	}
	
	public int getResult(){
		return result;
	}
	
	public Course getCourseName(){
		return cs;
	}
	
	public Student getStudentID(){
		return st;
	}
}
