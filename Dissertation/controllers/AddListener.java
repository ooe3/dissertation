package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.*;
import main.*;
/*
 * This is the actionListener for AdminAdd class
 */
public class AddListener implements ActionListener{
	AdminAdd ad;
	Users us;
	Queries q = Queries.getQueries();//Object of the Queries class
	AddQueries aq = AddQueries.getMain();
	public AddListener(AdminAdd am){
		ad = am;
		us = q.getUser();//get the user currently logged in
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Submit")){
			if(ad.textField_6().getText().trim().equals("") || ad.textField_7().getText().trim().equals("")){//Check if the textFields_6 or textFields_7 are empty
				JOptionPane.showMessageDialog(null, "One or more textfields empty. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);//display error message
			}else{
				try{
					int exam = Integer.parseInt(ad.textField_6().getText().trim());//converts input in textField_6 to integer
					int cw = Integer.parseInt(ad.textField_7().getText().trim());//converts input in textField_7 to integer
					if(exam > 100 || cw > 100){//Check if input in both textfields are greater than 100
						JOptionPane.showMessageDialog(null, "Number greater than 100 or less than 0. Enter numbers between 0 & 100", "Error message", JOptionPane.ERROR_MESSAGE);
						ad.textField_6().setText("");
						ad.textField_7().setText("");
					}else{
						//call the calculatescore method to store the result in overall
						//It takes the number stored in the exam, cw and the percentage for both the coursework & exam of the course selected
						int overall = aq.calculateScore(exam, cw, aq.getCwPercentage(), aq.getExamPercentage());
						//calls the insertCourseScore method taking the overall, selected course and name of the student
						aq.insertCourseScore(overall, ad.selected5(), ad.getNames()[0], ad.getNames()[1]);
						JOptionPane.showMessageDialog(null, "Result added", "Window",
								JOptionPane.INFORMATION_MESSAGE);
						//calls the checkResults method to check if the student has all their results entered
						if((q.checkResults(ad.getNames()[0], ad.getNames()[1])).equals("No")){
							AdminAdd aa = new AdminAdd();
							aa.setVisible(true);
							ad.dispose();
						}else{
							int show = JOptionPane.showConfirmDialog(null, "Do you want calculate & add the overall mark for this student?");//dialog box to ask if admin wants to calculate overall mark
							if(show == 0){//if yes, store the result in the datatabase
								String s = aq.getResult(ad.getNames()[0], ad.getNames()[1]);
								aq.insertOverall(s, ad.getNames()[0], ad.getNames()[1]);
								JOptionPane.showMessageDialog(null, "Overall added", "Window",
										JOptionPane.INFORMATION_MESSAGE);
								AdminAdd aa = new AdminAdd();
								aa.setVisible(true);
								ad.dispose();
							}else if(show == 1){
								AdminAdd aa = new AdminAdd();//create a new adminAdd object
								aa.setVisible(true);
								ad.dispose();//dispose the previous adminAdd object
							}else {
								AdminAdd aad = new AdminAdd();
								aad.setVisible(true);
								ad.dispose();
							}
						}
					}
				}catch(NumberFormatException e1){//Exception to catch non-numeric or negative numbers
					JOptionPane.showMessageDialog(null, "Numeric input required", "Error message", JOptionPane.ERROR_MESSAGE);
					ad.textField_6().setText("");
					ad.textField_7().setText("");

				}
			}
		}else if(e.getActionCommand().equals("Add") ){//calls this method if the ActionCommand is "Add"
			ad.setVisible(true);
		}else if(e.getActionCommand().equals("Home Menu") ){//calls this method if the ActionCommand is "Home Menu"
			Main mn = new Main();
			mn.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("Change Password") ){//calls this method if the ActionCommand is "Change Password"

		}else if(e.getActionCommand().equals("LogOut")){//calls this method if the ActionCommand is "LogOut"
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("Refresh")){
			AdminAdd ada = new AdminAdd();
			ada.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("Calculate")){
			//check if choice hasn't been selected 
			if(ad.choice().getSelectedItem().equals("(select student)")){
				JOptionPane.showMessageDialog(null, "No student selected.", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				if((q.checkResults(ad.getNameCalc()[0], ad.getNameCalc()[1])).equals("No")){
					JOptionPane.showMessageDialog(null, "All results for this student has not been entered. Add results on the left", "Error message", JOptionPane.ERROR_MESSAGE);
				}else {
				//calculate the overall score of the student if all results have been stored
					String s = aq.getResult(ad.getNameCalc()[0], ad.getNameCalc()[1]);
					aq.insertOverall(s, ad.getNameCalc()[0], ad.getNameCalc()[1]);
					JOptionPane.showMessageDialog(null, "Overall add", "Window",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
			AdminAdd ada = new AdminAdd();
			ada.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("Add Student")){
			CreateStudent cs = new CreateStudent();
			cs.setVisible(true);
			ad.dispose();
		}

	}

}
