package controllers;

import java.awt.event.ActionEvent;
import java.util.*;
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
	MainQueries m = MainQueries.getMain();//MainQueries object created to access methods in the MainQueries class
	AddQueries aq = AddQueries.getMain();
	List<Student> stt;
	List<Degree> dg;//Create a list containing degree objects
	List<Course> cdg;
	Student st;
	List<CourseResult> crt;
	List<CourseDegree> getCdg;
	public AddListener(AdminAdd am){
		ad = am;
		us = q.getUser();//get the user currently logged in
		dg = m.getList();//initializes list by calling getList method to get all Degrees under the Admin's school
		cdg = m.getCourseList();
		stt = q.getStudents();
		st = aq.getStudent();
		crt = aq.getInfo();
		getCdg = m.getCourseDegreeList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Submit")){
			String exam = ad.textField_6().getText().trim();
			String cw = ad.textField_7().getText().trim();
			String checkExam = aq.checkMark(exam);
			String checkCw = aq.checkMark(cw);
			if(exam.equals("") || cw.trim().equals("")){//Check if the textFields_6 or textFields_7 are empty
				JOptionPane.showMessageDialog(null, "One or more textfields empty. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);//display error message
			}else if(checkExam.equals("Error") || checkCw.equals("Error")){
					JOptionPane.showMessageDialog(null, "Mark bands not entered. Enter bands from A1 - H", "Error message", JOptionPane.ERROR_MESSAGE);
				}else{
					int overall = aq.calculateScore(exam, cw, aq.getDetails().getCoursework(), aq.getDetails().getExamPercentage());
					double e1 = (double)aq.getDetails().getExamPercentage()/100;
					double c1 = (double)aq.getDetails().getCoursework()/100;
					//calls the insertCourseScore method taking the overall, selected course and name of the student
					int check = JOptionPane.showConfirmDialog(null, "Exam = "+exam+"*"+e1+"and Coursework = "+cw+"*"+c1+". Total = "+overall);//dialog box to ask if admin wants to calculate overall mark
					if(check == 0){//if yes, store the result in the datatabase
						aq.insertCourseScore(overall, aq.getDetails().getCourse(), aq.getStudent().getStudentID());
						JOptionPane.showMessageDialog(null, "Result added", "Window",
								JOptionPane.INFORMATION_MESSAGE);
						crt.removeAll(crt);
						CourseResult crs = aq.getDetails(aq.getStudent().getStudentID());
						//calls the checkResults method to check if the student has all their results entered
						if((aq.checkResults()).equals("No")){
							stt.removeAll(stt);
							crt.removeAll(crt);
							AdminAdd aa = new AdminAdd();
							aa.setVisible(true);
							ad.dispose();
						}else{
							int show = JOptionPane.showConfirmDialog(null, "Do you want calculate & add the overall mark for this student?");//dialog box to ask if admin wants to calculate overall mark
							if(show == 0){//if yes, store the result in the datatabase
								String s = aq.getResult();
								aq.insertOverall(s, aq.getStudent().getStudentID());
								JOptionPane.showMessageDialog(null, "Overall added", "Window",
										JOptionPane.INFORMATION_MESSAGE);
								AdminAdd aa = new AdminAdd();
								aa.setVisible(true);
								ad.dispose();
							}else {
								AdminAdd aa = new AdminAdd();
								aa.setVisible(true);
								ad.dispose();
								
							}
						}

					}else {
						ad.setVisible(true);
						ad.textField_6().setText("");
						ad.textField_7().setText("");
					}
				
			}

		}else if(e.getActionCommand().equals("Add") ){//calls this method if the ActionCommand is "Add"
			ad.setVisible(true);
		}else if(e.getActionCommand().equals("Home Menu") ){//calls this method if the ActionCommand is "Home Menu"
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			Main mn = new Main();
			mn.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("Change Password") ){//calls this method if the ActionCommand is "Change Password"

		}else if(e.getActionCommand().equals("LogOut")){//calls this method if the ActionCommand is "LogOut"
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("Refresh")){
			stt.removeAll(stt);
			AdminAdd ada = new AdminAdd();
			ada.setVisible(true);
			ad.dispose();
		}else if(e.getActionCommand().equals("Calculate")){
			//check if choice hasn't been selected 
			if(ad.choice().getSelectedItem().equals("(select student)")){
				JOptionPane.showMessageDialog(null, "No student selected.", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				if((aq.checkResults()).equals("No")){
					JOptionPane.showMessageDialog(null, "All results for this student has not been entered or student has no result. Add results on the left if student has enrolled courses", "Error message", JOptionPane.ERROR_MESSAGE);
				}else {
					//calculate the overall score of the student if all results have been stored
					String s = aq.getResult();
					aq.insertOverall(s, aq.getStudent().getStudentID());
					JOptionPane.showMessageDialog(null, "Overall add", "Window",
							JOptionPane.INFORMATION_MESSAGE);
					stt.removeAll(stt);
					AdminAdd ada = new AdminAdd();
					ada.setVisible(true);
					ad.dispose();
				}
			}

		}else if(e.getActionCommand().equals("Add Student")){
			dg.removeAll(dg);
			getCdg.removeAll(getCdg);
			CreateStudent cs = new CreateStudent();
			cs.setVisible(true);
			ad.dispose();
		}else{
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			ViewResult vr = new ViewResult();
			vr.setVisible(true);
			ad.dispose();
		}

	}

}
