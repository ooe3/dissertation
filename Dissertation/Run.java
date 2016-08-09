import java.awt.EventQueue;


public class Run {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatabaseConnection dc = new DatabaseConnection();
					Queries q = new Queries(dc);
					StartGUI window = new StartGUI(q);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
