package other;

import static org.junit.Assert.*;
import org.junit.Test;
import main.Queries;
import main.StudentQueries;

public class StudentQueriesTest {

	@Test
	public void test() {
	StudentQueries sq = StudentQueries.getMain();
	Queries q = Queries.getQueries();
	
	Student sdt = new Student(1,1, "Chukwubuikem", "Emuwa","olubunmi_e@hotmail.coom");
	StudentDegree sd = q.getSDInfo(sdt);
	sdt.setDegree(sd);
	
	CourseDegree cd = sq.displayCourses(sdt);
	assertNotNull(cd);
	}

}
