package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.*;
import main.*;

public class ViewListener implements ActionListener{
	ViewResult vr;
	Users us;
	Queries q = Queries.getQueries();
	public ViewListener(ViewResult v){
		vr = v;
		us = q.getUser();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
		if(e.getActionCommand().equals("LogOut")){
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			vr.dispose();
		}else if(e.getActionCommand().equals("Refresh")){
			ViewResult vrt = new ViewResult();
			vrt.setVisible(true);
			vr.dispose();
		}else if(e.getActionCommand().equals("Home Menu")){
			Main mn = new Main();
			mn.setVisible(true);
			vr.dispose();
		}else if(e.getActionCommand().equals("View")){
			vr.setVisible(true);
			
		}else if(e.getActionCommand().equals("Add")){
			AdminAdd ad = new AdminAdd();
			ad.setVisible(true);
			vr.dispose();
		}else if(e.getActionCommand().equals("Change Password")){
			
			
		}else{
			
		}
	
	}

}
