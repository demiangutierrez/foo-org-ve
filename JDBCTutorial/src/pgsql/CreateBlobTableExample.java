package pgsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Demián Gutierrez
 * @author Hugo Morillo
 * @date   22/03/2010
 */
public class CreateBlobTableExample {

  public static void main(String[] args) //
      throws ClassNotFoundException, SQLException {

    // ----------------------------------------
    // registrar driver
    // ----------------------------------------

    Class.forName("org.postgresql.Driver");

    // ----------------------------------------
    // establecer conexion
    // ----------------------------------------

    Connection conn = DriverManager.getConnection( //
        "jdbc:postgresql://localhost/test", // url
        "postgres", ""); // login, password

    // ----------------------------------------

    try {
      StringBuffer query = new StringBuffer();

      // ----------------------------------------
      // DROP TABLE
      // ----------------------------------------

      query.append("DROP TABLE IF EXISTS ");
      query.append("imagen");

      System.out.println(query.toString());

      conn.createStatement().executeUpdate(query.toString());

      // ----------------------------------------
      // CREATE TABLE
      // ----------------------------------------

      query = new StringBuffer();

      query.append("CREATE TABLE imagen (");
      query.append("id   SERIAL, ");
      query.append("data BYTEA   ");
      query.append(")");

      System.out.println(query.toString());

      conn.createStatement().executeUpdate(query.toString());

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      conn.close();
    }
  }
}
