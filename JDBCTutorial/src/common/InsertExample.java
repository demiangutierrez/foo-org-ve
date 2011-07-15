package common;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import connection.ConnectionFactory;

/**
 * @author Demián Gutierrez
 * @author Hugo Morillo
 * @date   22/03/2010
 */
public class InsertExample {

  public static void main(String[] args) //
      throws ClassNotFoundException, SQLException {

    // ----------------------------------------

    Connection conn = ConnectionFactory.getConnection();

    // ----------------------------------------

    try {
      StringBuffer query = new StringBuffer();

      // ----------------------------------------

      Calendar cal = Calendar.getInstance();
      Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
      System.err.println(timestamp.toString());

      // ----------------------------------------

      query.append("INSERT INTO ");
      query.append("empleado (nombre, cedula, rfecha) ");
      query.append("VALUES (");
      query.append(escapeString("Juan Perez"));
      query.append(", ");
      query.append(105993635);
      query.append(", ");
      query.append(escapeString(timestamp.toString()));
      query.append(")");

      System.out.println(query.toString());

      conn.createStatement().executeUpdate(query.toString());

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      conn.close();
    }
  }

  // -------------------------------------------------------------------

  private static String escapeString(String str) {
    return "'" + str + "'";
  }
}
