package other;
import main.*;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Test;

public class AddQueriesTest {
	
	@Test
	public void test() {
	AddQueries aq = AddQueries.getMain();
	try {
	Connection conn = aq.testConn();
	}
	catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	
	//checks to see if the total is 18. Math.round rounds it to the lowest or highest
	int check = aq.calculateScore("B1", "A4", 30, 70);//B1 is 17, A4 is 19
	assertEquals(18, check);
	//checks to see if 16 is represented as B2
	int score = aq.getMark("B2");
	assertEquals(16, score);
	//checkMark only accepts bands from A1-H. Returns "Error" when otherwise
	String checkMark = aq.checkMark("20");
	assertEquals("Error", checkMark);
	//checks to see if there's a student with that as a firstname and lastname
	Student st = aq.getSelected("Chukwubuikem", "Emuwa");
	assertNotNull(st);
	//checks to see if theres a student with a course in the system
	CourseResult cr = aq.getDetails(1);
	assertNotNull(cr);
	
	//check to see if there's an course called Programming
	Course cs = aq.getCourseDetails("Programming");
	assertNotNull(cs);
	
	}

}
