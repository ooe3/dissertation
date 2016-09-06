package other;

import static org.junit.Assert.*;
import org.junit.Test;
import main.ViewResultQueries;

public class ViewResultTest {

	@Test
	public void test() {
	
		ViewResultQueries vrq = ViewResultQueries.getMain();
		School sc = new School("Football");
		
		StudentDegree sdt = vrq.getDegreeInfo(1);
		assertNotNull(sdt);
		
		Student st = vrq.getInfo(1);
		assertNotNull(st);
		
		CourseResult cr = vrq.getCourses("Programming");
		assertNotNull(cr);
		
		StudentDegree sd = vrq.getOveralSchool(sc);
		assertNull(sd);
		
		
	}

}
