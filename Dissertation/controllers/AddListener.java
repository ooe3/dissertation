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
						if((q.checkResults(ad.getNames()[0], ad.getNames()[1])).equals("No")){
						AdminAdd aa = new AdminAdd();
						aa.setVisible(true);
						ad.dispose();
						}else{
							int show = JOptionPane.showConfirmDialog(null, "Do you want calculate & add the overall mark for this student?");
							if(show == 0){
								String s = q.getResult(ad.getNames()[0], ad.getNames()[1]);
								q.insertOverall(s, ad.getNames()[0], ad.getNames()[1]);
								JOptionPane.showMessageDialog(null, "Overall added", "Window",
										JOptionPane.ERROR_MESSAGE);
								AdminAdd aa = new AdminAdd();
								aa.setVisible(true);
								ad.dispose();
							}else if(show == 1){
								AdminAdd aa = new AdminAdd();
								aa.setVisible(true);
								ad.dispose();
							}else {
							AdminAdd aad = new AdminAdd();
							aad.setVisible(true);
							ad.dispose();
							}
						}
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
		}else if(e.getActionCommand().equals("Calculate")){
			if((q.checkResults(ad.getNameCalc()[0], ad.getNameCalc()[1])).equals("No")){
				JOptionPane.showMessageDialog(null, "All results for this student has not been entered. Add results on the left", "Error message", JOptionPane.ERROR_MESSAGE);
			}else {
				String s = q.getResult(ad.getNameCalc()[0], ad.getNameCalc()[1]);
				q.insertOverall(s, ad.getNameCalc()[0], ad.getNameCalc()[1]);
				JOptionPane.showMessageDialog(null, "Overall add", "Window",
						JOptionPane.ERROR_MESSAGE);
				
			}
			AdminAdd ada = new AdminAdd();
			ada.setVisible(true);
			ad.dispose();
		}
		
		else{
			System.exit(0);
		}
	
	}

}
