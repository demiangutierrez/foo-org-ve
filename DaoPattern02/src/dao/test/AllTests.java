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
    suite.addTestSuite(TestProf.class);
    suite.addTestSuite(TestDept.class);
    suite.addTestSuite(TestDeptProf.class);
    suite.addTestSuite(TestPublication.class);
    suite.addTestSuite(TestBook.class);
    //$JUnit-END$
    return suite;
  }
}
