package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import gui.*;
import main.*;

public class StudentMainListener implements ActionListener{
	StudentMain sm;
	Queries q = Queries.getQueries();
	Users us;
	public StudentMainListener(StudentMain smn){
		this.sm = smn;
		us = q.getUser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("ADD") ){
			if(sm.getChoice().getSelectedItem().equals("(select course)") || sm.getChoice().getSelectedItem().equals("")){
				JOptionPane.showMessageDialog(null, "No course selected", "Window",
						JOptionPane.ERROR_MESSAGE);
			}else{
				if(q.insertChoice(sm.getSelected(), ((Student)us).getStudentID()).equals("Full")){
					JOptionPane.showMessageDialog(null, "Maximum credits selected. Remove course to be able to add this course", "Window",
							JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Course selection successful", "Window",
							JOptionPane.INFORMATION_MESSAGE);
				}


			}
			StudentMain mn = new StudentMain();
			mn.setVisible(true);
			sm.dispose();
		}else if(e.getActionCommand().equals("REMOVE") ){
			if(sm.getChoice_1().getSelectedItem().equals("(select course)") || sm.getChoice_1().getSelectedItem().equals("")){
				JOptionPane.showMessageDialog(null, "No course selected", "Window",
						JOptionPane.ERROR_MESSAGE);
			}else{
				if(q.removeChoice(sm.getSelected_1(), ((Student)us).getStudentID()).equals("Exists")){
					JOptionPane.showMessageDialog(null, "This course already has mark and can't be removed.", "Window",
							JOptionPane.ERROR_MESSAGE);
				}else{
				JOptionPane.showMessageDialog(null, "Removal successful", "Window",
						JOptionPane.ERROR_MESSAGE);
				}
			}
			StudentMain mn = new StudentMain();
			mn.setVisible(true);
			sm.dispose();
		}else if(e.getActionCommand().equals("Home Menu") ){
			sm.setVisible(true);
		}else if(e.getActionCommand().equals("Change Password") ){

		}else if(e.getActionCommand().equals("View") ){
			String display = q.displayResult(((Student)us).getStudentID());
			if(display.equals("Not Available")){
				JOptionPane.showMessageDialog(null, "Your results arent available yet. Try again later", "Window",
						JOptionPane.ERROR_MESSAGE);
			}else{
				ResultsFrame rf = new ResultsFrame();
				rf.getText().setText(display);
				rf.setVisible(true);
				sm.dispose();

			}
		}else if(e.getActionCommand().equals("Log Out") ){
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			sm.dispose();
		}else {
			StudentMain smn = new StudentMain();
			smn.setVisible(true);
			sm.dispose();
		}

	}

}
