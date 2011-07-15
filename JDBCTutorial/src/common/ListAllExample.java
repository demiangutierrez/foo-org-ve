package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionFactory;

/**
 * @author Demián Gutierrez
 * @author Hugo Morillo
 * @date   22/03/2010
 */
public class ListAllExample {

  public static void main(String[] args) //
      throws ClassNotFoundException, SQLException {

    // ----------------------------------------

    Connection conn = ConnectionFactory.getConnection();

    // ----------------------------------------

    try {
      StringBuffer query = new StringBuffer();

      query.append("SELECT * ");
      query.append("FROM empleado ");
      query.append("WHERE cedula = 105993635");

      System.out.println(query.toString());

      ResultSet rs = conn.createStatement().executeQuery(query.toString());

      while (rs.next()) {
        System.err.println( //
            rs.getInt("id") + ", " + //
                rs.getString("nombre") + ", " + //
                rs.getInt("cedula") + ", " + //
                rs.getTimestamp("rfecha"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      conn.close();
    }
  }
}
