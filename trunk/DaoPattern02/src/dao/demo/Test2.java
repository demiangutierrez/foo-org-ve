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
import dao.example.base.DepartmentDAO;
import dao.example.base.DepartmentDTO;

public class Test2 {

  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    // --------------------------------------------------------------------------------
    // Instanciar DAO
    // --------------------------------------------------------------------------------

    IDAO dd = AbstractFactoryDAO.getFactoryDAO(). //
        getDAO(DepartmentDAO.class, conn);

    try {

      // --------------------------------------------------------------------------------
      // SELECT ALL
      // --------------------------------------------------------------------------------

      //List<DataObject> dataList = dd.listAll(3, 3);

      List<IDTO> dataList = dd.listAll();

      for (IDTO dto : dataList) {
        DepartmentDTO ddo = (DepartmentDTO) dto;
        System.err.println(ddo.getId() + ";" + ddo.getName() + ";" + ddo.getDescription());
      }

      System.err.println("**********************************");

      // --------------------------------------------------------------------------------
      // SELECT BY ID
      // --------------------------------------------------------------------------------

      DepartmentDTO ddo = (DepartmentDTO) dd.loadById(5);

      System.err.println(ddo.getId() + ";" + ddo.getName() + ";" + ddo.getDescription());
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
