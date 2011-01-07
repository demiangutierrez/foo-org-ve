package dao.test;

import java.util.List;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.TireDAO;
import dao.example.base.TireDTO;

/**
 * @author Demián Gutierrez
 */
public class TestTire extends TestCase {

  private boolean compareDTO(TireDTO tireDTO1, TireDTO tireDTO2) {
    boolean ret = true;

    ret = ret && tireDTO1.getId() == tireDTO2.getId();
    ret = ret && tireDTO1.getSpeed() == tireDTO2.getSpeed();
    ret = ret && tireDTO1.getRating() == tireDTO2.getRating();

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testInsertLoad() throws Exception {
    ConnectionBean connectionBean;
    TireDAO tireDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    tireDAO.createTable();

    TireDTO tireDTO1 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);
    tireDAO.insert(tireDTO1);
    assertEquals(tireDTO1.getId(), 1);

    TireDTO tireDTO2 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO2.setSpeed(2);
    tireDTO2.setRating(22);
    tireDAO.insert(tireDTO2);
    assertEquals(tireDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    TireDTO tireDTOX = (TireDTO) tireDAO.loadById(1);
    assertTrue(compareDTO(tireDTO1, tireDTOX));
    assertNotSame(tireDTO1, tireDTOX);

    TireDTO tireDTOY = (TireDTO) tireDAO.loadById(2);
    assertTrue(compareDTO(tireDTO2, tireDTOY));
    assertNotSame(tireDTO2, tireDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testUpdateLoad() throws Exception {
    ConnectionBean connectionBean;
    TireDAO tireDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    tireDAO.createTable();

    TireDTO tireDTO1 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);
    tireDAO.insert(tireDTO1);
    assertEquals(tireDTO1.getId(), 1);

    TireDTO tireDTO2 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO2.setSpeed(2);
    tireDTO2.setRating(22);
    tireDAO.insert(tireDTO2);
    assertEquals(tireDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / UPDATE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    TireDTO tireDTOX = (TireDTO) tireDAO.loadById(1);
    tireDTOX.setSpeed(3);
    tireDTOX.setRating(33);
    tireDAO.update(tireDTOX); // Changes the 111

    TireDTO tireDTOY = (TireDTO) tireDAO.loadById(2);
    tireDTOY.setSpeed(4);
    tireDTOY.setRating(44);
    tireDAO.update(tireDTOY); // Changes the 222

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    TireDTO tireDTOA = (TireDTO) tireDAO.loadById(1);
    assertTrue(compareDTO(tireDTOX, tireDTOA));
    assertNotSame(tireDTOX, tireDTOA);

    TireDTO tireDTOB = (TireDTO) tireDAO.loadById(2);
    assertTrue(compareDTO(tireDTOY, tireDTOB));
    assertNotSame(tireDTOY, tireDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteLoad() throws Exception {
    ConnectionBean connectionBean;
    TireDAO tireDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    tireDAO.createTable();

    TireDTO tireDTO1 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);
    tireDAO.insert(tireDTO1);
    assertEquals(tireDTO1.getId(), 1);

    TireDTO tireDTO2 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO2.setSpeed(2);
    tireDTO2.setRating(22);
    tireDAO.insert(tireDTO2);
    assertEquals(tireDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    TireDTO tireDTOX = (TireDTO) tireDAO.loadById(1);
    tireDAO.delete(tireDTOX);

    TireDTO tireDTOY = (TireDTO) tireDAO.loadById(2);
    tireDAO.delete(tireDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    TireDTO tireDTOA = (TireDTO) tireDAO.loadById(1);
    assertNull(tireDTOA);

    TireDTO tireDTOB = (TireDTO) tireDAO.loadById(2);
    assertNull(tireDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCacheLoad() throws Exception {
    ConnectionBean connectionBean;
    TireDAO tireDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    tireDAO.createTable();

    TireDTO tireDTO1 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);
    tireDAO.insert(tireDTO1);
    assertEquals(tireDTO1.getId(), 1);

    TireDTO tireDTO2 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO2.setSpeed(2);
    tireDTO2.setRating(22);
    tireDAO.insert(tireDTO2);
    assertEquals(tireDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / RE-LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    TireDTO tireDTOX = (TireDTO) tireDAO.loadById(1);
    TireDTO tireDTOA = (TireDTO) tireDAO.loadById(1);
    assertTrue(compareDTO(tireDTOX, tireDTOA));
    assertSame(tireDTOX, tireDTOA);

    TireDTO tireDTOY = (TireDTO) tireDAO.loadById(2);
    TireDTO tireDTOB = (TireDTO) tireDAO.loadById(2);
    assertTrue(compareDTO(tireDTOY, tireDTOB));
    assertSame(tireDTOY, tireDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReInsert() throws Exception {
    ConnectionBean connectionBean;
    TireDAO tireDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    tireDAO.createTable();

    TireDTO tireDTO1 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);
    tireDAO.insert(tireDTO1);
    assertEquals(tireDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    TireDTO tireDTOX = (TireDTO) tireDAO.loadById(1);
    tireDAO.delete(tireDTOX);

    try {
      tireDAO.insert(tireDTOX);
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
    TireDAO tireDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    tireDAO.createTable();

    TireDTO tireDTO1 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);
    tireDAO.insert(tireDTO1);
    assertEquals(tireDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    TireDTO tireDTOX = (TireDTO) tireDAO.loadById(1);
    tireDAO.delete(tireDTOX);

    try {
      tireDAO.update(tireDTOX);
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
    TireDAO tireDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    tireDAO.createTable();

    TireDTO tireDTO1 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);
    tireDAO.insert(tireDTO1);
    assertEquals(tireDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    TireDTO tireDTOX = (TireDTO) tireDAO.loadById(1);
    tireDAO.delete(tireDTOX);

    try {
      tireDAO.delete(tireDTOX);
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
    TireDAO tireDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    tireDAO.createTable();

    TireDTO tireDTO1 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);

    TireDTO tireDTO2 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);

    // ----------------------------------------
    // UPDATE NON INSERTED
    // ----------------------------------------

    try {
      tireDAO.update(tireDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // DELETE NON INSERTED
    // ----------------------------------------

    try {
      tireDAO.delete(tireDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // INSERT TAMPERED
    // ----------------------------------------

    try {
      tireDTO1.setId(1); // TAMPERED ID
      tireDAO.insert(tireDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      tireDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // UPDATE TAMPERED
    // ----------------------------------------

    try {
      tireDTO1.setId(1); // TAMPERED ID
      tireDAO.update(tireDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      tireDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DELETE TAMPERED
    // ----------------------------------------

    try {
      tireDTO1.setId(1); // TAMPERED ID
      tireDAO.delete(tireDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      tireDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DTO INSERT
    // ----------------------------------------

    tireDAO.insert(tireDTO1); // WORKS
    tireDAO.insert(tireDTO2); // WORKS

    // ----------------------------------------
    // RE-INSERT TAMPERED
    // ----------------------------------------

    try {
      tireDTO1.setId(0); // TAMPERED ID
      tireDAO.insert(tireDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      tireDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-UPDATE TAMPERED
    // ----------------------------------------

    try {
      tireDTO1.setId(2); // TAMPERED ID
      tireDAO.update(tireDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      tireDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-DELETE TAMPERED
    // ----------------------------------------

    try {
      tireDTO1.setId(2); // TAMPERED ID
      tireDAO.delete(tireDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      tireDTO1.setId(1); // UNTAMPER ID
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCount() throws Exception {
    ConnectionBean connectionBean;
    TireDAO tireDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    tireDAO.createTable();

    TireDTO tireDTO1 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);
    tireDAO.insert(tireDTO1);

    TireDTO tireDTO2 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO2.setSpeed(2);
    tireDTO2.setRating(22);
    tireDAO.insert(tireDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    assertEquals(2, tireDAO.countAll());

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAll() throws Exception {
    ConnectionBean connectionBean;
    TireDAO tireDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    tireDAO.createTable();

    TireDTO tireDTO1 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);
    tireDAO.insert(tireDTO1);

    TireDTO tireDTO2 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO2.setSpeed(2);
    tireDTO2.setRating(22);
    tireDAO.insert(tireDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    List<IDTO> tireList = tireDAO.listAll();
    assertTrue(compareDTO(tireDTO1, (TireDTO) tireList.get(0)));
    assertTrue(compareDTO(tireDTO2, (TireDTO) tireList.get(1)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAllLimOff() throws Exception {
    ConnectionBean connectionBean;
    TireDAO tireDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    tireDAO.createTable();

    TireDTO tireDTO1 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO1.setSpeed(1);
    tireDTO1.setRating(11);
    tireDAO.insert(tireDTO1);

    TireDTO tireDTO2 = (TireDTO) fd.getDTO( //
        TireDTO.class, connectionBean);

    tireDTO2.setSpeed(2);
    tireDTO2.setRating(22);
    tireDAO.insert(tireDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    tireDAO = (TireDAO) fd.getDAO( //
        TireDAO.class, connectionBean);

    List<IDTO> tireList;

    tireList = tireDAO.listAll(1, 0);
    assertEquals(1, tireList.size());
    assertTrue(compareDTO(tireDTO1, (TireDTO) tireList.get(0)));

    tireList = tireDAO.listAll(1, 1);
    assertEquals(1, tireList.size());
    assertTrue(compareDTO(tireDTO2, (TireDTO) tireList.get(0)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }
}
