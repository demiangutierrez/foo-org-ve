package dao.base.api;

import java.sql.ResultSet;
import java.util.List;

import dao.connection.ConnectionBean;

/**
 * @author Demián Gutierrez
 */
public interface IDAO {

  public void init( //
      ConnectionBean connectionBean, FactoryDAO factoryDAO);

  // --------------------------------------------------------------------------------

  public void createTable() //
      throws Exception;

  // --------------------------------------------------------------------------------

  public void insert(IDTO dto) //
      throws Exception;

  public void update(IDTO dto) //
      throws Exception;

  public void delete(IDTO dto) //
      throws Exception;

  // --------------------------------------------------------------------------------

  public IDTO loadById(Object id) //
      throws Exception;

  // --------------------------------------------------------------------------------

  public List<IDTO> listBy(String key, Object val) //
      throws Exception;

  public List<IDTO> listAll(int lim, int off) //
      throws Exception;

  public List<IDTO> listAll() //
      throws Exception;

  // --------------------------------------------------------------------------------

  public int countAll() //
      throws Exception;

  // --------------------------------------------------------------------------------

  public IDTO resultSetToDO(ResultSet rs) //
      throws Exception;
}
