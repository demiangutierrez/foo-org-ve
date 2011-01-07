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
import dao.example.base.DepartmentDAO;
import dao.example.base.DepartmentDTO;

public class Test3 {

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
      DepartmentDTO ddo = (DepartmentDTO) dd.loadById(10);

      ddo.setName("NewName");
      ddo.setDescription("NewDescription");

      // --------------------------------------------------------------------------------
      // UPDATE
      // --------------------------------------------------------------------------------

      dd.update(ddo);

      for (int i = 1; i < 5; i++) {
        DepartmentDTO ddoo = (DepartmentDTO) dd.loadById(i);
        dd.delete(ddoo);
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
