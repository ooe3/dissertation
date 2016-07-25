
public class Users {
	String matricNumber, password, userType;
	int userID;
	
	public Users(){
	//default
	}
	
	public int getID(){
		return userID;
	}
	
	public String getMatric(){
		return matricNumber;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getType(){
		return userType;
	}
	
	public void setID(int i){
		this.userID = i;
	}
	
	public void setMatric(String m){
		this.matricNumber=m;
	}
	
	public void setPassword(String p){
		this.password=p;
	}
	
	public void setType(String t){
		this.userType=t;
	}

}
