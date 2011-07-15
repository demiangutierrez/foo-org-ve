package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionFactory;

/**
 * @author Demián Gutierrez
 * @author Hugo Morillo
 * @date   22/03/2010
 */
public class PreparedStatementExample {

  public static void main(String[] args) //
      throws ClassNotFoundException, SQLException {

    // ----------------------------------------

    Connection conn = ConnectionFactory.getConnection();

    // ----------------------------------------

    try {
      StringBuffer query = new StringBuffer();

      query.append("SELECT * FROM empleado ");
      query.append("WHERE ");
      query.append("cedula = ? OR nombre = ?");

      PreparedStatement ps = conn.prepareStatement(query.toString());

      ps.setInt(1, 105993635);
      ps.setString(2, "foo");

      System.out.println(query.toString());
      System.out.println(ps.toString());

      ResultSet rs = ps.executeQuery();

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
