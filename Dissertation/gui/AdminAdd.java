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

public class AdminAdd extends JFrame{
	Users us;
	Queries q = Queries.getQueries();
	Choice choice, choice_1, choice_2;
	JTextField textField_6, textField_7;
	JLabel label, lblNewLabel_5;
	Course cs;
	JLabel lblExam, lblExamPercentage, lblCoursework, lblPercentage,lblNewLabel_4;
	JButton btnSubmit;
	String selected5, name_selected;
	String[] tokens_5, tokens_6;
	private JLabel lblIfYouWant;
	private JLabel lblSelectTheStudents;
	public AdminAdd(){
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

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setActionCommand("LogOut");
		mntmLogOut.addActionListener(new AddListener(this));

		//closes the whole program
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setActionCommand("Exit");
		mntmExit.addActionListener(new AddListener(this));

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
		
		JMenuItem mntmView = new JMenuItem("View Results");
		mntmView.setActionCommand("View");
		mntmView.addActionListener(new AddListener(this));
		
		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.setActionCommand("Refresh");
		mntmRefresh.addActionListener(new AddListener(this));

		JMenu admin = new JMenu(us.getFirstName() + " " + us.getLastName());
		admin.add(mntmAdminHome);
		admin.add(mntmResults);
		admin.add(mntmView);
		admin.add(mntmPassword);
		admin.add(mntmRefresh);
		admin.add(mntmLogOut);
		admin.add(mntmExit);
		menuBar.add(admin);

		JLabel lblSelectTheStudent = new JLabel("Select the student whose result you want to add below");
		lblSelectTheStudent.setBounds(19, 82, 390, 16);
		panel_3.add(lblSelectTheStudent);

		lblNewLabel_4 = new JLabel("Select course you want to add mark for below");
		lblNewLabel_4.setBounds(19, 137, 354, 16);
		lblNewLabel_4.setVisible(false);
		panel_3.add(lblNewLabel_4);

		choice_1 = new Choice();
		choice_1.setBounds(19, 104, 307, 27);
		choice_1.add("(select student)");
		String select4 = q.displayStudents(us.getID());
		String[]tokens_4 = select4.split(",");

		for(int i = 0; i<tokens_4.length;i++){
			choice_1.add(tokens_4[i]);
		}
		panel_3.add(choice_1);

		choice_2 = new Choice();
		choice_2.setBounds(20, 177, 295, 27);
		choice_2.add("(select course)");
		panel_3.add(choice_2);

		choice_2.setVisible(false);

		choice_1.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				String selected4 = choice_1.getSelectedItem();
				lblNewLabel_4.setVisible(true);
				tokens_5 = selected4.split(" ");
				String select7 = q.removeSelection(tokens_5[1]);
				String[]tokens_7 = select7.split(",");
				choice_2.setVisible(true);
				for(int i = 0; i<tokens_7.length;i++){
					choice_2.add(tokens_7[i]);
				}


				choice_2.addItemListener(new ItemListener(){
					public void itemStateChanged(ItemEvent ie)
					{
						selected5 = choice_2.getSelectedItem();
						lblExam.setVisible(true);
						lblCoursework.setVisible(true);
						lblPercentage.setVisible(true);
						textField_6.setVisible(true);
						textField_7.setVisible(true);
						btnSubmit.setVisible(true);
						q.getCourseDetails(selected5);
						label.setVisible(true);
						lblNewLabel_5.setVisible(true);
						label.setText(""+q.getCwPercentage()+"");
						lblNewLabel_5.setText(""+q.getExamPercentage()+"");


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
				System.out.print(q.checkResults(tokens_6[0], tokens_6[1]));
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
