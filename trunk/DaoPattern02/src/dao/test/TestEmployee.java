package dao.test;

import java.util.List;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.EmployeeDAO;
import dao.example.base.EmployeeDTO;

/**
 * @author Demián Gutierrez
 */
public class TestEmployee extends TestCase {

  private boolean compareDTO(EmployeeDTO employeeDTO1, EmployeeDTO employeeDTO2) {
    boolean ret = true;

    ret = ret && employeeDTO1.getId() == employeeDTO2.getId();
    ret = ret && employeeDTO1.getFrstName().equals(employeeDTO2.getFrstName());
    ret = ret && employeeDTO1.getLastName().equals(employeeDTO2.getLastName());

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testInsertLoad() throws Exception {
    ConnectionBean connectionBean;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FooFrstName111");
    employeeDTO1.setLastName("FooLastName111");
    employeeDAO.insert(employeeDTO1);
    assertEquals(employeeDTO1.getId(), 1);

    EmployeeDTO employeeDTO2 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO2.setFrstName("FooFrstName222");
    employeeDTO2.setLastName("FooLastName222");
    employeeDAO.insert(employeeDTO2);
    assertEquals(employeeDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    EmployeeDTO employeeDTOX = (EmployeeDTO) employeeDAO.loadById(1);
    assertTrue(compareDTO(employeeDTO1, employeeDTOX));
    assertNotSame(employeeDTO1, employeeDTOX);

    EmployeeDTO employeeDTOY = (EmployeeDTO) employeeDAO.loadById(2);
    assertTrue(compareDTO(employeeDTO2, employeeDTOY));
    assertNotSame(employeeDTO2, employeeDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testUpdateLoad() throws Exception {
    ConnectionBean connectionBean;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FooFrstName111");
    employeeDTO1.setLastName("FooLastName111");
    employeeDAO.insert(employeeDTO1);
    assertEquals(employeeDTO1.getId(), 1);

    EmployeeDTO employeeDTO2 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO2.setFrstName("FooFrstName222");
    employeeDTO2.setLastName("FooLastName222");
    employeeDAO.insert(employeeDTO2);
    assertEquals(employeeDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / UPDATE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    EmployeeDTO employeeDTOX = (EmployeeDTO) employeeDAO.loadById(1);
    employeeDTOX.setFrstName("FooFrstNameXXX");
    employeeDTOX.setLastName("FooLastNameXXX");
    employeeDAO.update(employeeDTOX); // Changes the 111

    EmployeeDTO employeeDTOY = (EmployeeDTO) employeeDAO.loadById(2);
    employeeDTOY.setFrstName("FooFrstNameYYY");
    employeeDTOY.setLastName("FooLastNameYYY");
    employeeDAO.update(employeeDTOY); // Changes the 222

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    EmployeeDTO employeeDTOA = (EmployeeDTO) employeeDAO.loadById(1);
    assertTrue(compareDTO(employeeDTOX, employeeDTOA));
    assertNotSame(employeeDTOX, employeeDTOA);

    EmployeeDTO employeeDTOB = (EmployeeDTO) employeeDAO.loadById(2);
    assertTrue(compareDTO(employeeDTOY, employeeDTOB));
    assertNotSame(employeeDTOY, employeeDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteLoad() throws Exception {
    ConnectionBean connectionBean;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FooFrstName111");
    employeeDTO1.setLastName("FooLastName111");
    employeeDAO.insert(employeeDTO1);
    assertEquals(employeeDTO1.getId(), 1);

    EmployeeDTO employeeDTO2 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO2.setFrstName("FooFrstName222");
    employeeDTO2.setLastName("FooLastName222");
    employeeDAO.insert(employeeDTO2);
    assertEquals(employeeDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    EmployeeDTO employeeDTOX = (EmployeeDTO) employeeDAO.loadById(1);
    employeeDAO.delete(employeeDTOX);

    EmployeeDTO employeeDTOY = (EmployeeDTO) employeeDAO.loadById(2);
    employeeDAO.delete(employeeDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    EmployeeDTO employeeDTOA = (EmployeeDTO) employeeDAO.loadById(1);
    assertNull(employeeDTOA);

    EmployeeDTO employeeDTOB = (EmployeeDTO) employeeDAO.loadById(2);
    assertNull(employeeDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCacheLoad() throws Exception {
    ConnectionBean connectionBean;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FooFrstName111");
    employeeDTO1.setLastName("FooLastName111");
    employeeDAO.insert(employeeDTO1);
    assertEquals(employeeDTO1.getId(), 1);

    EmployeeDTO employeeDTO2 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO2.setFrstName("FooFrstName222");
    employeeDTO2.setLastName("FooLastName222");
    employeeDAO.insert(employeeDTO2);
    assertEquals(employeeDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / RE-LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    EmployeeDTO employeeDTOX = (EmployeeDTO) employeeDAO.loadById(1);
    EmployeeDTO employeeDTOA = (EmployeeDTO) employeeDAO.loadById(1);
    assertTrue(compareDTO(employeeDTOX, employeeDTOA));
    assertSame(employeeDTOX, employeeDTOA);

    EmployeeDTO employeeDTOY = (EmployeeDTO) employeeDAO.loadById(2);
    EmployeeDTO employeeDTOB = (EmployeeDTO) employeeDAO.loadById(2);
    assertTrue(compareDTO(employeeDTOY, employeeDTOB));
    assertSame(employeeDTOY, employeeDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReInsert() throws Exception {
    ConnectionBean connectionBean;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FooFrstName");
    employeeDTO1.setLastName("FooLastName");
    employeeDAO.insert(employeeDTO1);
    assertEquals(employeeDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    EmployeeDTO employeeDTOX = (EmployeeDTO) employeeDAO.loadById(1);
    employeeDAO.delete(employeeDTOX);

    try {
      employeeDAO.insert(employeeDTOX);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReUpdate() throws Exception {
    ConnectionBean connectionBean;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FooFrstName");
    employeeDTO1.setLastName("FooLastName");
    employeeDAO.insert(employeeDTO1);
    assertEquals(employeeDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    EmployeeDTO employeeDTOX = (EmployeeDTO) employeeDAO.loadById(1);
    employeeDAO.delete(employeeDTOX);

    try {
      employeeDAO.update(employeeDTOX);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReDelete() throws Exception {
    ConnectionBean connectionBean;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FooFrstName");
    employeeDTO1.setLastName("FooLastName");
    employeeDAO.insert(employeeDTO1);
    assertEquals(employeeDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    EmployeeDTO employeeDTOX = (EmployeeDTO) employeeDAO.loadById(1);
    employeeDAO.delete(employeeDTOX);

    try {
      employeeDAO.delete(employeeDTOX);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCacheCrash() throws Exception {
    ConnectionBean connectionBean;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FooFrstName111");
    employeeDTO1.setLastName("FooLastName111");

    EmployeeDTO employeeDTO2 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO2.setFrstName("FooFrstName222");
    employeeDTO2.setLastName("FooLastName222");

    // ----------------------------------------
    // UPDATE NON INSERTED
    // ----------------------------------------

    try {
      employeeDAO.update(employeeDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // DELETE NON INSERTED
    // ----------------------------------------

    try {
      employeeDAO.delete(employeeDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // INSERT TAMPERED
    // ----------------------------------------

    try {
      employeeDTO1.setId(1); // TAMPERED ID
      employeeDAO.insert(employeeDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      employeeDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // UPDATE TAMPERED
    // ----------------------------------------

    try {
      employeeDTO1.setId(1); // TAMPERED ID
      employeeDAO.update(employeeDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      employeeDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DELETE TAMPERED
    // ----------------------------------------

    try {
      employeeDTO1.setId(1); // TAMPERED ID
      employeeDAO.delete(employeeDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      employeeDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DTO INSERT
    // ----------------------------------------

    employeeDAO.insert(employeeDTO1); // WORKS
    employeeDAO.insert(employeeDTO2); // WORKS

    // ----------------------------------------
    // RE-INSERT TAMPERED
    // ----------------------------------------

    try {
      employeeDTO1.setId(0); // TAMPERED ID
      employeeDAO.insert(employeeDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      employeeDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-UPDATE TAMPERED
    // ----------------------------------------

    try {
      employeeDTO1.setId(2); // TAMPERED ID
      employeeDAO.update(employeeDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      employeeDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-DELETE TAMPERED
    // ----------------------------------------

    try {
      employeeDTO1.setId(2); // TAMPERED ID
      employeeDAO.delete(employeeDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      employeeDTO1.setId(1); // UNTAMPER ID
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCount() throws Exception {
    ConnectionBean connectionBean;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FooFrstName111");
    employeeDTO1.setLastName("FooLastName111");
    employeeDAO.insert(employeeDTO1);

    EmployeeDTO employeeDTO2 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO2.setFrstName("FooFrstName222");
    employeeDTO2.setLastName("FooLastName222");
    employeeDAO.insert(employeeDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    assertEquals(2, employeeDAO.countAll());

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAll() throws Exception {
    ConnectionBean connectionBean;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FooFrstName111");
    employeeDTO1.setLastName("FooLastName111");
    employeeDAO.insert(employeeDTO1);

    EmployeeDTO employeeDTO2 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO2.setFrstName("FooFrstName222");
    employeeDTO2.setLastName("FooLastName222");
    employeeDAO.insert(employeeDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    List<IDTO> employeeList = employeeDAO.listAll();
    assertTrue(compareDTO(employeeDTO1, (EmployeeDTO) employeeList.get(0)));
    assertTrue(compareDTO(employeeDTO2, (EmployeeDTO) employeeList.get(1)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAllLimOff() throws Exception {
    ConnectionBean connectionBean;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FooFrstName111");
    employeeDTO1.setLastName("FooLastName111");
    employeeDAO.insert(employeeDTO1);

    EmployeeDTO employeeDTO2 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO2.setFrstName("FooFrstName222");
    employeeDTO2.setLastName("FooLastName222");
    employeeDAO.insert(employeeDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    List<IDTO> employeeList;

    employeeList = employeeDAO.listAll(1, 0);
    assertEquals(1, employeeList.size());
    assertTrue(compareDTO(employeeDTO1, (EmployeeDTO) employeeList.get(0)));

    employeeList = employeeDAO.listAll(1, 1);
    assertEquals(1, employeeList.size());
    assertTrue(compareDTO(employeeDTO2, (EmployeeDTO) employeeList.get(0)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }
}
