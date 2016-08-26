package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.*;
import main.*;


public class CreateListener implements ActionListener{
	CreateStudent cs;
	Users us;
	Queries q = Queries.getQueries();
	String matricno, password, initial;
	public CreateListener(CreateStudent c){
		cs = c;
		us = q.getUser();
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

			if(name.equals("") || lname.equals("") || address.equals("") || email.equals("") || matric.equals("") || password.equals("") || cs.getSelected().equals("") || cs.getSelected().equals("(select degree)")){
				JOptionPane.showMessageDialog(null, "No text entered. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				if(q.checkString(name).equals("Exists") || q.checkLastName(lname).equals("Exists") || q.checkEmail(email).equals("Exists") || q.checkAddress(address).equals("Exists")){
					JOptionPane.showMessageDialog(null, "Special characters not accepted", "Error message", JOptionPane.ERROR_MESSAGE);
				}else{
					String[] tokens = cs.getSelected().split("\\(");
					if(q.insertStudent(name, lname, email, address, matric, tokens[0]).equals("Error")){
						JOptionPane.showMessageDialog(null, "Student exists or matric number exists", "Error message", JOptionPane.ERROR_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "Student added", "Window",
								JOptionPane.INFORMATION_MESSAGE);
						CreateStudent cst = new CreateStudent();
						cst.setVisible(true);
						cs.dispose();
					}
				}
			}
		}else if(e.getActionCommand().equals("Generate")){
			String s1 = cs.textField1().getText().trim();
			if(q.checkLastName(s1).equals("Exists")){
				JOptionPane.showMessageDialog(null, "Last name contains special characters", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				if(s1.equals("")){
					JOptionPane.showMessageDialog(null, "You need surname to generate unique username", "Error message", JOptionPane.ERROR_MESSAGE);
				}else{
					String unique = q.getUnique();
					initial = s1;
					String s = initial.substring(0,1);
					String letter = s.toLowerCase();
					StringBuilder sb = new StringBuilder("");
					sb.append(unique);
					sb.append(letter);
					cs.textField4().setText(sb.toString());
					cs.passwordField().setText(sb.toString());
					System.out.print(s1);
				}
			}

		}else if(e.getActionCommand().equals("Add") ){
			AdminAdd ad = new AdminAdd();
			ad.setVisible(true);
			cs.dispose();
		}else if(e.getActionCommand().equals("LogOut")){
			StartGUI sg = new StartGUI();
			sg.setVisible(true);
			cs.dispose();
		}else if(e.getActionCommand().equals("View")){
			ViewResult vr = new ViewResult();
			vr.setVisible(true);
			cs.dispose();
		}else if(e.getActionCommand().equals("Home Menu")){
			Main mn = new Main();
			mn.setVisible(true);
			cs.dispose();
		}else if(e.getActionCommand().equals("Add Student")){
			cs.setVisible(true);
		}else{

		}

	}

}
