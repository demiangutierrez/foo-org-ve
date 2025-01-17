/**
 * Ejemplo de CREATE TABLE y INSERT usando Framework DAO
 * 
 * @author Preparador Hugo Morillo
 * @date   25/03/2010
 * 
 */
package dao.demo;

import java.sql.SQLException;

import dao.base.api.FactoryDAO;
import dao.base.api.IDAO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DepartmentDTO;

public class Test1 {

  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    FactoryDAO factoryDAO = AbstractFactoryDAO.getFactoryDAO();

    // --------------------------------------------------------------------------------
    // Instanciar DAO
    // --------------------------------------------------------------------------------

    IDAO dd = factoryDAO.getDAO( //
        DepartmentDTO.class, conn);

    // --------------------------------------------------------------------------------
    // CREATE TABLE
    // --------------------------------------------------------------------------------

    dd.createTable();

    try {
      for (int i = 0; i < 10; i++) {
        DepartmentDTO ddo = (DepartmentDTO) factoryDAO.getDTO( //
            DepartmentDTO.class, conn);

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
