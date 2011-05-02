package dao.connection;

import java.sql.Connection;

import org.hibernate.Session;

/**
 * @author Demián Gutierrez
 */
public class ConnectionBean {

  private DtaSession dtaSession;
  private Connection connection;

  private Session session;

  // --------------------------------------------------------------------------------

  public ConnectionBean(DtaSession dtaSession, Connection connection) {
    this.dtaSession = dtaSession;
    this.connection = connection;
  }

  // --------------------------------------------------------------------------------

  public ConnectionBean(Session session) {
    this.session = session;
  }

  // --------------------------------------------------------------------------------

  public DtaSession getDtaSession() {
    return dtaSession;
  }

  public Connection getConnection() {
    return connection;
  }

  // --------------------------------------------------------------------------------

  public Session getSession() {
    return session;
  }
}
