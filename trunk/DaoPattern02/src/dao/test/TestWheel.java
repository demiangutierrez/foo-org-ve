package dao.test;

import java.util.List;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.WheelDAO;
import dao.example.base.WheelDTO;

/**
 * @author Demián Gutierrez
 */
public class TestWheel extends TestCase {

  private boolean compareDTO(WheelDTO wheelDTO1, WheelDTO wheelDTO2) {
    boolean ret = true;

    ret = ret && wheelDTO1.getId() == wheelDTO2.getId();
    ret = ret && wheelDTO1.getColor().equals(wheelDTO2.getColor());
    ret = ret && wheelDTO1.getSize() == wheelDTO2.getSize();

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testInsertLoad() throws Exception {
    ConnectionBean connectionBean;
    WheelDAO wheelDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    wheelDAO.createTable();

    WheelDTO wheelDTO1 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);
    wheelDAO.insert(wheelDTO1);
    assertEquals(wheelDTO1.getId(), 1);

    WheelDTO wheelDTO2 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO2.setColor("brown");
    wheelDTO2.setSize(2);
    wheelDAO.insert(wheelDTO2);
    assertEquals(wheelDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    WheelDTO wheelDTOX = (WheelDTO) wheelDAO.loadById(1);
    assertTrue(compareDTO(wheelDTO1, wheelDTOX));
    assertNotSame(wheelDTO1, wheelDTOX);

    WheelDTO wheelDTOY = (WheelDTO) wheelDAO.loadById(2);
    assertTrue(compareDTO(wheelDTO2, wheelDTOY));
    assertNotSame(wheelDTO2, wheelDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testUpdateLoad() throws Exception {
    ConnectionBean connectionBean;
    WheelDAO wheelDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    wheelDAO.createTable();

    WheelDTO wheelDTO1 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);
    wheelDAO.insert(wheelDTO1);
    assertEquals(wheelDTO1.getId(), 1);

    WheelDTO wheelDTO2 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO2.setColor("brown");
    wheelDTO2.setSize(2);
    wheelDAO.insert(wheelDTO2);
    assertEquals(wheelDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / UPDATE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    WheelDTO wheelDTOX = (WheelDTO) wheelDAO.loadById(1);
    wheelDTOX.setColor("blue");
    wheelDTOX.setSize(3);
    wheelDAO.update(wheelDTOX); // Changes the 111

    WheelDTO wheelDTOY = (WheelDTO) wheelDAO.loadById(2);
    wheelDTOY.setColor("cyan");
    wheelDTOY.setSize(4);
    wheelDAO.update(wheelDTOY); // Changes the 222

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    WheelDTO wheelDTOA = (WheelDTO) wheelDAO.loadById(1);
    assertTrue(compareDTO(wheelDTOX, wheelDTOA));
    assertNotSame(wheelDTOX, wheelDTOA);

    WheelDTO wheelDTOB = (WheelDTO) wheelDAO.loadById(2);
    assertTrue(compareDTO(wheelDTOY, wheelDTOB));
    assertNotSame(wheelDTOY, wheelDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteLoad() throws Exception {
    ConnectionBean connectionBean;
    WheelDAO wheelDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    wheelDAO.createTable();

    WheelDTO wheelDTO1 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);
    wheelDAO.insert(wheelDTO1);
    assertEquals(wheelDTO1.getId(), 1);

    WheelDTO wheelDTO2 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO2.setColor("brown");
    wheelDTO2.setSize(2);
    wheelDAO.insert(wheelDTO2);
    assertEquals(wheelDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    WheelDTO wheelDTOX = (WheelDTO) wheelDAO.loadById(1);
    wheelDAO.delete(wheelDTOX);

    WheelDTO wheelDTOY = (WheelDTO) wheelDAO.loadById(2);
    wheelDAO.delete(wheelDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    WheelDTO wheelDTOA = (WheelDTO) wheelDAO.loadById(1);
    assertNull(wheelDTOA);

    WheelDTO wheelDTOB = (WheelDTO) wheelDAO.loadById(2);
    assertNull(wheelDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCacheLoad() throws Exception {
    ConnectionBean connectionBean;
    WheelDAO wheelDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    wheelDAO.createTable();

    WheelDTO wheelDTO1 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);
    wheelDAO.insert(wheelDTO1);
    assertEquals(wheelDTO1.getId(), 1);

    WheelDTO wheelDTO2 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO2.setColor("brown");
    wheelDTO2.setSize(2);
    wheelDAO.insert(wheelDTO2);
    assertEquals(wheelDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / RE-LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    WheelDTO wheelDTOX = (WheelDTO) wheelDAO.loadById(1);
    WheelDTO wheelDTOA = (WheelDTO) wheelDAO.loadById(1);
    assertTrue(compareDTO(wheelDTOX, wheelDTOA));
    assertSame(wheelDTOX, wheelDTOA);

    WheelDTO wheelDTOY = (WheelDTO) wheelDAO.loadById(2);
    WheelDTO wheelDTOB = (WheelDTO) wheelDAO.loadById(2);
    assertTrue(compareDTO(wheelDTOY, wheelDTOB));
    assertSame(wheelDTOY, wheelDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReInsert() throws Exception {
    ConnectionBean connectionBean;
    WheelDAO wheelDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    wheelDAO.createTable();

    WheelDTO wheelDTO1 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);
    wheelDAO.insert(wheelDTO1);
    assertEquals(wheelDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    WheelDTO wheelDTOX = (WheelDTO) wheelDAO.loadById(1);
    wheelDAO.delete(wheelDTOX);

    try {
      wheelDAO.insert(wheelDTOX);
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
    WheelDAO wheelDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    wheelDAO.createTable();

    WheelDTO wheelDTO1 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);
    wheelDAO.insert(wheelDTO1);
    assertEquals(wheelDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    WheelDTO wheelDTOX = (WheelDTO) wheelDAO.loadById(1);
    wheelDAO.delete(wheelDTOX);

    try {
      wheelDAO.update(wheelDTOX);
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
    WheelDAO wheelDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    wheelDAO.createTable();

    WheelDTO wheelDTO1 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);
    wheelDAO.insert(wheelDTO1);
    assertEquals(wheelDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    WheelDTO wheelDTOX = (WheelDTO) wheelDAO.loadById(1);
    wheelDAO.delete(wheelDTOX);

    try {
      wheelDAO.delete(wheelDTOX);
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
    WheelDAO wheelDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    wheelDAO.createTable();

    WheelDTO wheelDTO1 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);

    WheelDTO wheelDTO2 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);

    // ----------------------------------------
    // UPDATE NON INSERTED
    // ----------------------------------------

    try {
      wheelDAO.update(wheelDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // DELETE NON INSERTED
    // ----------------------------------------

    try {
      wheelDAO.delete(wheelDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // INSERT TAMPERED
    // ----------------------------------------

    try {
      wheelDTO1.setId(1); // TAMPERED ID
      wheelDAO.insert(wheelDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      wheelDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // UPDATE TAMPERED
    // ----------------------------------------

    try {
      wheelDTO1.setId(1); // TAMPERED ID
      wheelDAO.update(wheelDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      wheelDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DELETE TAMPERED
    // ----------------------------------------

    try {
      wheelDTO1.setId(1); // TAMPERED ID
      wheelDAO.delete(wheelDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      wheelDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DTO INSERT
    // ----------------------------------------

    wheelDAO.insert(wheelDTO1); // WORKS
    wheelDAO.insert(wheelDTO2); // WORKS

    // ----------------------------------------
    // RE-INSERT TAMPERED
    // ----------------------------------------

    try {
      wheelDTO1.setId(0); // TAMPERED ID
      wheelDAO.insert(wheelDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      wheelDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-UPDATE TAMPERED
    // ----------------------------------------

    try {
      wheelDTO1.setId(2); // TAMPERED ID
      wheelDAO.update(wheelDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      wheelDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-DELETE TAMPERED
    // ----------------------------------------

    try {
      wheelDTO1.setId(2); // TAMPERED ID
      wheelDAO.delete(wheelDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      wheelDTO1.setId(1); // UNTAMPER ID
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCount() throws Exception {
    ConnectionBean connectionBean;
    WheelDAO wheelDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    wheelDAO.createTable();

    WheelDTO wheelDTO1 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);
    wheelDAO.insert(wheelDTO1);

    WheelDTO wheelDTO2 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO2.setColor("brown");
    wheelDTO2.setSize(2);
    wheelDAO.insert(wheelDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    assertEquals(2, wheelDAO.countAll());

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAll() throws Exception {
    ConnectionBean connectionBean;
    WheelDAO wheelDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    wheelDAO.createTable();

    WheelDTO wheelDTO1 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);
    wheelDAO.insert(wheelDTO1);

    WheelDTO wheelDTO2 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO2.setColor("brown");
    wheelDTO2.setSize(2);
    wheelDAO.insert(wheelDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    List<IDTO> wheelList = wheelDAO.listAll();
    assertTrue(compareDTO(wheelDTO1, (WheelDTO) wheelList.get(0)));
    assertTrue(compareDTO(wheelDTO2, (WheelDTO) wheelList.get(1)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAllLimOff() throws Exception {
    ConnectionBean connectionBean;
    WheelDAO wheelDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    wheelDAO.createTable();

    WheelDTO wheelDTO1 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO1.setColor("green");
    wheelDTO1.setSize(1);
    wheelDAO.insert(wheelDTO1);

    WheelDTO wheelDTO2 = (WheelDTO) fd.getDTO( //
        WheelDTO.class, connectionBean);

    wheelDTO2.setColor("brown");
    wheelDTO2.setSize(2);
    wheelDAO.insert(wheelDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    wheelDAO = (WheelDAO) fd.getDAO( //
        WheelDAO.class, connectionBean);

    List<IDTO> wheelList;

    wheelList = wheelDAO.listAll(1, 0);
    assertEquals(1, wheelList.size());
    assertTrue(compareDTO(wheelDTO1, (WheelDTO) wheelList.get(0)));

    wheelList = wheelDAO.listAll(1, 1);
    assertEquals(1, wheelList.size());
    assertTrue(compareDTO(wheelDTO2, (WheelDTO) wheelList.get(0)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }
}
