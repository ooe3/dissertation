import javax.swing.*;
import java.sql.*;


public class DatabaseConnection{
	Connection conn = null;

	public static Connection connectToDatabase(){
		//Database connection code from H2 database website

		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
			JOptionPane.showMessageDialog(null, "Connection successful", "Connection check",
					JOptionPane.ERROR_MESSAGE);
			return conn;
		} catch (Exception e){
			return null;
		}
		
	}
	
}
