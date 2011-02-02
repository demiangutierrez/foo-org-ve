/**
 *  Ejemplo de PreparedStatemen en JDBC
 *  
 *  @author Preparador Hugo Morillo
 *  @date   22/03/2010
 *  
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementExample {

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

      query.append("SELECT * FROM empleado");
      query.append(" WHERE ");
      query.append("cedula = ? OR nombre = ?");

      PreparedStatement ps = conn.prepareStatement(query.toString());

      ps.setInt(1, 10245312);
      ps.setString(2, "foo");

      System.out.println(query.toString());
      System.out.println(ps.toString());

      ResultSet rs = ps.executeQuery();

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
