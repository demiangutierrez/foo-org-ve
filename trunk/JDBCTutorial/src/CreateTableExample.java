/**
 *  Creacion de una tabla con JDBC
 *  
 *  @author Preparador Hugo Morillo
 *  @date   22/03/2010
 *  
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateTableExample {

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

      query.append("DROP TABLE IF EXISTS ");
      query.append("empleado");

      System.out.println(query.toString());

      // Ejecutar Query
      conn.createStatement().executeUpdate(query.toString());

      query = new StringBuffer();

      query.append("CREATE TABLE ");
      query.append("empleado");
      query.append(" (");
      query.append("id INT AUTO_INCREMENT PRIMARY KEY, ");
      query.append("nombre VARCHAR(100) NOT NULL, ");
      query.append("cedula INT NOT NULL UNIQUE");
      query.append(")");

      System.out.println(query.toString());

      conn.createStatement().executeUpdate(query.toString());

    } catch (SQLException e) {
      System.err.println("Error: " + e.getMessage());

    } finally {
      conn.close();
    }
  }
}
