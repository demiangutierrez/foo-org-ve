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
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;

public class Test1 {

  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    try {
      FactoryDAO factoryDAO = AbstractFactoryDAO.getFactoryDAO();

      // --------------------------------------------------------------------------------
      // Instanciar DAO
      // --------------------------------------------------------------------------------

      IDAO deptDao = factoryDAO.getDAO( //
          DeptDAO.class, conn);

      // --------------------------------------------------------------------------------
      // CREATE TABLE
      // --------------------------------------------------------------------------------

      deptDao.createTable();

      for (int i = 1; i < 11; i++) {
        DeptDTO deptDto = (DeptDTO) factoryDAO.getDTO( //
            DeptDTO.class, conn);

        deptDto.setDeptAtt1("Foo" + i);
        deptDto.setDeptAtt2("Faa" + i);

        // --------------------------------------------------------------------------------
        // INSERT
        // --------------------------------------------------------------------------------

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
