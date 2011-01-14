package mysql;

import java.util.List;

import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import base.UsuarioDAO;
import base.UsuarioDTO;

public class UsuarioDAOImpl implements UsuarioDAO {

  private ConnectionBean connectionBean;

  @Override
  public void init(ConnectionBean connectionBean) {
    this.connectionBean = connectionBean;
  }

  @Override
  public void createTable() throws Exception {

    // DROP TABLE IF EXIST usuario
    String sql = "DROP TABLE IF EXISTS " + getTableName() + " CASCADE";

    System.err.println(sql);

    connectionBean.getConnection().createStatement().execute(sql);

    // CREATE TABLE usuario (
    //   id INT PRIMARY KEY AUTO_INCREMENT,
    //   login VARCHAR(50) NOT NULL,
    //    pass  VARCHAR(50) NOT NULL
    // )

    sql = "CREATE TABLE " + getTableName() + //
        " (id INT PRIMARY KEY AUTO_INCREMENT, " + //
        "login VARCHAR(50) NOT NULL, " + //
        "pass  VARCHAR(50) NOT NULL)";

    System.err.println(sql);

    connectionBean.getConnection().createStatement().execute(sql);
  }

  @Override
  public void insert(IDTO bean) throws Exception {
    UsuarioDTO usuarioDTO = (UsuarioDTO) bean;

    // INSERT INTO usuario (login, pass) VALUES ('xxx', 'yyy');
    String sql = "INSERT INTO " + getTableName() + //
        " (" + UsuarioDTO.LOGIN + ", " + UsuarioDTO.PASS + ") VALUES ('" + usuarioDTO.getLogin() + //
        "', '" + usuarioDTO.getPass() + "')";

    connectionBean.getConnection().createStatement().execute(sql);
  }

  @Override
  public void update(IDTO bean) throws Exception {
    UsuarioDTO usuarioDTO = (UsuarioDTO) bean;

    //UPDATE usuario SET login='aaa' ,pass='bbb' WHERE id=xx);
    String sql = "UPDATE " + getTableName() + "SET " + //
        " (" + UsuarioDTO.LOGIN + ", " + UsuarioDTO.PASS + ") VALUES ('" + usuarioDTO.getLogin() + //
        "', '" + usuarioDTO.getPass() + "')";

    connectionBean.getConnection().createStatement().execute(sql);
  }

  @Override
  public void delete(IDTO bean) throws Exception {

    // DELETE FROM usuario WHERE login ==

  }

  @Override
  public int countAll() throws Exception {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getTableName() {
    return "usuario";
  }

  @Override
  public List<IDTO> listAll(int lim, int off) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<IDTO> listAll() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<IDTO> listBy(String key, Object val) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IDTO loadById(int id) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean autenticarUsuario(String login, String pass) {
    // TODO Auto-generated method stub
    return false;
  }

}
