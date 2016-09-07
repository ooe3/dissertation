package other;

//CourseDegree class
//Takes course object and degree object as parameters
public class CourseDegree {
	private Course cs;
	private Degree dg;
	
	public CourseDegree(Course cr, Degree d){
		//default
		this.cs = cr;
		this.dg = d;
	}
	
	public Course getName(){
		return cs;
	}
	
	public Degree getDegreeID(){
		return dg;
	}
	
}
