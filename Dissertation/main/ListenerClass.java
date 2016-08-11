package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import gui.*;

public class ListenerClass implements ActionListener{
	StartGUI sg;
	Queries q = Queries.getQueries();
	Users us;
	String matricno, password;
	public ListenerClass(StartGUI sg){
		this.sg = sg;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("LOG_IN") ){
				matricno = sg.matric().getText();
				password = new String(sg.pass().getPassword());
				us = q.LogIn(matricno, password);
				if(us!=null){
					if(us.getType().equals("Student")){
						StudentMain mn = new StudentMain();
						mn.setVisible(true);
						sg.dispose();
					}else{
						Main m = new Main();
						m.setVisible(true);
						sg.dispose();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Incorrect username or password", "Incorrect",
							JOptionPane.ERROR_MESSAGE);
				}
			sg.matric().setText("");
			sg.pass().setText("");

		}

	}

}
