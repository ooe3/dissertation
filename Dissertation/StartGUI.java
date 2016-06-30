import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Choice;

public class StartGUI implements ActionListener{

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField textField;
	private JPanel panel, panel_1;
	private JButton btnLogIn, btnLogOut;
	String text = "ooe";
	String password = "olubunmi";
	private JTextField textField_1;

	/**
	 * Create the application.
	 */
	public StartGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.setSize(1000, 800);

		panel = new JPanel();
		frame.getContentPane().add(panel, "name_1756125214064824");
		panel.setLayout(null);

		btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(this);
		btnLogIn.setBounds(390, 310, 181, 49);
		panel.add(btnLogIn);

		passwordField = new JPasswordField();
		passwordField.setBounds(477, 252, 147, 26);
		panel.add(passwordField);

		textField = new JTextField();
		textField.setBounds(477, 193, 147, 26);
		textField.addActionListener(this);
		panel.add(textField);
		textField.setColumns(10);


		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(352, 198, 80, 16);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(352, 257, 61, 16);
		panel.add(lblNewLabel_1);

		JLabel lblLogInPage = new JLabel("Log In Page");
		lblLogInPage.setBounds(362, 68, 209, 61);
		lblLogInPage.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel.add(lblLogInPage);

		panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, "name_1756125223660446");
		panel_1.setLayout(null);

		btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(this);
		btnLogOut.setBounds(842, 6, 129, 52);
		panel_1.add(btnLogOut);

		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(6, 11, 77, 28);
		panel_1.add(lblWelcome);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(16, 99, 735, 339);
		panel_1.add(textArea);
		
		JButton btnNewButton = new JButton("Add Course");
		btnNewButton.setBounds(16, 544, 117, 29);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Remove Course");
		btnNewButton_1.setBounds(16, 675, 135, 29);
		panel_1.add(btnNewButton_1);
		
		JButton btnViewResults = new JButton("View Results");
		btnViewResults.setBounds(634, 468, 117, 29);
		panel_1.add(btnViewResults);
		
		JLabel lblToAddA = new JLabel("To add a course select from the list below");
		lblToAddA.setBounds(22, 473, 276, 16);
		panel_1.add(lblToAddA);
		
		Choice choice = new Choice();
		choice.setBounds(16, 495, 264, 27);
		panel_1.add(choice);
		
		JLabel lblEnterCourseCode = new JLabel("Enter course code below to remove course");
		lblEnterCourseCode.setBounds(16, 601, 281, 16);
		panel_1.add(lblEnterCourseCode);
		
		JLabel lblCourseCode = new JLabel("Course Code");
		lblCourseCode.setBounds(22, 635, 96, 16);
		panel_1.add(lblCourseCode);
		
		textField_1 = new JTextField();
		textField_1.setBounds(150, 629, 130, 26);
		panel_1.add(textField_1);
		textField_1.setColumns(10);

		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, "name_1756148928342669");
		
		
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ee){
		if(ee.getSource() == btnLogIn){
			if(textField.getText().equals(text)){
				textField.setText("");
				panel_1.setVisible(true);
				panel.setVisible(false);
			} else 
				JOptionPane.showMessageDialog(null, "Username incorrrect", "Incorrect Username", JOptionPane.ERROR_MESSAGE);


		} else if(ee.getSource() == btnLogOut){
			panel_1.setVisible(false);
			panel.setVisible(true);
		}


	}
}
