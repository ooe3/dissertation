package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.*;
import main.*;

public class ResultsFrameListener implements ActionListener{
	Users us;
	Queries q = Queries.getQueries();
	ResultsFrame rf;
	
	public ResultsFrameListener(ResultsFrame rfe){
		rf = rfe;
		us = q.getUser();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
		if(e.getActionCommand().equals("View") ){
			rf.setVisible(true);
	}else if(e.getActionCommand().equals("Home Menu") ){
		StudentMain sm = new StudentMain();
		sm.setVisible(true);
	}else if(e.getActionCommand().equals("Change Password") ){
		
	}else if(e.getActionCommand().equals("Log Out") ){
		StartGUI sg = new StartGUI();
		sg.setVisible(true);
		rf.dispose();
	}
	else {
		System.exit(0);
	}
	
	}
}
