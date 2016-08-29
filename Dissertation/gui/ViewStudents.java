package gui;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import main.*;
import controllers.*;

public class ViewStudents extends JFrame{
	Users us;
	Queries q = Queries.getQueries();
	School sc;
	List<Student> studentList;
	Student sdt;
	public ViewStudents(){
		us = q.getUser();
		sc = q.getSchool();
		sdt = q.getAll(sc);
		studentList = q.getStudents();
		
		initialize();
	}
	
	public void initialize(){
		
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
		mntmLogOut.addActionListener(new ViewStudentsListener(this));

		JMenuItem mntmResults = new JMenuItem("Add Result");
		mntmResults.setActionCommand("Add");
		mntmResults.addActionListener(new ViewStudentsListener(this));
		//admin home
		JMenuItem mntmAdminHome = new JMenuItem("Home");
		mntmAdminHome.setActionCommand("Home Menu");
		mntmAdminHome.addActionListener(new ViewStudentsListener(this));

		//change password menu item
		JMenuItem mntmPassword = new JMenuItem("Change Password");
		mntmPassword.setActionCommand("Change Password");
		mntmPassword.addActionListener(new ViewStudentsListener(this));
		
		JMenuItem mntmView = new JMenuItem("View General Results");
		mntmView.setActionCommand("View");
		mntmView.addActionListener(new ViewStudentsListener(this));
		
		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.setActionCommand("Refresh");
		mntmRefresh.addActionListener(new ViewStudentsListener(this));
		
		JMenuItem mntmCreate = new JMenuItem("Add Student");
		mntmCreate.setActionCommand("Add Student");
		mntmCreate.addActionListener(new ViewStudentsListener(this));
		
		JMenu admin = new JMenu(us.getFirstName() + " " + us.getLastName());
		admin.add(mntmAdminHome);
		admin.add(mntmResults);
		admin.add(mntmCreate);
		admin.add(mntmView);
		admin.add(mntmPassword);
		admin.add(mntmRefresh);
		admin.add(mntmLogOut);
		menuBar.add(admin);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(6, 111, 976, 607);
		textArea_1.setFont(new Font("Courier", Font.PLAIN, 14));//set font type for text in text area
		textArea_1.setEditable(false);
		textArea_1.setText(q.allStudents());
		
		JScrollPane scrollPane = new JScrollPane(textArea_1);
		scrollPane.setBounds(6, 111, 976, 607);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		JLabel lblAllStudentDetails = new JLabel("All Student Details");
		lblAllStudentDetails.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblAllStudentDetails.setBounds(6, 16, 409, 38);
		panel.add(lblAllStudentDetails);
	}
}
