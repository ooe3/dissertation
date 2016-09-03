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
		}
	
	}

}
