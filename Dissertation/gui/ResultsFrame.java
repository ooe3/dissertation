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

public class ResultsFrame extends JFrame{
	Users us;
	Queries q = Queries.getQueries();
	StudentDegree sd;
	JTextArea textArea_2;
	
	public ResultsFrame(){
		us = q.getUser();
		sd = q.getStudentDegree();
		initialize();
	}
	
	public void initialize(){
		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(1000, 800);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setActionCommand("Log Out");
		mntmLogOut.addActionListener(new ResultsFrameListener(this));
		//add result menu item

		JMenuItem mntmStudentResults = new JMenuItem("View Results");
		mntmStudentResults.setActionCommand("View");
		mntmStudentResults.addActionListener(new ResultsFrameListener(this));

		JMenuItem mntmPassword = new JMenuItem("Change Password");
		mntmPassword.setActionCommand("Change Password");
		mntmPassword.addActionListener(new ResultsFrameListener(this));

		//Student home
		JMenuItem mntmStudentHome = new JMenuItem("Main Page");
		mntmStudentHome.setActionCommand("Home Menu");
		mntmStudentHome.addActionListener(new ResultsFrameListener(this));

		JMenu mnMain = new JMenu(us.getFirstName() + " " + us.getLastName()+" | Home");
		mnMain.add(mntmStudentHome);
		mnMain.add(mntmStudentResults);
		mnMain.add(mntmPassword);
		mnMain.add(mntmLogOut);
		menuBar.add(mnMain);
		JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4, "name_168856079465156");
		panel_4.setLayout(null);

		textArea_2 = new JTextArea();
		textArea_2.setBounds(26, 81, 829, 553);
		textArea_2.setEditable(false);//method to prevent text area from being edited
		textArea_2.setFont(new Font("Courier", Font.PLAIN, 14));//set font type for text in text area
		panel_4.add(textArea_2, BorderLayout.CENTER);

		JLabel lblResults = new JLabel("Results - "+sd.getDegree().getDegreeType()+" "+sd.getDegree().getDegreeName());
		lblResults.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblResults.setBounds(26, 16, 829, 25);
		panel_4.add(lblResults);
	}
	
	public JTextArea getText(){
		return textArea_2;
	}
}
