package dao.test;

import java.util.List;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.NewsDAO;
import dao.example.base.NewsDTO;

/**
 * @author Demián Gutierrez
 */
public class TestNews extends TestCase {

  private boolean compareDTO(NewsDTO newsDTO1, NewsDTO newsDTO2) {
    boolean ret = true;

    ret = ret && newsDTO1.getId() == newsDTO2.getId();
    ret = ret && newsDTO1.getNewsAtt1().equals(newsDTO2.getNewsAtt1());
    ret = ret && newsDTO1.getNewsAtt2().equals(newsDTO2.getNewsAtt2());

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testInsertLoad() throws Exception {
    ConnectionBean connectionBean;
    NewsDAO newsDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    newsDAO.createTable();

    NewsDTO newsDTO1 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;
    newsDAO.insert(newsDTO1);
    assertEquals(newsDTO1.getId(), 1);

    NewsDTO newsDTO2 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO2.setNewsAtt1("2");;
    newsDTO2.setNewsAtt2("22");;
    newsDAO.insert(newsDTO2);
    assertEquals(newsDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    NewsDTO newsDTOX = (NewsDTO) newsDAO.loadById(1);
    assertTrue(compareDTO(newsDTO1, newsDTOX));
    assertNotSame(newsDTO1, newsDTOX);

    NewsDTO newsDTOY = (NewsDTO) newsDAO.loadById(2);
    assertTrue(compareDTO(newsDTO2, newsDTOY));
    assertNotSame(newsDTO2, newsDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testUpdateLoad() throws Exception {
    ConnectionBean connectionBean;
    NewsDAO newsDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    newsDAO.createTable();

    NewsDTO newsDTO1 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;
    newsDAO.insert(newsDTO1);
    assertEquals(newsDTO1.getId(), 1);

    NewsDTO newsDTO2 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO2.setNewsAtt1("2");;
    newsDTO2.setNewsAtt2("22");;
    newsDAO.insert(newsDTO2);
    assertEquals(newsDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / UPDATE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    NewsDTO newsDTOX = (NewsDTO) newsDAO.loadById(1);
    newsDTOX.setNewsAtt1("3");;
    newsDTOX.setNewsAtt2("33");;
    newsDAO.update(newsDTOX); // Changes the 111

    NewsDTO newsDTOY = (NewsDTO) newsDAO.loadById(2);
    newsDTOY.setNewsAtt1("4");;
    newsDTOY.setNewsAtt2("44");;
    newsDAO.update(newsDTOY); // Changes the 222

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    NewsDTO newsDTOA = (NewsDTO) newsDAO.loadById(1);
    assertTrue(compareDTO(newsDTOX, newsDTOA));
    assertNotSame(newsDTOX, newsDTOA);

    NewsDTO newsDTOB = (NewsDTO) newsDAO.loadById(2);
    assertTrue(compareDTO(newsDTOY, newsDTOB));
    assertNotSame(newsDTOY, newsDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteLoad() throws Exception {
    ConnectionBean connectionBean;
    NewsDAO newsDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    newsDAO.createTable();

    NewsDTO newsDTO1 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;
    newsDAO.insert(newsDTO1);
    assertEquals(newsDTO1.getId(), 1);

    NewsDTO newsDTO2 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO2.setNewsAtt1("2");;
    newsDTO2.setNewsAtt2("22");;
    newsDAO.insert(newsDTO2);
    assertEquals(newsDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    NewsDTO newsDTOX = (NewsDTO) newsDAO.loadById(1);
    newsDAO.delete(newsDTOX);

    NewsDTO newsDTOY = (NewsDTO) newsDAO.loadById(2);
    newsDAO.delete(newsDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    NewsDTO newsDTOA = (NewsDTO) newsDAO.loadById(1);
    assertNull(newsDTOA);

    NewsDTO newsDTOB = (NewsDTO) newsDAO.loadById(2);
    assertNull(newsDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCacheLoad() throws Exception {
    ConnectionBean connectionBean;
    NewsDAO newsDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    newsDAO.createTable();

    NewsDTO newsDTO1 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;
    newsDAO.insert(newsDTO1);
    assertEquals(newsDTO1.getId(), 1);

    NewsDTO newsDTO2 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO2.setNewsAtt1("2");;
    newsDTO2.setNewsAtt2("22");;
    newsDAO.insert(newsDTO2);
    assertEquals(newsDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / RE-LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    NewsDTO newsDTOX = (NewsDTO) newsDAO.loadById(1);
    NewsDTO newsDTOA = (NewsDTO) newsDAO.loadById(1);
    assertTrue(compareDTO(newsDTOX, newsDTOA));
    assertSame(newsDTOX, newsDTOA);

    NewsDTO newsDTOY = (NewsDTO) newsDAO.loadById(2);
    NewsDTO newsDTOB = (NewsDTO) newsDAO.loadById(2);
    assertTrue(compareDTO(newsDTOY, newsDTOB));
    assertSame(newsDTOY, newsDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReInsert() throws Exception {
    ConnectionBean connectionBean;
    NewsDAO newsDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    newsDAO.createTable();

    NewsDTO newsDTO1 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;
    newsDAO.insert(newsDTO1);
    assertEquals(newsDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    NewsDTO newsDTOX = (NewsDTO) newsDAO.loadById(1);
    newsDAO.delete(newsDTOX);

    try {
      newsDAO.insert(newsDTOX);
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
    NewsDAO newsDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    newsDAO.createTable();

    NewsDTO newsDTO1 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;
    newsDAO.insert(newsDTO1);
    assertEquals(newsDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    NewsDTO newsDTOX = (NewsDTO) newsDAO.loadById(1);
    newsDAO.delete(newsDTOX);

    try {
      newsDAO.update(newsDTOX);
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
    NewsDAO newsDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    newsDAO.createTable();

    NewsDTO newsDTO1 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;
    newsDAO.insert(newsDTO1);
    assertEquals(newsDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    NewsDTO newsDTOX = (NewsDTO) newsDAO.loadById(1);
    newsDAO.delete(newsDTOX);

    try {
      newsDAO.delete(newsDTOX);
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
    NewsDAO newsDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    newsDAO.createTable();

    NewsDTO newsDTO1 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;

    NewsDTO newsDTO2 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;

    // ----------------------------------------
    // UPDATE NON INSERTED
    // ----------------------------------------

    try {
      newsDAO.update(newsDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // DELETE NON INSERTED
    // ----------------------------------------

    try {
      newsDAO.delete(newsDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // INSERT TAMPERED
    // ----------------------------------------

    try {
      newsDTO1.setId(1); // TAMPERED ID
      newsDAO.insert(newsDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      newsDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // UPDATE TAMPERED
    // ----------------------------------------

    try {
      newsDTO1.setId(1); // TAMPERED ID
      newsDAO.update(newsDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      newsDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DELETE TAMPERED
    // ----------------------------------------

    try {
      newsDTO1.setId(1); // TAMPERED ID
      newsDAO.delete(newsDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      newsDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DTO INSERT
    // ----------------------------------------

    newsDAO.insert(newsDTO1); // WORKS
    newsDAO.insert(newsDTO2); // WORKS

    // ----------------------------------------
    // RE-INSERT TAMPERED
    // ----------------------------------------

    try {
      newsDTO1.setId(0); // TAMPERED ID
      newsDAO.insert(newsDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      newsDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-UPDATE TAMPERED
    // ----------------------------------------

    try {
      newsDTO1.setId(2); // TAMPERED ID
      newsDAO.update(newsDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      newsDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-DELETE TAMPERED
    // ----------------------------------------

    try {
      newsDTO1.setId(2); // TAMPERED ID
      newsDAO.delete(newsDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      newsDTO1.setId(1); // UNTAMPER ID
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCount() throws Exception {
    ConnectionBean connectionBean;
    NewsDAO newsDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    newsDAO.createTable();

    NewsDTO newsDTO1 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;
    newsDAO.insert(newsDTO1);

    NewsDTO newsDTO2 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO2.setNewsAtt1("2");;
    newsDTO2.setNewsAtt2("22");;
    newsDAO.insert(newsDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    assertEquals(2, newsDAO.countAll());

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAll() throws Exception {
    ConnectionBean connectionBean;
    NewsDAO newsDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    newsDAO.createTable();

    NewsDTO newsDTO1 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;
    newsDAO.insert(newsDTO1);

    NewsDTO newsDTO2 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO2.setNewsAtt1("2");;
    newsDTO2.setNewsAtt2("22");;
    newsDAO.insert(newsDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    List<IDTO> newsList = newsDAO.listAll();
    assertTrue(compareDTO(newsDTO1, (NewsDTO) newsList.get(0)));
    assertTrue(compareDTO(newsDTO2, (NewsDTO) newsList.get(1)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAllLimOff() throws Exception {
    ConnectionBean connectionBean;
    NewsDAO newsDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    newsDAO.createTable();

    NewsDTO newsDTO1 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO1.setNewsAtt1("1");;
    newsDTO1.setNewsAtt2("11");;
    newsDAO.insert(newsDTO1);

    NewsDTO newsDTO2 = (NewsDTO) fd.getDTO( //
        NewsDTO.class, connectionBean);

    newsDTO2.setNewsAtt1("2");;
    newsDTO2.setNewsAtt2("22");;
    newsDAO.insert(newsDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    newsDAO = (NewsDAO) fd.getDAO( //
        NewsDAO.class, connectionBean);

    List<IDTO> newsList;

    newsList = newsDAO.listAll(1, 0);
    assertEquals(1, newsList.size());
    assertTrue(compareDTO(newsDTO1, (NewsDTO) newsList.get(0)));

    newsList = newsDAO.listAll(1, 1);
    assertEquals(1, newsList.size());
    assertTrue(compareDTO(newsDTO2, (NewsDTO) newsList.get(0)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }
}
