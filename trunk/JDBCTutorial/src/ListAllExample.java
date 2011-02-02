/**
 *  Ejemplo de SELECT en JDBC
 *  
 *  @author Preparador Hugo Morillo
 *  @date   22/03/2010
 *  
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListAllExample {

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

      query.append("SELECT * FROM empleado WHERE cedula=10245312");

      System.out.println(query.toString());

      ResultSet rs = conn.createStatement().executeQuery(query.toString());

      while (rs.next()) {
        System.err.println(rs.getString("nombre") + "," + rs.getInt("cedula"));
      }

    } catch (SQLException e) {
      System.err.println("Error: " + e.getMessage());

    } finally {
      conn.close();
    }
  }
}
