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
			password = new String(cs.passwordField().getPassword());
			if(cs.textField().getText().equals("") || cs.textField1().getText().equals("") || cs.textField2().getText().equals("") || cs.textField3().getText().equals("") || cs.textField4().getText().equals("") || password.equals("")){
				JOptionPane.showMessageDialog(null, "No text entered. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				String[] tokens = cs.getSelected().split("\\(");
				if(q.insertStudent(cs.textField().getText(), cs.textField1().getText(), cs.textField3().getText(), cs.textField2().getText(), cs.textField4().getText(), tokens[0]).equals("Error")){
					JOptionPane.showMessageDialog(null, "Matric number exists already. Generate another", "Error message", JOptionPane.ERROR_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "Student added", "Window",
							JOptionPane.ERROR_MESSAGE);
					CreateStudent cst = new CreateStudent();
					cst.setVisible(true);
					cs.dispose();
				}
			}
		}else if(e.getActionCommand().equals("Generate")){
			if(cs.textField1().getText().equals("")){
				JOptionPane.showMessageDialog(null, "You need surname to generate unique username", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
			String unique = q.getUnique();
			initial = cs.textField1().getText();
			String s = initial.substring(0,1);
			String letter = s.toLowerCase();
			StringBuilder sb = new StringBuilder("");
			sb.append(unique);
			sb.append(letter);
			cs.textField4().setText(sb.toString());
			cs.passwordField().setText(sb.toString());
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
