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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StudentMain extends JFrame{
	Users us;
	Queries q = Queries.getQueries();
	StudentQueries s = StudentQueries.getMain();
	String select, selected, selected1;
	Choice choice, choice_4;
	public StudentMain(){
		us = q.getUser();
		initialize();
		
	}

	public void initialize(){
		
		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(1000, 800);

		JPanel panel = new JPanel();
		getContentPane().add(panel, "name_1756125223660446");
		panel.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setActionCommand("Log Out");
		mntmLogOut.addActionListener(new StudentMainListener(this));
		//add result menu item

		JMenuItem mntmStudentResults = new JMenuItem("View Results");
		mntmStudentResults.setActionCommand("View");
		mntmStudentResults.addActionListener(new StudentMainListener(this));

		JMenuItem mntmPassword = new JMenuItem("Change Password");
		mntmPassword.setActionCommand("Change Password");
		mntmPassword.addActionListener(new StudentMainListener(this));

		//Student home
		JMenuItem mntmStudentHome = new JMenuItem("Home");
		mntmStudentHome.setActionCommand("Home Menu");
		mntmStudentHome.addActionListener(new StudentMainListener(this));
		
		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.setActionCommand("Refresh");
		mntmRefresh.addActionListener(new StudentMainListener(this));

		JMenu mnMain = new JMenu(us.getFirstName() + " " + us.getLastName());
		mnMain.add(mntmStudentHome);
		mnMain.add(mntmStudentResults);
		mnMain.add(mntmPassword);
		mnMain.add(mntmRefresh);
		mnMain.add(mntmLogOut);
		menuBar.add(mnMain);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(16, 95, 978, 339);
		textArea.setFont(new Font("Courier", Font.PLAIN, 14));
		textArea.setEditable(false);
		textArea.setText(s.displayStudentCourses(us.getMatric()));
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(16, 95, 978, 339);
		panel.add(scrollPane, BorderLayout.CENTER);

		JLabel label = new JLabel();
		label.setBounds(219, 58, 276, 16);
		label.setText(us.getEmail());
		panel.add(label);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(175, 58, 45, 16);
		panel.add(lblEmail);

		JLabel label_1 = new JLabel();
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		label_1.setBounds(175, 6, 400, 28);
		label_1.setText(q.displayDetails(us.getType(), us.getMatric()));
		panel.add(label_1);


		choice = new Choice();
		choice.setBounds(22, 495, 264, 27);
		choice.add("(select course)");
		panel.add(choice);

		choice_4 = new Choice();
		choice_4.setBounds(22, 623, 281, 27);
		choice_4.add("(select course)");
		panel.add(choice_4);

		String select1 = s.removeSelection(us.getFirstName(), us.getLastName());
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

		select = s.displayCourses(((Student)us).getStudentID());
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

		JButton btnNewButton = new JButton("Enroll");
		btnNewButton.setBounds(16, 544, 117, 29);
		btnNewButton.setActionCommand("ADD");
		btnNewButton.addActionListener(new StudentMainListener(this));
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Remove");
		btnNewButton_1.setBounds(16, 667, 135, 29);
		btnNewButton_1.setActionCommand("REMOVE");
		btnNewButton_1.addActionListener(new StudentMainListener(this));
		panel.add(btnNewButton_1);

		JLabel lblToAddA = new JLabel("To enroll in a course select from the list below");
		lblToAddA.setBounds(22, 473, 310, 16);
		panel.add(lblToAddA);

		JLabel lblEnterCourseCode = new JLabel("Select course you want to remove below");
		lblEnterCourseCode.setBounds(22, 601, 281, 16);
		panel.add(lblEnterCourseCode);
		
		JLabel lblEnrolledCourses = new JLabel("Enrolled Courses");
		lblEnrolledCourses.setBounds(22, 77, 129, 16);
		lblEnrolledCourses.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		panel.add(lblEnrolledCourses);
	}

	public Choice getChoice(){
		return choice;

	}
	
	public Choice getChoice_1(){
		return choice_4;
	}

	public String getSelected(){
		return selected;
	}

	public String getSelected_1(){
		return selected1;
	}
}
