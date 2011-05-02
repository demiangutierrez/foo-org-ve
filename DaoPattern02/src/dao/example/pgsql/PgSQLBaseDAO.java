//package dao.example.pgsql;
//
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import dao.base.api.IDTO;
//import dao.base.impl.BaseDAO;
//
///**
// * @author Demián Gutierrez
// */
//abstract class PgSQLBaseDAO extends BaseDAO {
//
//  protected Class<? extends IDTO> dtoClass;
//
//  // --------------------------------------------------------------------------------
//
//  public PgSQLBaseDAO( //
//      Class<? extends IDTO> dtoClass) {
//    this.dtoClass = dtoClass;
//  }
//
//  // --------------------------------------------------------------------------------
//  // DataAccessObject
//  // --------------------------------------------------------------------------------
//
//  public void createTable() throws Exception {
//    StringBuffer strbuf;
//
//    // ----------------------------------------
//
//    strbuf = new StringBuffer();
//
//    strbuf.append("DROP TABLE IF EXISTS ");
//    strbuf.append(getTableName());
//    strbuf.append(" CASCADE");
//
//    System.err.println(strbuf.toString());
//
//    connection.createStatement().execute(strbuf.toString());
//
//    // ----------------------------------------
//
//    strbuf = new StringBuffer();
//
//    strbuf.append("DROP SEQUENCE IF EXISTS ");
//    strbuf.append("seq_");
//    strbuf.append(getTableName());
//
//    System.err.println(strbuf.toString());
//
//    connection.createStatement().execute(strbuf.toString());
//
//    // ----------------------------------------
//
//    strbuf = new StringBuffer();
//
//    strbuf.append("CREATE TABLE ");
//    strbuf.append(getTableName());
//    strbuf.append(" (");
//
//    strbuf.append(createTableColumns());
//
//    strbuf.append(")");
//
//    System.err.println(strbuf.toString());
//
//    connection.createStatement().execute(strbuf.toString());
//
//    // ----------------------------------------
//
//    strbuf = new StringBuffer();
//
//    strbuf.append("CREATE SEQUENCE ");
//    strbuf.append("seq_");
//    strbuf.append(getTableName());
//
//    System.err.println(strbuf.toString());
//
//    connection.createStatement().execute(strbuf.toString());
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public void insert(IDTO dto) throws Exception {
//    checkCache(dto, CHECK_INSERT);
//    checkClass(dto, dtoClass, CHECK_INSERT);
//
//    dto.setId(getNextId());
//
//    StringBuffer strbuf = new StringBuffer();
//
//    strbuf.append("INSERT INTO ");
//    strbuf.append(getTableName());
//    strbuf.append(" VALUES (");
//
//    strbuf.append(createInsertValues(dto));
//
//    strbuf.append(")");
//
//    System.err.println(strbuf.toString());
//
//    connection.createStatement().execute(strbuf.toString());
//
//    dtaSession.add(dto);
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public void update(IDTO dto) throws Exception {
//    checkCache(dto, CHECK_UPDATE);
//    checkClass(dto, dtoClass, CHECK_UPDATE);
//
//    StringBuffer strbuf = new StringBuffer();
//
//    strbuf.append("UPDATE ");
//    strbuf.append(getTableName());
//    strbuf.append(" SET ");
//
//    strbuf.append(createUpdateValues(dto));
//
//    strbuf.append(" WHERE ");
//    strbuf.append(DeptDTOImpl.ID);
//    strbuf.append(" = ");
//    strbuf.append(dto.getId());
//
//    System.err.println(strbuf.toString());
//
//    connection.createStatement().execute(strbuf.toString());
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public void delete(IDTO dto) throws Exception {
//    checkCache(dto, CHECK_DELETE);
//    checkClass(dto, dtoClass, CHECK_DELETE);
//
//    StringBuffer strbuf = new StringBuffer();
//
//    strbuf.append("DELETE FROM ");
//    strbuf.append(getTableName());
//
//    strbuf.append(" WHERE ");
//    strbuf.append(IDTO.ID);
//    strbuf.append(" = ");
//    strbuf.append(dto.getId());
//
//    System.err.println(strbuf.toString());
//
//    connection.createStatement().execute(strbuf.toString());
//
//    dtaSession.del(dto);
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public IDTO loadById(int id) throws Exception {
//    List<IDTO> retList = listBy(IDTO.ID, id);
//
//    if (retList.isEmpty()) {
//      return null;
//    }
//
//    return retList.get(0);
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public List<IDTO> listAll(int lim, int off) throws Exception {
//    StringBuffer strbuf = new StringBuffer();
//
//    strbuf.append("SELECT * FROM ");
//    strbuf.append(getTableName());
//
//    if (lim >= 0 && off >= 0) {
//      strbuf.append(" LIMIT  ");
//      strbuf.append(lim);
//      strbuf.append(" OFFSET ");
//      strbuf.append(off);
//    }
//
//    System.err.println(strbuf.toString());
//
//    ResultSet rs = //
//    connection.createStatement().executeQuery(strbuf.toString());
//
//    List<IDTO> ret = new ArrayList<IDTO>();
//
//    while (rs.next()) {
//      ret.add(resultSetToDTO(rs));
//    }
//
//    return ret;
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public List<IDTO> listAll() throws Exception {
//    return listAll(-1, -1);
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public int countAll() throws Exception {
//    StringBuffer strbuf = new StringBuffer();
//
//    strbuf.append("SELECT COUNT(*) FROM ");
//    strbuf.append(getTableName());
//
//    System.err.println(strbuf.toString());
//
//    ResultSet rs = //
//    connection.createStatement().executeQuery(strbuf.toString());
//
//    rs.next();
//
//    return rs.getInt("count");
//  }
//
//  // --------------------------------------------------------------------------------
//
//  public List<IDTO> listBy(String key, Object val) //
//      throws Exception {
//
//    if (key == null || val == null) {
//      throw new IllegalArgumentException("key == null || val == null");
//    }
//
//    StringBuffer strbuf = new StringBuffer();
//
//    strbuf.append("SELECT * FROM ");
//    strbuf.append(getTableName());
//
//    strbuf.append(" WHERE ");
//    strbuf.append(key);
//    strbuf.append(" = ");
//    strbuf.append(val);
//
//    System.err.println(strbuf.toString());
//
//    ResultSet rs = //
//    connection.createStatement().executeQuery(strbuf.toString());
//
//    List<IDTO> ret = new ArrayList<IDTO>();
//
//    while (rs.next()) {
//      ret.add(resultSetToDTO(rs));
//    }
//
//    return ret;
//  }
//
//  // --------------------------------------------------------------------------------
//  // Misc
//  // --------------------------------------------------------------------------------
//
//  protected int getNextId() throws Exception {
//    StringBuffer strbuf = new StringBuffer();
//
//    strbuf.append("SELECT nextval(");
//    strbuf.append(singleQuotes("seq_" + getTableName()));
//    strbuf.append(")");
//
//    System.err.println(strbuf.toString());
//
//    ResultSet rs = //
//    connection.createStatement().executeQuery(strbuf.toString());
//
//    if (!rs.next()) {
//      throw new IllegalStateException("!rs.next()");
//    }
//
//    return rs.getInt("nextval");
//  }
//
//  // --------------------------------------------------------------------------------
//
//  protected abstract String createTableColumns() throws Exception;
//
//  // --------------------------------------------------------------------------------
//
//  protected abstract String createInsertValues(IDTO dto) //
//      throws Exception;
//
//  protected abstract String createUpdateValues(IDTO dto) //
//      throws Exception;
//
//  // --------------------------------------------------------------------------------
//
//  protected abstract IDTO resultSetToDTO(ResultSet rs) throws Exception;
//}
