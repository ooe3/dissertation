package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.*;
import main.*;

public class AddListener implements ActionListener{
	AdminAdd ad;
	Users us;
	Queries q = Queries.getQueries();
	public AddListener(AdminAdd am){
		ad = am;
		us = q.getUser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Submit")){
			if(ad.textField_6().getText().equals("") || ad.textField_7().getText().equals("")){
				JOptionPane.showMessageDialog(null, "One or more textfields empty. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				try{
					int exam = Integer.parseInt(ad.textField_6().getText());
					int cw = Integer.parseInt(ad.textField_7().getText());
					if((exam < 0 || exam > 100) || (cw < 0 || cw > 100)){
						JOptionPane.showMessageDialog(null, "Number greater than 100 or less than 0. Enter numbers between 0 & 100", "Error message", JOptionPane.ERROR_MESSAGE);
						ad.textField_6().setText("");
						ad.textField_7().setText("");
					}else{
						int overall = q.calculateScore(exam, cw, q.getCwPercentage(), q.getExamPercentage());
						q.insertCourseScore(overall, ad.selected5(), ad.getNames()[0], ad.getNames()[1]);
						JOptionPane.showMessageDialog(null, "Result added", "Window",
								JOptionPane.ERROR_MESSAGE);
						AdminAdd aa = new AdminAdd();
						aa.setVisible(true);
						ad.dispose();
					}
				}catch(NumberFormatException e1){
					JOptionPane.showMessageDialog(null, "Numeric input required", "Error message", JOptionPane.ERROR_MESSAGE);
					ad.textField_6().setText("");
					ad.textField_7().setText("");

				}
			}
		}else if(e.getActionCommand().equals("Add") ){
			ad.setVisible(true);
		}else if(e.getActionCommand().equals("Home Menu") ){
			Main mn = new Main();
			mn.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("Change Password") ){
			
		}else if(e.getActionCommand().equals("LogOut")){
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("Refresh")){
			AdminAdd ada = new AdminAdd();
			ada.setVisible(true);
			ad.dispose();
		}else{
			System.exit(0);
		}
	
	}

}
