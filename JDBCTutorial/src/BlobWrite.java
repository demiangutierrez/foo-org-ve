/**
 *  Ejemplo de UPDATE en JDBC
 *  
 *  @author Preparador Hugo Morillo
 *  @date   22/03/2010
 *  
 */

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BlobWrite {

	// -------------------------------------------------------------------

	private static String escapeString(String str) {
		return "'" + str + "'";
	}

	// -------------------------------------------------------------------

	public static byte[] readFile(String file) throws Exception {
		File f = new File(file);

		byte[] ret = new byte[(int) f.length()];

		FileInputStream fis = new FileInputStream(f);

		fis.read(ret);

		return ret;
	}

	public static void main(String[] args) //
			throws Exception {

		// Registrar Driver
		Class.forName("org.postgresql.Driver");

		// Establecer Conexion
		Connection conn = DriverManager.getConnection(
				"jdbc:postgresql://localhost/test", // URL
				"postgres", // Login
				""); // Password

    // Registrar Driver
//    Class.forName("com.mysql.jdbc.Driver");
//
//    // Establecer Conexion
//    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/prueba", //URL
//        "root", //Login
//        ""); //Password
		
		
		try {
			byte[] data = readFile("WindowsInstall.jpg");

			StringBuffer query = new StringBuffer();

			query.append("INSERT INTO foo ");
			query.append(" VALUES (");
			query.append("?, ?");
			query.append(")");

			System.out.println(query.toString());

			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setInt(1, 1);
			ps.setBytes(2, data);

			ps.execute();
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());

		} finally {
			conn.close();
		}
	}
}
