package other;

/**
 * Users class created to store user information from the database
 * @author ooemuwa
 *
 */
public class Users {
	String matricNumber, password, userType, firstName, lastName,email;
	int userID;
	
	public Users(int userID, String firstn, String lastn, String email){
	this.userID = userID;
	this.firstName = firstn;
	this.lastName = lastn;
	this.email = email;
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

	
	public void setMatric(String m){
		this.matricNumber=m;
	}
	
	public void setPassword(String p){
		this.password=p;
	}
	
	public void setType(String t){
		this.userType=t;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getEmail(){
		return email;
	}

}
