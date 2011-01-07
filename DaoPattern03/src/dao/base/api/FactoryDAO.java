package dao.base.api;

import dao.connection.ConnectionBean;

/**
 * @author Demián Gutierrez
 */
public interface FactoryDAO {

  public IDAO getDAO( //
      Class<?> clazz, //
      ConnectionBean connectionBean) //
      throws ClassNotFoundException, Exception;

  // --------------------------------------------------------------------------------

  public IDTO getDTO( //
      Class<?> clazz, //
      ConnectionBean connectionBean) //
      throws ClassNotFoundException, Exception;
}
