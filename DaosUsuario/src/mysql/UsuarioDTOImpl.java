package mysql;

import base.UsuarioDTO;
import dao.connection.ConnectionBean;

public class UsuarioDTOImpl implements UsuarioDTO {

  private int id;

  private String login;

  private String pass;

  @Override
  public String getLogin() {
    return login;
  }

  @Override
  public String getPass() {
    return pass;
  }

  @Override
  public void setLogin(String login) {
    this.login = login;
  }

  @Override
  public void setPass(String pass) {
    this.pass = pass;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void init(ConnectionBean connectionBean) {
    // Empty
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }
}
