/**
 * Ejemplo de CREATE TABLE y INSERT usando Framework DAO
 * 
 * @author Preparador Hugo Morillo
 * @date   25/03/2010
 * 
 */

package dao.demo;

import dao.base.api.FactoryDAO;
import dao.base.api.IDAO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DeptDAO;
import dao.example.base.ProfDAO;

public class TestX {

  public static void main(String[] args) throws Exception {
    ConnectionBean conn = ConnectionFactory.getConnectionBean();
    FactoryDAO factoryDAO = AbstractFactoryDAO.getFactoryDAO();

    IDAO dd = factoryDAO.getDAO( //
        DeptDAO.class, conn);

    IDAO ee = factoryDAO.getDAO( //
        ProfDAO.class, conn);

    dd.createTable();
    ee.createTable();

    ConnectionFactory.closeConnection(conn.getConnection());
  }
}
