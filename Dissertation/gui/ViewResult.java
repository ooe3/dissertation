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
import other.Course;
import other.CourseResult;
import other.Degree;
import other.School;
import other.Student;
import other.StudentDegree;
import other.Users;
import controllers.*;
import javax.swing.JScrollBar;
//ViewResults frame
public class ViewResult extends JFrame{
	//Class objects created
	Users us;
	School sc;
	//Queries, MainQueries, AddQueries and ViewResultQueries all called to access the methods
	Queries q = Queries.getQueries();
	MainQueries m = MainQueries.getMain();
	ViewResultQueries v = ViewResultQueries.getMain();
	AddQueries aq = AddQueries.getMain();
	//List objects and class objects
	List<Degree> dg;
	CourseResult crr;
	List<CourseResult> cdt;
	Degree d;
	Course cd;
	List<Course> cdg;
	Student sdt;
	List<Student> stt;
	List<StudentDegree> sd;
	String selected3, selected2;
	Choice choice, choice_1, choice_2, choice_3;
	JTextArea textArea;
	JLabel lblNewLabel;
	JScrollPane scrollPane;
	JPanel panel;
	int id = 0;
	int count = 0;
	//strings that can't be changed
	final String choice1 = "View a particular students result", choice2 = "View overall results of a particular degree", 
			choice3 = "View overall results for a particular course", choice4 = "View overall results for the school";
	public ViewResult(){
		//methods called to retrieve class objects and lists
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
		//set Frame properties
		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(1000, 800);
		//JPanel
		panel = new JPanel();
		getContentPane().add(panel, "name_1756148928342669");
		panel.setLayout(null);

		JLabel lblSelectTheOption = new JLabel("Select the option you want below");
		lblSelectTheOption.setBounds(22, 6, 230, 16);
		panel.add(lblSelectTheOption);
		choice_2 = new Choice();
		choice_2.setBounds(22, 86, 395, 27);
		choice_2.setVisible(false);
		panel.add(choice_2);

		choice_1 = new Choice();
		choice_1.setBounds(22, 86, 395, 27);
		choice_1.setVisible(false);
		panel.add(choice_1);

		choice_3 = new Choice();
		choice_3.setBounds(22, 86, 395, 27);
		choice_3.setVisible(false);
		panel.add(choice_3);

		choice = new Choice();
		choice.setBounds(22, 28, 395, 27);
		choice.add("(select option)");
		//choices added
		choice.add(choice1);
		choice.add(choice2);
		choice.add(choice3);
		choice.add(choice4);
		choice.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selected3 = choice.getSelectedItem();
				if(ie.getStateChange() == ItemEvent.SELECTED){
					count+=1;//increase count
				}

				if(count > 1){//removeall and setScrollPane false if greater than one
					choice_2.removeAll();
					scrollPane.setVisible(false);
				}

				if(selected3.equals(choice1)){
					lblNewLabel.setText("Select the student result you want to view");
					lblNewLabel.setVisible(true);

					choice_1.add("(select student)");
					//retrieve the objects from the list
					for(int i = 0; i<stt.size();i++){
						choice_1.add(stt.get(i).getFirstName()+" "+stt.get(i).getLastName());//add full name
					}
					//display only choice_1
					choice_1.setVisible(true);
					choice_2.setVisible(false);
					choice_3.setVisible(false);
					choice_1.addItemListener(new ItemListener(){
						public void itemStateChanged(ItemEvent ie)
						{
							selected2 = choice_1.getSelectedItem();
							if(!selected2.equals("(select student)")){
								//method called to get list
								cdt = aq.getInfo();
								String[] tokens_5 = selected2.split(" ");//string split into firstane and lastname
								Student st = aq.getSelected(tokens_5[0], tokens_5[1]);//Student object created
								cdt.removeAll(cdt);
								crr = aq.getDetails(st.getStudentID());//getDetails method called
								String display = v.showResult();//showResult method called to display results of the student
								textArea.setText(display);
								scrollPane.setVisible(true);//scrollPane containing textArea visible
							}
						}
					});
				}else if(selected3.equals(choice2)){
					lblNewLabel.setText("Select the degree results you want to view");
					lblNewLabel.setVisible(true);
					//retrieve the objects from the list
					choice_2.add("(select degree)");
					for(int i = 0; i<dg.size();i++){
						choice_2.add(dg.get(i).getDegreeName()+"("+dg.get(i).getDegreeType()+")");//add degree name and type
					}
					//choice_2 now visible
					choice_1.setVisible(false);
					choice_2.setVisible(true);
					choice_3.setVisible(false);
					choice_2.addItemListener(new ItemListener(){
						public void itemStateChanged(ItemEvent ie)
						{
							selected2 = choice_2.getSelectedItem();
							if(!selected2.equals("(select degree)")){
								sd = v.getStudentDegree();//call getStudentDegree method
								String[] tokens = selected2.split("\\(");//split token before opening bracket
								sd.removeAll(sd);
								//retrieve objects from the list
								for(int i = 0; i<dg.size();i++){
									if((dg.get(i).getDegreeName().equals(tokens[0]))&&(dg.get(i).getDegreeType().equals(tokens[1].substring(0, tokens[1].length()-1)))){
										id = dg.get(i).getDegreeID();//get the id of the dgree
									}
								}
								StudentDegree sd = v.getDegreeInfo(id);//Create Student degree object and call getDegreeInfo on the id
								String display = v.degreeResult();//call degreeReslt method
								if(display.equals("No results for that degree") || selected2.equals("(select degree)")){
									textArea.setText("No result available for this degree or no degree selected");
								}else{
									textArea.setText(display);//display string
								}
								scrollPane.setVisible(true);//scrollPane visible
							}
						}
					});
				}else if(selected3.equals(choice3)){
					lblNewLabel.setText("Select the course results you want to view");
					lblNewLabel.setVisible(true);
					choice_3.add("(select course)");
					//return list of objects
					for(int i = 0;i<cdg.size();i++){
						choice_3.add(cdg.get(i).getCourse());//retrieve the coursenames in object

					}
					//choice_3 visible
					choice_1.setVisible(false);
					choice_2.setVisible(false);
					choice_3.setVisible(true);
					choice_3.addItemListener(new ItemListener(){
						public void itemStateChanged(ItemEvent ie)
						{
							selected2 = choice_3.getSelectedItem();
							if(!selected2.equals("(select course)")){
								List<CourseResult> getCourses = v.getCourseResults();//Create new list and call method
								getCourses.removeAll(getCourses);
								CourseResult cr = v.getCourses(selected2);//call method on selected2 and create Courseresult
								String display = v.overallCourse();//string displayed from overall course
								if(display.equals("Not Available") || selected2.equals("(select course)")){
									textArea.setText("No result available for this course or no course slected");
								}else{
									textArea.setText(display);
								}
								scrollPane.setVisible(true);//scrollPane visible
							}
						}
					});
				}else if(selected3.equals(choice4)){
					//choices and labels set to false
					//only textarea visible
					choice_1.setVisible(false);
					choice_2.setVisible(false);
					choice_3.setVisible(false);
					lblNewLabel.setVisible(false);
					sd = v.getStudentDegree();//call studentDegree method
					sd.removeAll(sd);
					StudentDegree sdg = v.getOveralSchool(sc);//call method on sc and create studentDegree
					String display = v.overallSchool();//call method to return string
					if(display.equals("No results for this school")){
						textArea.setText("No results set for this school yet or no students.");
					}else{
						textArea.setText(display);
					}
					scrollPane.setVisible(true);//scrollPane now visible
				}

			}
		});
		panel.add(choice);

		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(22, 61, 434, 16);
		lblNewLabel.setVisible(false);
		panel.add(lblNewLabel);


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
		JMenuItem mntmAdminHome = new JMenuItem("Main");
		mntmAdminHome.setActionCommand("Home Menu");
		mntmAdminHome.addActionListener(new ViewListener(this));

		JMenuItem mntmView = new JMenuItem("View General Results");
		mntmView.setActionCommand("View");
		mntmView.addActionListener(new ViewListener(this));
		//Add Degree
		JMenuItem mntmAddDegree = new JMenuItem("Add Degree");
		mntmAddDegree.setActionCommand("AddD");
		mntmAddDegree.addActionListener(new ViewListener(this));

		JMenuItem mntmViewStudent = new JMenuItem("View Students");
		mntmViewStudent.setActionCommand("ViewS");
		mntmViewStudent.addActionListener(new ViewListener(this));

		JMenuItem mntmCreate = new JMenuItem("Add Student");
		mntmCreate.setActionCommand("Add Student");
		mntmCreate.addActionListener(new ViewListener(this));

		JMenu admin = new JMenu(us.getFirstName() + " " + us.getLastName()+" | Home");
		admin.add(mntmAdminHome);
		admin.add(mntmResults);
		admin.add(mntmAddDegree);
		admin.add(mntmCreate);
		admin.add(mntmView);
		admin.add(mntmViewStudent);
		admin.add(mntmLogOut);
		menuBar.add(admin);

	}
}
