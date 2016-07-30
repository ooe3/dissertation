import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
public class StartGUI{

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField textField, textField_2, textField_3, textField_4, textField_6, textField_7 ;
	private JTextArea textArea, textArea_1;
	private JPanel panel, panel_1, panel_2, panel_3;
	private JLabel lblNewLabel, lblNewLabel_1, lblLogInPage, lblToAddA, lblEnterCourseCode, lblAddACourse, lblEmail, label, label_1, lblEmail_1, label_2, label_3;
	private JButton btnLogIn, btnNewButton, btnNewButton_1, btnAddCourse, btnRemoveCourse;
	private JMenuBar menuBar;
	private JMenu mnMain, admin, mnHome;
	private JMenuItem mntmLogOut, mntmExit, mntmResults, mntmAdminHome, mntmPassword, mntmStudentResults, mntmStudentHome;
	private String studentf, studentl, studentE,selected,adminSchool,selected1,selected2, selected3;
	private int studentID;
	private Queries q;
	private JTextField textField_8;
	private Choice choice, choice_1, choice_2;
	private Choice choice_3, choice_4, choice_5;
	private Users us;
	private Student st;
	private Admin ad;
	private Course cs;
	private School sc;
	private Degree dg;
	private CourseResult cr;
	private StudentDegree sd;
	private CourseDegree cd;


