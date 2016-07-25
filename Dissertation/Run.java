import java.awt.EventQueue;


public class Run {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatabaseConnection dc = new DatabaseConnection();
					Users u = new Users();
					Student st = new Student();
					Admin ad = new Admin();
					Course course = new Course();
					Degree degree = new Degree();
					School school = new School();
					CourseDegree cd = new CourseDegree();
					StudentDegree sd = new StudentDegree();
					CourseResult cr = new CourseResult();
					Queries q = new Queries(dc, u, st, ad, course, school, degree, cr, sd, cd);
					StartGUI window = new StartGUI(q, u, st, ad, course, school, degree, cr, sd, cd);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
