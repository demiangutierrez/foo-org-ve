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
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;
import dao.example.base.ProfDTO;

public class Test5 {

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

      IDAO deptDao = factoryDAO.getDAO( //
          DeptDAO.class, conn);

      List<IDTO> dataList = deptDao.listAll();

      if (dataList.isEmpty()) {
        return;
      }

      DeptDTO deptDTO = (DeptDTO) dataList.get(0);

      System.err.println("Att1: " + deptDTO.getDeptAtt1());
      System.err.println("Att2: " + deptDTO.getDeptAtt2());

      System.err.println("ProfDTO list size: " + deptDTO.getProfDTOList().size());

      ProfDTO profDto = (ProfDTO) factoryDAO.getDTO( //
          ProfDTO.class, conn);

      profDto.setProfAtt1("ProfAtt1 XXX");
      profDto.setProfAtt1("ProfAtt2 XXX");

      deptDTO.getProfDTOList().add(profDto);
      profDto.setDeptDTORef(deptDTO);

    } catch (SQLException e) {
      e.printStackTrace();

    } finally {
      ConnectionFactory.closeConnection(conn.getConnection());
    }
  }
}
