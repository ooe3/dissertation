package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import gui.*;
import main.*;
/*
 * This is the listener class for the AddDegree Class
 * Listens to the buttons or menu items selected and performs the required
 */
public class AddDegreeListener implements ActionListener{
	AddDegree ad;//AddDegree object
	Users us;//User object
	Queries q = Queries.getQueries();//Queries object created to access methods in the Queries class
	MainQueries m = MainQueries.getMain();//MainQueries object created to access methods in the MainQueries class
	StudentQueries sq = StudentQueries.getMain();
	List<Degree> dg;//Create a list containing degree objects
	List<Course> cdg;//List containing course objects
	School sc;//School object
	List<Student> stt;//List containing student objects
	List<CourseDegree> courseDegreeList;//list containing CourseDegree objects
	public AddDegreeListener(AddDegree a){
		ad = a;
		us = q.getUser();//initialize Users object by calling getUSer method to know current user
		sc = q.getSchool();//initialize the School object by calling the getSchool method to know the school of the current user
		dg = m.getList();//initializes list by calling getList method to get all Degrees under the Admin's school
		cdg = m.getCourseList();//initializes list by calling getCourseList method to get all courses under a school
		stt = q.getStudents();//initializes list by calling getStudents method to get all students under a school
		courseDegreeList = m.getCourseDegreeList();//initializes list by calling the getCourseDegreeList method to get all courses and the degrees
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("ADD") ){
			String name = ad.textField().getText().trim();//input in textField stored in string name
			String type = ad.textField1().getText().trim();//input in textField stored in string type
			if(name.equals("") || type.equals("")){
				JOptionPane.showMessageDialog(null, "One or more textfields empty. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);//displays error message
			}else 
				//checks if the name contains anything other than letters a-z,A-z, a space and the & sign
				//checks if the type contains anything other than letters a-z or A-Z
				if(!name.matches("[a-zA-Z\\s&]*") || !type.matches("[a-zA-Z]*")){
					JOptionPane.showMessageDialog(null, "Special characters not accepted or incorrect format", "Error message", JOptionPane.ERROR_MESSAGE);//displays error message
				}else{
				//stores the string returned from insertDegree method in string check
					//if a string is returned then it displays the error message
					//otherwise to performs the insertion of the degree
					String check = m.insertDegree(name, type, sc);
					if(check.equals("Error")){
						JOptionPane.showMessageDialog(null, "Degree exists already", "Error message", JOptionPane.ERROR_MESSAGE);//displays error message
					}else{
						JOptionPane.showMessageDialog(null, "Degree addition successful", "Window",
								JOptionPane.INFORMATION_MESSAGE);//displays information message
							AddDegree adge = new AddDegree();//AddDegree object created and displayed 
							adge.setVisible(true);
							ad.dispose();//current AddDegree object disposed
					}
				
			}
		}else if(e.getActionCommand().equals("AddD") ){//if AddDegree menu item selected
			ad.setVisible(true);//displays the current page
		}else if(e.getActionCommand().equals("LogOut")){//if logout menu item selected
			//All objects stored in the lists below are removed
			courseDegreeList.removeAll(courseDegreeList);
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			StartGUI sg = new StartGUI();//New startgui object created and displayed
			sg.setVisible(true);
			ad.dispose();//current AddDegree object disposed
		}else if(e.getActionCommand().equals("View")){
		//All objects stored in the lists below are removed
			courseDegreeList.removeAll(courseDegreeList);
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			ViewResult vr = new ViewResult();
			vr.setVisible(true);
			ad.dispose();//current AddDegree object disposed
		}else if(e.getActionCommand().equals("Home Menu")){
		//All objects stored in the lists below are removed
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			Main mn = new Main();
			mn.setVisible(true);
			ad.dispose();//current AddDegree object disposed
		}else if(e.getActionCommand().equals("Add Result")){
		//All objects stored in the lists below are removed
			courseDegreeList.removeAll(courseDegreeList);
			stt.removeAll(stt);
			AdminAdd add = new AdminAdd();
			add.setVisible(true);
			ad.dispose();//current AddDegree object disposed
		}
		else if(e.getActionCommand().equals("Add Student")){
		//All objects stored in the lists below are removed
			courseDegreeList.removeAll(courseDegreeList);
			dg.removeAll(dg);
			CreateStudent c = new CreateStudent();
			c.setVisible(true);
			ad.dispose();//current AddDegree object disposed
		}else{
		//All objects stored in the lists below are removed
			courseDegreeList.removeAll(courseDegreeList);
			ViewStudents vs = new ViewStudents();
			vs.setVisible(true);
			ad.dispose();//current AddDegree object disposed
		}


	}

}
