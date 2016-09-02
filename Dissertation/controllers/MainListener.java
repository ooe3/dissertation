package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import gui.*;
import main.*;
/*
 * The listener class for Main class
 */
public class MainListener implements ActionListener{
	Main mn;
	Users us;
	Queries q = Queries.getQueries();//Queries object created to access methods in the Queries class
	MainQueries m = MainQueries.getMain();//MainQueries object created to access methods in the MainQueries class
	List<Degree> dg;//Create a list containing degree objects
	List<Course> cdg;
	List<Student> stt;
	List<CourseDegree> courseDegreeList;

	Student st;
	public MainListener(Main min){
		mn = min;
		us = q.getUser();//initialize Users object by calling getUSer method to know current user
		dg = m.getList();//initializes list by calling getList method to get all Degrees under the Admin's school
		cdg = m.getCourseList();
		stt = q.getStudents();
		courseDegreeList = m.getCourseDegreeList();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("ADD") ){
			//.trim() method to remove white spaces at the end or beginning
			String credits = mn.getTextField_3().getText().trim();
			String exams = mn.getTextField_4().getText().trim();
			String cwks = mn.getTextField_8().getText().trim();
			String name = mn.getTextField_2().getText().trim();
			//to check textfields and options for empty inputs or no selections
			if(name.equals("") || credits.equals("") || exams.equals("") || cwks.equals("") || mn.choice1().getSelectedItem().equals("(select degree)")){
				JOptionPane.showMessageDialog(null, "One or more textfields empty. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				try{
					int credit = Integer.parseInt(credits);//convert string input into an integer and store in credit
					int exam = Integer.parseInt(exams);//convert string input into an integer and store in exam
					int cw = Integer.parseInt(cwks);//convert string input into an integer and store in cw
					if(m.insertCourse(name, credit, exam, cw).equals("Error")){//insertCourse method called to check if the course already exists
						JOptionPane.showMessageDialog(null, "Course already exists", "Error message", JOptionPane.ERROR_MESSAGE);
						mn.getTextField_2().setText("");
						mn.getTextField_3().setText("");
						mn.getTextField_4().setText("");
						mn.getTextField_8().setText("");
					}else{
						if(q.checkString(name).equals("Exists")){//check for special characters in the name textField
							JOptionPane.showMessageDialog(null, "Special characters not accepted for the name.", "Window",
									JOptionPane.ERROR_MESSAGE);
						}else{
							if(exam > 100 || cw > 100 || credit > 60){
								JOptionPane.showMessageDialog(null, "Number greater than 100 or less than 0 or credits greater than 60", "Error message", JOptionPane.ERROR_MESSAGE);

							}else{
								String[] tokens = mn.selected5().split("\\(");//gets the degree selection and splits it on the opening bracket
								String degree = m.addCourseDegree(name, tokens[0]);//calls the addCourseDegree method to store the course and degree
								if(degree.equals("Error")){//checks if the course has already been assigned to the degree
									JOptionPane.showMessageDialog(null, "Course already assigned to this degree", "Window",
											JOptionPane.ERROR_MESSAGE);
								}else{

									JOptionPane.showMessageDialog(null, "Course addition successful", "Window",
											JOptionPane.INFORMATION_MESSAGE);
									courseDegreeList.removeAll(courseDegreeList);
									cdg.removeAll(cdg);
									dg.removeAll(dg);
									Main m = new Main();//Create new main jframe to get updated info
									m.setVisible(true);
									mn.dispose();//dispose current main jframe
								}
							}
						}

					}
				}catch (NumberFormatException e1){//checks for non-numeric input or non-positive numbers in the credits, exam & cwks textField
					JOptionPane.showMessageDialog(null, "Numeric input required", "Error message", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if(e.getActionCommand().equals("Remove Course") ){
			//checks if no selection has been made
			if(mn.choice5().getSelectedItem().equals("(select course)")){
				JOptionPane.showMessageDialog(null, "No course selected", "Window",
						JOptionPane.ERROR_MESSAGE);
			}else{
				//confirmation dialog box to confirm admin selection
				int show = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+mn.choice5().getSelectedItem()+"?");//dialog box to ask
				if(show == 0){
					m.removeCourse(mn.getSelected2());
					JOptionPane.showMessageDialog(null, "Removal successful", "Window",
							JOptionPane.INFORMATION_MESSAGE);
					courseDegreeList.removeAll(courseDegreeList);
					dg.removeAll(dg);
					cdg.removeAll(cdg);
					Main m = new Main();
					m.setVisible(true);
					mn.dispose();
				}else{
					mn.setVisible(true);
				}

			}
		}else if(e.getActionCommand().equals("Add") ){
			courseDegreeList.removeAll(courseDegreeList);
			stt.removeAll(stt);
			AdminAdd ad = new AdminAdd();
			ad.setVisible(true);
			mn.dispose();
		}else if(e.getActionCommand().equals("LogOut")){
			courseDegreeList.removeAll(courseDegreeList);
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			mn.dispose();
		}else if(e.getActionCommand().equals("View")){
			courseDegreeList.removeAll(courseDegreeList);
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			ViewResult vr = new ViewResult();
			vr.setVisible(true);
			mn.dispose();
		}else if(e.getActionCommand().equals("Home Menu")){
			mn.setVisible(true);
		}else if(e.getActionCommand().equals("Degree")){
			//check if no options have been selected
			if(mn.choice3().getSelectedItem().equals("(select degree)") || mn.choice().getSelectedItem().equals("(select course)")){
				JOptionPane.showMessageDialog(null, "No course or degree selected", "Window",
						JOptionPane.ERROR_MESSAGE);
			}else{
				String[] tokens = mn.getSelected3().split("\\(");//gets the degree selection and splits it on the opening bracket
				String degree = m.addCourseDegree(mn.getSelected4(), tokens[0]);//calls the addCourseDegree method to store the course and degree
				if(degree.equals("Error")){//checks if the course has already been assigned to the degree
					JOptionPane.showMessageDialog(null, "Course already assigned to this degree", "Window",
							JOptionPane.ERROR_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "Course added to degree succesfully", "Window",
							JOptionPane.INFORMATION_MESSAGE);
					courseDegreeList.removeAll(courseDegreeList);
					dg.removeAll(dg);
					cdg.removeAll(cdg);
					stt.removeAll(stt);
					Main m = new Main();
					m.setVisible(true);
					mn.dispose();
				}
			}
		}else if(e.getActionCommand().equals("Refresh")){
			courseDegreeList.removeAll(courseDegreeList);
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			Main m = new Main();
			m.setVisible(true);
			mn.dispose();
		}
		else if(e.getActionCommand().equals("Add Student")){
			courseDegreeList.removeAll(courseDegreeList);
			dg.removeAll(dg);
			CreateStudent c = new CreateStudent();
			c.setVisible(true);
			mn.dispose();
		}else if(e.getActionCommand().equals("Remove")){
			if(mn.choice4().getSelectedItem().equals("(select degree)") || mn.choice2().getSelectedItem().equals("(select course)")){
				JOptionPane.showMessageDialog(null, "No course or degree selected", "Window",
						JOptionPane.ERROR_MESSAGE);
			}else{
				int show = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+mn.choice2().getSelectedItem()+" from "+mn.choice4().getSelectedItem()+"?");//dialog box to ask
				if(show == 0){
					String[] tokens = mn.getSelected6().split("\\(");
					String check = m.removeCourseFromDegree(mn.selected7(), tokens[0]);
					if(check.equals("Error")){
						JOptionPane.showMessageDialog(null, "Course already removed from this degree", "Window",
								JOptionPane.ERROR_MESSAGE);
					}else{
					JOptionPane.showMessageDialog(null, "Removal successful", "Window",
							JOptionPane.INFORMATION_MESSAGE);
					
					}
					courseDegreeList.removeAll(courseDegreeList);
					dg.removeAll(dg);
					cdg.removeAll(cdg);
					Main m = new Main();
					m.setVisible(true);
					mn.dispose();
				}else{
					mn.setVisible(true);
				}
			}
		}
		else{
			courseDegreeList.removeAll(courseDegreeList);
			ViewStudents vs = new ViewStudents();
			vs.setVisible(true);
			mn.dispose();
		}
	}

}
