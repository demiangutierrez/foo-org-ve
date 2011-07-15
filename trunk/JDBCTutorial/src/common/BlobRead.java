package common;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionFactory;

/**
 * @author Demián Gutierrez
 * @date   22/03/2010
 */
public class BlobRead {

  public static void main(String[] args) //
      throws Exception {

    // ----------------------------------------

    Connection conn = ConnectionFactory.getConnection();

    // ----------------------------------------

    try {
      StringBuffer query = new StringBuffer();

      query.append("SELECT * FROM imagen WHERE id=1");

      System.out.println(query.toString());

      ResultSet rs = conn.createStatement().executeQuery(query.toString());

      if (rs.next()) {
        System.err.println(rs.getInt("id"));

        writeFile(rs.getBytes("data"), "test_out.jpg");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      conn.close();
    }
  }

  // -------------------------------------------------------------------

  public static void writeFile(byte[] data, String file) throws Exception {
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(data);
    fos.close();
  }
}
