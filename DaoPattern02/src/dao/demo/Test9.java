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
      PublicationDTO pdo = (PublicationDTO) pd.loadById(6);

      pdo.setManufacturer("NewManufacturer");
      pdo.setNumber("NewNumber");
      pdo.setDescription("NewDescription");

      // --------------------------------------------------------------------------------
      // UPDATE
      // --------------------------------------------------------------------------------

      pd.update(pdo);

      for (int i = 1; i < 6; i++) {
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
