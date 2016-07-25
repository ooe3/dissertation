
public class Degree {
	private int degreeID;
	private String degreeName, degreeType, schoolName;
	private School sc;
	
	public Degree(){
		//default
	}
	
	public int getDegreeID(){
		return degreeID;
	}
	
	public String getDegreeName(){
		return degreeName;
	}
	
	public String getDegreeType(){
		return degreeType;
	}
	
	public String getName(){
		return schoolName;
	}
	
	public void setSchoolName(String s){
		this.schoolName = s;
	}
	
	public void setType(String d){
		this.degreeType = d;
	}
	
	public void setID(int ID){
		this.degreeID = ID;
	}
	
	public void setName(String n){
		this.degreeName = n;
	}
}
