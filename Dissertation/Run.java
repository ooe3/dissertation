import java.sql.*;
import javax.swing.*;
public class Run {

	public static void main(String[] args) throws Exception{
	// TODO Auto-generated method stub
		//Database connection code from H2 database website
		Class.forName("org.h2.Driver");
    Connection conn = DriverManager.
        getConnection("jdbc:h2:~/test", "sa", "");
    // add application code here
    //LogInGUI lg = new LogInGUI();
    MainGUI mg = new MainGUI();
    
    javax.swing.SwingUtilities.invokeLater(new Runnable(){
    	public void run(){
    		mg.displayGUI();
    	}
    });
    //database connection
    //add extra comment here
    conn.close();
		
	}

}
