/**
 * Ejemplo de manejo de relaciones usando Framework DAO
 * 
 * @author Demián Gutierrez
 * @date   13/04/2010
 * 
 */

package dao.demo;

import java.sql.SQLException;
import java.util.List;

import dao.base.api.FactoryDAO;
import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DepartmentDTO;
import dao.example.base.EmployeeDTO;

public class Test5 {

  public static void main(String[] args) throws Exception {

    // --------------------------------------------------------------------------------
    // Obtener Conexion
    // --------------------------------------------------------------------------------

    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    FactoryDAO factoryDAO = AbstractFactoryDAO.getFactoryDAO();

    // --------------------------------------------------------------------------------
    // Instanciar DAO
    // --------------------------------------------------------------------------------

    IDAO depDao = factoryDAO.getDAO( //
        DepartmentDTO.class, conn);

    try {

      List<IDTO> compList = depDao.listAll();

      if (compList.isEmpty()) {
        throw new IllegalStateException("compList.isEmpty()");
      }

      DepartmentDTO departmentDTO = (DepartmentDTO) compList.get(0);

      System.err.println("dep name: " + departmentDTO.getName());
      System.err.println("dep desc: " + departmentDTO.getDescription());

      System.err.println(departmentDTO.getEmployeeDTOList().size());

      // Tiene sentido tratar de crear un nuevo departamento y tratar de añadirlo
      // a la lista aquí antes de cargar la lista???

      // XXX: Not needed right now
      // dd.loadEmployeeList(departmentDTO); // Se carga la lista de empleados...
      System.err.println(departmentDTO.getEmployeeDTOList().size());

      EmployeeDTO doEmp = (EmployeeDTO) factoryDAO.getDTO( //
          EmployeeDTO.class, conn);

      doEmp.setFrstName("NombreCCC ");
      doEmp.setLastName("ApellidoCCC ");
      departmentDTO.getEmployeeDTOList().add(doEmp);
      doEmp.setDepartmentDTORef(departmentDTO);
      // INSERT

      for (EmployeeDTO employeeDTO : departmentDTO.getEmployeeDTOList()) {
        System.err.println("******************************>");
        System.err.println("Nombre: " + employeeDTO.getFrstName());
        System.err.println("Apellido: " + employeeDTO.getLastName());
      }

    } catch (SQLException e) {
      e.printStackTrace();

    } finally {
      ConnectionFactory.closeConnection(conn.getConnection());
    }
  }
}