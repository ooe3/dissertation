package gui;
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
import main.*;
import controllers.*;
import javax.swing.JScrollBar;

public class ViewResult extends JFrame{
	Users us;
	School sc;
	Queries q = Queries.getQueries();
	MainQueries m = MainQueries.getMain();
	ViewResultQueries v = ViewResultQueries.getMain();
	List<Degree> dg;
	Degree d;
	Course cd;
	List<Course> cdg;
	Student sdt;
	List<Student> stt;
	String selected3, selected2;
	Choice choice, choice_1;
	JTextArea textArea;
	JLabel lblNewLabel;
	JScrollPane scrollPane;
	int count = 0;
	final String choice1 = "View a particular students result", choice2 = "View overall results of a particular degree", 
			choice3 = "View overall results for a particular course", choice4 = "View overall results for the school";
	public ViewResult(){
		us = q.getUser();
		sc = q.getSchool();
		d = m.displayDegree(sc);
		cd = m.getCourses(sc);
		sdt = q.getAll(sc);
		stt = q.getStudents();
		dg = m.getList();
		cdg = m.getCourseList();
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

		JLabel lblSelectTheOption = new JLabel("Select the option you want below");
		lblSelectTheOption.setBounds(22, 6, 230, 16);
		panel.add(lblSelectTheOption);

		choice = new Choice();
		choice.setBounds(22, 28, 395, 27);
		choice.add("(select option)");
		choice.add(choice1);
		choice.add(choice2);
		choice.add(choice3);
		choice.add(choice4);
		choice.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selected3 = choice.getSelectedItem();
				if(ie.getStateChange() == ItemEvent.SELECTED){
					count+=1;
				}

				if(count > 1){
					JOptionPane.showMessageDialog(null, "The page needs to be refreshed before you make another selection.", "Window",
							JOptionPane.INFORMATION_MESSAGE);
					dg.removeAll(dg);
					cdg.removeAll(cdg);
					stt.removeAll(stt);
					ViewResult vr = new ViewResult();
					vr.setVisible(true);
					dispose();

				}

				if(selected3.equals(choice1)){
					lblNewLabel.setText("Select the student result you want to view");
					lblNewLabel.setVisible(true);
					choice_1.add("(select student)");
					for(int i = 0; i<stt.size();i++){
						choice_1.add(stt.get(i).getFirstName()+" "+stt.get(i).getLastName());
					}
					choice_1.setVisible(true);
					choice_1.addItemListener(new ItemListener(){
						public void itemStateChanged(ItemEvent ie)
						{
							selected2 = choice_1.getSelectedItem();
							String[] tokens_5 = selected2.split(" ");
							String display = v.showResult(tokens_5[0], tokens_5[1]);
							textArea.setText(display);
							scrollPane.setVisible(true);
						}
					});
				}else if(selected3.equals(choice2)){
					lblNewLabel.setText("Select the degree results you want to view");
					lblNewLabel.setVisible(true);
					choice_1.add("(select degree)");
					for(int i = 0; i<dg.size();i++){
						choice_1.add(dg.get(i).getDegreeName()+"("+dg.get(i).getDegreeType()+")");
					}
					choice_1.setVisible(true);
					choice_1.addItemListener(new ItemListener(){
						public void itemStateChanged(ItemEvent ie)
						{
							selected2 = choice_1.getSelectedItem();
							String[] tokens = selected2.split("\\(");
							String display = v.degreeResult(tokens[0]);
							if(display.equals("No results for that degree") || selected2.equals("(select degree)")){
								textArea.setText("No result available for this degree or no degree selected");
							}else{
								textArea.setText(display);
							}
							scrollPane.setVisible(true);
						}
					});
				}else if(selected3.equals(choice3)){
					lblNewLabel.setText("Select the course results you want to view");
					lblNewLabel.setVisible(true);
					choice_1.add("(select course)");
					for(int i = 0;i<cdg.size();i++){
						choice_1.add(cdg.get(i).getCourse());

					}
					choice_1.setVisible(true);
					choice_1.addItemListener(new ItemListener(){
						public void itemStateChanged(ItemEvent ie)
						{
							selected2 = choice_1.getSelectedItem();
							String display = v.overallCourse(selected2);
							if(display.equals("Not Available") || selected2.equals("(select course)")){
								textArea.setText("No result available for this course or no course slected");
							}else{
								textArea.setText(display);
							}
							scrollPane.setVisible(true);
						}
					});
				}else if(selected3.equals(choice4)){
					String display = v.overallSchool(((Admin)us).getID());
					if(display.equals("No results for this school")){
						textArea.setText("No results set for this school yet or no students.");
					}else{
						textArea.setText(display);
					}
					scrollPane.setVisible(true);
				}
				
			}
		});
		panel.add(choice);

		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(22, 61, 434, 16);
		lblNewLabel.setVisible(false);
		panel.add(lblNewLabel);

		choice_1 = new Choice();
		choice_1.setBounds(22, 86, 395, 27);
		choice_1.setVisible(false);
		panel.add(choice_1);

		textArea = new JTextArea();
		textArea.setBounds(22, 148, 972, 555);
		textArea.setEditable(false);
		textArea.setFont(new Font("Courier", Font.PLAIN, 14));//set font type for text in text area
		scrollPane = new JScrollPane(textArea);
		scrollPane.setVisible(false);
		scrollPane.setBounds(22, 148, 972, 555);
		panel.add(scrollPane, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setActionCommand("LogOut");
		mntmLogOut.addActionListener(new ViewListener(this));

		JMenuItem mntmResults = new JMenuItem("Add Result");
		mntmResults.setActionCommand("Add");
		mntmResults.addActionListener(new ViewListener(this));
		//admin home
		JMenuItem mntmAdminHome = new JMenuItem("Home");
		mntmAdminHome.setActionCommand("Home Menu");
		mntmAdminHome.addActionListener(new ViewListener(this));

		//change password menu item
		JMenuItem mntmPassword = new JMenuItem("Change Password");
		mntmPassword.setActionCommand("Change Password");
		mntmPassword.addActionListener(new ViewListener(this));

		JMenuItem mntmView = new JMenuItem("View General Results");
		mntmView.setActionCommand("View");
		mntmView.addActionListener(new ViewListener(this));

		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.setActionCommand("Refresh");
		mntmRefresh.addActionListener(new ViewListener(this));

		JMenuItem mntmCreate = new JMenuItem("Add Student");
		mntmCreate.setActionCommand("Add Student");
		mntmCreate.addActionListener(new ViewListener(this));

		JMenu admin = new JMenu(us.getFirstName() + " " + us.getLastName());
		admin.add(mntmAdminHome);
		admin.add(mntmResults);
		admin.add(mntmCreate);
		admin.add(mntmView);
		admin.add(mntmPassword);
		admin.add(mntmRefresh);
		admin.add(mntmLogOut);
		menuBar.add(admin);

	}
}
