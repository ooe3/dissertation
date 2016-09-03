package gui;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.*;
import main.*;
import controllers.*;

public class ViewStudents extends JFrame{
	Users us;
	Queries q = Queries.getQueries();
	MainQueries m = MainQueries.getMain();
	School sc;
	List<Degree> dg;
//	List<Student> studentList;
//	Student sdt;
	Choice choice_1;
	JTextArea textArea_1;
	public ViewStudents(){
		us = q.getUser();
		sc = q.getSchool();
		
		
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
		JMenuItem mntmAdminHome = new JMenuItem("Main Page");
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
		
		JMenuItem mntmViewStudent = new JMenuItem("View Students");
		mntmViewStudent.setActionCommand("ViewS");
		mntmViewStudent.addActionListener(new ViewStudentsListener(this));
		
		JMenu admin = new JMenu(us.getFirstName() + " " + us.getLastName()+" | Home");
		admin.add(mntmAdminHome);
		admin.add(mntmResults);
		admin.add(mntmCreate);
		admin.add(mntmView);
		admin.add(mntmViewStudent);
		admin.add(mntmPassword);
		admin.add(mntmRefresh);
		admin.add(mntmLogOut);
		menuBar.add(admin);
		
		textArea_1 = new JTextArea();
		textArea_1.setBounds(6, 111, 976, 607);
		textArea_1.setFont(new Font("Courier", Font.PLAIN, 14));//set font type for text in text area
		textArea_1.setEditable(false);
		textArea_1.setText(q.allStudents("All"));
		
		
		JScrollPane scrollPane = new JScrollPane(textArea_1);
		scrollPane.setBounds(6, 111, 976, 607);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		JLabel lblAllStudentDetails = new JLabel("All Student Details");
		lblAllStudentDetails.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblAllStudentDetails.setBounds(6, 6, 409, 29);
		panel.add(lblAllStudentDetails);
		
		choice_1 = new Choice();
		choice_1.setBounds(6, 63, 315, 27);
		choice_1.add("(select degree)");
		dg = m.getList();
		dg.removeAll(dg);
		List<Student> studentList = q.getStudents();
		studentList.removeAll(studentList);
		Student sdt = q.getAll(sc);
		Degree d = m.displayDegree(sc);
		for(int i = 0;i<dg.size();i++){
			choice_1.add(dg.get(i).getDegreeName()+"("+dg.get(i).getDegreeType()+")");
		}
		choice_1.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				String selected = choice_1.getSelectedItem();
				
				if(selected.equals("(select degree)")){
					textArea_1.setText(q.allStudents("All"));
				}else{
					textArea_1.setText(q.allStudents(selected));
				}
			}
		});
		panel.add(choice_1);
		
		JLabel lblFilterStudentsBy = new JLabel("Filter students by degree");
		lblFilterStudentsBy.setBounds(6, 41, 200, 16);
		panel.add(lblFilterStudentsBy);
	}
	
}
