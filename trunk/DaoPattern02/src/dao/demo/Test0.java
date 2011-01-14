/**
 * Ejemplo de CREATE TABLE y INSERT (Sin usar el factory)
 * 
 * @author Demián Gutierrez
 * @date   26/10/2010
 * 
 */
package dao.demo;

import java.sql.SQLException;

import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.mysql.DeptDAOImpl;
import dao.example.mysql.DeptDTOImpl;

public class Test0 {

  // Avoids using factory on purpose
  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    // --------------------------------------------------------------------------------
    // Instanciar DAO
    // --------------------------------------------------------------------------------

    try {
      DeptDAOImpl deptDao = new DeptDAOImpl();
      deptDao.init(conn);

      // --------------------------------------------------------------------------------
      // CREATE TABLE
      // --------------------------------------------------------------------------------

      deptDao.createTable();

      // --------------------------------------------------------------------------------
      // INSERT
      // --------------------------------------------------------------------------------

      for (int i = 0; i < 11; i++) {
        DeptDTOImpl deptDto = new DeptDTOImpl();

        deptDto.setDeptAtt1("Foo" + i);
        deptDto.setDeptAtt2("Faa" + i);

        deptDao.insert(deptDto);
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
