package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import gui.*;
import main.*;
import other.Course;
import other.Degree;
import other.Student;
//ViewStudentsListener class
public class ViewStudentsListener implements ActionListener{
	ViewStudents vs;
	List<Degree> dg;//Create a list containing degree objects
	List<Course> cdg;
	List<Student> stt;
	Queries q = Queries.getQueries();
	MainQueries m = MainQueries.getMain();
	public ViewStudentsListener(ViewStudents v){
		vs = v;
		stt = q.getStudents();
		dg = m.getList();
		cdg = m.getCourseList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Add") ){
			stt.removeAll(stt);
			//AdminAdd object created and displayed
			AdminAdd ad = new AdminAdd();
			ad.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("LogOut")){
			//StartGui object created and displayed
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("View")){
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			//ViewResult object created and displayed
			ViewResult vr = new ViewResult();
			vr.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("Home Menu")){
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			//Main object created and displayed
			Main mn = new Main();
			mn.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("AddDegree")){
			dg.removeAll(dg);
			AddDegree adg = new AddDegree();
			adg.setVisible(true);
			vs.dispose();
		}
		else{
			dg.removeAll(dg);
			//CreateStudent object created and displayed
			CreateStudent c = new CreateStudent();
			c.setVisible(true);
			vs.dispose();
		}

	
	}
}
