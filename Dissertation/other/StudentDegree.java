package other;


public class StudentDegree {
	private String result;
	private Student st;
	private Degree dg;
	
	public StudentDegree(Student s, Degree d, String res){
		//default
		this.st = s;
		this.dg = d;
		this.result = res;
	}
	
	public String getResult(){
		return result;
	}
	
	public Student getStudent(){
		return st;
	}
	
	public Degree getDegree(){
		return dg;
	}
}
