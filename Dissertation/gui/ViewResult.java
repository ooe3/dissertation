package gui;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import main.*;
import controllers.*;
import javax.swing.JScrollBar;

public class ViewResult extends JFrame{
	Users us;
	Queries q = Queries.getQueries();
	String selected3;
	Choice choice, choice_1;
	final String choice1 = "View a particular students result", choice2 = "View overall results of a particular degree", 
			choice3 = "View overall results for a particular course", choice4 = "View overall results for the school";
	public ViewResult(){
		initialize();
	}
	
	public void initialize(){
		us = q.getUser();
		setTitle("University Record System");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(1000, 800);

		JPanel panel = new JPanel();
		getContentPane().add(panel, "name_1756148928342669");
		panel.setLayout(null);
		
		JLabel lblSelectTheOption = new JLabel("Select the option you want below");
		lblSelectTheOption.setBounds(22, 6, 230, 16);
		panel.add(lblSelectTheOption);
		
		choice = new Choice();
		choice.setBounds(22, 28, 306, 27);
		choice.add("(select option)");
		choice.add(choice1);
		choice.add(choice2);
		choice.add(choice3);
		choice.add(choice4);
		choice.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				selected3 = choice.getSelectedItem();
				if(selected3.equals(choice1)){
					
				}else if(selected3.equals(choice2)){
					
				}else if(selected3.equals(choice3)){
					
				}else if(selected3.equals(choice4)){
					
				}
			}
			});
		panel.add(choice);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(22, 61, 216, 16);
		lblNewLabel.setVisible(false);
		panel.add(lblNewLabel);
		
		choice_1 = new Choice();
		choice_1.setBounds(22, 86, 230, 27);
		choice_1.setVisible(false);
		panel.add(choice_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(22, 148, 972, 555);
		textArea.setEditable(false);
		textArea.setVisible(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(22, 148, 972, 555);
		panel.add(scrollPane, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setActionCommand("LogOut");
		mntmLogOut.addActionListener(new ViewListener(this));

		//closes the whole program
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setActionCommand("Exit");
		mntmExit.addActionListener(new ViewListener(this));

		JMenuItem mntmResults = new JMenuItem("Add Result");
		mntmResults.setActionCommand("Add");
		mntmResults.addActionListener(new ViewListener(this));
		//admin home
		JMenuItem mntmAdminHome = new JMenuItem("Home");
		mntmAdminHome.setActionCommand("Home Menu");
		mntmAdminHome.addActionListener(new ViewListener(this));

		//change password menu item
		JMenuItem mntmPassword = new JMenuItem("Change Password");
		mntmPassword.setActionCommand("Change Password");
		mntmPassword.addActionListener(new ViewListener(this));
		
		JMenuItem mntmView = new JMenuItem("View General Results");
		mntmView.setActionCommand("View");
		mntmView.addActionListener(new ViewListener(this));
		
		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.setActionCommand("Refresh");
		mntmRefresh.addActionListener(new ViewListener(this));

		JMenu admin = new JMenu(us.getFirstName() + " " + us.getLastName());
		admin.add(mntmAdminHome);
		admin.add(mntmResults);
		admin.add(mntmView);
		admin.add(mntmPassword);
		admin.add(mntmRefresh);
		admin.add(mntmLogOut);
		admin.add(mntmExit);
		menuBar.add(admin);
		
	}
}
