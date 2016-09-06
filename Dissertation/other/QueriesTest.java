package other;

import static org.junit.Assert.*;
import org.junit.Test;
import main.MainQueries;
import main.Queries;

public class QueriesTest {

	@Test
	public void test() {
	Queries q = Queries.getQueries();
	School sc = new School("Engineering");
	Student sdt = new Student(1,1, "Chukwubuikem", "Emuwa","olubunmi_e@hotmail.coom");
	
	String s = q.checkAddress("London");
	assertEquals("Exists", s);
	
	String s1 = q.checkEmail("www.bbc.com");
	assertEquals("Exists", s1);
	
	String s2 = q.checkString("For&//");
	assertEquals("Exists", s2);
	
	String s3 = q.checkUsername("Obi");
	assertEquals("Exists", s3);
	
	String s4 = q.checkFirstName("Tom Frank");
	assertEquals("Exists", s4);
	
	String s5 = q.checkFirstName("Tom&Frank");
	assertEquals("Exists", s5);
	
	Degree dg = q.getInfo(1);
	assertNotNull(dg);
	
	Users us = q.LogIn("2200422e", "2200422e");
	assertNotNull(us);
	
	Student st = q.getAll(sc);
	assertNotNull(st);
	
	School sh = q.getSchoolInfo("Engineering");
	assertNotNull(sh);
	
	School sh1 = q.getSchoolInfo("Football");
	assertNull(sh1);
	
	StudentDegree sd = q.getSDInfo(sdt);
	assertNotNull(sd);
	
	CourseResult cr = q.getDetails(sdt);
	assertNotNull(cr);
	
	Course c = q.getCourseDetails("Advance");
	assertNull(c);
	
	
	}
	

}
