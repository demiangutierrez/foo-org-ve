package dao.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  /**
   * @author Demián Gutierrez
   */
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
