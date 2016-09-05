package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import gui.*;
import main.*;
import other.CourseDegree;
import other.CourseResult;
import other.Users;
/*
 * Listener class for ResultsFrame class
 */
public class ResultsFrameListener implements ActionListener{
	Users us;
	Queries q = Queries.getQueries();//Queries object created to access methods in the Queries class
	StudentQueries sq = StudentQueries.getMain();//MainQueries object created to access methods in the MainQueries class
	ResultsFrame rf;//ResultFrame object created
	List<CourseResult> cr;//List containing courseresult objects
	List<CourseDegree> cd;//List containing CourseDegree object
	
	public ResultsFrameListener(ResultsFrame rfe){
		rf = rfe;
		us = q.getUser();//initialize users object by calling the getUser method
		cr = q.getDetails();//call the getDetails method to return list of courses and results for the student
		cd = sq.getCD();//calls the getCD method to return list of courses and the degree it belongs to
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
		if(e.getActionCommand().equals("View") ){
			rf.setVisible(true);
	}else if(e.getActionCommand().equals("Home Menu") ){
		//method to clear both lists cr & cd to avoid repeated info
		cr.removeAll(cr);
		cd.removeAll(cd);
		StudentMain sm = new StudentMain();
		sm.setVisible(true);
		rf.dispose();
	}else if(e.getActionCommand().equals("Log Out") ){
		StartGUI sg = new StartGUI();
		sg.setVisible(true);
		rf.dispose();
	}
	
	}
}
