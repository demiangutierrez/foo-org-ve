/**
 * Ejemplo de manejo de herencia usando Framework DAO
 * 
 * @author Demián Gutierrez
 * @date   10/01/2011
 * 
 */

package dao.demo;

import java.sql.SQLException;
import java.util.List;

import dao.base.api.FactoryDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.BookDAO;
import dao.example.base.BookDTO;
import dao.example.base.NewsDAO;
import dao.example.base.NewsDTO;
import dao.example.base.PublicationDAO;
import dao.example.base.PublicationDTO;

public class Test8 {

  public static void print(PublicationDTO publDto) {
    System.err.println("publDto.getClass(): " + publDto.getClass());

    System.err.println(publDto.getId() + ";" + //
        publDto.getPublAtt1() + ";" + publDto.getPublAtt2());

    if (publDto instanceof BookDTO) {
      BookDTO bookDto = (BookDTO) publDto;

      System.err.println( //
          bookDto.getBookAtt1() + ";" + bookDto.getBookAtt2());
    }

    if (publDto instanceof NewsDTO) {
      NewsDTO newsDto = (NewsDTO) publDto;

      System.err.println( //
          newsDto.getNewsAtt1() + ";" + newsDto.getNewsAtt2());
    }
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    try {
      FactoryDAO factoryDAO = AbstractFactoryDAO.getFactoryDAO();

      // --------------------------------------------------------------------------------
      // Instanciar DAO
      // --------------------------------------------------------------------------------

      PublicationDAO publDao = (PublicationDAO) factoryDAO. //
          getDAO(PublicationDAO.class, conn);

      BookDAO bookDao = (BookDAO) factoryDAO. //
          getDAO(BookDAO.class, conn);

      NewsDAO newsDao = (NewsDAO) factoryDAO. //
          getDAO(NewsDAO.class, conn);

      // --------------------------------------------------------------------------------
      // SELECT COUNT
      // --------------------------------------------------------------------------------

      System.err.println("count publ: " + publDao.countAll());
      System.err.println("count book: " + bookDao.countAll());
      System.err.println("count news: " + newsDao.countAll());

      System.err.println("**********************************");

      // --------------------------------------------------------------------------------
      // SELECT ALL
      // --------------------------------------------------------------------------------

      // List<DataObject> dataList = dd.listAll(3, 3);
      List<IDTO> dtoList = publDao.listAll();

      for (IDTO dto : dtoList) {
        PublicationDTO publDto = (PublicationDTO) dto;

        print(publDto);
      }

      System.err.println("**********************************");

      // --------------------------------------------------------------------------------
      // SELECT BY ID
      // --------------------------------------------------------------------------------

      // A book
      BookDTO bookDTO = (BookDTO) publDao.loadById(5);
      print(bookDTO);

      // A news
      NewsDTO newsDTO = (NewsDTO) publDao.loadById(15);
      print(newsDTO);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {

      // --------------------------------------------------------------------------------
      // Cerrar Conexion
      // --------------------------------------------------------------------------------

      ConnectionFactory.closeConnection(conn.getConnection());
    }
  }
}
