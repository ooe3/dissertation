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
			String credits = mn.getTextField_3().getText().trim();
			String exams = mn.getTextField_4().getText().trim();
			String cwks = mn.getTextField_8().getText().trim();
			String name = mn.getTextField_2().getText().trim();
			if(name.equals("") || credits.equals("") || exams.equals("") || cwks.equals("") || mn.choice1().getSelectedItem().equals("(select degree)") || mn.choice1().getSelectedItem().equals("")){
				JOptionPane.showMessageDialog(null, "One or more textfields empty. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				try{
					int credit = Integer.parseInt(credits);
					int exam = Integer.parseInt(exams);
					int cw = Integer.parseInt(cwks);
					if(q.insertCourse(name, credit, exam, cw).equals("Error")){
						JOptionPane.showMessageDialog(null, "Course already exists", "Error message", JOptionPane.ERROR_MESSAGE);
						mn.getTextField_2().setText("");
						mn.getTextField_3().setText("");
						mn.getTextField_4().setText("");
						mn.getTextField_8().setText("");
					}else{
						if(q.checkString(name).equals("Exists")){
							JOptionPane.showMessageDialog(null, "Special characters not accepted for the name.", "Window",
									JOptionPane.ERROR_MESSAGE);
						}else{
							if(exam > 100 || cw > 100 || credit > 60){
								JOptionPane.showMessageDialog(null, "Number greater than 100 or less than 0 or credits greater than 60", "Error message", JOptionPane.ERROR_MESSAGE);
								
							}else{
						String[] tokens = mn.selected5().split("\\(");
						String degree = q.addCourseDegree(name, tokens[0]);
						if(degree.equals("Error")){
							JOptionPane.showMessageDialog(null, "Course already assigned to this degree", "Window",
									JOptionPane.ERROR_MESSAGE);
						}else{

							JOptionPane.showMessageDialog(null, "Course addition successful", "Window",
									JOptionPane.INFORMATION_MESSAGE);
							Main m = new Main();
							m.setVisible(true);
							mn.dispose();
							}
							}
						}
						
					}
				}catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(null, "Numeric input required", "Error message", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if(e.getActionCommand().equals("Remove") ){
			if(mn.choice5().getSelectedItem().equals("(select course)") || mn.choice5().getSelectedItem().equals("")){
				JOptionPane.showMessageDialog(null, "No course selected", "Window",
						JOptionPane.ERROR_MESSAGE);
			}else{
				if(q.removeCourse(mn.getSelected2()).equals("Exists")){
					JOptionPane.showMessageDialog(null, "Course already selected by student and can't be removed.", "Window",
							JOptionPane.ERROR_MESSAGE);
				}else{
				JOptionPane.showMessageDialog(null, "Removal successful", "Window",
						JOptionPane.ERROR_MESSAGE);
			}
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
		}else if(e.getActionCommand().equals("Home Menu")){
			mn.setVisible(true);
		}else if(e.getActionCommand().equals("Degree")){
			if(mn.choice3().getSelectedItem().equals("(select degree)") || mn.choice().getSelectedItem().equals("(select school)") || mn.choice().getSelectedItem().equals("") || mn.choice3().getSelectedItem().equals("")){
				JOptionPane.showMessageDialog(null, "No course or school selected", "Window",
						JOptionPane.ERROR_MESSAGE);
			}else{
				String[] tokens = mn.getSelected3().split("\\(");
				String degree = q.addCourseDegree(mn.getSelected4(), tokens[0]);
				if(degree.equals("Error")){
					JOptionPane.showMessageDialog(null, "Course already assigned to this degree", "Window",
							JOptionPane.ERROR_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "Course added to degree succesfully", "Window",
							JOptionPane.INFORMATION_MESSAGE);
				}
				Main m = new Main();
				m.setVisible(true);
				mn.dispose();
			}
		}else if(e.getActionCommand().equals("Refresh")){
			Main m = new Main();
			m.setVisible(true);
			mn.dispose();
		}
		else if(e.getActionCommand().equals("Add Student")){
			CreateStudent c = new CreateStudent();
			c.setVisible(true);
			mn.dispose();
		}else{
			ViewStudents vs = new ViewStudents();
			vs.setVisible(true);
			mn.dispose();
		}
	}

}
