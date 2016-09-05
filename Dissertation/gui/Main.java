package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Font;
import java.util.List;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import main.*;
import other.Course;
import other.CourseDegree;
import other.CourseResult;
import other.Degree;
import other.School;
import other.Student;
import other.Users;
import controllers.*;
import javax.swing.JScrollBar;
import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;

public class Main extends JFrame{
	Users us;
	Queries q = Queries.getQueries();
	MainQueries m = MainQueries.getMain();
	StudentQueries sq = StudentQueries.getMain();
	AddQueries aq = AddQueries.getMain();
	School sc;
	List<Degree> dg;
	List<Course> cdg;
	List<CourseDegree> cr;
	List<CourseResult> courseResult;
	Degree d;
	Course cd;
	CourseDegree courseDegree;
	List<CourseDegree> courseDegreeList;
	JTextField textField_2, textField_3, textField_4, textField_8;
	Choice choice_3, choice_5, choice, choice_1, choice_2, choice_4, choice_6, choice_7;
	String selected3, selected2, selected4, selected5,selected6,selected7, selectedStudent, selectedCourse;
	JScrollPane scrollPane;
	JLabel lblNewLabel;
	JButton btnEnrollStudent;
	int count = 0;
	int id = 0;
	public Main(){
		us = q.getUser();
		sc = q.getSchool();
		d = m.displayDegree(sc);
		cd = m.getCourses(sc);
		courseDegree = m.getInserted(sc);
		courseDegreeList = m.getCourseDegreeList();
		dg = m.getList();
		cdg = m.getCourseList();
		courseResult = q.getDetails();
		initialize();

	}

