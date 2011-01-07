package dao.connection;

import java.sql.Connection;

/**
 * @author Demián Gutierrez
 */
public class ConnectionBean {

  private DtaSession dtaSession;
  private Connection connection;

  // --------------------------------------------------------------------------------

  public ConnectionBean(DtaSession dtaSession, Connection connection) {
    this.dtaSession = dtaSession;
    this.connection = connection;
  }

  // --------------------------------------------------------------------------------

  public DtaSession getDtaSession() {
    return dtaSession;
  }

  public Connection getConnection() {
    return connection;
  }
}
