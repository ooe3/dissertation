package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import gui.*;
import main.*;
import other.CourseDegree;
import other.CourseResult;
import other.Student;
import other.Users;
/*
 * The listener class for StudentMain class
 * Executes button interactions
 */
public class StudentMainListener implements ActionListener{
	StudentMain sm;//StudentMain object created
	Queries q = Queries.getQueries();//Queries object created to access all methods in the Queries class
	StudentQueries sq = StudentQueries.getMain();//StudentQueries object created to access all methods in the StudentQueries class
	Users us;//Users object
	List<CourseResult> cr;//list object containing CourseResult object
	List<CourseDegree> cd;//list object containing CourseDegree object
	public StudentMainListener(StudentMain smn){
		this.sm = smn;//initialize StudentMain object
		us = q.getUser();//initialize Users object to get current user
		cr = q.getDetails();//initialize list object to get CourseResult objects
		cd = sq.getCD();//initialize list to get CourseDegree objects
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("ADD") ){
			if(sm.getChoice().getSelectedItem().equals("(select course)")){//check if selected equals the string in the parameter
				JOptionPane.showMessageDialog(null, "No course selected", "Window",
						JOptionPane.ERROR_MESSAGE);//JOptionPane error message
			}else{
				if(sq.insertChoice(sm.getSelected(), ((Student)us).getStudentID()).equals("Full")){//check if the string returned equals the string in the parameter
					JOptionPane.showMessageDialog(null, "Maximum credits selected. Remove course to be able to add this course", "Window",
							JOptionPane.ERROR_MESSAGE);//JOptionPane error message
				}else {
					JOptionPane.showMessageDialog(null, "Course selection successful", "Window",
							JOptionPane.INFORMATION_MESSAGE);//JOptionPane information message

				}
				//removeAll method called to remove objects from the lists below
				cr.removeAll(cr);
				cd.removeAll(cd);


			}
			//StudentMain object created and displayed as a frame
			StudentMain mn = new StudentMain();
			mn.setVisible(true);
			sm.dispose();//dispose of current frame
		}else if(e.getActionCommand().equals("REMOVE") ){
			if(sm.getChoice_1().getSelectedItem().equals("(select course)")){//check if selected equals the string in the parameter
				JOptionPane.showMessageDialog(null, "No course selected", "Window",
						JOptionPane.ERROR_MESSAGE);//JOptionPane error message
			}else{
				//JOption confirmDialg that provides 3 options, yes, no and cancel
				int show = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove "+sm.getChoice_1().getSelectedItem()+"?");//dialog box to ask
				if(show == 0){//0 indicates yes
					sq.removeChoice(sm.getChoice_1().getSelectedItem(), ((Student)us).getStudentID());//removeChoice method called to remove selected course by student
					JOptionPane.showMessageDialog(null, "Removal successful", "Window",
							JOptionPane.INFORMATION_MESSAGE);//JOptionPane information message
					//removeAll method called to remove objects from the lists below
					cr.removeAll(cr);
					cd.removeAll(cd);
				}else{
					sm.setVisible(true);//display current frame
				}

			}
			//StudentMain object created and displayed as a frame
			StudentMain mn = new StudentMain();
			mn.setVisible(true);
			sm.dispose();//dispose of current frame
		}else if(e.getActionCommand().equals("Home Menu") ){
			sm.setVisible(true);//display current frame
		}else if(e.getActionCommand().equals("View") ){
			//ResultsFrame object created and displayed as a frame
			ResultsFrame rf = new ResultsFrame();
			rf.setVisible(true);
			sm.dispose();//dispose of current frame
		}else if(e.getActionCommand().equals("Log Out") ){
			//removeAll method called to remove objects from the lists below
			cr.removeAll(cr);
			cd.removeAll(cd);
			//StartGUI object created and displayed as a frame
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			sm.dispose();//dispose of current frame
		}

	}

}
