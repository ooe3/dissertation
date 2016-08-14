package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.*;
import main.*;

public class MainListener implements ActionListener{
	Main mn;
	Users us;
	Queries q = Queries.getQueries();

	public MainListener(Main min){
		mn = min;
		us = q.getUser();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("ADD") ){
			if(mn.getTextField_2().getText().equals("") || mn.getTextField_3().getText().equals("") || mn.getTextField_4().getText().equals("") || mn.getTextField_8().getText().equals("") || mn.choice3().getSelectedItem().equals("(select school)")){
				JOptionPane.showMessageDialog(null, "One or more textfields empty. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				try{
					int credit = Integer.parseInt(mn.getTextField_3().getText());
					int exam = Integer.parseInt(mn.getTextField_4().getText());
					int cw = Integer.parseInt(mn.getTextField_8().getText());
					if(q.insertCourse(mn.getTextField_2().getText(), credit, exam, cw).equals("Error")){
						JOptionPane.showMessageDialog(null, "Course already exists", "Error message", JOptionPane.ERROR_MESSAGE);
						mn.getTextField_2().setText("");
						mn.getTextField_3().setText("");
						mn.getTextField_4().setText("");
						mn.getTextField_8().setText("");
					}else{
						String[] tokens = mn.getSelected3().split("\\(");
						q.addCourseDegree(mn.getTextField_2().getText(), tokens[0]);
						JOptionPane.showMessageDialog(null, "Course addition successful", "Window",
								JOptionPane.INFORMATION_MESSAGE);
						Main m = new Main();
						m.setVisible(true);
						mn.dispose();
					}
				}catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(null, "Numeric input required", "Error message", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if(e.getActionCommand().equals("Remove") ){
			if(mn.choice5().getSelectedItem().equals("(select course)")){
				JOptionPane.showMessageDialog(null, "No course selected", "Window",
						JOptionPane.ERROR_MESSAGE);
			}else{
				q.removeCourse(mn.getSelected2());
				JOptionPane.showMessageDialog(null, "Removal successful", "Window",
						JOptionPane.ERROR_MESSAGE);
				Main m = new Main();
				m.setVisible(true);
				mn.dispose();
			}
		}else if(e.getActionCommand().equals("Add") ){
			AdminAdd ad = new AdminAdd();
			ad.setVisible(true);
			mn.dispose();
		}else if(e.getActionCommand().equals("LogOut")){
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			mn.dispose();
		}else if(e.getActionCommand().equals("View")){
			ViewResult vr = new ViewResult();
			vr.setVisible(true);
			mn.dispose();
		}else
		{
			System.exit(0);
		}

	}

}
