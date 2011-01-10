/**
 * Ejemplo de manejo de relaciones usando Framework DAO
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
import dao.example.base.PartDAO;
import dao.example.base.TireDAO;
import dao.example.base.TireDTO;

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

    TireDAO daoTire = /*  */(TireDAO) /*  */factoryDAO. //
        getDAO(TireDAO.class,/*  */conn);

    PartDAO daoPart = /*  */(PartDAO) /*  */factoryDAO. //
        getDAO(PartDAO.class,/*  */conn);

    daoPart.createTable();
    daoTire.createTable();

    TireDTO dtoTire = (TireDTO) factoryDAO.getDTO( //
        TireDTO.class, conn);

    try {

      dtoTire.setManufacturer("Some Manufacturer");
      dtoTire.setNumber("x1b123");
      dtoTire.setDescription("Some Description");
      dtoTire.setSpeed(1);
      dtoTire.setRating(10);

      daoTire.insert(dtoTire);
    } catch (SQLException e) {
      e.printStackTrace();

    } finally {
      ConnectionFactory.closeConnection(conn.getConnection());
    }
  }
}