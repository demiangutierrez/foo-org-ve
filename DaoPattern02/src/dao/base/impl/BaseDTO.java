package dao.base.impl;

import java.sql.Connection;

import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.DtaSession;

/**
 * @author Demián Gutierrez
 */
public class BaseDTO implements IDTO {

  protected Class<? extends IDAO> daoClass;

  // --------------------------------------------------------------------------------

  protected ConnectionBean connectionBean;

  protected DtaSession dtaSession;
  protected Connection connection;

  // --------------------------------------------------------------------------------

  protected int id;

  // --------------------------------------------------------------------------------

  public BaseDTO( //
      Class<? extends IDAO> daoClass) {
    this.daoClass = daoClass;
  }

  // --------------------------------------------------------------------------------
  // DataTransfObject
  // --------------------------------------------------------------------------------

  @Override
  final public void init(ConnectionBean connectionBean) {
    if (this.connectionBean != null) {
      throw new IllegalStateException("this.connectionBean != null");
    }

    this.connectionBean = connectionBean;

    dtaSession = connectionBean.getDtaSession();
    connection = connectionBean.getConnection();
  }

  // --------------------------------------------------------------------------------

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }
}
