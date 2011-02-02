/**
 *  Creacion de una tabla con JDBC
 *  
 *  @author Preparador Hugo Morillo
 *  @date   22/03/2010
 *  
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateTableExample2 {

    public static void main(String[] args) //
        throws ClassNotFoundException, SQLException {

        // Registrar Driver
        Class.forName("org.postgresql.Driver");

        // Establecer Conexion
        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost/test", //URL
                "postgres",                             //Login
                "");                           //Password

        try {

            StringBuffer query = new StringBuffer();

            query.append("DROP TABLE IF EXISTS ");
            query.append("empleado");

            // Ejecutar Query
            conn.createStatement().executeUpdate(query.toString());

            query = new StringBuffer();

            query.append("CREATE TABLE ");
            query.append("empleado");
            query.append(" (");
            query.append("id SERIAL, ");
            query.append("nombre VARCHAR(100) NOT NULL, ");
            query.append("cedula INT NOT NULL UNIQUE, ");
            query.append("fregistro TIMESTAMP"); // Ojo, este es extra
            query.append(")");
            
            System.out.println(query.toString());

            conn.createStatement().executeUpdate(query.toString());

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();

        } finally {
            conn.close();
        }
    }
}
