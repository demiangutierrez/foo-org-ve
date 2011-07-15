package common;

import java.sql.Connection;
import java.sql.SQLException;

import connection.ConnectionFactory;

/**
 * @author Demián Gutierrez
 * @author Hugo Morillo
 * @date   22/03/2010
 */
public class DeleteExample {

  public static void main(String[] args) //
      throws ClassNotFoundException, SQLException {

    // ----------------------------------------

    Connection conn = ConnectionFactory.getConnection();

    // ----------------------------------------

    try {
      StringBuffer query = new StringBuffer();

      query.append("DELETE FROM empleado ");
      query.append("WHERE ");
      query.append("cedula = 105993635");

      System.out.println(query.toString());

      conn.createStatement().executeUpdate(query.toString());

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      conn.close();
    }
  }
}
