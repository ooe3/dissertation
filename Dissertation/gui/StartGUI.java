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
import controllers.ListenerClass;
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
import javax.swing.ImageIcon;

///
// This is the class displayed at the start of the program.
// Displays textfields to enter login details
// 
//
// 
public class StartGUI extends JFrame{
	//variables declared
	private JPasswordField passwordField;
	private JTextField textField;
	private JPanel panel;
	private JButton btnLogIn;
	private JLabel lblLogInPage, lblNewLabel, lblNewLabel_1;
	private JLabel lblToAddAn;
	private JButton btnAddAdmin;


	/**
	 * Create the application.
	 */
	public StartGUI(){
		initialize();//call initialize method
	}

	/**
	 * Initialize the contents of the 
	 */
	private void initialize() {
		//set properties of the JFrame
		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(1000, 800);

		//JPanel object created
		panel = new JPanel();
		getContentPane().add(panel, "name_1756125214064824");
		panel.setLayout(null);
		//JPasswordfield object created
		passwordField = new JPasswordField();
		passwordField.setBounds(477, 252, 147, 26);
		panel.add(passwordField);
		//JTextfield object created
		textField = new JTextField();
		textField.setBounds(477, 193, 147, 26);
		panel.add(textField);
		textField.setColumns(10);

		//JLabel object created that displays the logo
		lblLogInPage = new JLabel("");
		lblLogInPage.setIcon(new ImageIcon("./glasgowuni-logo.jpg"));
		lblLogInPage.setBounds(352, 43, 277, 113);
		lblLogInPage.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel.add(lblLogInPage);
		//JLabel object to show username label
		lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(352, 198, 80, 16);
		panel.add(lblNewLabel);
		//JLabel object to show password label
		lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(352, 257, 61, 16);
		panel.add(lblNewLabel_1);

		//log in button
		//The action listener much contains all the functions of the system
		//Can't access the system without logging in
		btnLogIn = new JButton("Log In");
		btnLogIn.setActionCommand("LOG_IN");
		btnLogIn.addActionListener(new ListenerClass(this));
		btnLogIn.setBounds(416, 306, 181, 49);
		panel.add(btnLogIn);
		//JLabel object created to show text in the parameter below
		lblToAddAn = new JLabel("To add an administrator, click the button below");
		lblToAddAn.setBounds(356, 423, 326, 16);
		panel.add(lblToAddAn);
		//JButton object created
		btnAddAdmin = new JButton("Add Admin");
		btnAddAdmin.addActionListener(new ListenerClass(this));
		btnAddAdmin.setBounds(456, 451, 117, 29);
		panel.add(btnAddAdmin);


		setVisible(true);//call setVisible to show Jframe

	}
	//JTextfield method
	public JTextField matric(){
		return textField;
	}
	//JPasswordField method
	public JPasswordField pass(){
		return passwordField;
	}
}