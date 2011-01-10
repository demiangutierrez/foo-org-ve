/**
 * Ejemplo de UPDATE y DELETE usando Framework DAO
 * 
 * @author Preparador Hugo Morillo
 * @date   25/03/2010
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

//TODO: WRONG: DOES NOT LOAD THE RIGHT CHILD CLASS, LOADS PART INSTANCES
public class Test9 {

  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    // --------------------------------------------------------------------------------
    // Instanciar DAO
    // --------------------------------------------------------------------------------

    IDAO pd = AbstractFactoryDAO.getFactoryDAO(). //
        getDAO(PublicationDAO.class, conn);

    try {
      PublicationDTO pdo;

      pdo = (PublicationDTO) pd.loadById(6);

      // I know this is a book:

      BookDTO bookDTO = (BookDTO) pdo;

      bookDTO.setManufacturer("BookNewManufacturer");
      bookDTO.setNumber("BookNewNumber");
      bookDTO.setDescription("BookNewDescription");
      bookDTO.setSpeed(100);
      bookDTO.setRating(1000);

      // --------------------------------------------------------------------------------
      // UPDATE
      // --------------------------------------------------------------------------------

      pd.update(pdo);

      pdo = (PublicationDTO) pd.loadById(16);

      // I know this is a book:

      NewsDTO newsDTO = (NewsDTO) pdo;

      newsDTO.setManufacturer("NewsNewManufacturer");
      newsDTO.setNumber("NewNumber");
      newsDTO.setDescription("NewsNewDescription");
      newsDTO.setSize(100);
      newsDTO.setType(1000);

      // --------------------------------------------------------------------------------
      // UPDATE
      // --------------------------------------------------------------------------------

      pd.update(pdo);

      // --------------------------------------------------------------------------------
      // DELETE
      // --------------------------------------------------------------------------------

      for (int i = 1; i < 6; i++) {
        PublicationDTO ddoo = (PublicationDTO) pd.loadById(i);
        pd.delete(ddoo);
      }

      // --------------------------------------------------------------------------------
      // DELETE
      // --------------------------------------------------------------------------------

      for (int i = 11; i < 16; i++) {
        PublicationDTO ddoo = (PublicationDTO) pd.loadById(i);
        pd.delete(ddoo);
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
