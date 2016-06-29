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

public class StartGUI {

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
		try {
			StartGUI window = new StartGUI();
			window.frame.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
	});
	}

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
	
	JPanel panel = new JPanel();
	frame.getContentPane().add(panel, BorderLayout.CENTER);
	panel.setLayout(null);
	
	JButton btnLogIn = new JButton("Log In");
	btnLogIn.setBounds(91, 157, 117, 29);
	panel.add(btnLogIn);
	
	JButton btnLogInAs = new JButton("Log In As Admin");
	btnLogInAs.setBounds(220, 157, 141, 29);
	panel.add(btnLogInAs);
	
	passwordField = new JPasswordField();
	passwordField.setBounds(198, 119, 147, 26);
	panel.add(passwordField);
	
	textField = new JTextField();
	textField.setBounds(198, 84, 147, 26);
	panel.add(textField);
	textField.setColumns(10);
	
	JLabel lblNewLabel = new JLabel("Username");
	lblNewLabel.setBounds(80, 89, 80, 16);
	panel.add(lblNewLabel);
	
	JLabel lblNewLabel_1 = new JLabel("Password");
	lblNewLabel_1.setBounds(80, 124, 61, 16);
	panel.add(lblNewLabel_1);
	
	JLabel lblLogInPage = new JLabel("Log In Page");
	lblLogInPage.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
	lblLogInPage.setBounds(133, 42, 117, 30);
	panel.add(lblLogInPage);
	}
}
