/**
 *  Ejemplo de UPDATE en JDBC
 *  
 *  @author Preparador Hugo Morillo
 *  @date   22/03/2010
 *  
 */

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlobRead {

  // -------------------------------------------------------------------

  private static String escapeString(String str) {
    return "'" + str + "'";
  }

  // -------------------------------------------------------------------

  public static void writeFile(byte[] data, String file) throws Exception {
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(data);
    fos.close();
  }

  public static void main(String[] args) //
      throws Exception {

//    // Registrar Driver
//    Class.forName("com.mysql.jdbc.Driver");
//
//    // Establecer Conexion
//    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/prueba", //URL
//        "root", //Login
//        ""); //Password

    		// Registrar Driver
    		Class.forName("org.postgresql.Driver");
    
    		// Establecer Conexion
    		Connection conn = DriverManager.getConnection(
    				"jdbc:postgresql://localhost/test", // URL
    				"postgres", // Login
    				""); // Password

    try {

      StringBuffer query = new StringBuffer();
      query.append("SELECT * FROM foo WHERE id=1");

      System.out.println(query.toString());

      ResultSet rs = conn.createStatement().executeQuery(query.toString());

      rs.next();

      System.err.println(rs.getInt("id"));

      writeFile(rs.getBytes("data"), "foo.jpg");
    } catch (SQLException e) {
      System.err.println("Error: " + e.getMessage());

    } finally {
      conn.close();
    }
  }
}
