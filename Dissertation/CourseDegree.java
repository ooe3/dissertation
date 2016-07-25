
public class CourseDegree {
	private String course;
	private int degree;
	
	public CourseDegree(){
		//default
	}
	
	public String getName(){
		return course;
	}
	
	public int getDegreeID(){
		return degree;
	}
	
	public void setName(String n){
		this.course = n;
	}
	
	public void setID(int id){
		this.degree=id;
	}
}
