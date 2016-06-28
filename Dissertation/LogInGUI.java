import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LogInGUI extends JFrame implements ActionListener{
	private JButton button, button1, button2, button3;
	private JPanel panel, panel1, panel2;
	private JMenu menu;
	private CardLayout cl = new CardLayout();
	
	public LogInGUI(){
		setTitle("University Record System");
		setSize(1000, 800);
		
		button = new JButton("Log In");
		button1 = new JButton("Log In as Administrator");
		panel = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		add(button, "South");
		add(button1, "Center");
		
		
		
		panel.setLayout(cl);
		//panel1.add(button3);
		panel.add(panel1);
		panel.add(panel2);
		
		
		cl.show(panel, "1");
		//button.addActionListener(this);
		//button1.addActionListener(this);
		//button2.addActionListener(this);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
	}
}
