package gui;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import main.ListenerClass;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Choice;
import java.awt.Label;

import java.sql.*;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.List;

/**
 * This class is the view class. Contains all the interface functions
 * Methods are called from the queries class in order to show the updates on the GUI
 * It has an initialize method where all the GUI is created
 * Constructor calls the initialize method as well creating an instance of the Query class in order to execute
 * the queries
 * Also contains a method which helps display a menu bar at the end
 * One JFrame is created. This frame contains more than one panel to display the information for that panel
 * Each panel has its own information
 * @author ooemuwa
 *
 */
public class StartGUI extends JFrame{

	private JPasswordField passwordField;
	private JTextField textField;
	private JPanel panel;
	private JButton btnLogIn;
	private JLabel lblLogInPage, lblNewLabel, lblNewLabel_1;
	
	
	/**
	 * Create the application.
	 */
	public StartGUI(){
		initialize();
	}

	/**
	 * Initialize the contents of the 
	 */
	private void initialize() {

		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(1000, 800);
		

		panel = new JPanel();
		getContentPane().add(panel, "name_1756125214064824");
		panel.setLayout(null);

		passwordField = new JPasswordField();
		passwordField.setBounds(477, 252, 147, 26);
		panel.add(passwordField);

		textField = new JTextField();
		textField.setBounds(477, 193, 147, 26);
		panel.add(textField);
		textField.setColumns(10);
		

		lblLogInPage = new JLabel("Log In Page");
		lblLogInPage.setBounds(362, 68, 209, 61);
		lblLogInPage.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel.add(lblLogInPage);
		
		lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(352, 198, 80, 16);
		panel.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(352, 257, 61, 16);
		panel.add(lblNewLabel_1);

		//log in button
		//The action listener much contains all the functions of the system
		//Can't access the system without logging in
		btnLogIn = new JButton("Log In");
		btnLogIn.setActionCommand("LOG_IN");
		btnLogIn.addActionListener(new ListenerClass(this));
		btnLogIn.setBounds(390, 310, 181, 49);
		panel.add(btnLogIn);


		setVisible(true);

	}
	
	public JTextField matric(){
		return textField;
	}
	
	public JPasswordField pass(){
		return passwordField;
	}
}