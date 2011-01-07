package dao.base.api;

import dao.annotation.PK;
import dao.annotation.SQLType;
import dao.connection.ConnectionBean;

/**
 * @author Demián Gutierrez
 */
public interface IDTO {

  public void init( //
      ConnectionBean connectionBean, FactoryDAO factoryDAO);

  // --------------------------------------------------------------------------------

  public ConnectionBean getConnectionBean();

  // --------------------------------------------------------------------------------

  public FactoryDAO getFactoryDAO();

  // --------------------------------------------------------------------------------

  public boolean/**/getLoaded();

  public void/*   */setLoaded(boolean loaded);

  // --------------------------------------------------------------------------------

  @PK
  @SQLType("INT")
  public int/* */getId();

  public void/**/setId(int id);
}
