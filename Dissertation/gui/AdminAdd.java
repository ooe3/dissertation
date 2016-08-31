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
import java.util.List;
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
	List<Student> stt;
	Student sdt;
	School sc;
	CourseResult crr;
	List<CourseResult> crt;
	int id;
	int count = 0;
	private JLabel lblSelectTheStudents, label, lblNewLabel_5, lblExam, lblExamPercentage, lblCoursework, lblPercentage,lblNewLabel_4,lblrIndicatesThe;
	public AdminAdd(){
		//initialize user object to get the current user
		us = q.getUser();
		sc = q.getSchool();
		sdt = q.getAll(sc);
		stt = q.getStudents();

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

		for(int i = 0; i<stt.size();i++){
			choice_1.add(stt.get(i).getFirstName()+" "+stt.get(i).getLastName());
		}
		panel_3.add(choice_1);
		//Choice that contains a dropdown list of courses
		choice_2 = new Choice();
		choice_2.setBounds(19, 204, 295, 27);
		choice_2.add("(select course)");
		panel_3.add(choice_2);

		choice_2.setVisible(false);
		//itemlistener for the choice containing the dropdown list of courses
		choice_1.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				String selected4 = choice_1.getSelectedItem();//store the selected item in selected4
				if(!selected4.equals("(select student)")){
					crt = aq.getInfo();
					lblNewLabel_4.setVisible(true);
					lblrIndicatesThe.setVisible(true);
					tokens_5 = selected4.split(" ");//split selected4 after each space in tokens. This contains the name of the student
					Student student = aq.getSelected(tokens_5[0], tokens_5[1]);
					crt.removeAll(crt);
					crr = aq.getDetails(student.getStudentID());
					
					for(int i = 0;i<crt.size();i++){
						if(crt.get(i).getResult() != 0){
							choice_2.add(crt.get(i).getCourseName().getCourse()+"(R)");
						}else{
							choice_2.add(crt.get(i).getCourseName().getCourse());
						}
					}
					choice_2.setVisible(true);

					//itemlistener for choice_2
					choice_2.addItemListener(new ItemListener(){
						public void itemStateChanged(ItemEvent ie)
						{

							selected5 = choice_2.getSelectedItem();
							//once the selecteditem is chosen, the components below become visible
							if(!selected5.equals("(select course)")){
								lblExam.setVisible(true);
								lblCoursework.setVisible(true);
								lblPercentage.setVisible(true);
								textField_6.setVisible(true);
								textField_7.setVisible(true);
								btnSubmit.setVisible(true);
								label.setVisible(true);
								lblNewLabel_5.setVisible(true);
								String[] tokens = selected5.split("\\(");
								if(tokens.length>1){
									Course cs = aq.getCourseDetails(tokens[0]);
									label.setText(""+cs.getCoursework()+"");
									lblNewLabel_5.setText(""+cs.getExamPercentage()+"");
								}else{
									Course	cs = aq.getCourseDetails(selected5);
									label.setText(""+cs.getCoursework()+"");
									lblNewLabel_5.setText(""+cs.getExamPercentage()+"");
								}
							}




						}});
					if(ie.getStateChange() == ItemEvent.SELECTED){
						count+=1;
					}

					if(count > 1){
						crt.removeAll(crt);
						stt.removeAll(stt);
						JOptionPane.showMessageDialog(null, "The page needs to be refreshed before you make another selection.", "Window",
								JOptionPane.INFORMATION_MESSAGE);
						AdminAdd add = new AdminAdd();
						add.setVisible(true);
						dispose();
					}
				}
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

		lblSelectTheStudents = new JLabel("Select the student's overall average to calculate below");
		lblSelectTheStudents.setBounds(421, 82, 354, 16);
		panel_3.add(lblSelectTheStudents);

		//choice containing a drop down list of students
		//this is used by the calculate overall button
		choice = new Choice();
		choice.setBounds(421, 104, 340, 27);
		choice.add("(select student)");
		for(int i = 0; i<stt.size();i++){
			choice.add(stt.get(i).getFirstName()+" "+stt.get(i).getLastName());
		}

		choice.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				List<CourseResult> crts = aq.getInfo();
				name_selected = choice.getSelectedItem();
				tokens_6 = name_selected.split(" ");
				Student student = aq.getSelected(tokens_6[0], tokens_6[1]);
				crts.removeAll(crts);
				CourseResult crt = aq.getDetails(student.getStudentID());


			}
		});


		panel_3.add(choice);

		JButton btnCalculateOverall = new JButton("Calculate Overall");
		btnCalculateOverall.setBounds(418, 132, 185, 29);
		btnCalculateOverall.addActionListener(new AddListener(this));
		btnCalculateOverall.setActionCommand("Calculate");
		panel_3.add(btnCalculateOverall);

		lblrIndicatesThe = new JLabel("*(R) indicates the course already has a mark*");
		lblrIndicatesThe.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblrIndicatesThe.setBounds(29, 165, 251, 16);
		lblrIndicatesThe.setVisible(false);
		panel_3.add(lblrIndicatesThe);
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
}
