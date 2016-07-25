
public class StudentDegree {
	private int result, studenID, degreeID;
	
	public StudentDegree(){
		//default
	}
	
	public int getID(){
		return studenID;
	}
	
	public void setID(int sid){
		this.studenID=sid;
	}
	
	public int getDegreeID(){
		return degreeID;
	}
	
	public void setDegreeID(int did){
		this.degreeID=did;
	}
	
	public int getResult(){
		return result;
	}
	
	public void setResult(int results){
		result = results;
	}
}
