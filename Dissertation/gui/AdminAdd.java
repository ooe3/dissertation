package gui;
import main.*;
import controllers.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBoxMenuItem;
import java.awt.List;
/*
 * The AdminAdd class that displays GUI for the admin to add results of students 
 * and calculate overall mark
 */
public class AdminAdd extends JFrame{
	Users us;
	//creates an object of the queries class
	Queries q = Queries.getQueries();
	StudentQueries sq = StudentQueries.getMain();
	AddQueries aq = AddQueries.getMain();
	Choice choice, choice_1, choice_2;
	JTextField textField_6, textField_7;
	JButton btnSubmit;
	String selected5, name_selected;
	String[] tokens_5, tokens_6;
	private JLabel lblSelectTheStudents, label, lblNewLabel_5, lblExam, lblExamPercentage, lblCoursework, lblPercentage,lblNewLabel_4,lblIfYouWant;
	public AdminAdd(){
		//initialize user object to get the current user
		us = q.getUser();
		initialize();
	}

	public void initialize(){
		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(1000, 800);

		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, "name_1845822356495898");
		panel_3.setLayout(null);

		JLabel lblAddingResults = new JLabel("Adding results");
		lblAddingResults.setBounds(19, 54, 147, 16);
		panel_3.add(lblAddingResults);
		
		//Object of the JMenuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		/*
		 * JMenuItems added to the JMenuBar
		 * Add result, log out, view general results, change password, add student, refresh, view student
		 * All created as menu items
		 */
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setActionCommand("LogOut");
		mntmLogOut.addActionListener(new AddListener(this));

		JMenuItem mntmResults = new JMenuItem("Add Result");
		mntmResults.setActionCommand("Add");
		mntmResults.addActionListener(new AddListener(this));
		//admin home
		JMenuItem mntmAdminHome = new JMenuItem("Home");
		mntmAdminHome.setActionCommand("Home Menu");
		mntmAdminHome.addActionListener(new AddListener(this));

		//change password menu item
		JMenuItem mntmPassword = new JMenuItem("Change Password");
		mntmPassword.setActionCommand("Change Password");
		mntmPassword.addActionListener(new AddListener(this));
		
		JMenuItem mntmView = new JMenuItem("View General Results");
		mntmView.setActionCommand("View");
		mntmView.addActionListener(new AddListener(this));
		
		JMenuItem mntmCreate = new JMenuItem("Add Student");
		mntmCreate.setActionCommand("Add Student");
		mntmCreate.addActionListener(new AddListener(this));
		
		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.setActionCommand("Refresh");
		mntmRefresh.addActionListener(new AddListener(this));

		JMenuItem mntmViewStudent = new JMenuItem("View Students");
		mntmViewStudent.setActionCommand("ViewS");
		mntmViewStudent.addActionListener(new AddListener(this));

		JMenu admin = new JMenu(us.getFirstName() + " " + us.getLastName());//get users first name & last name and makes it a JMenu
		admin.add(mntmAdminHome);
		admin.add(mntmResults);
		admin.add(mntmCreate);
		admin.add(mntmView);
		admin.add(mntmViewStudent);
		admin.add(mntmPassword);
		admin.add(mntmRefresh);
		admin.add(mntmLogOut);
		menuBar.add(admin);

		JLabel lblSelectTheStudent = new JLabel("Select the student whose result you want to add below");
		lblSelectTheStudent.setBounds(19, 82, 390, 16);
		panel_3.add(lblSelectTheStudent);

		lblNewLabel_4 = new JLabel("Select course you want to add mark for below");
		lblNewLabel_4.setBounds(19, 137, 354, 16);
		lblNewLabel_4.setVisible(false);
		panel_3.add(lblNewLabel_4);
		
		//Choice object which consists a dropdown of lists
		//Contains a lists of students
		choice_1 = new Choice();
		choice_1.setBounds(19, 104, 307, 27);
		choice_1.add("(select student)");
		String select4 = q.displayStudents(us.getID());//stores the result from the displayStudents into select4
		String[]tokens_4 = select4.split(",");//splits select4 after each comma
		//add the elements in the tokens_4 array as options in the dropdwon
		for(int i = 0; i<tokens_4.length;i++){
			choice_1.add(tokens_4[i]);
		}
		panel_3.add(choice_1);
		//Choice that contains a dropdown list of courses
		choice_2 = new Choice();
		choice_2.setBounds(20, 177, 295, 27);
		choice_2.add("(select course)");
		panel_3.add(choice_2);

