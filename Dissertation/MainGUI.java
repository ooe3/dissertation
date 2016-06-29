import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainGUI implements ItemListener {
	JPanel main, box, login, welcome;
	JButton button1, button2;
	String mainPanel = "Log In";
	String welcomePanel = "Welcome";
	JTextField txtfield, txtfield1;
	JPasswordField jp;
	JLabel jb, jb1, jb2;
	JMenu jm;
	JMenuBar jmb;
	
	
	public MainGUI(){
		//default constructor
		button1 = new JButton("Log In As Student");
		button2 = new JButton("Log In As Administrator");
		jb2 = new JLabel(mainPanel);
		jb2.setFont((new Font("Serif", Font.BOLD, 20)));
		jb = new JLabel("Username");
		jb1 = new JLabel("Password");
		//jmb = new JMenuBar();
		//jm = new JMenu("Menu");
		
	}
	
	public void addComponent(Container cn){
		box = new JPanel();
		
		//jmb.add(jm);
		
		login = new JPanel();
		box.add(jb2);
		login.add(jb);
		login.add(jb1);
		login.add(button1);
		login.add(button2);
		
		main = new JPanel(new CardLayout());
		//main.add(jmb, BorderLayout.SOUTH);
		main.add(login, mainPanel);
		//main.add(welcome, welcomePanel);
		
		
		cn.add(main, BorderLayout.CENTER);
		
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
	// TODO Auto-generated method stub
		
		CardLayout cl = (CardLayout) main.getLayout();
		cl.show(main, (String)e.getItem());
	
	}
	
	public void displayGUI(){
		JFrame jf = new JFrame("University Records System");
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		MainGUI mg = new MainGUI();
		mg.addComponent(jf.getContentPane());
		
		jf.setVisible(true);
		jf.setSize(1000, 800);
	}
	
	public void actionPerformed(ActionEvent e){
		
	}

}
