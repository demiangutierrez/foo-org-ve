package dao.test;

import java.util.List;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.ProfDAO;
import dao.example.base.ProfDTO;

/**
 * @author Demián Gutierrez
 */
public class TestProf extends TestCase {

  private boolean compareDTO(ProfDTO profDTO1, ProfDTO profDTO2) {
    boolean ret = true;

    ret = ret && profDTO1.getId() == profDTO2.getId();

    ret = ret && profDTO1.getProfAtt1().equals(profDTO2.getProfAtt1());
    ret = ret && profDTO1.getProfAtt2().equals(profDTO2.getProfAtt2());

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testInsertLoad() throws Exception {
    ConnectionBean connectionBean;
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("FooFrstName111");
    profDTO1.setProfAtt2("FooLastName111");
    profDAO.insert(profDTO1);
    assertEquals(profDTO1.getId(), 1);

    ProfDTO profDTO2 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO2.setProfAtt1("FooFrstName222");
    profDTO2.setProfAtt2("FooLastName222");
    profDAO.insert(profDTO2);
    assertEquals(profDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    ProfDTO profDTOX = (ProfDTO) profDAO.loadById(1);
    assertTrue(compareDTO(profDTO1, profDTOX));
    assertNotSame(profDTO1, profDTOX);

    ProfDTO profDTOY = (ProfDTO) profDAO.loadById(2);
    assertTrue(compareDTO(profDTO2, profDTOY));
    assertNotSame(profDTO2, profDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testUpdateLoad() throws Exception {
    ConnectionBean connectionBean;
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("FooFrstName111");
    profDTO1.setProfAtt2("FooLastName111");
    profDAO.insert(profDTO1);
    assertEquals(profDTO1.getId(), 1);

    ProfDTO profDTO2 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO2.setProfAtt1("FooFrstName222");
    profDTO2.setProfAtt2("FooLastName222");
    profDAO.insert(profDTO2);
    assertEquals(profDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / UPDATE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    ProfDTO profDTOX = (ProfDTO) profDAO.loadById(1);
    profDTOX.setProfAtt1("FooFrstNameXXX");
    profDTOX.setProfAtt2("FooLastNameXXX");
    profDAO.update(profDTOX); // Changes the 111

    ProfDTO profDTOY = (ProfDTO) profDAO.loadById(2);
    profDTOY.setProfAtt1("FooFrstNameYYY");
    profDTOY.setProfAtt2("FooLastNameYYY");
    profDAO.update(profDTOY); // Changes the 222

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    ProfDTO profDTOA = (ProfDTO) profDAO.loadById(1);
    assertTrue(compareDTO(profDTOX, profDTOA));
    assertNotSame(profDTOX, profDTOA);

    ProfDTO profDTOB = (ProfDTO) profDAO.loadById(2);
    assertTrue(compareDTO(profDTOY, profDTOB));
    assertNotSame(profDTOY, profDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteLoad() throws Exception {
    ConnectionBean connectionBean;
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("FooFrstName111");
    profDTO1.setProfAtt2("FooLastName111");
    profDAO.insert(profDTO1);
    assertEquals(profDTO1.getId(), 1);

    ProfDTO profDTO2 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO2.setProfAtt1("FooFrstName222");
    profDTO2.setProfAtt2("FooLastName222");
    profDAO.insert(profDTO2);
    assertEquals(profDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    ProfDTO profDTOX = (ProfDTO) profDAO.loadById(1);
    profDAO.delete(profDTOX);

    ProfDTO profDTOY = (ProfDTO) profDAO.loadById(2);
    profDAO.delete(profDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    ProfDTO profDTOA = (ProfDTO) profDAO.loadById(1);
    assertNull(profDTOA);

    ProfDTO profDTOB = (ProfDTO) profDAO.loadById(2);
    assertNull(profDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCacheLoad() throws Exception {
    ConnectionBean connectionBean;
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("FooFrstName111");
    profDTO1.setProfAtt2("FooLastName111");
    profDAO.insert(profDTO1);
    assertEquals(profDTO1.getId(), 1);

    ProfDTO profDTO2 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO2.setProfAtt1("FooFrstName222");
    profDTO2.setProfAtt2("FooLastName222");
    profDAO.insert(profDTO2);
    assertEquals(profDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / RE-LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    ProfDTO profDTOX = (ProfDTO) profDAO.loadById(1);
    ProfDTO profDTOA = (ProfDTO) profDAO.loadById(1);
    assertTrue(compareDTO(profDTOX, profDTOA));
    assertSame(profDTOX, profDTOA);

    ProfDTO profDTOY = (ProfDTO) profDAO.loadById(2);
    ProfDTO profDTOB = (ProfDTO) profDAO.loadById(2);
    assertTrue(compareDTO(profDTOY, profDTOB));
    assertSame(profDTOY, profDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReInsert() throws Exception {
    ConnectionBean connectionBean;
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("FooFrstName");
    profDTO1.setProfAtt2("FooLastName");
    profDAO.insert(profDTO1);
    assertEquals(profDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    ProfDTO profDTOX = (ProfDTO) profDAO.loadById(1);
    profDAO.delete(profDTOX);

    try {
      profDAO.insert(profDTOX);
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
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("FooFrstName");
    profDTO1.setProfAtt2("FooLastName");
    profDAO.insert(profDTO1);
    assertEquals(profDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    ProfDTO profDTOX = (ProfDTO) profDAO.loadById(1);
    profDAO.delete(profDTOX);

    try {
      profDAO.update(profDTOX);
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
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("FooFrstName");
    profDTO1.setProfAtt2("FooLastName");
    profDAO.insert(profDTO1);
    assertEquals(profDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    ProfDTO profDTOX = (ProfDTO) profDAO.loadById(1);
    profDAO.delete(profDTOX);

    try {
      profDAO.delete(profDTOX);
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
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("FooFrstName111");
    profDTO1.setProfAtt2("FooLastName111");

    ProfDTO profDTO2 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO2.setProfAtt1("FooFrstName222");
    profDTO2.setProfAtt2("FooLastName222");

    // ----------------------------------------
    // UPDATE NON INSERTED
    // ----------------------------------------

    try {
      profDAO.update(profDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // DELETE NON INSERTED
    // ----------------------------------------

    try {
      profDAO.delete(profDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // INSERT TAMPERED
    // ----------------------------------------

    try {
      profDTO1.setId(1); // TAMPERED ID
      profDAO.insert(profDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      profDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // UPDATE TAMPERED
    // ----------------------------------------

    try {
      profDTO1.setId(1); // TAMPERED ID
      profDAO.update(profDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      profDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DELETE TAMPERED
    // ----------------------------------------

    try {
      profDTO1.setId(1); // TAMPERED ID
      profDAO.delete(profDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      profDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DTO INSERT
    // ----------------------------------------

    profDAO.insert(profDTO1); // WORKS
    profDAO.insert(profDTO2); // WORKS

    // ----------------------------------------
    // RE-INSERT TAMPERED
    // ----------------------------------------

    try {
      profDTO1.setId(0); // TAMPERED ID
      profDAO.insert(profDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      profDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-UPDATE TAMPERED
    // ----------------------------------------

    try {
      profDTO1.setId(2); // TAMPERED ID
      profDAO.update(profDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      profDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-DELETE TAMPERED
    // ----------------------------------------

    try {
      profDTO1.setId(2); // TAMPERED ID
      profDAO.delete(profDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      profDTO1.setId(1); // UNTAMPER ID
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCount() throws Exception {
    ConnectionBean connectionBean;
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("FooFrstName111");
    profDTO1.setProfAtt2("FooLastName111");
    profDAO.insert(profDTO1);

    ProfDTO profDTO2 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO2.setProfAtt1("FooFrstName222");
    profDTO2.setProfAtt2("FooLastName222");
    profDAO.insert(profDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    assertEquals(2, profDAO.countAll());

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAll() throws Exception {
    ConnectionBean connectionBean;
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("FooFrstName111");
    profDTO1.setProfAtt2("FooLastName111");
    profDAO.insert(profDTO1);

    ProfDTO profDTO2 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO2.setProfAtt1("FooFrstName222");
    profDTO2.setProfAtt2("FooLastName222");
    profDAO.insert(profDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    List<IDTO> profList = profDAO.listAll();
    assertTrue(compareDTO(profDTO1, (ProfDTO) profList.get(0)));
    assertTrue(compareDTO(profDTO2, (ProfDTO) profList.get(1)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAllLimOff() throws Exception {
    ConnectionBean connectionBean;
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("FooFrstName111");
    profDTO1.setProfAtt2("FooLastName111");
    profDAO.insert(profDTO1);

    ProfDTO profDTO2 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO2.setProfAtt1("FooFrstName222");
    profDTO2.setProfAtt2("FooLastName222");
    profDAO.insert(profDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    List<IDTO> profList;

    profList = profDAO.listAll(1, 0);
    assertEquals(1, profList.size());
    assertTrue(compareDTO(profDTO1, (ProfDTO) profList.get(0)));

    profList = profDAO.listAll(1, 1);
    assertEquals(1, profList.size());
    assertTrue(compareDTO(profDTO2, (ProfDTO) profList.get(0)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }
}
