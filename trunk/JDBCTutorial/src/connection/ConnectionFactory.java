package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Demián Gutierrez
 * @date   14/07/2011
 */
public class ConnectionFactory {

  public static Connection getConnection() throws SQLException, ClassNotFoundException {

    // ----------------------------------------
    // establecer conexion
    // ----------------------------------------

    //    Class.forName("com.mysql.jdbc.Driver");
    //
    //    Connection conn = DriverManager.getConnection( //
    //        "jdbc:mysql://localhost/prueba", // url
    //        "root", ""); // login, password

    Class.forName("org.postgresql.Driver");

    Connection conn = DriverManager.getConnection( //
        "jdbc:postgresql://localhost/test", // url
        "postgres", ""); // login, password

    return conn;
  }
}
