/**
 * Ejemplo de CREATE TABLE y INSERT usando Framework DAO
 * 
 * @author Preparador Hugo Morillo
 * @date   25/03/2010
 * 
 */

package demo;

import java.sql.SQLException;

import base.AbstractFactoryDAO;
import base.UsuarioDAO;
import base.UsuarioDTO;
import dao.base.api.FactoryDAO;
import dao.base.api.IDAO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;

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

      IDAO dd = factoryDAO.getDAO( //
          UsuarioDAO.class, conn);

      // --------------------------------------------------------------------------------
      // CREATE TABLE
      // --------------------------------------------------------------------------------

      dd.createTable();

      for (int i = 1; i < 11; i++) {
        UsuarioDTO ddo = (UsuarioDTO) factoryDAO.getDTO( //
            UsuarioDTO.class, conn);

        ddo.setLogin("xxx" + i);
        ddo.setPass("yyy" + i);

        // --------------------------------------------------------------------------------
        // INSERT
        // --------------------------------------------------------------------------------

        dd.insert(ddo);
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
