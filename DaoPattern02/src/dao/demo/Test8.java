/**
 * Ejemplo de SELECT usando Framework DAO
 * 
 * @author Preparador Hugo Morillo
 * @date   25/03/2010
 * 
 */

package dao.demo;

import java.sql.SQLException;
import java.util.List;

import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.BookDTO;
import dao.example.base.NewsDTO;
import dao.example.base.PublicationDAO;
import dao.example.base.PublicationDTO;

// TODO: WRONG: DOES NOT LOAD THE RIGHT CHILD CLASS, LOADS PART INSTANCES
public class Test8 {

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

      // --------------------------------------------------------------------------------
      // SELECT ALL
      // --------------------------------------------------------------------------------

      //List<DataObject> dataList = dd.listAll(3, 3);

      List<IDTO> dataList = pd.listAll();

      for (IDTO dto : dataList) {
        PublicationDTO ddo = (PublicationDTO) dto;
        System.err.println(ddo.getClass());

        System.err.println(ddo.getId() + ";" + //
            ddo.getManufacturer() + ";" + ddo.getNumber() + ";" + ddo.getDescription());

        if (ddo instanceof BookDTO) {
          BookDTO bookDTO = (BookDTO) ddo;

          System.err.println(bookDTO.getSpeed() + ";" + //
              bookDTO.getRating());
        }

        if (ddo instanceof NewsDTO) {
          NewsDTO newsDTO = (NewsDTO) ddo;

          System.err.println(newsDTO.getSize() + ";" + //
              newsDTO.getType());
        }
      }

      System.err.println("**********************************");

      // --------------------------------------------------------------------------------
      // SELECT BY ID
      // --------------------------------------------------------------------------------

      // A book
      BookDTO bookDTO = (BookDTO) pd.loadById(5);

      System.err.println(bookDTO.getClass());
      System.err.println(bookDTO.getId() + ";" + //
          bookDTO.getManufacturer() + ";" + bookDTO.getNumber() + ";" + bookDTO.getDescription());
      System.err.println(bookDTO.getSpeed() + ";" + //
          bookDTO.getRating());

      // A news
      NewsDTO newsDTO = (NewsDTO) pd.loadById(15);

      System.err.println(newsDTO.getClass());
      System.err.println(newsDTO.getId() + ";" + //
          newsDTO.getManufacturer() + ";" + newsDTO.getNumber() + ";" + newsDTO.getDescription());
      System.err.println(newsDTO.getSize() + ";" + //
          newsDTO.getType());

    } catch (SQLException e) {
      System.err.println("Error: " + e.getMessage());
    } finally {

      // --------------------------------------------------------------------------------
      // Cerrar Conexion
      // --------------------------------------------------------------------------------

      ConnectionFactory.closeConnection(conn.getConnection());
    }
  }
}
