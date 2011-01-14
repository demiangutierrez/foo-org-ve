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
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;

public class Test3 {

  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    // --------------------------------------------------------------------------------
    // Instanciar DAO
    // --------------------------------------------------------------------------------

    try {
      IDAO deptDao = AbstractFactoryDAO.getFactoryDAO(). //
          getDAO(DeptDAO.class, conn);

      DeptDTO deptDtoUpd = (DeptDTO) deptDao.loadById(10);

      deptDtoUpd.setDeptAtt1("NewFoo");
      deptDtoUpd.setDeptAtt2("NewFaa");

      // --------------------------------------------------------------------------------
      // UPDATE
      // --------------------------------------------------------------------------------

      deptDao.update(deptDtoUpd);

      for (int i = 1; i < 5; i++) {
        DeptDTO deptDtoDel = (DeptDTO) deptDao.loadById(i);
        deptDao.delete(deptDtoDel);
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
