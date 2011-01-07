/**
 * Ejemplo de CREATE TABLE y INSERT usando Framework DAO
 * 
 * @author Preparador Hugo Morillo
 * @date   25/03/2010
 * 
 */

package dao.demo;

import java.sql.SQLException;

import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.mysql.DepartmentDAOImpl;
import dao.example.mysql.DepartmentDTOImpl;

public class Test0 {

  // Avoids using factory on purpose
  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    // FactoryDAO factoryDAO = AbstractFactoryDAO.getFactoryDAO();

    // --------------------------------------------------------------------------------
    // Instanciar DAO
    // --------------------------------------------------------------------------------

    //    DataAccessObject dd = factoryDAO.getDAO( //
    //        DepartmentDAO.class, conn);
    DepartmentDAOImpl dd = new DepartmentDAOImpl();
    dd.init(conn);

    // --------------------------------------------------------------------------------
    // CREATE TABLE
    // --------------------------------------------------------------------------------

    dd.createTable();

    try {
      for (int i = 0; i < 11; i++) {
        //        DepartmentDTO ddo = (DepartmentDTO) factoryDAO.getDTO( //
        //            DepartmentDTO.class, conn);
        DepartmentDTOImpl ddo = new DepartmentDTOImpl();

        ddo.setName("Foo" + i);
        ddo.setDescription("Faa" + i);

        // --------------------------------------------------------------------------------
        // INSERT
        // --------------------------------------------------------------------------------

        dd.insert(ddo);
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
