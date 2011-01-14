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
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;
import dao.example.base.ProfDAO;
import dao.example.base.ProfDTO;

public class Test4 {

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

      DeptDAO deptDao = (DeptDAO) factoryDAO. //
          getDAO(DeptDAO.class, conn);

      ProfDAO profDao = (ProfDAO) factoryDAO. //
          getDAO(ProfDAO.class, conn);

      // --------------------------------------------------------------------------------
      // CREATE TABLE
      // --------------------------------------------------------------------------------

      deptDao.createTable();
      profDao.createTable();

      DeptDTO deptDto = (DeptDTO) factoryDAO.getDTO( //
          DeptDTO.class, conn);

      deptDto.setDeptAtt1("Foo");
      deptDto.setDeptAtt2("Faa");

      // --------------------------------------------------------------------------------
      // INSERT
      // --------------------------------------------------------------------------------

      deptDao.insert(deptDto);

      // --------------------------------------------------------------------------------
      // Crea 10 empleados
      // --------------------------------------------------------------------------------

      for (int i = 0; i < 10; i++) {
        ProfDTO profDto = (ProfDTO) factoryDAO.getDTO( //
            ProfDTO.class, conn);

        profDto.setProfAtt1("Foo " + i);
        profDto.setProfAtt2("Faa " + i);

        deptDto.getProfDTOList().add(profDto);
        profDto.setDeptDTORef(deptDto);

        profDao.insert(profDto);
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
