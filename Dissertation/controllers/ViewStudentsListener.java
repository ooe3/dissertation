package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import gui.*;
import main.*;

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
			AdminAdd ad = new AdminAdd();
			ad.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("LogOut")){
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("View")){
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			ViewResult vr = new ViewResult();
			vr.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("Home Menu")){
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			Main mn = new Main();
			mn.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("Refresh")){
			stt.removeAll(stt);
			ViewStudents m = new ViewStudents();
			m.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("Change Password")){
			
		}
		else{
			dg.removeAll(dg);
			CreateStudent c = new CreateStudent();
			c.setVisible(true);
			vs.dispose();
		}

	
	}
}
