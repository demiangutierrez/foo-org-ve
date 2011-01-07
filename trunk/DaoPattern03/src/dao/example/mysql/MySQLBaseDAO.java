package dao.example.mysql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.base.api.IDTO;
import dao.base.impl.BaseDAO;

/**
 * @author Demián Gutierrez
 */
abstract class MySQLBaseDAO extends BaseDAO {

  protected Class<?> dataTransfObjectClass;

  // --------------------------------------------------------------------------------

  public MySQLBaseDAO( //
      Class<?> dataObjectClass) {
    this.dataTransfObjectClass = dataObjectClass;
  }

  // --------------------------------------------------------------------------------
  // DataAccessObject
  // --------------------------------------------------------------------------------

  public void createTable() throws Exception {
    StringBuffer strbuf;

    // ----------------------------------------

    strbuf = new StringBuffer();

    strbuf.append("DROP TABLE IF EXISTS ");
    strbuf.append(getTableName());
    strbuf.append(" CASCADE");

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());

    // ----------------------------------------

    //    strbuf = new StringBuffer();
    //
    //    strbuf.append("DROP SEQUENCE IF EXISTS ");
    //    strbuf.append("seq_");
    //    strbuf.append(getTableName());
    //
    //    System.err.println(strbuf.toString());
    //
    //    connection.createStatement().execute(strbuf.toString());

    // ----------------------------------------

    strbuf = new StringBuffer();

    strbuf.append("CREATE TABLE ");
    strbuf.append(getTableName());
    strbuf.append(" (");

    strbuf.append(createTableColumns());

    strbuf.append(")");

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());

    // ----------------------------------------

    //    strbuf = new StringBuffer();
    //
    //    strbuf.append("CREATE SEQUENCE ");
    //    strbuf.append("seq_");
    //    strbuf.append(getTableName());
    //
    //    System.err.println(strbuf.toString());
    //
    //    connection.createStatement().execute(strbuf.toString());
  }

  // --------------------------------------------------------------------------------

  @Override
  public void insert(IDTO dataTransfObject) throws Exception {
    checkCache(dataTransfObject, CHECK_INSERT);
    checkClass(dataTransfObject, dataTransfObjectClass, CHECK_INSERT);

    StringBuffer strbuf;

    // ----------------------------------------

    strbuf = new StringBuffer();

    strbuf.append("INSERT INTO ");
    strbuf.append(getTableName());
    strbuf.append(" (");
    strbuf.append(createInsertCollst(dataTransfObject));
    strbuf.append(") VALUES (");

    strbuf.append(createInsertValues(dataTransfObject));

    strbuf.append(")");

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());

    dataTransfObject.setId(getLastId());

    // ----------------------------------------

    dtaSession.add(dataTransfObject);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void update(IDTO dataTransfObject) throws Exception {
    checkCache(dataTransfObject, CHECK_UPDATE);
    checkClass(dataTransfObject, dataTransfObjectClass, CHECK_UPDATE);

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("UPDATE ");
    strbuf.append(getTableName());
    strbuf.append(" SET ");

    strbuf.append(createUpdateValues(dataTransfObject));

    strbuf.append(" WHERE ");
    strbuf.append(DepartmentDTOImpl.ID);
    strbuf.append(" = ");
    strbuf.append(dataTransfObject.getId());

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());
  }

  // --------------------------------------------------------------------------------

  @Override
  public void delete(IDTO dataObject) throws Exception {
    checkCache(dataObject, CHECK_DELETE);
    checkClass(dataObject, dataTransfObjectClass, CHECK_DELETE);

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("DELETE FROM ");
    strbuf.append(getTableName());

    strbuf.append(" WHERE ");
    strbuf.append(DataTransfObject.IDTO);
    strbuf.append(" = ");
    strbuf.append(dataObject.getId());

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());

    dtaSession.del(dataObject);
  }

  // --------------------------------------------------------------------------------

  @Override
  public IDTO loadById(int id) throws Exception {
    List<IDTO> retList = listBy(DataTransfObject.IDTO, id);

    if (retList.isEmpty()) {
      return null;
    }

    return retList.get(0);
  }

  // --------------------------------------------------------------------------------

  @Override
  public List<IDTO> listAll(int lim, int off) throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append("SELECT * FROM ");
    strbuf.append(getTableName());

    if (lim >= 0 && off >= 0) {
      strbuf.append(" LIMIT  ");
      strbuf.append(lim);
      strbuf.append(" OFFSET ");
      strbuf.append(off);
    }

    System.err.println(strbuf.toString());

    ResultSet rs = //
    connection.createStatement().executeQuery(strbuf.toString());

    List<IDTO> ret = new ArrayList<IDTO>();

    while (rs.next()) {
      ret.add(resultSetToDO(rs));
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  @Override
  public List<IDTO> listAll() throws Exception {
    return listAll(-1, -1);
  }

  // --------------------------------------------------------------------------------

  @Override
  public int countAll() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append("SELECT COUNT(*) AS count FROM ");
    strbuf.append(getTableName());

    System.err.println(strbuf.toString());

    ResultSet rs = //
    connection.createStatement().executeQuery(strbuf.toString());

    rs.next();

    return rs.getInt("count");
  }

  // --------------------------------------------------------------------------------

  public List<IDTO> listBy(String key, Object val) //
      throws Exception {

    if (key == null || val == null) {
      throw new IllegalArgumentException("key == null || val == null");
    }

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("SELECT * FROM ");
    strbuf.append(getTableName());

    strbuf.append(" WHERE ");
    strbuf.append(key);
    strbuf.append(" = ");
    strbuf.append(val);

    System.err.println(strbuf.toString());

    ResultSet rs = //
    connection.createStatement().executeQuery(strbuf.toString());

    List<IDTO> ret = new ArrayList<IDTO>();

    while (rs.next()) {
      ret.add(resultSetToDO(rs));
    }

    return ret;
  }

  // --------------------------------------------------------------------------------
  // Misc
  // --------------------------------------------------------------------------------

  protected int getLastId() throws Exception {
    StringBuffer strbuf = new StringBuffer();
    strbuf.append("SELECT LAST_INSERT_ID() AS id");

    System.err.println(strbuf.toString());

    ResultSet rs = //
    connection.createStatement().executeQuery(strbuf.toString());

    if (!rs.next()) {
      throw new IllegalStateException("!rs.next()");
    }

    return rs.getInt("id");
  }

  // --------------------------------------------------------------------------------

  protected abstract String createTableColumns() throws Exception;

  // --------------------------------------------------------------------------------

  protected abstract String createInsertCollst(IDTO dataTransfObject) //
      throws Exception;

  protected abstract String createInsertValues(IDTO dataTransfObject) //
      throws Exception;

  protected abstract String createUpdateValues(IDTO dataTransfObject) //
      throws Exception;

  // --------------------------------------------------------------------------------

  protected abstract IDTO resultSetToDO(ResultSet rs) throws Exception;
}
