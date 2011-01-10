package dao.test;

import java.util.List;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.PublicationDAO;
import dao.example.base.PublicationDTO;

/**
 * @author Demián Gutierrez
 */
public class TestPublication extends TestCase {

  private boolean compareDTO(PublicationDTO publicationDTO1, PublicationDTO publicationDTO2) {
    boolean ret = true;

    ret = ret && publicationDTO1.getId() == publicationDTO2.getId();
    ret = ret && publicationDTO1.getManufacturer().equals(publicationDTO2.getManufacturer());
    ret = ret && publicationDTO1.getNumber().equals(publicationDTO2.getNumber());
    ret = ret && publicationDTO1.getDescription().equals(publicationDTO2.getDescription());

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testInsertLoad() throws Exception {
    ConnectionBean connectionBean;
    PublicationDAO publicationDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    publicationDAO.createTable();

    PublicationDTO publicationDTO1 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");
    publicationDAO.insert(publicationDTO1);
    assertEquals(publicationDTO1.getId(), 1);

    PublicationDTO publicationDTO2 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO2.setManufacturer("FooManufacturer222");
    publicationDTO2.setNumber("FooNumber222");
    publicationDTO2.setDescription("FooDescription222");
    publicationDAO.insert(publicationDTO2);
    assertEquals(publicationDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    PublicationDTO publicationDTOX = (PublicationDTO) publicationDAO.loadById(1);
    assertTrue(compareDTO(publicationDTO1, publicationDTOX));
    assertNotSame(publicationDTO1, publicationDTOX);

    PublicationDTO publicationDTOY = (PublicationDTO) publicationDAO.loadById(2);
    assertTrue(compareDTO(publicationDTO2, publicationDTOY));
    assertNotSame(publicationDTO2, publicationDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testUpdateLoad() throws Exception {
    ConnectionBean connectionBean;
    PublicationDAO publicationDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    publicationDAO.createTable();

    PublicationDTO publicationDTO1 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");
    publicationDAO.insert(publicationDTO1);
    assertEquals(publicationDTO1.getId(), 1);

    PublicationDTO publicationDTO2 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO2.setManufacturer("FooManufacturer222");
    publicationDTO2.setNumber("FooNumber222");
    publicationDTO2.setDescription("FooDescription222");
    publicationDAO.insert(publicationDTO2);
    assertEquals(publicationDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / UPDATE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    PublicationDTO publicationDTOX = (PublicationDTO) publicationDAO.loadById(1);
    publicationDTOX.setManufacturer("FooManufacturerXXX");
    publicationDTOX.setNumber("FooNumberXXX");
    publicationDTOX.setDescription("FooDescriptionXXX");
    publicationDAO.update(publicationDTOX); // Changes the 111

    PublicationDTO publicationDTOY = (PublicationDTO) publicationDAO.loadById(2);
    publicationDTOY.setManufacturer("FooManufacturerYYY");
    publicationDTOY.setNumber("FooNumberYYY");
    publicationDTOY.setDescription("FooDescriptionYYY");
    publicationDAO.update(publicationDTOY); // Changes the 222

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    PublicationDTO publicationDTOA = (PublicationDTO) publicationDAO.loadById(1);
    assertTrue(compareDTO(publicationDTOX, publicationDTOA));
    assertNotSame(publicationDTOX, publicationDTOA);

    PublicationDTO publicationDTOB = (PublicationDTO) publicationDAO.loadById(2);
    assertTrue(compareDTO(publicationDTOY, publicationDTOB));
    assertNotSame(publicationDTOY, publicationDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteLoad() throws Exception {
    ConnectionBean connectionBean;
    PublicationDAO publicationDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    publicationDAO.createTable();

    PublicationDTO publicationDTO1 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");
    publicationDAO.insert(publicationDTO1);
    assertEquals(publicationDTO1.getId(), 1);

    PublicationDTO publicationDTO2 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO2.setManufacturer("FooManufacturer222");
    publicationDTO2.setNumber("FooNumber222");
    publicationDTO2.setDescription("FooDescription222");
    publicationDAO.insert(publicationDTO2);
    assertEquals(publicationDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    PublicationDTO publicationDTOX = (PublicationDTO) publicationDAO.loadById(1);
    publicationDAO.delete(publicationDTOX);

    PublicationDTO publicationDTOY = (PublicationDTO) publicationDAO.loadById(2);
    publicationDAO.delete(publicationDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    PublicationDTO publicationDTOA = (PublicationDTO) publicationDAO.loadById(1);
    assertNull(publicationDTOA);

    PublicationDTO publicationDTOB = (PublicationDTO) publicationDAO.loadById(2);
    assertNull(publicationDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCacheLoad() throws Exception {
    ConnectionBean connectionBean;
    PublicationDAO publicationDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    publicationDAO.createTable();

    PublicationDTO publicationDTO1 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");
    publicationDAO.insert(publicationDTO1);
    assertEquals(publicationDTO1.getId(), 1);

    PublicationDTO publicationDTO2 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO2.setManufacturer("FooManufacturer222");
    publicationDTO2.setNumber("FooNumber222");
    publicationDTO2.setDescription("FooDescription222");
    publicationDAO.insert(publicationDTO2);
    assertEquals(publicationDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / RE-LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    PublicationDTO publicationDTOX = (PublicationDTO) publicationDAO.loadById(1);
    PublicationDTO publicationDTOA = (PublicationDTO) publicationDAO.loadById(1);
    assertTrue(compareDTO(publicationDTOX, publicationDTOA));
    assertSame(publicationDTOX, publicationDTOA);

    PublicationDTO publicationDTOY = (PublicationDTO) publicationDAO.loadById(2);
    PublicationDTO publicationDTOB = (PublicationDTO) publicationDAO.loadById(2);
    assertTrue(compareDTO(publicationDTOY, publicationDTOB));
    assertSame(publicationDTOY, publicationDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReInsert() throws Exception {
    ConnectionBean connectionBean;
    PublicationDAO publicationDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    publicationDAO.createTable();

    PublicationDTO publicationDTO1 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");
    publicationDAO.insert(publicationDTO1);
    assertEquals(publicationDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    PublicationDTO publicationDTOX = (PublicationDTO) publicationDAO.loadById(1);
    publicationDAO.delete(publicationDTOX);

    try {
      publicationDAO.insert(publicationDTOX);
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
    PublicationDAO publicationDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    publicationDAO.createTable();

    PublicationDTO publicationDTO1 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");
    publicationDAO.insert(publicationDTO1);
    assertEquals(publicationDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    PublicationDTO publicationDTOX = (PublicationDTO) publicationDAO.loadById(1);
    publicationDAO.delete(publicationDTOX);

    try {
      publicationDAO.update(publicationDTOX);
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
    PublicationDAO publicationDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    publicationDAO.createTable();

    PublicationDTO publicationDTO1 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");
    publicationDAO.insert(publicationDTO1);
    assertEquals(publicationDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    PublicationDTO publicationDTOX = (PublicationDTO) publicationDAO.loadById(1);
    publicationDAO.delete(publicationDTOX);

    try {
      publicationDAO.delete(publicationDTOX);
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
    PublicationDAO publicationDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    publicationDAO.createTable();

    PublicationDTO publicationDTO1 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");

    PublicationDTO publicationDTO2 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");

    // ----------------------------------------
    // UPDATE NON INSERTED
    // ----------------------------------------

    try {
      publicationDAO.update(publicationDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // DELETE NON INSERTED
    // ----------------------------------------

    try {
      publicationDAO.delete(publicationDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // INSERT TAMPERED
    // ----------------------------------------

    try {
      publicationDTO1.setId(1); // TAMPERED ID
      publicationDAO.insert(publicationDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      publicationDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // UPDATE TAMPERED
    // ----------------------------------------

    try {
      publicationDTO1.setId(1); // TAMPERED ID
      publicationDAO.update(publicationDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      publicationDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DELETE TAMPERED
    // ----------------------------------------

    try {
      publicationDTO1.setId(1); // TAMPERED ID
      publicationDAO.delete(publicationDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      publicationDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DTO INSERT
    // ----------------------------------------

    publicationDAO.insert(publicationDTO1); // WORKS
    publicationDAO.insert(publicationDTO2); // WORKS

    // ----------------------------------------
    // RE-INSERT TAMPERED
    // ----------------------------------------

    try {
      publicationDTO1.setId(0); // TAMPERED ID
      publicationDAO.insert(publicationDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      publicationDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-UPDATE TAMPERED
    // ----------------------------------------

    try {
      publicationDTO1.setId(2); // TAMPERED ID
      publicationDAO.update(publicationDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      publicationDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-DELETE TAMPERED
    // ----------------------------------------

    try {
      publicationDTO1.setId(2); // TAMPERED ID
      publicationDAO.delete(publicationDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      publicationDTO1.setId(1); // UNTAMPER ID
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCount() throws Exception {
    ConnectionBean connectionBean;
    PublicationDAO publicationDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    publicationDAO.createTable();

    PublicationDTO publicationDTO1 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");
    publicationDAO.insert(publicationDTO1);

    PublicationDTO publicationDTO2 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO2.setManufacturer("FooManufacturer222");
    publicationDTO2.setNumber("FooNumber222");
    publicationDTO2.setDescription("FooDescription222");
    publicationDAO.insert(publicationDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    assertEquals(2, publicationDAO.countAll());

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAll() throws Exception {
    ConnectionBean connectionBean;
    PublicationDAO publicationDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    publicationDAO.createTable();

    PublicationDTO publicationDTO1 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");
    publicationDAO.insert(publicationDTO1);

    PublicationDTO publicationDTO2 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO2.setManufacturer("FooManufacturer222");
    publicationDTO2.setNumber("FooNumber222");
    publicationDTO2.setDescription("FooDescription222");
    publicationDAO.insert(publicationDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    List<IDTO> publicationList = publicationDAO.listAll();
    assertTrue(compareDTO(publicationDTO1, (PublicationDTO) publicationList.get(0)));
    assertTrue(compareDTO(publicationDTO2, (PublicationDTO) publicationList.get(1)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAllLimOff() throws Exception {
    ConnectionBean connectionBean;
    PublicationDAO publicationDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    publicationDAO.createTable();

    PublicationDTO publicationDTO1 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO1.setManufacturer("FooManufacturer111");
    publicationDTO1.setNumber("FooNumber111");
    publicationDTO1.setDescription("FooDescription111");
    publicationDAO.insert(publicationDTO1);

    PublicationDTO publicationDTO2 = (PublicationDTO) fd.getDTO( //
        PublicationDTO.class, connectionBean);

    publicationDTO2.setManufacturer("FooManufacturer222");
    publicationDTO2.setNumber("FooNumber222");
    publicationDTO2.setDescription("FooDescription222");
    publicationDAO.insert(publicationDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    publicationDAO = (PublicationDAO) fd.getDAO( //
        PublicationDAO.class, connectionBean);

    List<IDTO> publicationList;

    publicationList = publicationDAO.listAll(1, 0);
    assertEquals(1, publicationList.size());
    assertTrue(compareDTO(publicationDTO1, (PublicationDTO) publicationList.get(0)));

    publicationList = publicationDAO.listAll(1, 1);
    assertEquals(1, publicationList.size());
    assertTrue(compareDTO(publicationDTO2, (PublicationDTO) publicationList.get(0)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }
}
