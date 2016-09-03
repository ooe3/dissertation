package gui;
import main.*;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import controllers.*;

public class CreateAdmin extends JFrame{
	Users us;
	Queries q = Queries.getQueries();
	MainQueries m = MainQueries.getMain();
	Student sdt;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JPasswordField passwordField;
	private String selected3;
	private JTextField textField_5;
	public CreateAdmin(){
		
		
		initialize();
	}
	
	public void initialize(){
		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(800, 500);

		JPanel panel = new JPanel();
		getContentPane().add(panel, "name_1756148928342669");
		panel.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name*");
		lblFirstName.setBounds(37, 98, 92, 16);
		panel.add(lblFirstName);
		
		JLabel lblNewLabel = new JLabel("Surname*");
		lblNewLabel.setBounds(37, 137, 116, 16);
		panel.add(lblNewLabel);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(37, 182, 61, 16);
		panel.add(lblAddress);
		
		JLabel lblEmail = new JLabel("Email*");
		lblEmail.setBounds(37, 223, 61, 16);
		panel.add(lblEmail);
		
		JLabel lblUsername = new JLabel("Username*");
		lblUsername.setBounds(37, 267, 74, 16);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password*");
		lblPassword.setBounds(37, 317, 92, 16);
		panel.add(lblPassword);
		
		JLabel lblSchool = new JLabel("School*");
		lblSchool.setBounds(37, 359, 61, 16);
		panel.add(lblSchool);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new CreateAdminListener(this));
		btnAdd.setActionCommand("ADD");
		btnAdd.setBounds(209, 403, 117, 29);
		panel.add(btnAdd);
		
		JLabel lblAddAdministrator = new JLabel("Add Administrator");
		lblAddAdministrator.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblAddAdministrator.setBounds(37, 27, 287, 29);
		panel.add(lblAddAdministrator);
		
		textField = new JTextField();
		textField.setBounds(158, 93, 168, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(158, 132, 168, 26);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(158, 177, 168, 26);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(158, 218, 168, 26);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(158, 262, 168, 26);
		textField_4.setEditable(false);
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(158, 312, 168, 26);
		passwordField.setEditable(false);
		panel.add(passwordField);
		passwordField.setColumns(10);
		
		JButton btnGenerateUniqueUsername = new JButton("Generate Unique Username");
		btnGenerateUniqueUsername.setBounds(445, 262, 205, 29);
		btnGenerateUniqueUsername.setActionCommand("Generate");
		btnGenerateUniqueUsername.addActionListener(new CreateAdminListener(this));
		panel.add(btnGenerateUniqueUsername);
		
		JLabel lblIndicatesRequired = new JLabel("* indicates required fields");
		lblIndicatesRequired.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblIndicatesRequired.setBounds(37, 57, 142, 16);
		panel.add(lblIndicatesRequired);
		
		JLabel lbldefaultPasswordIs = new JLabel("*Default password is the username*");
		lbldefaultPasswordIs.setBounds(454, 317, 236, 16);
		panel.add(lbldefaultPasswordIs);
		
		textField_5 = new JTextField();
		textField_5.setBounds(158, 354, 255, 26);
		panel.add(textField_5);
		textField_5.setColumns(10);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmGoBack = new JMenuItem("Go back");
		mntmGoBack.setActionCommand("Go");
		mntmGoBack.addActionListener(new CreateAdminListener(this));

		JMenu admin = new JMenu("Home");
		admin.add(mntmGoBack);
		menuBar.add(admin);
	}
	
	public String getSelected(){
		return selected3;
	}
	
	public JTextField textField(){
		return textField;
	}
	
	public JTextField textField1(){
		return textField_1;
	}
	
	public JTextField textField2(){
		return textField_2;
	}
	
	public JTextField textField3(){
		return textField_3;
	}
	
	public JTextField textField4(){
		return textField_4;
	}
	
	public JPasswordField passwordField(){
		return passwordField;
	}
}
