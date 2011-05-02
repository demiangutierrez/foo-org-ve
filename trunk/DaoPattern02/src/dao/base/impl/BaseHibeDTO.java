package dao.base.impl;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import dao.base.api.IDTO;
import dao.connection.ConnectionBean;

/**
 * @author Demián Gutierrez
 */
@MappedSuperclass
public class BaseHibeDTO implements IDTO {

  protected int id;

  // --------------------------------------------------------------------------------

  public BaseHibeDTO() {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // DataTransfObject
  // --------------------------------------------------------------------------------

  @Override
  final public void init(ConnectionBean connectionBean) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }
}
