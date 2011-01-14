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
import dao.example.base.DeptDTO;
import dao.example.base.ProfDAO;
import dao.example.base.ProfDTO;

public class Test6 {

  // TODO: Check this example, there is something wrong with it
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

      ProfDAO profDao = (ProfDAO) factoryDAO. //
          getDAO(ProfDAO.class, conn);

      ProfDTO profDTO = (ProfDTO) profDao.loadById(5); // Cargar un empleado cualquiera (por id)

      if (profDTO == null) {
        return;
      }

      System.err.println("******************************>");
      System.err.println("Att1: " + profDTO.getProfAtt1());
      System.err.println("Att2: " + profDTO.getProfAtt2());

      // Ahora necesitamos el departamento asociado al empleado...
      // Pero la lista referencia está vacía... hay que cargarla
      System.err.println("******************************>");
      //System.err.println("El id (sólo por curiosidad): " + profDTO.getDeptDTORef().getId());
      System.err.println("La referencia: " + profDTO.getDeptDTORef());

      // Tiene sentido apuntar el empleado a otro departamento antes de cargar la referencia?

      // daoEmp.loadDeptRef(profDTO); // Se carga la referencia al departamento

      System.err.println("******************************>");
      System.err.println(" id: " + profDTO.getDeptDTORef().getId());
      System.err.println("ref: " + profDTO.getDeptDTORef());

      DeptDTO deptDTO = profDTO.getDeptDTORef();

      System.err.println("******************************>");
      System.err.println("Att1: " + deptDTO.getDeptAtt1());
      System.err.println("Att2: " + deptDTO.getDeptAtt2());
    } catch (SQLException e) {
      e.printStackTrace();

    } finally {
      ConnectionFactory.closeConnection(conn.getConnection());
    }
  }
}