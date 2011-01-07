package dao.base.impl;

import java.sql.Connection;

import dao.base.api.FactoryDAO;
import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.DtaSession;

/**
 * @author Demián Gutierrez
 */
public class BaseDTO implements IDTO {

  protected Class<? extends IDAO> dataAccessObjectClass;

  // --------------------------------------------------------------------------------

  protected ConnectionBean connectionBean;

  protected DtaSession dtaSession;
  protected Connection connection;

  protected FactoryDAO factoryDAO;

  protected boolean loaded;

  // --------------------------------------------------------------------------------

  protected int id;

  // --------------------------------------------------------------------------------

  public BaseDTO( //
      Class<? extends IDAO> dataAccessObjectClass) {
    this.dataAccessObjectClass = dataAccessObjectClass;
  }

  // --------------------------------------------------------------------------------
  // DataTransfObject
  // --------------------------------------------------------------------------------

  @Override
  final public void init( //
      ConnectionBean connectionBean, FactoryDAO factoryDAO) {
    if (this.connectionBean != null) {
      throw new IllegalStateException("this.connectionBean != null");
    }

    this.connectionBean = connectionBean;

    dtaSession = connectionBean.getDtaSession();
    connection = connectionBean.getConnection();

    if (this.factoryDAO != null) {
      throw new IllegalStateException("this.factoryDAO != null");
    }

    this.factoryDAO = factoryDAO;
  }

  // --------------------------------------------------------------------------------

  @Override
  public ConnectionBean getConnectionBean() {
    return connectionBean;
  }

  // --------------------------------------------------------------------------------

  @Override
  public FactoryDAO getFactoryDAO() {
    return factoryDAO;
  }

  // --------------------------------------------------------------------------------

  @Override
  public boolean/**/getLoaded() {
    return loaded;
  }

  @Override
  public void/*   */setLoaded(boolean loaded) {
    this.loaded = loaded;
  }

  // --------------------------------------------------------------------------------

  @Override
  public int/* */getId() {
    return id;
  }

  @Override
  public void/**/setId(int id) {
    this.id = id;
  }
}
