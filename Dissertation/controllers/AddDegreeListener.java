package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import gui.*;
import main.*;
public class AddDegreeListener implements ActionListener{
	AddDegree ad;
	Users us;
	Queries q = Queries.getQueries();//Queries object created to access methods in the Queries class
	MainQueries m = MainQueries.getMain();//MainQueries object created to access methods in the MainQueries class
	StudentQueries sq = StudentQueries.getMain();
	List<Degree> dg;//Create a list containing degree objects
	List<Course> cdg;
	School sc;
	List<Student> stt;
	List<CourseDegree> courseDegreeList;
	public AddDegreeListener(AddDegree a){
		ad = a;
		us = q.getUser();//initialize Users object by calling getUSer method to know current user
		sc = q.getSchool();
		dg = m.getList();//initializes list by calling getList method to get all Degrees under the Admin's school
		cdg = m.getCourseList();
		stt = q.getStudents();
		courseDegreeList = m.getCourseDegreeList();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("ADD") ){
			String name = ad.textField().getText().trim();
			String type = ad.textField1().getText().trim();
			if(name.equals("") || type.equals("")){
				JOptionPane.showMessageDialog(null, "One or more textfields empty. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);
			}else if(!name.matches("[a-zA-Z\\s]*") || !type.matches("[a-zA-Z]*")){
					JOptionPane.showMessageDialog(null, "Special characters not accepted or incorrect format", "Error message", JOptionPane.ERROR_MESSAGE);
				}else{
					String check = m.insertDegree(name, type, sc);
					if(check.equals("Error")){
						JOptionPane.showMessageDialog(null, "Degree exists already", "Error message", JOptionPane.ERROR_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "Degree addition successful", "Window",
								JOptionPane.INFORMATION_MESSAGE);
							AddDegree adge = new AddDegree();
							adge.setVisible(true);
							ad.dispose();
					}
				
			}
		}else if(e.getActionCommand().equals("AddD") ){
			ad.setVisible(true);
		}else if(e.getActionCommand().equals("LogOut")){
			courseDegreeList.removeAll(courseDegreeList);
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("View")){
			courseDegreeList.removeAll(courseDegreeList);
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			ViewResult vr = new ViewResult();
			vr.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("Home Menu")){
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			Main mn = new Main();
			mn.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("Add Result")){
			courseDegreeList.removeAll(courseDegreeList);
			stt.removeAll(stt);
			AdminAdd add = new AdminAdd();
			add.setVisible(true);
			ad.dispose();
		}
		else if(e.getActionCommand().equals("Add Student")){
			courseDegreeList.removeAll(courseDegreeList);
			dg.removeAll(dg);
			CreateStudent c = new CreateStudent();
			c.setVisible(true);
			ad.dispose();
		}else{
			courseDegreeList.removeAll(courseDegreeList);
			ViewStudents vs = new ViewStudents();
			vs.setVisible(true);
			ad.dispose();
		}


	}

}
