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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import controllers.StudentMainListener;
import main.*;
import other.StudentDegree;
import other.Users;
import controllers.*;
/*
 * The results frame class that displays the course and results of a student
 */
public class ResultsFrame extends JFrame{
	Users us;//user object
	Queries q = Queries.getQueries();//Queries object to access the methods
	StudentQueries sq = StudentQueries.getMain();
	StudentDegree sd;//StudentDegree object
	JTextArea textArea_2;//JText area

	public ResultsFrame(){
		us = q.getUser();//initialize to get the current user
		sd = q.getStudentDegree();//initialize to call the getStudentDegree method to get the list of courses
		initialize();//call the initialize method
	}

	public void initialize(){
		/*
		 * Sets the properties of the JFrame
		 */
		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(1000, 800);
		//MenuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		//Log out menu item
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setActionCommand("Log Out");
		mntmLogOut.addActionListener(new ResultsFrameListener(this));
		//view result menu item
		JMenuItem mntmStudentResults = new JMenuItem("View Results");
		mntmStudentResults.setActionCommand("View");
		mntmStudentResults.addActionListener(new ResultsFrameListener(this));//results frame listener passed as parameter to listen to the button

		//Student home
		JMenuItem mntmStudentHome = new JMenuItem("Main Page");
		mntmStudentHome.setActionCommand("Home Menu");
		mntmStudentHome.addActionListener(new ResultsFrameListener(this));//results frame listener passed as parameter to listen to the button

		//display the users name at the top of the menu item
		JMenu mnMain = new JMenu(us.getFirstName() + " " + us.getLastName()+" | Home");
		/*
		 * add the following menu items
		 */
		mnMain.add(mntmStudentHome);
		mnMain.add(mntmStudentResults);
		mnMain.add(mntmLogOut);
		menuBar.add(mnMain);
		JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4, "name_168856079465156");
		panel_4.setLayout(null);
		//textArea created to display the courses
		textArea_2 = new JTextArea();
		textArea_2.setBounds(26, 81, 829, 553);
		textArea_2.setEditable(false);//method to prevent text area from being edited
		textArea_2.setFont(new Font("Courier", Font.PLAIN, 14));//set font type for text in text area
		textArea_2.setText(sq.displayResult());//set the text in the text area
		panel_4.add(textArea_2, BorderLayout.CENTER);
		//label to display degree name and type of the current user
		JLabel lblResults = new JLabel("Results - "+sd.getDegree().getDegreeType()+" "+sd.getDegree().getDegreeName());
		lblResults.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblResults.setBounds(26, 16, 829, 25);
		panel_4.add(lblResults);
	}
}
