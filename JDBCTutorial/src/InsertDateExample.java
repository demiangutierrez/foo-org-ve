/**
 *  Ejemplo de INSERT en JDBC
 *  
 *  @author Preparador Hugo Morillo
 *  @date   22/03/2010
 *  
 */

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class InsertDateExample {

	// -------------------------------------------------------------------

	private static String escapeString(String str) {
		return "'" + str + "'";
	}

	// -------------------------------------------------------------------

	public static void main(String[] args) //
			throws ClassNotFoundException, SQLException {

		// Registrar Driver
		Class.forName("org.postgresql.Driver");

		// Establecer Conexion
		Connection conn = DriverManager.getConnection(
				"jdbc:postgresql://localhost/test", // URL
				"postgres", // Login
				""); // Password

		try {

			StringBuffer query = new StringBuffer();

			Calendar cal = Calendar.getInstance();

			Timestamp ts = new Timestamp(cal.getTimeInMillis());
			System.err.println(ts.toString());

			query.append("INSERT INTO ");
			query.append("empleado (nombre, cedula, fregistro)");
			query.append(" VALUES (");
			query.append(escapeString("Juan Perez"));
			query.append(", ");
			query.append(escapeString("105993635"));
			query.append(", ");
			query.append(escapeString(ts.toString()));
			query.append(")");

			System.out.println(query.toString());

//			Date sqlDate = new Date(cal.getTimeInMillis());
//			System.err.println(cal);
//			System.err.println(sqlDate.toString());
			

			conn.createStatement().executeUpdate(query.toString());

		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();

		} finally {
			conn.close();
		}

	}

}
