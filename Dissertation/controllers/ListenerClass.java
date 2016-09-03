package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import gui.*;
import main.Queries;
import main.Users;
/*
 * The listener class for the StartGUI
 * 
 */
public class ListenerClass implements ActionListener{
	StartGUI sg;//StartGUI object
	Queries q = Queries.getQueries();//Queries object to access all methods in Queries class
	Users us;
	String matricno, password;
	public ListenerClass(StartGUI sg){
		this.sg = sg;//Initialize the StartGUI object
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("LOG_IN") ){
				matricno = sg.matric().getText().trim();
				password = new String(sg.pass().getPassword()).trim();
				us = q.LogIn(matricno, password);//Initialize the Users object by calling the LogIn method takes the username & password as parameters
				if(us!=null){
					//Depending on the type of user, the frames will be display accordingly
					if(us.getType().equals("Student")){
						StudentMain mn = new StudentMain();//StudentMain created & displayed
						mn.setVisible(true);
						sg.dispose();//StartGUI disposed
					}else{
						Main m = new Main();//Main created & displayed
						m.setVisible(true);
						sg.dispose();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Incorrect username or password", "Incorrect",
							JOptionPane.ERROR_MESSAGE);
				}
			sg.matric().setText("");
			sg.pass().setText("");

		}else{
			CreateAdmin ca = new CreateAdmin();
			ca.setVisible(true);
		}

	}

}
