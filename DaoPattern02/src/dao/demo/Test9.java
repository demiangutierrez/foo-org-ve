/**
 * Ejemplo de manejo de herencia usando Framework DAO
 * 
 * @author Demián Gutierrez
 * @date   10/01/2011
 * 
 */

package dao.demo;

import java.sql.SQLException;

import dao.base.api.IDAO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.BookDTO;
import dao.example.base.NewsDTO;
import dao.example.base.PublicationDAO;
import dao.example.base.PublicationDTO;

public class Test9 {

  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    try {

      // --------------------------------------------------------------------------------
      // Instanciar DAO
      // --------------------------------------------------------------------------------

      IDAO publDao = AbstractFactoryDAO.getFactoryDAO(). //
          getDAO(PublicationDAO.class, conn);

      PublicationDTO publDto;

      // --------------------------------------------------------------------------------
      // UPDATE Book
      // --------------------------------------------------------------------------------

      publDto = (PublicationDTO) publDao.loadById(6);

      // I know this is a book:

      BookDTO bookDto = (BookDTO) publDto;

      bookDto.setPublAtt1("NewPublAtt1");
      bookDto.setPublAtt2("NewPublAtt2");
      bookDto.setBookAtt1("NewBookAtt1");
      bookDto.setBookAtt2("NewBookAtt2");

      publDao.update(publDto);

      // --------------------------------------------------------------------------------
      // UPDATE News
      // --------------------------------------------------------------------------------

      publDto = (PublicationDTO) publDao.loadById(16);

      // I know this is a news:

      NewsDTO newsDto = (NewsDTO) publDto;

      newsDto.setPublAtt1("NewPublAtt1");
      newsDto.setPublAtt2("NewPublAtt2");
      newsDto.setNewsAtt1("NewNewsAtt1");
      newsDto.setNewsAtt2("NewNewsAtt2");

      publDao.update(publDto);

      // --------------------------------------------------------------------------------
      // DELETE Book
      // --------------------------------------------------------------------------------

      for (int i = 1; i < 6; i++) {
        PublicationDTO ddoo = (PublicationDTO) publDao.loadById(i);
        publDao.delete(ddoo);
      }

      // --------------------------------------------------------------------------------
      // DELETE News
      // --------------------------------------------------------------------------------

      for (int i = 11; i < 16; i++) {
        PublicationDTO ddoo = (PublicationDTO) publDao.loadById(i);
        publDao.delete(ddoo);
      }

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
