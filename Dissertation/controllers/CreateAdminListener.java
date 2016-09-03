package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import gui.*;
import main.*;

public class CreateAdminListener implements ActionListener{
	CreateAdmin ca;
	Queries q = Queries.getQueries();//Queries object to have access to methods in the Queries class

	public CreateAdminListener(CreateAdmin c){
		ca = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Go") ){
			ca.dispose();
		}else if(e.getActionCommand().equals("Generate")){
			String s1 = ca.textField1().getText().trim();
			if(q.checkLastName(s1).equals("Exists")){//check for special characters
				JOptionPane.showMessageDialog(null, "Last name contains special characters", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				if(s1.equals("")){//check if a surname has been entered
					JOptionPane.showMessageDialog(null, "You need surname to generate unique username", "Error message", JOptionPane.ERROR_MESSAGE);
				}else{
					String unique = q.getUnique();//call getUnique and store generated number into unique
					String initial = s1;
					String s = initial.substring(0,1);//to get the first letter to use with generated number
					String letter = s.toLowerCase();
					//combine unique & letter to get matric
					StringBuilder sb = new StringBuilder("");
					sb.append(unique);
					sb.append(letter);
					ca.textField4().setText(sb.toString());
					ca.passwordField().setText(sb.toString());
				}
			}
		}else{
			String password = new String(ca.passwordField().getPassword()).trim();
			String name = ca.textField().getText().trim();
			String lname = ca.textField1().getText().trim();
			String address = ca.textField2().getText().trim();
			String email = ca.textField3().getText().trim();
			String matric = ca.textField4().getText().trim();
			String school = ca.textField5().getText().trim();
			if(name.equals("") || lname.equals("") || email.equals("") || matric.equals("") || password.equals("")){
				JOptionPane.showMessageDialog(null, "No text entered. Enter text", "Error message", JOptionPane.ERROR_MESSAGE);
			}else{
				//check for special characters in any of the textfields 
				if(q.checkFirstName(name).equals("Exists") || q.checkLastName(lname).equals("Exists")){
					JOptionPane.showMessageDialog(null, "Special characters not accepted for names", "Error message", JOptionPane.ERROR_MESSAGE);
					ca.textField().setText("");
					ca.textField1().setText("");
				}else if(q.checkEmail(email).equals("Exists")){
					JOptionPane.showMessageDialog(null, "Email entered not in email format", "Error message", JOptionPane.ERROR_MESSAGE);
					ca.textField().setText(name);
					ca.textField1().setText(lname);
					ca.textField3().setText("");
					ca.textField4().setText(matric);
					ca.passwordField().setText(matric);
				}else if(!address.equals("")){
					if(q.checkAddress(address).equals("Exists")){
						JOptionPane.showMessageDialog(null, "Address entered not in the right format", "Error message", JOptionPane.ERROR_MESSAGE);
						ca.textField().setText(name);
						ca.textField1().setText(lname);
						ca.textField2().setText("");
						ca.textField4().setText(matric);
						ca.passwordField().setText(matric);
					}
				}
				else if(q.checkUsername(matric).equals("Exists")){
					JOptionPane.showMessageDialog(null, "Username not in the right format", "Error message", JOptionPane.ERROR_MESSAGE);
					ca.textField().setText(name);
					ca.textField1().setText(lname);
					ca.textField4().setText("");
					ca.passwordField().setText("");
				}else if(q.checkString(school).equals("Exists")){
					JOptionPane.showMessageDialog(null, "School not in the right format", "Error message", JOptionPane.ERROR_MESSAGE);
					ca.textField().setText(name);
					ca.textField1().setText(lname);
					ca.textField5().setText("");
				}else if((school.matches("[A-Z]{1,1}+[a-z]{1,}+\\s[A-Z]{1,1}+[A-Za-z\\s]{1,}")) || (school.matches("[A-Z]{1,1}+[a-z]{1,}")) || !ca.choice().getSelectedItem().equals("(select school)")){
					if((school.indexOf("School of") != -1 )){
						JOptionPane.showMessageDialog(null, "Not in the right format. See example", "Error message", JOptionPane.ERROR_MESSAGE);
					}else if(school.indexOf("School") != -1){
						JOptionPane.showMessageDialog(null, "Not in the right format. See example", "Error message", JOptionPane.ERROR_MESSAGE);
					}else if(!ca.choice().getSelectedItem().equals("(select school)") && !ca.textField5().getText().equals("")){
						JOptionPane.showMessageDialog(null, "You entered text and selected a choice", "Error message", JOptionPane.ERROR_MESSAGE);
					}else {
						if(!ca.choice().getSelectedItem().equals("(select school)") && ca.textField5().getText().equals("")){
							school = ca.getSelected();
						}
							
					String check = q.insertAdmin(name, lname, email, address, matric, school);
					if(check.equals("Error")){//check if the student or matric exists already
						JOptionPane.showMessageDialog(null, "This student exists", "Error message", JOptionPane.ERROR_MESSAGE);
						ca.textField().setText("");
						ca.textField1().setText("");
						ca.textField2().setText("");
						ca.textField3().setText("");
						ca.textField4().setText("");
						ca.passwordField().setText("");
						ca.textField5().setText("");
					}else if(check.equals("Exists")){
						JOptionPane.showMessageDialog(null, "Matric number exists", "Error message", JOptionPane.ERROR_MESSAGE);
						ca.textField().setText(name);
						ca.textField1().setText(lname);
						ca.textField2().setText(address);
						ca.textField3().setText(email);
						ca.textField4().setText("");
						ca.passwordField().setText("");
					}else if(check.equals("Already")){
						JOptionPane.showMessageDialog(null, "Email exists", "Error message", JOptionPane.ERROR_MESSAGE);
						ca.textField().setText(name);
						ca.textField1().setText(lname);
						ca.textField2().setText(address);
						ca.textField3().setText("");
						ca.textField4().setText(matric);
						ca.passwordField().setText(matric);
					}
					else{ 
						JOptionPane.showMessageDialog(null, "Admin added", "Window",
								JOptionPane.INFORMATION_MESSAGE);
						ca.dispose();//current Admin object disposed
					}
					}
				}else{
					JOptionPane.showMessageDialog(null, "Not in the right format. See example", "Error message", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}

}
