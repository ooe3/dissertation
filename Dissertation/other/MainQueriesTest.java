package other;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import main.MainQueries;
//Test for the MainQueries
public class MainQueriesTest {
	
	@Test
	public void test() {
		MainQueries mq = MainQueries.getMain();
		School sc = new School("Engineering");

		Course c = mq.getCourses(sc);
		assertNotNull(c);

		Degree dg = mq.displayDegree(sc);
		assertNotNull(dg);

		String s1 = mq.insertDegree("Electronics & Electrical Engineering", "MSc", sc);
		assertEquals("Error", s1);

		String s2 = mq.insertCourse("Electrical Energy Systems", 10, 70, 30);
		assertEquals("Error", s2);

		CourseDegree cd = mq.getInserted(sc);
		assertNotNull(cd);

	}

}
