package dao.test;

import java.util.List;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DepartmentDAO;
import dao.example.base.DepartmentDTO;

/**
 * @author Demián Gutierrez
 */
public class TestDepartment extends TestCase {

  private boolean compareDTO(DepartmentDTO departmentDTO1, DepartmentDTO departmentDTO2) {
    boolean ret = true;

    ret = ret && departmentDTO1.getId() == departmentDTO2.getId();
    ret = ret && departmentDTO1.getName().equals(departmentDTO2.getName());
    ret = ret && departmentDTO1.getDescription().equals(departmentDTO2.getDescription());

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testInsertLoad() throws Exception {
    ConnectionBean connectionBean;
    DepartmentDAO departmentDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName111");
    departmentDTO1.setDescription("FooDescription111");
    departmentDAO.insert(departmentDTO1);
    assertEquals(departmentDTO1.getId(), 1);

    DepartmentDTO departmentDTO2 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO2.setName("FooName222");
    departmentDTO2.setDescription("FooDescription222");
    departmentDAO.insert(departmentDTO2);
    assertEquals(departmentDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    DepartmentDTO departmentDTOX = (DepartmentDTO) departmentDAO.loadById(1);
    assertTrue(compareDTO(departmentDTO1, departmentDTOX));
    assertNotSame(departmentDTO1, departmentDTOX);

    DepartmentDTO departmentDTOY = (DepartmentDTO) departmentDAO.loadById(2);
    assertTrue(compareDTO(departmentDTO2, departmentDTOY));
    assertNotSame(departmentDTO2, departmentDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testUpdateLoad() throws Exception {
    ConnectionBean connectionBean;
    DepartmentDAO departmentDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName111");
    departmentDTO1.setDescription("FooDescription111");
    departmentDAO.insert(departmentDTO1);
    assertEquals(departmentDTO1.getId(), 1);

    DepartmentDTO departmentDTO2 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO2.setName("FooName222");
    departmentDTO2.setDescription("FooDescription222");
    departmentDAO.insert(departmentDTO2);
    assertEquals(departmentDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / UPDATE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    DepartmentDTO departmentDTOX = (DepartmentDTO) departmentDAO.loadById(1);
    departmentDTOX.setName("FooNameXXX");
    departmentDTOX.setDescription("FooDescriptionXXX");
    departmentDAO.update(departmentDTOX); // Changes the 111

    DepartmentDTO departmentDTOY = (DepartmentDTO) departmentDAO.loadById(2);
    departmentDTOY.setName("FooNameYYY");
    departmentDTOY.setDescription("FooDescriptionYYY");
    departmentDAO.update(departmentDTOY); // Changes the 222

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    DepartmentDTO departmentDTOA = (DepartmentDTO) departmentDAO.loadById(1);
    assertTrue(compareDTO(departmentDTOX, departmentDTOA));
    assertNotSame(departmentDTOX, departmentDTOA);

    DepartmentDTO departmentDTOB = (DepartmentDTO) departmentDAO.loadById(2);
    assertTrue(compareDTO(departmentDTOY, departmentDTOB));
    assertNotSame(departmentDTOY, departmentDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteLoad() throws Exception {
    ConnectionBean connectionBean;
    DepartmentDAO departmentDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName111");
    departmentDTO1.setDescription("FooDescription111");
    departmentDAO.insert(departmentDTO1);
    assertEquals(departmentDTO1.getId(), 1);

    DepartmentDTO departmentDTO2 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO2.setName("FooName222");
    departmentDTO2.setDescription("FooDescription222");
    departmentDAO.insert(departmentDTO2);
    assertEquals(departmentDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    DepartmentDTO departmentDTOX = (DepartmentDTO) departmentDAO.loadById(1);
    departmentDAO.delete(departmentDTOX);

    DepartmentDTO departmentDTOY = (DepartmentDTO) departmentDAO.loadById(2);
    departmentDAO.delete(departmentDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    DepartmentDTO departmentDTOA = (DepartmentDTO) departmentDAO.loadById(1);
    assertNull(departmentDTOA);

    DepartmentDTO departmentDTOB = (DepartmentDTO) departmentDAO.loadById(2);
    assertNull(departmentDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCacheLoad() throws Exception {
    ConnectionBean connectionBean;
    DepartmentDAO departmentDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName111");
    departmentDTO1.setDescription("FooDescription111");
    departmentDAO.insert(departmentDTO1);
    assertEquals(departmentDTO1.getId(), 1);

    DepartmentDTO departmentDTO2 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO2.setName("FooName222");
    departmentDTO2.setDescription("FooDescription222");
    departmentDAO.insert(departmentDTO2);
    assertEquals(departmentDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / RE-LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    DepartmentDTO departmentDTOX = (DepartmentDTO) departmentDAO.loadById(1);
    DepartmentDTO departmentDTOA = (DepartmentDTO) departmentDAO.loadById(1);
    assertTrue(compareDTO(departmentDTOX, departmentDTOA));
    assertSame(departmentDTOX, departmentDTOA);

    DepartmentDTO departmentDTOY = (DepartmentDTO) departmentDAO.loadById(2);
    DepartmentDTO departmentDTOB = (DepartmentDTO) departmentDAO.loadById(2);
    assertTrue(compareDTO(departmentDTOY, departmentDTOB));
    assertSame(departmentDTOY, departmentDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReInsert() throws Exception {
    ConnectionBean connectionBean;
    DepartmentDAO departmentDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName");
    departmentDTO1.setDescription("FooDescription");
    departmentDAO.insert(departmentDTO1);
    assertEquals(departmentDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    DepartmentDTO departmentDTOX = (DepartmentDTO) departmentDAO.loadById(1);
    departmentDAO.delete(departmentDTOX);

    try {
      departmentDAO.insert(departmentDTOX);
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
    DepartmentDAO departmentDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName");
    departmentDTO1.setDescription("FooDescription");
    departmentDAO.insert(departmentDTO1);
    assertEquals(departmentDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    DepartmentDTO departmentDTOX = (DepartmentDTO) departmentDAO.loadById(1);
    departmentDAO.delete(departmentDTOX);

    try {
      departmentDAO.update(departmentDTOX);
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
    DepartmentDAO departmentDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName");
    departmentDTO1.setDescription("FooDescription");
    departmentDAO.insert(departmentDTO1);
    assertEquals(departmentDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    DepartmentDTO departmentDTOX = (DepartmentDTO) departmentDAO.loadById(1);
    departmentDAO.delete(departmentDTOX);

    try {
      departmentDAO.delete(departmentDTOX);
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
    DepartmentDAO departmentDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName111");
    departmentDTO1.setDescription("FooDescription111");

    DepartmentDTO departmentDTO2 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO2.setName("FooName222");
    departmentDTO2.setDescription("FooDescription222");

    // ----------------------------------------
    // UPDATE NON INSERTED
    // ----------------------------------------

    try {
      departmentDAO.update(departmentDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // DELETE NON INSERTED
    // ----------------------------------------

    try {
      departmentDAO.delete(departmentDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // INSERT TAMPERED
    // ----------------------------------------

    try {
      departmentDTO1.setId(1); // TAMPERED ID
      departmentDAO.insert(departmentDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      departmentDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // UPDATE TAMPERED
    // ----------------------------------------

    try {
      departmentDTO1.setId(1); // TAMPERED ID
      departmentDAO.update(departmentDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      departmentDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DELETE TAMPERED
    // ----------------------------------------

    try {
      departmentDTO1.setId(1); // TAMPERED ID
      departmentDAO.delete(departmentDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      departmentDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DTO INSERT
    // ----------------------------------------

    departmentDAO.insert(departmentDTO1); // WORKS
    departmentDAO.insert(departmentDTO2); // WORKS

    // ----------------------------------------
    // RE-INSERT TAMPERED
    // ----------------------------------------

    try {
      departmentDTO1.setId(0); // TAMPERED ID
      departmentDAO.insert(departmentDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      departmentDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-UPDATE TAMPERED
    // ----------------------------------------

    try {
      departmentDTO1.setId(2); // TAMPERED ID
      departmentDAO.update(departmentDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      departmentDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-DELETE TAMPERED
    // ----------------------------------------

    try {
      departmentDTO1.setId(2); // TAMPERED ID
      departmentDAO.delete(departmentDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      departmentDTO1.setId(1); // UNTAMPER ID
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCount() throws Exception {
    ConnectionBean connectionBean;
    DepartmentDAO departmentDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName111");
    departmentDTO1.setDescription("FooDescription111");
    departmentDAO.insert(departmentDTO1);

    DepartmentDTO departmentDTO2 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO2.setName("FooName222");
    departmentDTO2.setDescription("FooDescription222");
    departmentDAO.insert(departmentDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    assertEquals(2, departmentDAO.countAll());

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAll() throws Exception {
    ConnectionBean connectionBean;
    DepartmentDAO departmentDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName111");
    departmentDTO1.setDescription("FooDescription111");
    departmentDAO.insert(departmentDTO1);

    DepartmentDTO departmentDTO2 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO2.setName("FooName222");
    departmentDTO2.setDescription("FooDescription222");
    departmentDAO.insert(departmentDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    List<IDTO> departmentList = departmentDAO.listAll();
    assertTrue(compareDTO(departmentDTO1, (DepartmentDTO) departmentList.get(0)));
    assertTrue(compareDTO(departmentDTO2, (DepartmentDTO) departmentList.get(1)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAllLimOff() throws Exception {
    ConnectionBean connectionBean;
    DepartmentDAO departmentDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName111");
    departmentDTO1.setDescription("FooDescription111");
    departmentDAO.insert(departmentDTO1);

    DepartmentDTO departmentDTO2 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO2.setName("FooName222");
    departmentDTO2.setDescription("FooDescription222");
    departmentDAO.insert(departmentDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    List<IDTO> departmentList;

    departmentList = departmentDAO.listAll(1, 0);
    assertEquals(1, departmentList.size());
    assertTrue(compareDTO(departmentDTO1, (DepartmentDTO) departmentList.get(0)));

    departmentList = departmentDAO.listAll(1, 1);
    assertEquals(1, departmentList.size());
    assertTrue(compareDTO(departmentDTO2, (DepartmentDTO) departmentList.get(0)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }
}
