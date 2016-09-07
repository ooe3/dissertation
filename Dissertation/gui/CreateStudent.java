package gui;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import main.*;
import other.Degree;
import other.School;
import other.Student;
import other.Users;
import controllers.*;
import javax.swing.JScrollBar;
//CreateStudent frame
public class CreateStudent extends JFrame{
	//Object created
	Users us;
	//Queries and MainQueries object created to access methods
	Queries q = Queries.getQueries();
	MainQueries m = MainQueries.getMain();
	//School Object
	School sc;
	String selected3;
	Choice choice;
	//Lists containing degree objects and another containing student objects
	List<Degree> dg;
	List<Student> stt;
	Degree d;
	Student sdt;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JPasswordField passwordField;
	public CreateStudent(){
		//methods executed to create the objects and the lists
		us = q.getUser();
		sc = q.getSchool();
		d = m.displayDegree(sc);
		sdt = m.getAll();
		dg = m.getList();
		stt = m.getStudents();

		initialize();
	}

	public void initialize(){
		//set frame properties
		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(1000, 800);
		//JPanel object
		JPanel panel = new JPanel();
		getContentPane().add(panel, "name_1756148928342669");
		panel.setLayout(null);
		//JLabel objects
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

		JLabel lblDegree = new JLabel("Degree*");
		lblDegree.setBounds(37, 359, 61, 16);
		panel.add(lblDegree);
		//JButton
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new CreateListener(this));
		btnAdd.setActionCommand("ADD");
		btnAdd.setBounds(209, 403, 117, 29);
		panel.add(btnAdd);

		JLabel lblAddStudent = new JLabel("Add Student");
		lblAddStudent.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblAddStudent.setBounds(37, 27, 142, 29);
		panel.add(lblAddStudent);

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

		choice = new Choice();
		choice.setBounds(158, 348, 278, 27);
		choice.add("(select degree)");
		//retrieve list objects
		for(int i = 0; i<dg.size();i++){
			choice.add(dg.get(i).getDegreeName()+"("+dg.get(i).getDegreeType()+")");//add the degreename and type to the choice
		}

		choice.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selected3 = choice.getSelectedItem();
			}

		});
		panel.add(choice);


		JButton btnGenerateUniqueUsername = new JButton("Generate Unique Username");
		btnGenerateUniqueUsername.setBounds(445, 262, 205, 29);
		btnGenerateUniqueUsername.setActionCommand("Generate");
		btnGenerateUniqueUsername.addActionListener(new CreateListener(this));
		panel.add(btnGenerateUniqueUsername);

		JLabel lblIndicatesRequired = new JLabel("* indicates required fields");
		lblIndicatesRequired.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblIndicatesRequired.setBounds(37, 57, 142, 16);
		panel.add(lblIndicatesRequired);

		JLabel lbldefaultPasswordIs = new JLabel("*Default password is the username*");
		lbldefaultPasswordIs.setBounds(454, 317, 236, 16);
		panel.add(lbldefaultPasswordIs);
		//JMenuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		//JMenu ITtems
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setActionCommand("LogOut");
		mntmLogOut.addActionListener(new CreateListener(this));
		//Add result
		JMenuItem mntmResults = new JMenuItem("Add Result");
		mntmResults.setActionCommand("Add");
		mntmResults.addActionListener(new CreateListener(this));
		//admin home
		JMenuItem mntmAdminHome = new JMenuItem("Main");
		mntmAdminHome.setActionCommand("Home Menu");
		mntmAdminHome.addActionListener(new CreateListener(this));
		//Add Degree
		JMenuItem mntmAddDegree = new JMenuItem("Add Degree");
		mntmAddDegree.setActionCommand("AddDegree");
		mntmAddDegree.addActionListener(new CreateListener(this));
		//View Student
		JMenuItem mntmViewStudent = new JMenuItem("View Students");
		mntmViewStudent.setActionCommand("ViewS");
		mntmViewStudent.addActionListener(new CreateListener(this));
		//View General
		JMenuItem mntmView = new JMenuItem("View General Results");
		mntmView.setActionCommand("View");
		mntmView.addActionListener(new CreateListener(this));
		//Add student
		JMenuItem mntmCreate = new JMenuItem("Add Student");
		mntmCreate.setActionCommand("Add Student");
		mntmCreate.addActionListener(new CreateListener(this));
		//Add menu items to the Jmenu
		JMenu admin = new JMenu(us.getFirstName() + " " + us.getLastName()+" | Home");
		admin.add(mntmAdminHome);
		admin.add(mntmResults);
		admin.add(mntmAddDegree);
		admin.add(mntmCreate);
		admin.add(mntmView);
		admin.add(mntmViewStudent);
		admin.add(mntmLogOut);
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
