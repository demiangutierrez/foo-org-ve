/**
 * Ejemplo de manejo de herencia usando Framework DAO
 * 
 * @author Demián Gutierrez
 * @date   13/04/2010
 * 
 */

package dao.demo;

import java.sql.SQLException;

import dao.base.api.FactoryDAO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.BookDAO;
import dao.example.base.BookDTO;
import dao.example.base.NewsDAO;
import dao.example.base.NewsDTO;
import dao.example.base.PublicationDAO;

public class Test7 {

  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    FactoryDAO factoryDAO = AbstractFactoryDAO.getFactoryDAO();

    // --------------------------------------------------------------------------------
    // Instanciar DAO
    // --------------------------------------------------------------------------------

    PublicationDAO daoPublication = /*  */(PublicationDAO) /*  */factoryDAO. //
        getDAO(PublicationDAO.class,/*  */conn);

    BookDAO daoBook = /*  */(BookDAO) /*  */factoryDAO. //
        getDAO(BookDAO.class,/*  */conn);

    NewsDAO daoNews = /*  */(NewsDAO) /*  */factoryDAO. //
        getDAO(NewsDAO.class,/*  */conn);

    daoPublication.createTable();
    daoBook.createTable();
    daoNews.createTable();

    // --------------------------------------------------------------------------------

    try {
      for (int i = 1; i < 11; i++) {
        BookDTO dtoBook = (BookDTO) factoryDAO.getDTO( //
            BookDTO.class, conn);

        dtoBook.setManufacturer("BookManufacturer" + i);
        dtoBook.setNumber("BookNumber" + i);
        dtoBook.setDescription("BookDescription" + i);
        dtoBook.setSpeed(1);
        dtoBook.setRating(10);

        // --------------------------------------------------------------------------------
        // INSERT
        // --------------------------------------------------------------------------------

        // daoBook.insert(dtoBook); // Should work the same
        daoPublication.insert(dtoBook);
      }

      for (int i = 1; i < 11; i++) {
        NewsDTO dtoNews = (NewsDTO) factoryDAO.getDTO( //
            NewsDTO.class, conn);

        dtoNews.setManufacturer("NewsManufacturer" + i);
        dtoNews.setNumber("NewsNumber" + i);
        dtoNews.setDescription("NewsDescription" + i);
        dtoNews.setSize(2);
        dtoNews.setType(20);

        // --------------------------------------------------------------------------------
        // INSERT
        // --------------------------------------------------------------------------------

        // daoNews.insert(dtoNews); // Should work the same
        daoPublication.insert(dtoNews);
      }
    } catch (SQLException e) {
      e.printStackTrace();

    } finally {
      ConnectionFactory.closeConnection(conn.getConnection());
    }
  }
}