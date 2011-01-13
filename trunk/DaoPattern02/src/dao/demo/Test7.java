/**
 * Ejemplo de manejo de herencia usando Framework DAO
 * 
 * @author Demián Gutierrez
 * @date   10/01/2011
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

      publDao.createTable();
      bookDao.createTable();
      newsDao.createTable();

      // --------------------------------------------------------------------------------
      // INSERT Book
      // --------------------------------------------------------------------------------

      for (int i = 1; i < 11; i++) {
        BookDTO bookDto = (BookDTO) factoryDAO.getDTO( //
            BookDTO.class, conn);

        bookDto.setPublAtt1("PublAtt1-" + i);
        bookDto.setPublAtt2("PublAtt2-" + i);
        bookDto.setBookAtt1("BookAtt1-" + i);
        bookDto.setBookAtt2("BookAtt2-" + i);

        // bookDao.insert(dtoBook); // Should work the same
        publDao.insert(bookDto);
      }

      // --------------------------------------------------------------------------------
      // INSERT News
      // --------------------------------------------------------------------------------

      for (int i = 1; i < 11; i++) {
        NewsDTO newsDto = (NewsDTO) factoryDAO.getDTO( //
            NewsDTO.class, conn);

        newsDto.setPublAtt1("PublAtt1-" + i);
        newsDto.setPublAtt2("PublAtt2-" + i);
        newsDto.setNewsAtt1("NewsAtt1-" + i);
        newsDto.setNewsAtt2("NewsAtt2-" + i);

        // newsDao.insert(dtoNews); // Should work the same
        publDao.insert(newsDto);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      ConnectionFactory.closeConnection(conn.getConnection());
    }
  }
}
