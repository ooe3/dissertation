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
	Degree d = new Degree(1,"Information Technology", "MSc", sc);
//	Users us = q.LogIn("2600422a", "2600422a");
//	assertNull(us);
	
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
	}
	

}