	/**
	 * Create the application.
	 */
	public StartGUI(Queries qu, Users u, Student stu, Admin adm, Course course, School school, Degree dge, CourseResult crs, StudentDegree sds, CourseDegree cds) {
		initialize();
		q = qu;
		us = u;
		st = stu;
		ad = adm;
		this.cs= course;
		this.sc = school;
		this.dg = dge;
		this.cr= crs;
		this.sd = sds;
		this.cd = cds;



	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("University Record System");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.setSize(1000, 800);

		panel = new JPanel();
		frame.getContentPane().add(panel, "name_1756125214064824");
		panel.setLayout(null);

		passwordField = new JPasswordField();
		passwordField.setBounds(477, 252, 147, 26);
		panel.add(passwordField);

		textField = new JTextField();
		textField.setBounds(477, 193, 147, 26);
		panel.add(textField);
		textField.setColumns(10);

		mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e1){
				panel.setVisible(true);
				panel_1.setVisible(false);
				panel_2.setVisible(false);
				panel_3.setVisible(false);
				displayMenu(mnHome);
			}
		});

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e1){
				System.exit(0);
			}
		});

		mntmResults = new JMenuItem("Add Result");
		mntmResults.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e1){
				panel_2.setVisible(false);
				panel_3.setVisible(true);
			}
		});

		mntmAdminHome = new JMenuItem("Home");
		mntmAdminHome.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e1){
				panel_2.setVisible(true);
				panel_3.setVisible(false);
			}
		});
		mntmStudentHome = new JMenuItem("Home");
		mntmStudentHome.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e1){
				panel_1.setVisible(true);
			}
		});

		mntmStudentResults = new JMenuItem("View Results");
		mntmStudentResults.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e1){

			}
		});

		mntmPassword = new JMenuItem("Change Password");
		mntmPassword.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e1){
				panel_2.setVisible(true);
				panel_3.setVisible(false);
			}
		});


		btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uName = textField.getText();
				String pass = new String(passwordField.getPassword());
				if(q.LogIn(uName, pass).equals("Success")){
					if(us.getType().equals("Student")){

						mnMain = new JMenu(st.getFirstName() + " " + st.getLastName());
						mnMain.add(mntmStudentHome);
						mnMain.add(mntmStudentResults);
						mnMain.add(mntmPassword);
						mnMain.add(mntmLogOut);
						mnMain.add(mntmExit);
						displayMenu(mnMain);

						label_1 = new JLabel(q.displayDetails(us.getType(), us.getMatric()));
						label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
						label_1.setBounds(175, 6, 400, 28);
						panel_1.add(label_1);

						label = new JLabel(st.getEmail());
						label.setBounds(219, 58, 276, 16);
						panel_1.add(label);


						textArea.setText(q.displayStudentCourses(st.getLastName()));
						panel.setVisible(false);
						panel_1.setVisible(true);


						choice = new Choice();
						choice.setBounds(22, 495, 264, 27);
						choice.add("");
						String select = q.displayCourses(st.getStudentID());
						String[]tokens = select.split(",");

						for(int i = 0; i<tokens.length;i++){
							choice.add(tokens[i]);
							}

						choice.addItemListener(new ItemListener(){
							public void itemStateChanged(ItemEvent ie)
							{
								selected = choice.getSelectedItem();
							}

						});

						btnNewButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(choice.getSelectedItem().equals("")){
									JOptionPane.showMessageDialog(null, "No course selected", "Window",
											JOptionPane.ERROR_MESSAGE);
								}else{
									q.insertChoice(selected, st.getStudentID());
									JOptionPane.showMessageDialog(null, "Course selection successful", "Window",
											JOptionPane.INFORMATION_MESSAGE);

								}
							}
						});

						choice_4 = new Choice();
						choice_4.setBounds(22, 623, 281, 27);
						choice_4.add("");
						String select1 = q.removeSelection(st.getLastName());
						String[]tokens_1 = select1.split(",");

						for(int i = 0; i<tokens_1.length;i++){
							choice_4.add(tokens_1[i]);
						}

						choice_4.addItemListener(new ItemListener(){
							public void itemStateChanged(ItemEvent ie)
							{
								selected1 = choice_4.getSelectedItem();
							}

						});

						btnNewButton_1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(choice_4.getSelectedItem().equals("")){
									JOptionPane.showMessageDialog(null, "No course selected", "Window",
											JOptionPane.ERROR_MESSAGE);
								}else{
									q.removeChoice(selected1, st.getStudentID());
									JOptionPane.showMessageDialog(null, "Removal successful", "Window",
											JOptionPane.ERROR_MESSAGE);

								}
							}
						});

						panel_1.add(choice);
						panel_1.add(choice_4);

					}else{

						admin = new JMenu(ad.getFirstName() + " " + ad.getLastName());
						admin.add(mntmAdminHome);
						admin.add(mntmResults);
						admin.add(mntmPassword);
						admin.add(mntmLogOut);
						admin.add(mntmExit);
						displayMenu(admin);

						label_2 = new JLabel(q.displayDetails(us.getType(), us.getMatric()));
						label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
						label_2.setBounds(177, 16, 400, 28);
						panel_2.add(label_2);

						label_3 = new JLabel(ad.getEmail());
						label_3.setBounds(227, 83, 276, 16);
						panel_2.add(label_3);


						textArea_1.setText(q.displayAvailableCourses(ad.getLastName()));
						panel.setVisible(false);
						panel_2.setVisible(true);

						btnAddCourse.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(textField_2.getText().equals("") || textField_3.getText().equals("") || textField_4.getText().equals("") || textField_8.getText().equals("")){
									JOptionPane.showMessageDialog(null, "One or more textfields empty. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);
								}else{
									try{
										int credit = Integer.parseInt(textField_3.getText());
										int exam = Integer.parseInt(textField_4.getText());
										int cw = Integer.parseInt(textField_8.getText());
										if(q.insertCourse(textField_2.getText(), credit, exam, cw).equals("Error")){
											JOptionPane.showMessageDialog(null, "Course already exists", "Error message", JOptionPane.ERROR_MESSAGE);
										}else{
											String[] tokens = selected3.split("\\(");
											q.addCourseDegree(textField_2.getText(), tokens[0]);
											JOptionPane.showMessageDialog(null, "Course addition successful", "Window",
													JOptionPane.INFORMATION_MESSAGE);
										}
									}catch (NumberFormatException e1){
										JOptionPane.showMessageDialog(null, "Numeric input required", "Error message", JOptionPane.ERROR_MESSAGE);
									}
								}
							}
						});

						choice_5 = new Choice();
						choice_5.setBounds(18, 695, 507, 27);
						choice_5.add("");

						String select2 = q.removeSelectionAdmin(ad.getLastName());
						String[]tokens_2 = select2.split(",");

						for(int i = 0; i<tokens_2.length;i++){
							choice_5.add(tokens_2[i]);
						}

						choice_5.addItemListener(new ItemListener(){
							public void itemStateChanged(ItemEvent ie)
							{
								selected2 = choice_5.getSelectedItem();
							}

						});



						btnRemoveCourse.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(choice_5.getSelectedItem().equals("")){
									JOptionPane.showMessageDialog(null, "No course selected", "Window",
											JOptionPane.ERROR_MESSAGE);
								}else{
									q.removeCourse(selected2);
									JOptionPane.showMessageDialog(null, "Removal successful", "Window",
											JOptionPane.ERROR_MESSAGE);
								}
							}
						});

						choice_3 = new Choice();
						choice_3.setBounds(177, 603, 348, 27);
						choice_3.add("");

						String select3 = q.displayDegree(ad.getSchoolName());
						String[]tokens_3 = select3.split(",");

						for(int i = 0; i<tokens_3.length;i++){
							choice_3.add(tokens_3[i]);
						}

						choice_3.addItemListener(new ItemListener(){
							public void itemStateChanged(ItemEvent ie)
							{
								selected3 = choice_3.getSelectedItem();
							}

						});

						panel_2.add(choice_3);
						panel_2.add(choice_5);

					}

				}else{
					JOptionPane.showMessageDialog(null, "Incorrect username or password", "Incorrect",
							JOptionPane.ERROR_MESSAGE);
				}
				textField.setText("");
				passwordField.setText("");
			}



		});

		btnLogIn.setBounds(390, 310, 181, 49);
		panel.add(btnLogIn);


		lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(352, 198, 80, 16);
		panel.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(352, 257, 61, 16);
		panel.add(lblNewLabel_1);

		lblLogInPage = new JLabel("Log In Page");
		lblLogInPage.setBounds(362, 68, 209, 61);
		lblLogInPage.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel.add(lblLogInPage);

		panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, "name_1756125223660446");
		panel_1.setLayout(null);

		textArea = new JTextArea();
		textArea.setBounds(16, 95, 978, 339);
		textArea.setFont(new Font("Courier", Font.PLAIN, 14));
		panel_1.add(textArea, BorderLayout.CENTER);


		btnNewButton = new JButton("Add Course");

		btnNewButton.setBounds(16, 544, 117, 29);
		panel_1.add(btnNewButton);

		btnNewButton_1 = new JButton("Remove Course");
		btnNewButton_1.setBounds(16, 667, 135, 29);
		panel_1.add(btnNewButton_1);

		lblToAddA = new JLabel("To add a course select from the list below");
		lblToAddA.setBounds(22, 473, 276, 16);
		panel_1.add(lblToAddA);

		lblEnterCourseCode = new JLabel("Select course you want to remove below");
		lblEnterCourseCode.setBounds(22, 601, 281, 16);
		panel_1.add(lblEnterCourseCode);

		panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, "name_1756148928342669");
		panel_2.setLayout(null);

		textArea_1 = new JTextArea();
		textArea_1.setBounds(6, 111, 976, 335);
		textArea_1.setEditable(false);//method to prevent text area from being edited
		textArea_1.setFont(new Font("Courier", Font.PLAIN, 14));//set font type for text in text area
		panel_2.add(textArea_1, BorderLayout.CENTER);

		lblAddACourse = new JLabel("Add a course");
		lblAddACourse.setBounds(18, 458, 96, 16);
		panel_2.add(lblAddACourse);

		JLabel lblCourseCode_1 = new JLabel("Course Name");
		lblCourseCode_1.setBounds(18, 486, 88, 16);
		panel_2.add(lblCourseCode_1);

		JLabel lblNewLabel_2 = new JLabel("Course Credit");
		lblNewLabel_2.setBounds(18, 514, 88, 22);
		panel_2.add(lblNewLabel_2);

		JLabel lblCourseCredit = new JLabel("Exam Percentage");
		lblCourseCredit.setBounds(18, 548, 108, 16);
		panel_2.add(lblCourseCredit);

		textField_2 = new JTextField();
		textField_2.setBounds(177, 481, 348, 26);
		panel_2.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(177, 512, 348, 26);
		panel_2.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(177, 543, 348, 26);
		panel_2.add(textField_4);
		textField_4.setColumns(10);

		btnAddCourse = new JButton("Add Course");
		btnAddCourse.setBounds(533, 512, 117, 29);
		panel_2.add(btnAddCourse);

		JLabel lblNewLabel_3 = new JLabel("Select course you want to remove below");
		lblNewLabel_3.setBounds(18, 659, 259, 27);
		panel_2.add(lblNewLabel_3);

		btnRemoveCourse = new JButton("Remove Course");
		btnRemoveCourse.setBounds(533, 693, 130, 29);
		panel_2.add(btnRemoveCourse);

		lblEmail_1 = new JLabel("Email:");
		lblEmail_1.setBounds(177, 83, 44, 16);
		panel_2.add(lblEmail_1);

		JLabel lblCourseworkPercentage = new JLabel("Coursework Percentage");
		lblCourseworkPercentage.setBounds(18, 576, 147, 16);
		panel_2.add(lblCourseworkPercentage);

		textField_8 = new JTextField();
		textField_8.setBounds(177, 571, 348, 26);
		panel_2.add(textField_8);
		textField_8.setColumns(10);

		JLabel lblDegree = new JLabel("Degree");
		lblDegree.setBounds(18, 607, 61, 16);
		panel_2.add(lblDegree);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(175, 58, 45, 16);
		panel_1.add(lblEmail);

		panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, "name_1845822356495898");
		panel_3.setLayout(null);

		JLabel lblAddingResults = new JLabel("Adding results");
		lblAddingResults.setBounds(19, 19, 147, 16);
		panel_3.add(lblAddingResults);

		choice_1 = new Choice();
		choice_1.setBounds(19, 70, 307, 27);
		panel_3.add(choice_1);

		JLabel lblSelectTheStudent = new JLabel("Select the student whose result you want to add below");
		lblSelectTheStudent.setBounds(19, 48, 390, 16);
		panel_3.add(lblSelectTheStudent);

		JLabel lblNewLabel_4 = new JLabel("Select course you want to add mark for below");
		lblNewLabel_4.setBounds(19, 137, 354, 16);
		panel_3.add(lblNewLabel_4);

		choice_2 = new Choice();
		choice_2.setBounds(19, 179, 295, 27);
		panel_3.add(choice_2);

		JLabel lblExam = new JLabel("Exam");
		lblExam.setBounds(19, 351, 61, 16);
		panel_3.add(lblExam);

		JLabel lblCoursework = new JLabel("Coursework");
		lblCoursework.setBounds(19, 392, 81, 16);
		panel_3.add(lblCoursework);

		JLabel lblPercentage = new JLabel("Percentage");
		lblPercentage.setBounds(127, 319, 81, 16);
		panel_3.add(lblPercentage);

		textField_6 = new JTextField();
		textField_6.setBounds(217, 346, 66, 26);
		panel_3.add(textField_6);
		textField_6.setColumns(10);

		textField_7 = new JTextField();
		textField_7.setBounds(217, 387, 66, 26);
		panel_3.add(textField_7);
		textField_7.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(127, 351, 61, 16);
		panel_3.add(lblNewLabel_5);

		JLabel label = new JLabel("");
		label.setBounds(127, 392, 61, 16);
		panel_3.add(label);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(110, 425, 117, 29);
		panel_3.add(btnSubmit);

		mnHome = new JMenu("Home");
		mnHome.add(mntmExit);
		this.displayMenu(mnHome);


		frame.setVisible(true);

	}

	public void displayMenu(JMenu menub){
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		menuBar.add(menub);
	}
}