package dao.test;

import java.util.List;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;

/**
 * @author Demián Gutierrez
 */
public class TestDept extends TestCase {

  private boolean compareDTO(DeptDTO deptDTO1, DeptDTO deptDTO2) {
    boolean ret = true;

    ret = ret && deptDTO1.getId() == deptDTO2.getId();

    ret = ret && deptDTO1.getDeptAtt1().equals(deptDTO2.getDeptAtt1());
    ret = ret && deptDTO1.getDeptAtt2().equals(deptDTO2.getDeptAtt2());

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testInsertLoad() throws Exception {
    ConnectionBean connectionBean;
    DeptDAO deptDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("FooName111");
    deptDTO1.setDeptAtt2("FooDescription111");
    deptDAO.insert(deptDTO1);
    assertEquals(deptDTO1.getId(), 1);

    DeptDTO deptDTO2 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO2.setDeptAtt1("FooName222");
    deptDTO2.setDeptAtt2("FooDescription222");
    deptDAO.insert(deptDTO2);
    assertEquals(deptDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    DeptDTO deptDTOX = (DeptDTO) deptDAO.loadById(1);
    assertTrue(compareDTO(deptDTO1, deptDTOX));
    assertNotSame(deptDTO1, deptDTOX);

    DeptDTO deptDTOY = (DeptDTO) deptDAO.loadById(2);
    assertTrue(compareDTO(deptDTO2, deptDTOY));
    assertNotSame(deptDTO2, deptDTOY);

    ConnectionFactory.closeConnection(connectionBean);
  }

  // --------------------------------------------------------------------------------

  public void testUpdateLoad() throws Exception {
    ConnectionBean connectionBean;
    DeptDAO deptDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("FooName111");
    deptDTO1.setDeptAtt2("FooDescription111");
    deptDAO.insert(deptDTO1);
    assertEquals(deptDTO1.getId(), 1);

    DeptDTO deptDTO2 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO2.setDeptAtt1("FooName222");
    deptDTO2.setDeptAtt2("FooDescription222");
    deptDAO.insert(deptDTO2);
    assertEquals(deptDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // LOAD / UPDATE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    DeptDTO deptDTOX = (DeptDTO) deptDAO.loadById(1);
    deptDTOX.setDeptAtt1("FooNameXXX");
    deptDTOX.setDeptAtt2("FooDescriptionXXX");
    deptDAO.update(deptDTOX); // Changes the 111

    DeptDTO deptDTOY = (DeptDTO) deptDAO.loadById(2);
    deptDTOY.setDeptAtt1("FooNameYYY");
    deptDTOY.setDeptAtt2("FooDescriptionYYY");
    deptDAO.update(deptDTOY); // Changes the 222

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    DeptDTO deptDTOA = (DeptDTO) deptDAO.loadById(1);
    assertTrue(compareDTO(deptDTOX, deptDTOA));
    assertNotSame(deptDTOX, deptDTOA);

    DeptDTO deptDTOB = (DeptDTO) deptDAO.loadById(2);
    assertTrue(compareDTO(deptDTOY, deptDTOB));
    assertNotSame(deptDTOY, deptDTOB);

    ConnectionFactory.closeConnection(connectionBean);
  }

  // --------------------------------------------------------------------------------

  public void testDeleteLoad() throws Exception {
    ConnectionBean connectionBean;
    DeptDAO deptDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("FooName111");
    deptDTO1.setDeptAtt2("FooDescription111");
    deptDAO.insert(deptDTO1);
    assertEquals(deptDTO1.getId(), 1);

    DeptDTO deptDTO2 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO2.setDeptAtt1("FooName222");
    deptDTO2.setDeptAtt2("FooDescription222");
    deptDAO.insert(deptDTO2);
    assertEquals(deptDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // LOAD / DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    DeptDTO deptDTOX = (DeptDTO) deptDAO.loadById(1);
    deptDAO.delete(deptDTOX);

    DeptDTO deptDTOY = (DeptDTO) deptDAO.loadById(2);
    deptDAO.delete(deptDTOY);

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    DeptDTO deptDTOA = (DeptDTO) deptDAO.loadById(1);
    assertNull(deptDTOA);

    DeptDTO deptDTOB = (DeptDTO) deptDAO.loadById(2);
    assertNull(deptDTOB);

    ConnectionFactory.closeConnection(connectionBean);
  }

  // --------------------------------------------------------------------------------

  public void testCacheLoad() throws Exception {
    ConnectionBean connectionBean;
    DeptDAO deptDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("FooName111");
    deptDTO1.setDeptAtt2("FooDescription111");
    deptDAO.insert(deptDTO1);
    assertEquals(deptDTO1.getId(), 1);

    DeptDTO deptDTO2 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO2.setDeptAtt1("FooName222");
    deptDTO2.setDeptAtt2("FooDescription222");
    deptDAO.insert(deptDTO2);
    assertEquals(deptDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // LOAD / RE-LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    DeptDTO deptDTOX = (DeptDTO) deptDAO.loadById(1);
    DeptDTO deptDTOA = (DeptDTO) deptDAO.loadById(1);
    assertTrue(compareDTO(deptDTOX, deptDTOA));
    assertSame(deptDTOX, deptDTOA);

    DeptDTO deptDTOY = (DeptDTO) deptDAO.loadById(2);
    DeptDTO deptDTOB = (DeptDTO) deptDAO.loadById(2);
    assertTrue(compareDTO(deptDTOY, deptDTOB));
    assertSame(deptDTOY, deptDTOB);

    ConnectionFactory.closeConnection(connectionBean);
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReInsert() throws Exception {
    ConnectionBean connectionBean;
    DeptDAO deptDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("FooName");
    deptDTO1.setDeptAtt2("FooDescription");
    deptDAO.insert(deptDTO1);
    assertEquals(deptDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    DeptDTO deptDTOX = (DeptDTO) deptDAO.loadById(1);
    deptDAO.delete(deptDTOX);

    try {
      deptDAO.insert(deptDTOX);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    ConnectionFactory.closeConnection(connectionBean);
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReUpdate() throws Exception {
    ConnectionBean connectionBean;
    DeptDAO deptDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("FooName");
    deptDTO1.setDeptAtt2("FooDescription");
    deptDAO.insert(deptDTO1);
    assertEquals(deptDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    DeptDTO deptDTOX = (DeptDTO) deptDAO.loadById(1);
    deptDAO.delete(deptDTOX);

    try {
      deptDAO.update(deptDTOX);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    ConnectionFactory.closeConnection(connectionBean);
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReDelete() throws Exception {
    ConnectionBean connectionBean;
    DeptDAO deptDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("FooName");
    deptDTO1.setDeptAtt2("FooDescription");
    deptDAO.insert(deptDTO1);
    assertEquals(deptDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // LOAD / DELETE / RE-DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    DeptDTO deptDTOX = (DeptDTO) deptDAO.loadById(1);
    deptDAO.delete(deptDTOX);

    try {
      deptDAO.delete(deptDTOX);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    ConnectionFactory.closeConnection(connectionBean);
  }

  // --------------------------------------------------------------------------------

  public void testCacheCrash() throws Exception {
    ConnectionBean connectionBean;
    DeptDAO deptDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("FooName111");
    deptDTO1.setDeptAtt2("FooDescription111");

    DeptDTO deptDTO2 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO2.setDeptAtt1("FooName222");
    deptDTO2.setDeptAtt2("FooDescription222");

    // ----------------------------------------
    // UPDATE NON INSERTED
    // ----------------------------------------

    try {
      deptDAO.update(deptDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // DELETE NON INSERTED
    // ----------------------------------------

    try {
      deptDAO.delete(deptDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // INSERT TAMPERED
    // ----------------------------------------

    try {
      deptDTO1.setId(1); // TAMPERED ID
      deptDAO.insert(deptDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      deptDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // UPDATE TAMPERED
    // ----------------------------------------

    try {
      deptDTO1.setId(1); // TAMPERED ID
      deptDAO.update(deptDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      deptDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DELETE TAMPERED
    // ----------------------------------------

    try {
      deptDTO1.setId(1); // TAMPERED ID
      deptDAO.delete(deptDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      deptDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DTO INSERT
    // ----------------------------------------

    deptDAO.insert(deptDTO1); // WORKS
    deptDAO.insert(deptDTO2); // WORKS

    // ----------------------------------------
    // RE-INSERT TAMPERED
    // ----------------------------------------

    try {
      deptDTO1.setId(0); // TAMPERED ID
      deptDAO.insert(deptDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      deptDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-UPDATE TAMPERED
    // ----------------------------------------

    try {
      deptDTO1.setId(2); // TAMPERED ID
      deptDAO.update(deptDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      deptDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-DELETE TAMPERED
    // ----------------------------------------

    try {
      deptDTO1.setId(2); // TAMPERED ID
      deptDAO.delete(deptDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      deptDTO1.setId(1); // UNTAMPER ID
    }

    ConnectionFactory.closeConnection(connectionBean);
  }

  // --------------------------------------------------------------------------------

  public void testCount() throws Exception {
    ConnectionBean connectionBean;
    DeptDAO deptDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("FooName111");
    deptDTO1.setDeptAtt2("FooDescription111");
    deptDAO.insert(deptDTO1);

    DeptDTO deptDTO2 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO2.setDeptAtt1("FooName222");
    deptDTO2.setDeptAtt2("FooDescription222");
    deptDAO.insert(deptDTO2);

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    assertEquals(2, deptDAO.countAll());

    ConnectionFactory.closeConnection(connectionBean);
  }

  // --------------------------------------------------------------------------------

  public void testListAll() throws Exception {
    ConnectionBean connectionBean;
    DeptDAO deptDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("FooName111");
    deptDTO1.setDeptAtt2("FooDescription111");
    deptDAO.insert(deptDTO1);

    DeptDTO deptDTO2 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO2.setDeptAtt1("FooName222");
    deptDTO2.setDeptAtt2("FooDescription222");
    deptDAO.insert(deptDTO2);

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    List<IDTO> deptList = deptDAO.listAll();
    assertTrue(compareDTO(deptDTO1, (DeptDTO) deptList.get(0)));
    assertTrue(compareDTO(deptDTO2, (DeptDTO) deptList.get(1)));

    ConnectionFactory.closeConnection(connectionBean);
  }

  // --------------------------------------------------------------------------------

  public void testListAllLimOff() throws Exception {
    ConnectionBean connectionBean;
    DeptDAO deptDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("FooName111");
    deptDTO1.setDeptAtt2("FooDescription111");
    deptDAO.insert(deptDTO1);

    DeptDTO deptDTO2 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO2.setDeptAtt1("FooName222");
    deptDTO2.setDeptAtt2("FooDescription222");
    deptDAO.insert(deptDTO2);

    ConnectionFactory.closeConnection(connectionBean);

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    List<IDTO> deptList;

    deptList = deptDAO.listAll(1, 0);
    assertEquals(1, deptList.size());
    assertTrue(compareDTO(deptDTO1, (DeptDTO) deptList.get(0)));

    deptList = deptDAO.listAll(1, 1);
    assertEquals(1, deptList.size());
    assertTrue(compareDTO(deptDTO2, (DeptDTO) deptList.get(0)));

    ConnectionFactory.closeConnection(connectionBean);
  }
}
