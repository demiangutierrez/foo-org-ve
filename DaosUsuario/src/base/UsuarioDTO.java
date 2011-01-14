package base;

import dao.base.api.IDTO;

public interface UsuarioDTO extends IDTO {
  
  public static final String LOGIN = "login";
  public static final String PASS = "pass";

  public String getLogin();

  public void setLogin(String login);

  public String getPass();

  public void setPass(String pass);
}