	public void initialize(){

		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(1200, 800);

		JPanel panel = new JPanel();
		getContentPane().add(panel, "name_1756148928342669");
		panel.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setActionCommand("LogOut");
		mntmLogOut.addActionListener(new MainListener(this));

		JMenuItem mntmResults = new JMenuItem("Add Result");
		mntmResults.setActionCommand("Add");
		mntmResults.addActionListener(new MainListener(this));
		//admin home
		JMenuItem mntmAdminHome = new JMenuItem("Main Page");
		mntmAdminHome.setActionCommand("Home Menu");
		mntmAdminHome.addActionListener(new MainListener(this));
		
		JMenuItem mntmAddDegree = new JMenuItem("Add Degree");
		mntmAddDegree.setActionCommand("AddD");
		mntmAddDegree.addActionListener(new MainListener(this));

		JMenuItem mntmView = new JMenuItem("View General Results");
		mntmView.setActionCommand("View");
		mntmView.addActionListener(new MainListener(this));

		JMenuItem mntmCreate = new JMenuItem("Add Student");
		mntmCreate.setActionCommand("Add Student");
		mntmCreate.addActionListener(new MainListener(this));

		JMenuItem mntmViewStudent = new JMenuItem("View Students");
		mntmViewStudent.setActionCommand("ViewS");
		mntmViewStudent.addActionListener(new MainListener(this));

		JMenu admin = new JMenu(us.getFirstName() + " " + us.getLastName()+" | Home");
		admin.add(mntmAdminHome);
		admin.add(mntmResults);
		admin.add(mntmAddDegree);
		admin.add(mntmCreate);
		admin.add(mntmView);
		admin.add(mntmViewStudent);
		admin.add(mntmLogOut);
		menuBar.add(admin);

		JLabel label = new JLabel();
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		label.setBounds(177, 16, 400, 28);
		String school = sc.getName();
		if(school.equals("Dental") || school.equals("Adam Smith Business")){
			label.setText(school+" School");
		}else{
			label.setText("School of "+school);
		}
		panel.add(label);

		JLabel label_3 = new JLabel(us.getEmail());
		label_3.setBounds(227, 83, 276, 16);
		panel.add(label_3);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(6, 111, 722, 335);
		textArea_1.setFont(new Font("Courier", Font.PLAIN, 14));//set font type for text in text area
		textArea_1.setEditable(false);
		textArea_1.setText(m.displayAvailableCourses());
		//panel.add(textArea_1, BorderLayout.CENTER);

		JLabel lblAddACourse = new JLabel("Create a course");
		lblAddACourse.setBounds(18, 458, 130, 16);
		panel.add(lblAddACourse);

		JLabel lblCourseCode_1 = new JLabel("Course Name");
		lblCourseCode_1.setBounds(18, 486, 88, 16);
		panel.add(lblCourseCode_1);

		JLabel lblNewLabel_2 = new JLabel("Course Credit");
		lblNewLabel_2.setBounds(18, 514, 88, 22);
		panel.add(lblNewLabel_2);

		JLabel lblCourseCredit = new JLabel("Exam Percentage");
		lblCourseCredit.setBounds(18, 548, 108, 16);
		panel.add(lblCourseCredit);

		textField_2 = new JTextField();
		textField_2.setBounds(177, 481, 259, 26);
		panel.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(177, 512, 259, 26);
		panel.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(177, 543, 259, 26);
		panel.add(textField_4);
		textField_4.setColumns(10);

		textField_8 = new JTextField();
		textField_8.setBounds(177, 571, 259, 26);
		panel.add(textField_8);
		textField_8.setColumns(10);

		choice_1 = new Choice();
		choice_1.setBounds(177, 603, 259, 27);
		choice_1.add("(select degree)");

		choice_3 = new Choice();
		choice_3.setBounds(807, 174, 348, 27);
		choice_3.add("(select degree)");

		choice_4 = new Choice();
		choice_4.setBounds(807, 342, 348, 27);
		choice_4.add("(select degree)");
		
		choice_4.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selected6 = choice_4.getSelectedItem();
			}
		});
		panel.add(choice_4);

		for(int i = 0; i<dg.size();i++){
			choice_3.add(dg.get(i).getDegreeName()+"("+dg.get(i).getDegreeType()+")");
			choice_1.add(dg.get(i).getDegreeName()+"("+dg.get(i).getDegreeType()+")");
			choice_4.add(dg.get(i).getDegreeName()+"("+dg.get(i).getDegreeType()+")");
		}

		choice_3.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selected3 = choice_3.getSelectedItem();
			}

		});
		panel.add(choice_3);

		choice_5 = new Choice();
		choice_5.setBounds(18, 695, 507, 27);
		choice_5.add("(select course)");

		choice_5.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selected2 = choice_5.getSelectedItem();
			}

		});
		panel.add(choice_5);
		JButton btnAddCourse = new JButton("Create Course");
		btnAddCourse.setBounds(9, 632, 117, 29);
		btnAddCourse.setActionCommand("ADD");
		btnAddCourse.addActionListener(new MainListener(this));
		panel.add(btnAddCourse);

		JLabel lblNewLabel_3 = new JLabel("Select course you want to remove below");
		lblNewLabel_3.setBounds(18, 659, 259, 27);
		panel.add(lblNewLabel_3);

		JButton btnRemoveCourse = new JButton("Remove Course");
		btnRemoveCourse.setActionCommand("Remove Course");
		btnRemoveCourse.setBounds(533, 693, 130, 29);
		btnRemoveCourse.addActionListener(new MainListener(this));
		panel.add(btnRemoveCourse);

		JLabel lblEmail_1 = new JLabel("Email:");
		lblEmail_1.setBounds(177, 83, 44, 16);
		panel.add(lblEmail_1);

		JLabel lblCourseworkPercentage = new JLabel("Coursework Percentage");
		lblCourseworkPercentage.setBounds(18, 576, 147, 16);
		panel.add(lblCourseworkPercentage);

		JLabel lblDegree = new JLabel("Degree");
		lblDegree.setBounds(740, 174, 61, 16);
		panel.add(lblDegree);

		scrollPane = new JScrollPane(textArea_1);
		scrollPane.setBounds(6, 111, 722, 335);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				scrollPane.getVerticalScrollBar().setValue(0);//makes the scroll start at the top of the text
			}
		});
		panel.add(scrollPane, BorderLayout.CENTER);

		JLabel lblAddCourseTo = new JLabel("Add course to degree");
		lblAddCourseTo.setBounds(740, 109, 188, 16);
		panel.add(lblAddCourseTo);

		JLabel lblCourse = new JLabel("Course");
		lblCourse.setBounds(740, 137, 61, 16);
		panel.add(lblCourse);
		choice_2 = new Choice();
		choice_2.setBounds(807, 297, 348, 27);
		choice_2.add("(select course)");
		choice_2.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selected7 = choice_2.getSelectedItem();
			}
		});
		panel.add(choice_2);

		choice = new Choice();
		choice.setBounds(807, 131, 348, 27);
		choice.add("(select course)");
		for(int i = 0;i<cdg.size();i++){
			choice_5.add(cdg.get(i).getCourse());
			choice.add(cdg.get(i).getCourse());
			choice_2.add(cdg.get(i).getCourse());

		}

		choice.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selected4 = choice.getSelectedItem();
			}
		});
		panel.add(choice);

		JButton btnAddToDegree = new JButton("Add to Degree");
		btnAddToDegree.setActionCommand("Degree");
		btnAddToDegree.addActionListener(new MainListener(this));
		btnAddToDegree.setBounds(736, 222, 117, 29);
		panel.add(btnAddToDegree);

		JLabel lblDegree_1 = new JLabel("Degree");
		lblDegree_1.setBounds(18, 604, 61, 16);
		panel.add(lblDegree_1);


		choice_1.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selected5 = choice_1.getSelectedItem();
			}
		});
		panel.add(choice_1);

		JLabel lblRemoveCourseFrom = new JLabel("Remove course from degree");
		lblRemoveCourseFrom.setBounds(740, 272, 188, 16);
		panel.add(lblRemoveCourseFrom);

		JLabel lblCourse_1 = new JLabel("Course");
		lblCourse_1.setBounds(740, 300, 61, 16);
		panel.add(lblCourse_1);

		JLabel lblDegree_2 = new JLabel("Degree");
		lblDegree_2.setBounds(740, 342, 61, 16);
		panel.add(lblDegree_2);


		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(740, 383, 117, 29);
		btnRemove.setActionCommand("Remove");
		btnRemove.addActionListener(new MainListener(this));
		panel.add(btnRemove);

		JLabel lblEnrollStudentOnto = new JLabel("Enroll Student Onto a Course");
		lblEnrollStudentOnto.setBounds(740, 486, 188, 16);
		panel.add(lblEnrollStudentOnto);

		choice_6 = new Choice();
		choice_6.setBounds(740, 509, 393, 27);
		choice_6.setVisible(true);
		choice_6.add("(select student)");
		
		choice_7 = new Choice();
		choice_7.setBounds(740, 558, 393, 27);
		choice_7.setVisible(false);
		panel.add(choice_7);
		Student sdt = q.getAll(sc);
		List<Student> stt = q.getStudents();
		for(int i = 0;i<stt.size();i++){
			choice_6.add(stt.get(i).getFirstName()+" "+stt.get(i).getLastName());
		}
		choice_6.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selectedStudent = choice_6.getSelectedItem();
				if(!selectedStudent.equals("(select student)")){
					
					if(ie.getStateChange() == ItemEvent.SELECTED){
						count+=1;
					}

					if(count > 1){
						choice_7.removeAll();
					}
					btnEnrollStudent.setVisible(true);
					lblNewLabel.setVisible(true);
					choice_7.setVisible(true);
					choice_7.add("(select course)");
					String[] tokens_5 = selectedStudent.split(" ");
					Student st = aq.getSelected(tokens_5[0], tokens_5[1]);
					id=st.getStudentID();
					cr = sq.getCD();
					cr.removeAll(cr);
					courseResult.removeAll(courseResult);
					CourseResult crt = q.getDetails(st);
					CourseDegree cde = sq.displayCourses(st);
					for(int i = 0; i<cr.size();i++){
						choice_7.add(cr.get(i).getName().getCourse());
					}
					choice_7.addItemListener(new ItemListener(){
						public void itemStateChanged(ItemEvent ie)
						{
							selectedCourse = choice_7.getSelectedItem();
						}
					});
				}
			}
		});
		panel.add(choice_6);

		

		btnEnrollStudent = new JButton("Enroll Student");
		btnEnrollStudent.setBounds(740, 599, 117, 29);
		btnEnrollStudent.setVisible(false);
		btnEnrollStudent.addActionListener(new MainListener(this));
		btnEnrollStudent.setActionCommand("Enroll");
		panel.add(btnEnrollStudent);
		
		lblNewLabel = new JLabel("Select the course below");
		lblNewLabel.setBounds(740, 543, 188, 16);
		lblNewLabel.setVisible(false);
		panel.add(lblNewLabel);

	}

	public JTextField getTextField_2(){
		return textField_2;
	}

	public JTextField getTextField_3(){
		return textField_3;
	}

	public JTextField getTextField_4(){
		return textField_4;
	}

	public JTextField getTextField_8(){
		return textField_8;
	}

	public String getSelected3(){
		return selected3;
	}

	public String getSelected2(){
		return selected2;
	}

	public Choice choice3(){
		return choice_3;
	}

	public Choice choice5(){
		return choice_5;
	}

	public Choice choice(){
		return choice;
	}
	public Choice choice1(){
		return choice_1;
	}

	public Choice choice2(){
		return choice_2;
	}
	public Choice choice4(){
		return choice_4;
	}

	public String getSelected4(){
		return selected4;
	}

	public String selected5(){
		return selected5;
	}

	public String getSelected6(){
		return selected6;
	}

	public String selected7(){
		return selected7;
	}

	public String selectedStudent(){
		return selectedStudent;
	}
	public String selectedCourse(){
		return selectedCourse;
	}
	
	public int getID(){
		return id;
	}
	
	public Choice choiceStudent(){
		return choice_6;
	}
	
	public Choice choiceCourse(){
		return choice_7;
	}
}
