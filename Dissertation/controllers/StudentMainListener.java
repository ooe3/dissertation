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
 */
public class StudentMainListener implements ActionListener{
	StudentMain sm;
	Queries q = Queries.getQueries();
	StudentQueries sq = StudentQueries.getMain();
	Users us;
	List<CourseResult> cr;
	List<CourseDegree> cd;
	public StudentMainListener(StudentMain smn){
		this.sm = smn;
		us = q.getUser();
		cr = q.getDetails();
		cd = sq.getCD();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("ADD") ){
			if(sm.getChoice().getSelectedItem().equals("(select course)")){
				JOptionPane.showMessageDialog(null, "No course selected", "Window",
						JOptionPane.ERROR_MESSAGE);
			}else{
				if(sq.insertChoice(sm.getSelected(), ((Student)us).getStudentID()).equals("Full")){
					JOptionPane.showMessageDialog(null, "Maximum credits selected. Remove course to be able to add this course", "Window",
							JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Course selection successful", "Window",
							JOptionPane.INFORMATION_MESSAGE);

				}
				cr.removeAll(cr);
				cd.removeAll(cd);


			}
			StudentMain mn = new StudentMain();
			mn.setVisible(true);
			sm.dispose();
		}else if(e.getActionCommand().equals("REMOVE") ){
			if(sm.getChoice_1().getSelectedItem().equals("(select course)")){
				JOptionPane.showMessageDialog(null, "No course selected", "Window",
						JOptionPane.ERROR_MESSAGE);
			}else{
				int show = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove "+sm.getChoice_1().getSelectedItem()+"?");//dialog box to ask
				if(show == 0){
					sq.removeChoice(sm.getChoice_1().getSelectedItem(), ((Student)us).getStudentID());
					JOptionPane.showMessageDialog(null, "Removal successful", "Window",
							JOptionPane.INFORMATION_MESSAGE);
					cr.removeAll(cr);
					cd.removeAll(cd);
				}else{
					sm.setVisible(true);
				}

			}
			StudentMain mn = new StudentMain();
			mn.setVisible(true);
			sm.dispose();
		}else if(e.getActionCommand().equals("Home Menu") ){
			sm.setVisible(true);
		}else if(e.getActionCommand().equals("Change Password") ){

		}else if(e.getActionCommand().equals("View") ){
			ResultsFrame rf = new ResultsFrame();
			rf.setVisible(true);
			sm.dispose();
		}else if(e.getActionCommand().equals("Log Out") ){
			cr.removeAll(cr);
			cd.removeAll(cd);
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			sm.dispose();
		}else {
			cr.removeAll(cr);
			cd.removeAll(cd);
			StudentMain smn = new StudentMain();
			smn.setVisible(true);
			sm.dispose();
		}

	}

}
