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
import java.util.List;
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
	StudentDegree sd;
	List<CourseResult> cr;
	List<CourseDegree> cd;
	CourseResult crt;
	CourseDegree cde;
	JScrollPane scrollPane;
	public StudentMain(){
		us = q.getUser();
		sd = q.getStudentDegree();
		crt = q.getDetails((Student)us);
		cde = s.displayCourses((Student)us);
		cr = q.getDetails();
		cd = s.getCD();
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

		//Student home
		JMenuItem mntmStudentHome = new JMenuItem("Main Page");
		mntmStudentHome.setActionCommand("Home Menu");
		mntmStudentHome.addActionListener(new StudentMainListener(this));


		JMenu mnMain = new JMenu(us.getFirstName() + " " + us.getLastName()+" | Home");
		mnMain.add(mntmStudentHome);
		mnMain.add(mntmStudentResults);
		mnMain.add(mntmLogOut);
		menuBar.add(mnMain);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(16, 95, 978, 339);
		textArea.setFont(new Font("Courier", Font.PLAIN, 14));
		textArea.setEditable(false);
		textArea.setText(s.displayStudentCourses());
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(16, 95, 978, 339);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		   public void run() { 
		       scrollPane.getVerticalScrollBar().setValue(0);//makes the scroll start at the top of the text
		   }
		});
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
		label_1.setBounds(175, 6, 792, 28);
		label_1.setText(sd.getDegree().getDegreeType()+" "+sd.getDegree().getDegreeName());
		panel.add(label_1);

		choice = new Choice();
		choice.setBounds(22, 495, 264, 27);
		choice.add("(select course)");
		panel.add(choice);

		choice_4 = new Choice();
		choice_4.setBounds(22, 623, 281, 27);
		choice_4.add("(select course)");
		panel.add(choice_4);
		
		for(int i = 0; i<cr.size();i++){

			choice_4.add(cr.get(i).getCourseName().getCourse());
		}

		choice_4.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selected1 = choice_4.getSelectedItem();
			}

		});
		for(int i = 0; i<cd.size();i++){
			choice.add(cd.get(i).getName().getCourse());
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

		JLabel lblCredit = new JLabel("Credit");
		lblCredit.setBounds(352, 78, 61, 16);
		panel.add(lblCredit);
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
