package base;

import dao.base.api.IDAO;

public interface UsuarioDAO extends IDAO {

  public boolean autenticarUsuario(String login, String pass);
}
