package other;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
//Test suite to execute all test classes
@RunWith(Suite.class)
@SuiteClasses({ AddQueriesTest.class, MainQueriesTest.class, QueriesTest.class, StudentQueriesTest.class,
	ViewResultTest.class })
public class AllTests {

}
