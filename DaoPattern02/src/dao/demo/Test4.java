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
import dao.example.base.DepartmentDAO;
import dao.example.base.DepartmentDTO;
import dao.example.base.EmployeeDAO;
import dao.example.base.EmployeeDTO;

public class Test4 {

  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    FactoryDAO factoryDAO = AbstractFactoryDAO.getFactoryDAO();

    // --------------------------------------------------------------------------------
    // Instanciar DAO
    // --------------------------------------------------------------------------------

    DepartmentDAO daoDep = /**/(DepartmentDAO) /**/factoryDAO. //
        getDAO(DepartmentDAO.class,/**/conn);

    EmployeeDAO daoEmp = /*  */(EmployeeDAO) /*  */factoryDAO. //
        getDAO(EmployeeDAO.class,/*  */conn);

    // --------------------------------------------------------------------------------
    // CREATE TABLE
    // --------------------------------------------------------------------------------

    daoDep.createTable();
    daoEmp.createTable();

    try {
      DepartmentDTO doComp = (DepartmentDTO) factoryDAO.getDTO( //
          DepartmentDTO.class, conn);

      doComp.setName("Computacion");
      doComp.setDescription("... bla, bla bla ...");

      // --------------------------------------------------------------------------------
      // INSERT
      // --------------------------------------------------------------------------------

      daoDep.insert(doComp);

      // --------------------------------------------------------------------------------
      // Crea 10 empleados
      // --------------------------------------------------------------------------------

      for (int i = 0; i < 10; i++) {
        EmployeeDTO doEmp = (EmployeeDTO) factoryDAO.getDTO( //
            EmployeeDTO.class, conn);

        doEmp.setFrstName("Nombre " + i);
        doEmp.setLastName("Apellido " + i);
        doComp.getEmployeeDTOList().add(doEmp);
        doEmp.setDepartmentDTORef(doComp);
        daoEmp.insert(doEmp);
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
