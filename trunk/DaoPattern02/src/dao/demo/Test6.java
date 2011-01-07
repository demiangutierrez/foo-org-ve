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
        getDAO(EmployeeDAO.class,/*  */conn);

    try {

      EmployeeDTO employeeDTO = (EmployeeDTO) daoEmp.loadById(5); // Cargar un empleado cualquiera (por id)

      if (employeeDTO == null) {
        return;
      }

      System.err.println("******************************>");
      System.err.println("El nombre: " + employeeDTO.getFrstName());
      System.err.println("El apellido: " + employeeDTO.getLastName());

      // Ahora necesitamos el departamento asociado al empleado...
      // Pero la lista referencia está vacía... hay que cargarla
      System.err.println("******************************>");
      //System.err.println("El id (sólo por curiosidad): " + employeeDTO.getDepartmentDTORef().getId());
      System.err.println("La referencia: " + employeeDTO.getDepartmentDTORef());

      // Tiene sentido apuntar el empleado a otro departamento antes de cargar la referencia?

      // daoEmp.loadDepartmentRef(employeeDTO); // Se carga la referencia al departamento

      System.err.println("******************************>");
      System.err.println("El id (sólo por curiosidad): " + employeeDTO.getDepartmentDTORef().getId());
      System.err.println("La referencia: " + employeeDTO.getDepartmentDTORef());

      DepartmentDTO departmentDTO = employeeDTO.getDepartmentDTORef();

      System.err.println("******************************>");
      System.err.println("El nombre del departamento es: " + departmentDTO.getName());
      System.err.println("La descripción del departamento es: " + departmentDTO.getDescription());
    } catch (SQLException e) {
      e.printStackTrace();

    } finally {
      ConnectionFactory.closeConnection(conn.getConnection());
    }
  }
}