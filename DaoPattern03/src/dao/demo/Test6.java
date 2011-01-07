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
import dao.example.base.DepartmentDTO;
import dao.example.base.EmployeeDAO;
import dao.example.base.EmployeeDTO;

public class Test6 {

  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    FactoryDAO factoryDAO = AbstractFactoryDAO.getFactoryDAO();

    // --------------------------------------------------------------------------------
    // Instanciar DAO
    // --------------------------------------------------------------------------------

    EmployeeDAO daoEmp = /*  */(EmployeeDAO) /*  */factoryDAO. //
        getDAO(EmployeeDTO.class,/*  */conn);

    try {

      EmployeeDTO employeeDTO = (EmployeeDTO) daoEmp.loadById(5);

      if (employeeDTO == null) {
        throw new IllegalStateException("employeeDTO == null");
      }

      System.err.println("******************************>");
      System.err.println("FrstName: " + employeeDTO.getFrstName());
      System.err.println("LastName: " + employeeDTO.getLastName());

      // --------------------------------------------------------------------------------
      // Lazy load here
      // --------------------------------------------------------------------------------

      DepartmentDTO departmentDTO = employeeDTO.getDepartmentDTORef();

      System.err.println("******************************>");
      System.err.println("departmentDTO ref  : " + departmentDTO);
      System.err.println("departmentDTO id   : " + departmentDTO.getId());
      System.err.println("departmentDTO name : " + departmentDTO.getName());
      System.err.println("departmentDTO desc : " + departmentDTO.getDescription());
    } catch (SQLException e) {
      e.printStackTrace();

    } finally {
      ConnectionFactory.closeConnection(conn.getConnection());
    }
  }
}