		choice_2.setVisible(false);
		//itemlistener for the choice containing the dropdown list of courses
		choice_1.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				String selected4 = choice_1.getSelectedItem();//store the selected item in selected4
				lblNewLabel_4.setVisible(true);
				tokens_5 = selected4.split(" ");//split selected4 after each space in tokens. This contains the name of the student
				String select7 = sq.removeSelection(tokens_5[0], tokens_5[1]);//store the remove selection which takes the tokens as parameters in select7
				String[]tokens_7 = select7.split(",");//split after each comma
				choice_2.setVisible(true);
				for(int i = 0; i<tokens_7.length;i++){
					choice_2.add(tokens_7[i]);//store the elements of tokens_7 into the dropdown list
				}

				//itemlistener for choice_2
				choice_2.addItemListener(new ItemListener(){
					public void itemStateChanged(ItemEvent ie)
					{
						selected5 = choice_2.getSelectedItem();
						//once the selecteditem is chosen, the components below become visible
						lblExam.setVisible(true);
						lblCoursework.setVisible(true);
						lblPercentage.setVisible(true);
						textField_6.setVisible(true);
						textField_7.setVisible(true);
						btnSubmit.setVisible(true);
						aq.getCourseDetails(selected5);
						label.setVisible(true);
						lblNewLabel_5.setVisible(true);
						label.setText(""+aq.getCwPercentage()+"");
						lblNewLabel_5.setText(""+aq.getExamPercentage()+"");


					}});
			}
		});

		lblNewLabel_5 = new JLabel();
		lblNewLabel_5.setBounds(127, 305, 61, 16);
		lblNewLabel_5.setVisible(false);
		panel_3.add(lblNewLabel_5);

		label = new JLabel();
		label.setBounds(127, 343, 61, 16);
		label.setVisible(false);
		panel_3.add(label);

		lblExam = new JLabel("Exam");
		lblExam.setBounds(19, 305, 61, 16);
		lblExam.setVisible(false);
		panel_3.add(lblExam);

		lblCoursework = new JLabel("Coursework");
		lblCoursework.setBounds(19, 343, 81, 16);
		lblCoursework.setVisible(false);
		panel_3.add(lblCoursework);

		lblPercentage = new JLabel("Percentage");
		lblPercentage.setBounds(127, 265, 81, 16);
		lblPercentage.setVisible(false);
		panel_3.add(lblPercentage);

		textField_6 = new JTextField();
		textField_6.setBounds(217, 300, 66, 26);
		textField_6.setVisible(false);
		panel_3.add(textField_6);
		textField_6.setColumns(10);

		textField_7 = new JTextField();
		textField_7.setBounds(217, 338, 66, 26);
		textField_7.setVisible(false);
		panel_3.add(textField_7);
		textField_7.setColumns(10);

		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(99, 366, 117, 29);
		btnSubmit.setVisible(false);
		btnSubmit.addActionListener(new AddListener(this));
		btnSubmit.setActionCommand("Submit");
		panel_3.add(btnSubmit);

		lblIfYouWant = new JLabel("*To change student or course selected, click refresh in the menu.*");
		lblIfYouWant.setBounds(19, 6, 428, 16);
		panel_3.add(lblIfYouWant);

		lblSelectTheStudents = new JLabel("Select the student's overall average to calculate below");
		lblSelectTheStudents.setBounds(421, 82, 354, 16);
		panel_3.add(lblSelectTheStudents);
		
		//choice containing a drop down list of students
		//this is used by the calculate overall button
		choice = new Choice();
		choice.setBounds(421, 104, 340, 27);
		choice.add("(select student)");
		for(int i = 0; i<tokens_4.length;i++){
			choice.add(tokens_4[i]);
		}

		choice.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				name_selected = choice.getSelectedItem();
				tokens_6 = name_selected.split(" ");
			}
		});
		

		panel_3.add(choice);

		JButton btnCalculateOverall = new JButton("Calculate Overall");
		btnCalculateOverall.setBounds(418, 132, 185, 29);
		btnCalculateOverall.addActionListener(new AddListener(this));
		btnCalculateOverall.setActionCommand("Calculate");
		panel_3.add(btnCalculateOverall);
	}
	public Choice choice(){
		return choice;
	}

	public JTextField textField_6(){
		return textField_6;
	}

	public JTextField textField_7(){
		return textField_7;
	}

	public String[] getNames(){
		return tokens_5;
	}
	
	public String[] getNameCalc(){
		return tokens_6;
	}

	public String selected5(){
		return selected5;
	}
}
