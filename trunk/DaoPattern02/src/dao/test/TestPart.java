package dao.test;

import java.util.List;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.PartDAO;
import dao.example.base.PartDTO;

/**
 * @author Demián Gutierrez
 */
public class TestPart extends TestCase {

  private boolean compareDTO(PartDTO partDTO1, PartDTO partDTO2) {
    boolean ret = true;

    ret = ret && partDTO1.getId() == partDTO2.getId();
    ret = ret && partDTO1.getManufacturer().equals(partDTO2.getManufacturer());
    ret = ret && partDTO1.getNumber().equals(partDTO2.getNumber());
    ret = ret && partDTO1.getDescription().equals(partDTO2.getDescription());

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testInsertLoad() throws Exception {
    ConnectionBean connectionBean;
    PartDAO partDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    partDAO.createTable();

    PartDTO partDTO1 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");
    partDAO.insert(partDTO1);
    assertEquals(partDTO1.getId(), 1);

    PartDTO partDTO2 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO2.setManufacturer("FooManufacturer222");
    partDTO2.setNumber("FooNumber222");
    partDTO2.setDescription("FooDescription222");
    partDAO.insert(partDTO2);
    assertEquals(partDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    PartDTO partDTOX = (PartDTO) partDAO.loadById(1);
    assertTrue(compareDTO(partDTO1, partDTOX));
    assertNotSame(partDTO1, partDTOX);

    PartDTO partDTOY = (PartDTO) partDAO.loadById(2);
    assertTrue(compareDTO(partDTO2, partDTOY));
    assertNotSame(partDTO2, partDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testUpdateLoad() throws Exception {
    ConnectionBean connectionBean;
    PartDAO partDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    partDAO.createTable();

    PartDTO partDTO1 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");
    partDAO.insert(partDTO1);
    assertEquals(partDTO1.getId(), 1);

    PartDTO partDTO2 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO2.setManufacturer("FooManufacturer222");
    partDTO2.setNumber("FooNumber222");
    partDTO2.setDescription("FooDescription222");
    partDAO.insert(partDTO2);
    assertEquals(partDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / UPDATE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    PartDTO partDTOX = (PartDTO) partDAO.loadById(1);
    partDTOX.setManufacturer("FooManufacturerXXX");
    partDTOX.setNumber("FooNumberXXX");
    partDTOX.setDescription("FooDescriptionXXX");
    partDAO.update(partDTOX); // Changes the 111

    PartDTO partDTOY = (PartDTO) partDAO.loadById(2);
    partDTOY.setManufacturer("FooManufacturerYYY");
    partDTOY.setNumber("FooNumberYYY");
    partDTOY.setDescription("FooDescriptionYYY");
    partDAO.update(partDTOY); // Changes the 222

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    PartDTO partDTOA = (PartDTO) partDAO.loadById(1);
    assertTrue(compareDTO(partDTOX, partDTOA));
    assertNotSame(partDTOX, partDTOA);

    PartDTO partDTOB = (PartDTO) partDAO.loadById(2);
    assertTrue(compareDTO(partDTOY, partDTOB));
    assertNotSame(partDTOY, partDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteLoad() throws Exception {
    ConnectionBean connectionBean;
    PartDAO partDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    partDAO.createTable();

    PartDTO partDTO1 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");
    partDAO.insert(partDTO1);
    assertEquals(partDTO1.getId(), 1);

    PartDTO partDTO2 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO2.setManufacturer("FooManufacturer222");
    partDTO2.setNumber("FooNumber222");
    partDTO2.setDescription("FooDescription222");
    partDAO.insert(partDTO2);
    assertEquals(partDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    PartDTO partDTOX = (PartDTO) partDAO.loadById(1);
    partDAO.delete(partDTOX);

    PartDTO partDTOY = (PartDTO) partDAO.loadById(2);
    partDAO.delete(partDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    PartDTO partDTOA = (PartDTO) partDAO.loadById(1);
    assertNull(partDTOA);

    PartDTO partDTOB = (PartDTO) partDAO.loadById(2);
    assertNull(partDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCacheLoad() throws Exception {
    ConnectionBean connectionBean;
    PartDAO partDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    partDAO.createTable();

    PartDTO partDTO1 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");
    partDAO.insert(partDTO1);
    assertEquals(partDTO1.getId(), 1);

    PartDTO partDTO2 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO2.setManufacturer("FooManufacturer222");
    partDTO2.setNumber("FooNumber222");
    partDTO2.setDescription("FooDescription222");
    partDAO.insert(partDTO2);
    assertEquals(partDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / RE-LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    PartDTO partDTOX = (PartDTO) partDAO.loadById(1);
    PartDTO partDTOA = (PartDTO) partDAO.loadById(1);
    assertTrue(compareDTO(partDTOX, partDTOA));
    assertSame(partDTOX, partDTOA);

    PartDTO partDTOY = (PartDTO) partDAO.loadById(2);
    PartDTO partDTOB = (PartDTO) partDAO.loadById(2);
    assertTrue(compareDTO(partDTOY, partDTOB));
    assertSame(partDTOY, partDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReInsert() throws Exception {
    ConnectionBean connectionBean;
    PartDAO partDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    partDAO.createTable();

    PartDTO partDTO1 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");
    partDAO.insert(partDTO1);
    assertEquals(partDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    PartDTO partDTOX = (PartDTO) partDAO.loadById(1);
    partDAO.delete(partDTOX);

    try {
      partDAO.insert(partDTOX);
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
    PartDAO partDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    partDAO.createTable();

    PartDTO partDTO1 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");
    partDAO.insert(partDTO1);
    assertEquals(partDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    PartDTO partDTOX = (PartDTO) partDAO.loadById(1);
    partDAO.delete(partDTOX);

    try {
      partDAO.update(partDTOX);
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
    PartDAO partDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    partDAO.createTable();

    PartDTO partDTO1 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");
    partDAO.insert(partDTO1);
    assertEquals(partDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    PartDTO partDTOX = (PartDTO) partDAO.loadById(1);
    partDAO.delete(partDTOX);

    try {
      partDAO.delete(partDTOX);
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
    PartDAO partDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    partDAO.createTable();

    PartDTO partDTO1 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");

    PartDTO partDTO2 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");

    // ----------------------------------------
    // UPDATE NON INSERTED
    // ----------------------------------------

    try {
      partDAO.update(partDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // DELETE NON INSERTED
    // ----------------------------------------

    try {
      partDAO.delete(partDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // INSERT TAMPERED
    // ----------------------------------------

    try {
      partDTO1.setId(1); // TAMPERED ID
      partDAO.insert(partDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      partDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // UPDATE TAMPERED
    // ----------------------------------------

    try {
      partDTO1.setId(1); // TAMPERED ID
      partDAO.update(partDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      partDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DELETE TAMPERED
    // ----------------------------------------

    try {
      partDTO1.setId(1); // TAMPERED ID
      partDAO.delete(partDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      partDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DTO INSERT
    // ----------------------------------------

    partDAO.insert(partDTO1); // WORKS
    partDAO.insert(partDTO2); // WORKS

    // ----------------------------------------
    // RE-INSERT TAMPERED
    // ----------------------------------------

    try {
      partDTO1.setId(0); // TAMPERED ID
      partDAO.insert(partDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      partDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-UPDATE TAMPERED
    // ----------------------------------------

    try {
      partDTO1.setId(2); // TAMPERED ID
      partDAO.update(partDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      partDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-DELETE TAMPERED
    // ----------------------------------------

    try {
      partDTO1.setId(2); // TAMPERED ID
      partDAO.delete(partDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      partDTO1.setId(1); // UNTAMPER ID
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCount() throws Exception {
    ConnectionBean connectionBean;
    PartDAO partDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    partDAO.createTable();

    PartDTO partDTO1 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");
    partDAO.insert(partDTO1);

    PartDTO partDTO2 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO2.setManufacturer("FooManufacturer222");
    partDTO2.setNumber("FooNumber222");
    partDTO2.setDescription("FooDescription222");
    partDAO.insert(partDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    assertEquals(2, partDAO.countAll());

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAll() throws Exception {
    ConnectionBean connectionBean;
    PartDAO partDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    partDAO.createTable();

    PartDTO partDTO1 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");
    partDAO.insert(partDTO1);

    PartDTO partDTO2 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO2.setManufacturer("FooManufacturer222");
    partDTO2.setNumber("FooNumber222");
    partDTO2.setDescription("FooDescription222");
    partDAO.insert(partDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    List<IDTO> partList = partDAO.listAll();
    assertTrue(compareDTO(partDTO1, (PartDTO) partList.get(0)));
    assertTrue(compareDTO(partDTO2, (PartDTO) partList.get(1)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAllLimOff() throws Exception {
    ConnectionBean connectionBean;
    PartDAO partDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    partDAO.createTable();

    PartDTO partDTO1 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO1.setManufacturer("FooManufacturer111");
    partDTO1.setNumber("FooNumber111");
    partDTO1.setDescription("FooDescription111");
    partDAO.insert(partDTO1);

    PartDTO partDTO2 = (PartDTO) fd.getDTO( //
        PartDTO.class, connectionBean);

    partDTO2.setManufacturer("FooManufacturer222");
    partDTO2.setNumber("FooNumber222");
    partDTO2.setDescription("FooDescription222");
    partDAO.insert(partDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    partDAO = (PartDAO) fd.getDAO( //
        PartDAO.class, connectionBean);

    List<IDTO> partList;

    partList = partDAO.listAll(1, 0);
    assertEquals(1, partList.size());
    assertTrue(compareDTO(partDTO1, (PartDTO) partList.get(0)));

    partList = partDAO.listAll(1, 1);
    assertEquals(1, partList.size());
    assertTrue(compareDTO(partDTO2, (PartDTO) partList.get(0)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }
}
