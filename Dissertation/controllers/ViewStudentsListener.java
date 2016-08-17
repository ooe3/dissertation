package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.*;
import main.*;

public class ViewStudentsListener implements ActionListener{
	ViewStudents vs;
	
	public ViewStudentsListener(ViewStudents v){
		vs = v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Add") ){
			AdminAdd ad = new AdminAdd();
			ad.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("LogOut")){
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("View")){
			ViewResult vr = new ViewResult();
			vr.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("Home Menu")){
			Main mn = new Main();
			mn.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("Refresh")){
			ViewStudents m = new ViewStudents();
			m.setVisible(true);
			vs.dispose();
		}else if(e.getActionCommand().equals("Change Password")){
			
		}
		else{
			CreateStudent c = new CreateStudent();
			c.setVisible(true);
			vs.dispose();
		}

	
	}
}
