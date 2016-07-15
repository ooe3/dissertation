import java.awt.EventQueue;
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
public class StartGUI{

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField textField,textField_1, textField_2, textField_3, textField_4, textField_5, textField_6, textField_7 ;
	private JTextArea textArea, textArea_1;
	private JPanel panel, panel_1, panel_2, panel_3;
	private JLabel lblNewLabel, lblNewLabel_1, lblLogInPage, lblToAddA, lblEnterCourseCode, lblCourseCode, lblAddACourse;
	private JButton btnLogIn;
	String text = "ooe3";
	String password = "olubunmi";
	private Connection conn = null;
	private DatabaseConnection d;
	private Statement ps;
	private JMenuBar menuBar;
	private JMenu mnMain, admin, mnHome;
	private JMenuItem mntmLogOut, mntmExit;
	private String studentf, studentl;
	private Queries q;

	/**
	 * Create the application.
	 */
	public StartGUI(DatabaseConnection dc) {
		initialize();
		d = dc;
		conn = dc.connectToDatabase();
		q = new Queries(dc);
		
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(175, 58, 45, 16);
		panel_1.add(lblEmail);
		
		JLabel label = new JLabel("");
		label.setBounds(57, 69, 61, 16);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		label_1.setBounds(175, 6, 61, 16);
		panel_1.add(label_1);

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
		
		btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try{
					String uName = textField.getText();
					String pass = new String(passwordField.getPassword());
					String query = "SELECT * FROM USER WHERE MATRICNO = '"+uName+"' AND PASSWORD = '"+pass+"'";

					ps = conn.createStatement();
					ResultSet rs = ps.executeQuery(query);	

					if(rs.next()){

						String type = rs.getString("USERTYPE");
						
						if(type.equals("Student")){
							String query1 = "SELECT FIRSTNAME, LASTNAME FROM STUDENT WHERE USERID IN (SELECT ID FROM USER WHERE MATRICNO = '"+uName+"')";
							ResultSet rs1 = ps.executeQuery(query1);
							while(rs1.next()){
								studentf = rs1.getString("FIRSTNAME");
								studentl = rs1.getString("LASTNAME");
								mnMain = new JMenu(studentf + " " + studentl);
								mnMain.add(mntmLogOut);
								mnMain.add(mntmExit);
								displayMenu(mnMain);
								textArea.setText(q.displayStudentCourses(studentl));
								panel.setVisible(false);
								panel_1.setVisible(true);
							}
							rs1.close();
						}else{
							String query2 = "SELECT FIRSTNAME, LASTNAME FROM ADMIN WHERE USERID IN (SELECT ID FROM USER WHERE MATRICNO = '"+uName+"')";
							ResultSet rs2 = ps.executeQuery(query2);
							while(rs2.next()){
								String adminf = rs2.getString("FIRSTNAME");
								String adminl = rs2.getString("LASTNAME");
								admin = new JMenu(adminf + " " + adminl);
								admin.add(mntmLogOut);
								admin.add(mntmExit);
								displayMenu(admin);
								textArea_1.setText(q.displayAvailableCourses(adminl));
								panel.setVisible(false);
								panel_2.setVisible(true);
							}
							rs2.close();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Incorrect username or password", "Incorrect",
								JOptionPane.ERROR_MESSAGE);
					}
					textField.setText("");
					passwordField.setText("");
					rs.close();
					ps.close();
				}catch(Exception e1){
					e1.printStackTrace();
				} 
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
		textArea.setBounds(0, 97, 978, 339);
		textArea.setFont(new Font("Courier", Font.PLAIN, 14));
		panel_1.add(textArea, BorderLayout.CENTER);
		

		JButton btnNewButton = new JButton("Add Course");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(16, 544, 117, 29);
		panel_1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Remove Course");
		btnNewButton_1.setBounds(16, 667, 135, 29);
		panel_1.add(btnNewButton_1);

		lblToAddA = new JLabel("To add a course select from the list below");
		lblToAddA.setBounds(22, 473, 276, 16);
		panel_1.add(lblToAddA);

		Choice choice = new Choice();
		choice.setBounds(16, 495, 264, 27);
		panel_1.add(choice);

		lblEnterCourseCode = new JLabel("Enter course code below to remove course");
		lblEnterCourseCode.setBounds(22, 601, 281, 16);
		panel_1.add(lblEnterCourseCode);

		lblCourseCode = new JLabel("Course Code");
		lblCourseCode.setBounds(22, 634, 96, 16);
		panel_1.add(lblCourseCode);

		textField_1 = new JTextField();
		textField_1.setBounds(150, 629, 130, 26);
		panel_1.add(textField_1);
		textField_1.setColumns(10);

		panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, "name_1756148928342669");
		panel_2.setLayout(null);

