package other;


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
