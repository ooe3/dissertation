import java.awt.EventQueue;


public class Run {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatabaseConnection dc = new DatabaseConnection();
					StartGUI window = new StartGUI(dc);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
