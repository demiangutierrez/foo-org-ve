package dao.base.api;

import dao.connection.ConnectionBean;

/**
 * @author Demián Gutierrez
 */
public interface IDTO {

  public static final String ID = "id";

  // --------------------------------------------------------------------------------

  public void init(ConnectionBean connectionBean);

  // --------------------------------------------------------------------------------

  public int/* */getId();

  public void/**/setId(int id);
}
