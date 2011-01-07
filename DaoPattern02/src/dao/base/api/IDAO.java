package dao.base.api;

import java.util.List;

import dao.connection.ConnectionBean;

/**
 * @author Demián Gutierrez
 */
public interface IDAO {

  public void init(ConnectionBean connectionBean);

  // --------------------------------------------------------------------------------

  public void createTable() throws Exception;

  // --------------------------------------------------------------------------------

  public void insert(IDTO bean) throws Exception;

  public void update(IDTO bean) throws Exception;

  public void delete(IDTO bean) throws Exception;

  // --------------------------------------------------------------------------------

  public IDTO loadById(int id) throws Exception;

  // --------------------------------------------------------------------------------

  public List<IDTO> listAll(int lim, int off) throws Exception;

  public List<IDTO> listAll() throws Exception;

  public int countAll() throws Exception;

  // --------------------------------------------------------------------------------

  public List<IDTO> listBy(String key, Object val) //
      throws Exception;

  // --------------------------------------------------------------------------------

  public String getTableName();
}
