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
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;

public class Test2 {

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

      // --------------------------------------------------------------------------------
      // SELECT ALL
      // --------------------------------------------------------------------------------

      // List<DataObject> dataList = dd.listAll(3, 3);

      List<IDTO> dataList = deptDao.listAll();

      for (IDTO dto : dataList) {
        DeptDTO deptDto = (DeptDTO) dto;

        System.err.println(deptDto.getId() + ";" + //
            deptDto.getDeptAtt1() + ";" + deptDto.getDeptAtt2());
      }

      System.err.println("**********************************");

      // --------------------------------------------------------------------------------
      // SELECT BY ID
      // --------------------------------------------------------------------------------

      DeptDTO deptDto = (DeptDTO) deptDao.loadById(5);

      System.err.println(deptDto.getId() + ";" + //
          deptDto.getDeptAtt1() + ";" + deptDto.getDeptAtt1());

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
