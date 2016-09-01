package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import gui.*;
import main.*;

/*
 * The listener class for the CreateStudent class
 * All button actions perfromed here
 */
public class CreateListener implements ActionListener{
	CreateStudent cs;//CreateStudent object
	Users us;//Users object
	Queries q = Queries.getQueries();//Queries object to have access to methods in the Queries class
	MainQueries mq = MainQueries.getMain();//MainQueries object to have access to methods in MainQueriesClass
	String matricno, password, initial;
	List<Degree> dg;//List containing degree object
	List<Student> stt;
	List<Course> cdg;
	public CreateListener(CreateStudent c){
		cs = c;//initialize CreateStudent
		us = q.getUser();//initialize Users object to get the current user
		dg = mq.getList();//List initialized to get Degree objects
		stt = q.getStudents();
		cdg = mq.getCourseList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("ADD")){
			password = new String(cs.passwordField().getPassword()).trim();
			String name = cs.textField().getText().trim();
			String lname = cs.textField1().getText().trim();
			String address = cs.textField2().getText().trim();
			String email = cs.textField3().getText().trim();
			String matric = cs.textField4().getText().trim();
			//check if any of the textfields are empty
			if(name.equals("") || lname.equals("") || email.equals("") || matric.equals("") || password.equals("") || cs.getSelected().equals("") || cs.getSelected().equals("(select degree)")){
				JOptionPane.showMessageDialog(null, "No text entered. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				//check for special characters in any of the textfields 
				if(q.checkFirstName(name).equals("Exists") || q.checkLastName(lname).equals("Exists")){
					JOptionPane.showMessageDialog(null, "Special characters not accepted for names", "Error message", JOptionPane.ERROR_MESSAGE);
					cs.textField().setText("");
					cs.textField1().setText("");
				}else if(q.checkEmail(email).equals("Exists")){
					JOptionPane.showMessageDialog(null, "Email entered not in email format", "Error message", JOptionPane.ERROR_MESSAGE);
					cs.textField().setText(name);
					cs.textField1().setText(lname);
					cs.textField3().setText("");
					cs.textField4().setText(matric);
					cs.passwordField().setText(matric);
				}else if(!address.equals("")){
					if(q.checkAddress(address).equals("Exists")){
						JOptionPane.showMessageDialog(null, "Address entered not in the right format", "Error message", JOptionPane.ERROR_MESSAGE);
						cs.textField().setText(name);
						cs.textField1().setText(lname);
						cs.textField2().setText("");
						cs.textField4().setText(matric);
						cs.passwordField().setText(matric);
					}
				}
				else if(q.checkUsername(matric).equals("Exists")){
					JOptionPane.showMessageDialog(null, "Username not in the right format", "Error message", JOptionPane.ERROR_MESSAGE);
					cs.textField().setText(name);
					cs.textField1().setText(lname);
					cs.textField4().setText("");
					cs.passwordField().setText("");
				}else{
					String[] tokens = cs.getSelected().split("\\(");//to get the course selected
					String check = q.insertStudent(name, lname, email, address, matric, tokens[0]);
					if(check.equals("Error")){//check if the student or matric exists already
						JOptionPane.showMessageDialog(null, "This student exists", "Error message", JOptionPane.ERROR_MESSAGE);
						cs.textField().setText("");
						cs.textField1().setText("");
						cs.textField2().setText("");
						cs.textField3().setText("");
						cs.textField4().setText("");
						cs.passwordField().setText("");
					}else if(check.equals("Exists")){
						JOptionPane.showMessageDialog(null, "Matric number exists", "Error message", JOptionPane.ERROR_MESSAGE);
						cs.textField().setText(name);
						cs.textField1().setText(lname);
						cs.textField2().setText(address);
						cs.textField3().setText(email);
						cs.textField4().setText("");
						cs.passwordField().setText("");
					}else{
						JOptionPane.showMessageDialog(null, "Student added", "Window",
								JOptionPane.INFORMATION_MESSAGE);
						stt.removeAll(stt);
						dg.removeAll(dg);
						CreateStudent cst = new CreateStudent();//New CreateStudent object created to get updated info
						cst.setVisible(true);
						cs.dispose();//current createStudent object disposed
					}
				}
			}
		}else if(e.getActionCommand().equals("Generate")){//to generate unique matric number for student
			String s1 = cs.textField1().getText().trim();
			if(q.checkLastName(s1).equals("Exists")){//check for special characters
				JOptionPane.showMessageDialog(null, "Last name contains special characters", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				if(s1.equals("")){//check if a surname has been entered
					JOptionPane.showMessageDialog(null, "You need surname to generate unique username", "Error message", JOptionPane.ERROR_MESSAGE);
				}else{
					String unique = q.getUnique();//call getUnique and store generated number into unique
					initial = s1;
					String s = initial.substring(0,1);//to get the first letter to use with generated number
					String letter = s.toLowerCase();
					//combine unique & letter to get matric
					StringBuilder sb = new StringBuilder("");
					sb.append(unique);
					sb.append(letter);
					cs.textField4().setText(sb.toString());
					cs.passwordField().setText(sb.toString());
				}
			}

		}else if(e.getActionCommand().equals("Add") ){
			stt.removeAll(stt);
			AdminAdd ad = new AdminAdd();//New AdminAdd object created to go to its frame
			ad.setVisible(true);
			cs.dispose();//current disposed
		}else if(e.getActionCommand().equals("LogOut")){
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			StartGUI sg = new StartGUI();//New StartGUI object created to go to its frame
			sg.setVisible(true);
			cs.dispose();
		}else if(e.getActionCommand().equals("View")){
			cdg.removeAll(cdg);
			dg.removeAll(dg);
			stt.removeAll(stt);
			ViewResult vr = new ViewResult();//New ViewResult object created to go to its frame
			vr.setVisible(true);
			cs.dispose();
		}else if(e.getActionCommand().equals("Home Menu")){
			dg.removeAll(dg);
			cdg.removeAll(cdg);
			stt.removeAll(stt);
			Main mn = new Main();//New Main object created to go to its frame
			mn.setVisible(true);
			cs.dispose();
		}else if(e.getActionCommand().equals("Add Student")){
			cs.setVisible(true);
		}else{
			stt.removeAll(stt);
			ViewStudents vs = new ViewStudents();
			vs.setVisible(true);
			cs.dispose();
		}

	}

}
