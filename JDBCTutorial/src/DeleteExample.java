/**
 *  Ejemplo de DELETE en JDBC
 *  
 *  @author Preparador Hugo Morillo
 *  @date   22/03/2010
 *  
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DeleteExample {

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

      query.append("DELETE FROM empleado");
      query.append(" WHERE ");
      query.append("cedula = 10245312");

      System.out.println(query.toString());

      conn.createStatement().executeUpdate(query.toString());

    } catch (SQLException e) {
      System.err.println("Error: " + e.getMessage());

    } finally {
      conn.close();
    }

  }

}