		textArea_1 = new JTextArea();
		textArea_1.setBounds(6, 111, 976, 335);
		panel_2.add(textArea_1);

		lblAddACourse = new JLabel("Add a course");
		lblAddACourse.setBounds(18, 458, 96, 16);
		panel_2.add(lblAddACourse);

		JLabel lblCourseCode_1 = new JLabel("Course Code");
		lblCourseCode_1.setBounds(18, 496, 88, 16);
		panel_2.add(lblCourseCode_1);

		JLabel lblNewLabel_2 = new JLabel("Course Name");
		lblNewLabel_2.setBounds(18, 524, 88, 22);
		panel_2.add(lblNewLabel_2);

		JLabel lblCourseCredit = new JLabel("Course Credit");
		lblCourseCredit.setBounds(18, 558, 86, 16);
		panel_2.add(lblCourseCredit);

		textField_2 = new JTextField();
		textField_2.setBounds(140, 491, 348, 26);
		panel_2.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(140, 522, 348, 26);
		panel_2.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(140, 553, 348, 26);
		panel_2.add(textField_4);
		textField_4.setColumns(10);

		JButton btnAddCourse = new JButton("Add Course");
		btnAddCourse.setBounds(140, 580, 117, 29);
		panel_2.add(btnAddCourse);

		JLabel lblNewLabel_3 = new JLabel("Remove Course");
		lblNewLabel_3.setBounds(18, 621, 130, 27);
		panel_2.add(lblNewLabel_3);

		JLabel lblCourseCode_2 = new JLabel("Course Code");
		lblCourseCode_2.setBounds(18, 660, 96, 16);
		panel_2.add(lblCourseCode_2);

		textField_5 = new JTextField();
		textField_5.setBounds(140, 655, 348, 26);
		panel_2.add(textField_5);
		textField_5.setColumns(10);

		JButton btnRemoveCourse = new JButton("Remove Course");
		btnRemoveCourse.setBounds(140, 701, 130, 29);
		panel_2.add(btnRemoveCourse);
		
		JLabel lblEmail_1 = new JLabel("Email:");
		lblEmail_1.setBounds(177, 83, 38, 16);
		panel_2.add(lblEmail_1);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(227, 83, 61, 16);
		panel_2.add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		label_2.setBounds(177, 16, 61, 16);
		panel_2.add(label_2);

		panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, "name_1845822356495898");
		panel_3.setLayout(null);

		JLabel lblAddingResults = new JLabel("Adding results");
		lblAddingResults.setBounds(19, 19, 147, 16);
		panel_3.add(lblAddingResults);

		Choice choice_1 = new Choice();
		choice_1.setBounds(19, 70, 307, 27);
		panel_3.add(choice_1);

		JLabel lblSelectTheStudent = new JLabel("Select the student whose result you want to add below");
		lblSelectTheStudent.setBounds(19, 48, 390, 16);
		panel_3.add(lblSelectTheStudent);

		JLabel lblNewLabel_4 = new JLabel("Select course you want to add mark for below");
		lblNewLabel_4.setBounds(19, 137, 354, 16);
		panel_3.add(lblNewLabel_4);

		Choice choice_2 = new Choice();
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
