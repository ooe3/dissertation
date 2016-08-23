package main;
import javax.swing.*;
import java.sql.*;


public class DatabaseConnection{
	private static Connection conn = null;

	private DatabaseConnection(){

	}

	public static Connection getConnection(){
		//Database connection code from H2 database website
		if(conn == null){
			try {
				Class.forName("org.h2.Driver");
				conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
//				JOptionPane.showMessageDialog(null, "Connection successful", "Connection check",
//						JOptionPane.ERROR_MESSAGE);
			} catch (Exception e){
				return null;
			}
		}
		return conn;
	}

}
