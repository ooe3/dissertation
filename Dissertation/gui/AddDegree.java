package gui;
import main.*;
import other.School;
import other.Student;
import other.Users;
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
//Add Degree frame
public class AddDegree extends JFrame{
	//Class objects created
	Users us;
	School sc;
	//methods called in Queries and MAinQueries
	Queries q = Queries.getQueries();
	MainQueries m = MainQueries.getMain();
	Student sdt;//Student object
	Choice choice;//Choice object
	List<School> getSchool;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JPasswordField passwordField;
	private JTextField textField_5;
	String selected;
	public AddDegree(){
		us = q.getUser();//getUser method called
		sc = q.getSchool();//getSchool method called
		initialize();
	}

	public void initialize(){
		//set the properties of the frame
		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(800, 500);
		//JPanel object
		JPanel panel = new JPanel();
		getContentPane().add(panel, "name_1756148928342669");
		panel.setLayout(null);
		//JLable objects
		JLabel lblFirstName = new JLabel("Degree Name*");
		lblFirstName.setBounds(37, 98, 92, 16);
		panel.add(lblFirstName);

		JLabel lblNewLabel = new JLabel("Degree Type*");
		lblNewLabel.setBounds(37, 137, 116, 16);
		panel.add(lblNewLabel);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new AddDegreeListener(this));
		btnAdd.setActionCommand("ADD");
		btnAdd.setBounds(158, 194, 117, 29);
		panel.add(btnAdd);

		JLabel lblAddAdministrator = new JLabel("Add Degree");
		lblAddAdministrator.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblAddAdministrator.setBounds(37, 27, 135, 29);
		panel.add(lblAddAdministrator);

		textField = new JTextField();
		textField.setBounds(158, 93, 359, 26);
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(158, 132, 359, 26);
		panel.add(textField_1);
		textField_1.setColumns(10);
		//JMenuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		//JMenu item objects
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setActionCommand("LogOut");
		mntmLogOut.addActionListener(new AddDegreeListener(this));
		//Add result
		JMenuItem mntmResults = new JMenuItem("Add Result");
		mntmResults.setActionCommand("Add Result");
		mntmResults.addActionListener(new AddDegreeListener(this));
		//admin home
		JMenuItem mntmAdminHome = new JMenuItem("Main Page");
		mntmAdminHome.setActionCommand("Home Menu");
		mntmAdminHome.addActionListener(new AddDegreeListener(this));
		//View General results
		JMenuItem mntmView = new JMenuItem("View General Results");
		mntmView.setActionCommand("View");
		mntmView.addActionListener(new AddDegreeListener(this));
		//Add student
		JMenuItem mntmCreate = new JMenuItem("Add Student");
		mntmCreate.setActionCommand("Add Student");
		mntmCreate.addActionListener(new AddDegreeListener(this));
		//View Students
		JMenuItem mntmViewStudent = new JMenuItem("View Students");
		mntmViewStudent.setActionCommand("ViewS");
		mntmViewStudent.addActionListener(new AddDegreeListener(this));
		//Add Degree
		JMenuItem mntmAddDegree = new JMenuItem("Add Degree");
		mntmAddDegree.setActionCommand("AddD");
		mntmAddDegree.addActionListener(new AddDegreeListener(this));
		//JMenu object and menu items added
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

	public JTextField textField(){
		return textField;
	}

	public JTextField textField1(){
		return textField_1;
	}
}
