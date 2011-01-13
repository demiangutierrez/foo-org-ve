package dao.test;

import java.util.List;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.BookDAO;
import dao.example.base.BookDTO;

/**
 * @author Demián Gutierrez
 */
public class TestBook extends TestCase {

  private boolean compareDTO(BookDTO bookDTO1, BookDTO bookDTO2) {
    boolean ret = true;

    ret = ret && bookDTO1.getId() == bookDTO2.getId();
    ret = ret && bookDTO1.getBookAtt1().equals(bookDTO2.getBookAtt1());
    ret = ret && bookDTO1.getBookAtt2().equals(bookDTO2.getBookAtt2());

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testInsertLoad() throws Exception {
    ConnectionBean connectionBean;
    BookDAO bookDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    bookDAO.createTable();

    BookDTO bookDTO1 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;
    bookDAO.insert(bookDTO1);
    assertEquals(bookDTO1.getId(), 1);

    BookDTO bookDTO2 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO2.setBookAtt1("2");;
    bookDTO2.setBookAtt2("22");;
    bookDAO.insert(bookDTO2);
    assertEquals(bookDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    BookDTO bookDTOX = (BookDTO) bookDAO.loadById(1);
    assertTrue(compareDTO(bookDTO1, bookDTOX));
    assertNotSame(bookDTO1, bookDTOX);

    BookDTO bookDTOY = (BookDTO) bookDAO.loadById(2);
    assertTrue(compareDTO(bookDTO2, bookDTOY));
    assertNotSame(bookDTO2, bookDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testUpdateLoad() throws Exception {
    ConnectionBean connectionBean;
    BookDAO bookDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    bookDAO.createTable();

    BookDTO bookDTO1 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;
    bookDAO.insert(bookDTO1);
    assertEquals(bookDTO1.getId(), 1);

    BookDTO bookDTO2 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO2.setBookAtt1("2");;
    bookDTO2.setBookAtt2("22");;
    bookDAO.insert(bookDTO2);
    assertEquals(bookDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / UPDATE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    BookDTO bookDTOX = (BookDTO) bookDAO.loadById(1);
    bookDTOX.setBookAtt1("3");;
    bookDTOX.setBookAtt2("33");;
    bookDAO.update(bookDTOX); // Changes the 111

    BookDTO bookDTOY = (BookDTO) bookDAO.loadById(2);
    bookDTOY.setBookAtt1("4");;
    bookDTOY.setBookAtt2("44");;
    bookDAO.update(bookDTOY); // Changes the 222

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    BookDTO bookDTOA = (BookDTO) bookDAO.loadById(1);
    assertTrue(compareDTO(bookDTOX, bookDTOA));
    assertNotSame(bookDTOX, bookDTOA);

    BookDTO bookDTOB = (BookDTO) bookDAO.loadById(2);
    assertTrue(compareDTO(bookDTOY, bookDTOB));
    assertNotSame(bookDTOY, bookDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteLoad() throws Exception {
    ConnectionBean connectionBean;
    BookDAO bookDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    bookDAO.createTable();

    BookDTO bookDTO1 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;
    bookDAO.insert(bookDTO1);
    assertEquals(bookDTO1.getId(), 1);

    BookDTO bookDTO2 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO2.setBookAtt1("2");;
    bookDTO2.setBookAtt2("22");;
    bookDAO.insert(bookDTO2);
    assertEquals(bookDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    BookDTO bookDTOX = (BookDTO) bookDAO.loadById(1);
    bookDAO.delete(bookDTOX);

    BookDTO bookDTOY = (BookDTO) bookDAO.loadById(2);
    bookDAO.delete(bookDTOY);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    BookDTO bookDTOA = (BookDTO) bookDAO.loadById(1);
    assertNull(bookDTOA);

    BookDTO bookDTOB = (BookDTO) bookDAO.loadById(2);
    assertNull(bookDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCacheLoad() throws Exception {
    ConnectionBean connectionBean;
    BookDAO bookDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    bookDAO.createTable();

    BookDTO bookDTO1 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;
    bookDAO.insert(bookDTO1);
    assertEquals(bookDTO1.getId(), 1);

    BookDTO bookDTO2 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO2.setBookAtt1("2");;
    bookDTO2.setBookAtt2("22");;
    bookDAO.insert(bookDTO2);
    assertEquals(bookDTO2.getId(), 2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / RE-LOAD
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    BookDTO bookDTOX = (BookDTO) bookDAO.loadById(1);
    BookDTO bookDTOA = (BookDTO) bookDAO.loadById(1);
    assertTrue(compareDTO(bookDTOX, bookDTOA));
    assertSame(bookDTOX, bookDTOA);

    BookDTO bookDTOY = (BookDTO) bookDAO.loadById(2);
    BookDTO bookDTOB = (BookDTO) bookDAO.loadById(2);
    assertTrue(compareDTO(bookDTOY, bookDTOB));
    assertSame(bookDTOY, bookDTOB);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testDeleteReInsert() throws Exception {
    ConnectionBean connectionBean;
    BookDAO bookDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    bookDAO.createTable();

    BookDTO bookDTO1 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;
    bookDAO.insert(bookDTO1);
    assertEquals(bookDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    BookDTO bookDTOX = (BookDTO) bookDAO.loadById(1);
    bookDAO.delete(bookDTOX);

    try {
      bookDAO.insert(bookDTOX);
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
    BookDAO bookDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    bookDAO.createTable();

    BookDTO bookDTO1 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;
    bookDAO.insert(bookDTO1);
    assertEquals(bookDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    BookDTO bookDTOX = (BookDTO) bookDAO.loadById(1);
    bookDAO.delete(bookDTOX);

    try {
      bookDAO.update(bookDTOX);
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
    BookDAO bookDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    bookDAO.createTable();

    BookDTO bookDTO1 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;
    bookDAO.insert(bookDTO1);
    assertEquals(bookDTO1.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / DELETE / RE-DELETE
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    BookDTO bookDTOX = (BookDTO) bookDAO.loadById(1);
    bookDAO.delete(bookDTOX);

    try {
      bookDAO.delete(bookDTOX);
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
    BookDAO bookDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    bookDAO.createTable();

    BookDTO bookDTO1 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;

    BookDTO bookDTO2 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;

    // ----------------------------------------
    // UPDATE NON INSERTED
    // ----------------------------------------

    try {
      bookDAO.update(bookDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // DELETE NON INSERTED
    // ----------------------------------------

    try {
      bookDAO.delete(bookDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
    }

    // ----------------------------------------
    // INSERT TAMPERED
    // ----------------------------------------

    try {
      bookDTO1.setId(1); // TAMPERED ID
      bookDAO.insert(bookDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      bookDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // UPDATE TAMPERED
    // ----------------------------------------

    try {
      bookDTO1.setId(1); // TAMPERED ID
      bookDAO.update(bookDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      bookDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DELETE TAMPERED
    // ----------------------------------------

    try {
      bookDTO1.setId(1); // TAMPERED ID
      bookDAO.delete(bookDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      bookDTO1.setId(0); // UNTAMPER ID
    }

    // ----------------------------------------
    // DTO INSERT
    // ----------------------------------------

    bookDAO.insert(bookDTO1); // WORKS
    bookDAO.insert(bookDTO2); // WORKS

    // ----------------------------------------
    // RE-INSERT TAMPERED
    // ----------------------------------------

    try {
      bookDTO1.setId(0); // TAMPERED ID
      bookDAO.insert(bookDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      bookDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-UPDATE TAMPERED
    // ----------------------------------------

    try {
      bookDTO1.setId(2); // TAMPERED ID
      bookDAO.update(bookDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      bookDTO1.setId(1); // UNTAMPER ID
    }

    // ----------------------------------------
    // RE-DELETE TAMPERED
    // ----------------------------------------

    try {
      bookDTO1.setId(2); // TAMPERED ID
      bookDAO.delete(bookDTO1);
      fail("Should raise an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Exception (test OK)
      System.err.println(e.getClass() + " : " + e.getMessage());
      bookDTO1.setId(1); // UNTAMPER ID
    }

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testCount() throws Exception {
    ConnectionBean connectionBean;
    BookDAO bookDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    bookDAO.createTable();

    BookDTO bookDTO1 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;
    bookDAO.insert(bookDTO1);

    BookDTO bookDTO2 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO2.setBookAtt1("2");;
    bookDTO2.setBookAtt2("22");;
    bookDAO.insert(bookDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    assertEquals(2, bookDAO.countAll());

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAll() throws Exception {
    ConnectionBean connectionBean;
    BookDAO bookDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    bookDAO.createTable();

    BookDTO bookDTO1 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;
    bookDAO.insert(bookDTO1);

    BookDTO bookDTO2 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO2.setBookAtt1("2");;
    bookDTO2.setBookAtt2("22");;
    bookDAO.insert(bookDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    List<IDTO> bookList = bookDAO.listAll();
    assertTrue(compareDTO(bookDTO1, (BookDTO) bookList.get(0)));
    assertTrue(compareDTO(bookDTO2, (BookDTO) bookList.get(1)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testListAllLimOff() throws Exception {
    ConnectionBean connectionBean;
    BookDAO bookDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------
    // INSERT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    bookDAO.createTable();

    BookDTO bookDTO1 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO1.setBookAtt1("1");
    bookDTO1.setBookAtt2("11");;
    bookDAO.insert(bookDTO1);

    BookDTO bookDTO2 = (BookDTO) fd.getDTO( //
        BookDTO.class, connectionBean);

    bookDTO2.setBookAtt1("2");;
    bookDTO2.setBookAtt2("22");;
    bookDAO.insert(bookDTO2);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // COUNT
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    bookDAO = (BookDAO) fd.getDAO( //
        BookDAO.class, connectionBean);

    List<IDTO> bookList;

    bookList = bookDAO.listAll(1, 0);
    assertEquals(1, bookList.size());
    assertTrue(compareDTO(bookDTO1, (BookDTO) bookList.get(0)));

    bookList = bookDAO.listAll(1, 1);
    assertEquals(1, bookList.size());
    assertTrue(compareDTO(bookDTO2, (BookDTO) bookList.get(0)));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }
}
