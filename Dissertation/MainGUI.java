import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainGUI implements ItemListener {
	JPanel main, box, login, welcome;
	JButton button1, button2;
	String mainPanel = "Log In";
	String welcomePanel = "Welcome";
	
	public MainGUI(){
		//default constructor
		button1 = new JButton("Log In As Student");
		button2 = new JButton("Log In As Administrator");
	}
	
	public void addComponent(Container cn){
		box = new JPanel();
		String items[] = {mainPanel, welcomePanel};
		JComboBox jb = new JComboBox(items);
		jb.setEditable(false);
		jb.addItemListener(this);
		box.add(jb);
		
		login = new JPanel();
		login.add(button1);
		login.add(button2);
		
		cn.add(box, BorderLayout.PAGE_START);
		cn.add(main, BorderLayout.CENTER);
		
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
	// TODO Auto-generated method stub
		CardLayout cl = new CardLayout();
		main.getLayout();
		cl.show(main, (String)e.getItem());
	
	}
	
	public void displayGUI(){
		JFrame jf = new JFrame("University Records System");
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		
		jf.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		
	}

}
