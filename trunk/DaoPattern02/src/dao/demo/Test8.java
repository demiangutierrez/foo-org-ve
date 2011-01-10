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
      }

      System.err.println("**********************************");

      // --------------------------------------------------------------------------------
      // SELECT BY ID
      // --------------------------------------------------------------------------------

      PublicationDTO ddo;

      // A book
      ddo = (PublicationDTO) pd.loadById(5);

      System.err.println(ddo.getClass());
      System.err.println(ddo.getId() + ";" + //
          ddo.getManufacturer() + ";" + ddo.getNumber() + ";" + ddo.getDescription());

      // A news
      ddo = (PublicationDTO) pd.loadById(15);

      System.err.println(ddo.getClass());
      System.err.println(ddo.getId() + ";" + //
          ddo.getManufacturer() + ";" + ddo.getNumber() + ";" + ddo.getDescription());
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
