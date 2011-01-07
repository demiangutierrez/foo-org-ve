package dao.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Demián Gutierrez
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for dao.test");
    //$JUnit-BEGIN$
    suite.addTestSuite(TestDepartment.class);
    suite.addTestSuite(TestEmployee.class);
    suite.addTestSuite(TestDepartmentEmployee.class);
    //$JUnit-END$
    return suite;
  }
}
