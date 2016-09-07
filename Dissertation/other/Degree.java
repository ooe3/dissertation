package other;
public class Degree {
	private int degreeID;
	private String degreeName, degreeType;
	private School sc;
	//Degree object
	//includes School object as a parameter
	
	public Degree(int id, String name, String type, School s){
		//default
		this.degreeID=id;
		this.degreeName=name;
		this.degreeType = type;
		this.sc = s;
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
	
	public School getSchool(){
		return sc;
	}
}
