package gui;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import controllers.StudentMainListener;
import main.*;
import controllers.*;
import javax.swing.JScrollBar;

public class Main extends JFrame{
	Users us;
	Queries q = Queries.getQueries();
	JTextField textField_2, textField_3, textField_4, textField_8;
	Choice choice_3, choice_5;
	String selected3, selected2;
	public Main(){
		initialize();

	}

	public void initialize(){
		us = q.getUser();
		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(1000, 800);

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
		JMenuItem mntmAdminHome = new JMenuItem("Home");
		mntmAdminHome.setActionCommand("Home Menu");
		mntmAdminHome.addActionListener(new MainListener(this));

		//change password menu item
		JMenuItem mntmPassword = new JMenuItem("Change Password");
		mntmPassword.setActionCommand("Change Password");
		mntmPassword.addActionListener(new MainListener(this));
		
		JMenuItem mntmView = new JMenuItem("View General Results");
		mntmView.setActionCommand("View");
		mntmView.addActionListener(new MainListener(this));
		
		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.setActionCommand("Refresh");
		mntmRefresh.addActionListener(new MainListener(this));
		
		JMenuItem mntmCreate = new JMenuItem("Add Student");
		mntmCreate.setActionCommand("Add Student");
		mntmCreate.addActionListener(new MainListener(this));

		JMenu admin = new JMenu(us.getFirstName() + " " + us.getLastName());
		admin.add(mntmAdminHome);
		admin.add(mntmResults);
		admin.add(mntmCreate);
		admin.add(mntmView);
		admin.add(mntmPassword);
		admin.add(mntmRefresh);
		admin.add(mntmLogOut);
		menuBar.add(admin);

		JLabel label = new JLabel(q.displayDetails(us.getType(), us.getMatric()));
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		label.setBounds(177, 16, 400, 28);
		panel.add(label);

		JLabel label_3 = new JLabel(us.getEmail());
		label_3.setBounds(227, 83, 276, 16);
		panel.add(label_3);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(6, 111, 976, 335);
		textArea_1.setFont(new Font("Courier", Font.PLAIN, 14));//set font type for text in text area
		textArea_1.setEditable(false);
		textArea_1.setText(q.displayAvailableCourses(us.getLastName()));

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
		textField_2.setBounds(177, 481, 348, 26);
		panel.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(177, 512, 348, 26);
		panel.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(177, 543, 348, 26);
		panel.add(textField_4);
		textField_4.setColumns(10);

		textField_8 = new JTextField();
		textField_8.setBounds(177, 571, 348, 26);
		panel.add(textField_8);
		textField_8.setColumns(10);

		choice_3 = new Choice();
		choice_3.setBounds(177, 603, 348, 27);
		choice_3.add("(select degree)");

		String select3 = q.displayDegree(((Admin)us).getSchoolName());
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
		panel.add(choice_3);

		choice_5 = new Choice();
		choice_5.setBounds(18, 695, 507, 27);
		choice_5.add("(select course)");

		String select2 = q.removeSelectionAdmin(us.getLastName());
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
		panel.add(choice_5);
		JButton btnAddCourse = new JButton("Create Course");
		btnAddCourse.setBounds(533, 512, 117, 29);
		btnAddCourse.setActionCommand("ADD");
		btnAddCourse.addActionListener(new MainListener(this));
		panel.add(btnAddCourse);

		JLabel lblNewLabel_3 = new JLabel("Select course you want to remove below");
		lblNewLabel_3.setBounds(18, 659, 259, 27);
		panel.add(lblNewLabel_3);

		JButton btnRemoveCourse = new JButton("Remove Course");
		btnRemoveCourse.setActionCommand("Remove");
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
		lblDegree.setBounds(18, 607, 61, 16);
		panel.add(lblDegree);
		
		JScrollPane scrollPane = new JScrollPane(textArea_1);
		scrollPane.setBounds(6, 111, 976, 335);
		panel.add(scrollPane, BorderLayout.CENTER);

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
}
