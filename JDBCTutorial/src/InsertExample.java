/**
 *  Ejemplo de INSERT en JDBC
 *  
 *  @author Preparador Hugo Morillo
 *  @date   22/03/2010
 *  
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InsertExample {

  //-------------------------------------------------------------------

  private static String escapeString(String str) {
    return "'" + str + "'";
  }

  //-------------------------------------------------------------------

  public static void main(String[] args) //
      throws ClassNotFoundException, SQLException {

    // Registrar Driver
    Class.forName("com.mysql.jdbc.Driver");

    // Establecer Conexion
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/prueba", //URL
        "root", //Login
        ""); //Password

    try {

      StringBuffer query = new StringBuffer();

      query.append("INSERT INTO ");
      query.append("empleado (nombre, cedula)");
      query.append(" VALUES (");
      query.append(escapeString("XXX Delgado"));
      query.append(", ");
      query.append(escapeString("290120910"));
      query.append(")");

      System.out.println(query.toString());

      conn.createStatement().executeUpdate(query.toString());

    } catch (SQLException e) {
      e.printStackTrace();
      //System.err.println("Error: " + e.getMessage());

    } finally {
      conn.close();
    }

  }

}
