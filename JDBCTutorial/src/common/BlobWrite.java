package common;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connection.ConnectionFactory;

/**
 * @author Demián Gutierrez
 * @date   22/03/2010
 */
public class BlobWrite {

  public static void main(String[] args) //
      throws Exception {

    // ----------------------------------------

    Connection conn = ConnectionFactory.getConnection();

    // ----------------------------------------

    try {
      byte[] data = readFile("test_in.jpg");

      StringBuffer query = new StringBuffer();

      query.append("INSERT INTO imagen ");
      query.append("VALUES (");
      query.append("?, ?");
      query.append(")");

      System.out.println(query.toString());

      PreparedStatement ps = conn.prepareStatement(query.toString());
      ps.setInt(1, 1);
      ps.setBytes(2, data);

      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      conn.close();
    }
  }

  // -------------------------------------------------------------------

  public static byte[] readFile(String file) throws Exception {
    File f = new File(file);

    byte[] ret = new byte[(int) f.length()];

    FileInputStream fis = new FileInputStream(f);

    fis.read(ret);

    return ret;
  }
}
