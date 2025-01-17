package dao.example.mysql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.base.impl.BaseDAO;
import dao.example.base.AbstractFactoryDAO;

/**
 * @author Demián Gutierrez
 */
abstract class MySQLBaseDAO extends BaseDAO {

  protected Class<? extends IDTO> dtoClass;

  protected Class<? extends IDAO> daoParentClass;

  // --------------------------------------------------------------------------------

  public MySQLBaseDAO( //
      Class<? extends IDTO> dtoClass) {
    this.dtoClass = dtoClass;
  }

  // --------------------------------------------------------------------------------
  // IDAO
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

    strbuf = new StringBuffer();

    strbuf.append("CREATE TABLE ");
    strbuf.append(getTableName());
    strbuf.append(" (");

    strbuf.append(createTableColumns());

    strbuf.append(")");

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());
  }

  // --------------------------------------------------------------------------------

  @Override
  public void insert(IDTO dto) throws Exception {
    internalInsert(dto);
  }

  // --------------------------------------------------------------------------------

  protected void internalInsert(IDTO dto) throws Exception {
    checkCache(dto,/*       */CHECK_INSERT);
    checkClass(dto, dtoClass, CHECK_INSERT);

    if (daoParentClass != null) {
      MySQLBaseDAO dao = (MySQLBaseDAO) AbstractFactoryDAO.getFactoryDAO(). //
          getDAO(daoParentClass, connectionBean);

      dao.internalInsert(dto);
    }

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("INSERT INTO ");
    strbuf.append(getTableName());
    strbuf.append(" (");
    strbuf.append(createInsertCollst(dto));
    strbuf.append(") VALUES (");

    strbuf.append(createInsertValues(dto));

    strbuf.append(")");

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());

    if (dto.getId() == 0) {
      dto.setId(getLastId());
    }

    // ----------------------------------------

    dtaSession.add(dto);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void update(IDTO dto) throws Exception {
    internalUpdate(dto);
  }

  // --------------------------------------------------------------------------------

  protected void internalUpdate(IDTO dto) throws Exception {
    checkCache(dto,/*       */CHECK_UPDATE);
    checkClass(dto, dtoClass, CHECK_UPDATE);

    if (daoParentClass != null) {
      MySQLBaseDAO dao = (MySQLBaseDAO) AbstractFactoryDAO.getFactoryDAO(). //
          getDAO(daoParentClass, connectionBean);

      dao.internalUpdate(dto);
    }

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("UPDATE ");
    strbuf.append(getTableName());
    strbuf.append(" SET ");

    strbuf.append(createUpdateValues(dto));

    strbuf.append(" WHERE ");
    strbuf.append(IDTO.ID);
    strbuf.append(" = ");
    strbuf.append(dto.getId());

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());
  }

  // --------------------------------------------------------------------------------

  @Override
  public void delete(IDTO dto) throws Exception {
    internalDelete(dto);
  }

  // --------------------------------------------------------------------------------

  protected void internalDelete(IDTO dto) throws Exception {
    checkCache(dto,/*       */CHECK_DELETE);
    checkClass(dto, dtoClass, CHECK_DELETE);

    if (daoParentClass != null) {
      MySQLBaseDAO dao = (MySQLBaseDAO) AbstractFactoryDAO.getFactoryDAO(). //
          getDAO(daoParentClass, connectionBean);

      dao.internalDelete(dto);
    }

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("DELETE FROM ");
    strbuf.append(getTableName());

    strbuf.append(" WHERE ");
    strbuf.append(IDTO.ID);
    strbuf.append(" = ");
    strbuf.append(dto.getId());

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());

    // ----------------------------------------

    dtaSession.del(dto);
  }

  // --------------------------------------------------------------------------------

  @Override
  public IDTO loadById(int id) throws Exception {
    List<IDTO> retList = listBy(this.getTableName() + "." + IDTO.ID, id);

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
      ret.add(resultSetToDTO(rs));
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
      ret.add(resultSetToDTO(rs));
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

  protected abstract String createInsertCollst(IDTO dto) //
      throws Exception;

  protected abstract String createInsertValues(IDTO dto) //
      throws Exception;

  protected abstract String createUpdateValues(IDTO dto) //
      throws Exception;

  // --------------------------------------------------------------------------------

  protected IDTO/*     */resultSetToDTO(ResultSet rs/*      */) throws Exception {

    // TODO: THIS IS A HACK TO TRICK THE CACHE: I just create
    // an empty dto from dtoClass (interface) to get the class
    // of the current dto concrete implementation to perform
    // further queries from cache with the concrete class instead
    // the interface.
    IDTO ret = AbstractFactoryDAO.getFactoryDAO(). //
        getDTO(dtoClass, connectionBean);

    ret = dtaSession.getDtaByKey( //
        ret.getClass(), rs.getInt(NewsDTOImpl.ID));

    if (ret != null) {
      return ret;
    }

    // Already created (see previous comment), but nulled from getDtaByKey
    ret = AbstractFactoryDAO.getFactoryDAO(). //
        getDTO(dtoClass, connectionBean);

    if (daoParentClass != null) {
      MySQLBaseDAO dao = (MySQLBaseDAO) AbstractFactoryDAO.getFactoryDAO(). //
          getDAO(daoParentClass, connectionBean);

      dao.internalResultSetToDTO(rs, ret);
    }

    internalResultSetToDTO(rs, ret);

    return dtaSession.add(ret);
  }

  // --------------------------------------------------------------------------------

  protected abstract IDTO internalResultSetToDTO(ResultSet rs, IDTO dto) //
      throws Exception;
}
