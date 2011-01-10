package dao.example.mysql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.NewsDAO;
import dao.example.base.NewsDTO;
import dao.example.base.PublicationDAO;
import dao.example.base.PublicationDTO;

/**
 * @author Demián Gutierrez
 */
public class NewsDAOImpl extends MySQLBaseDAO implements NewsDAO {

  public NewsDAOImpl() {
    super(NewsDTO.class);
    daoParentClass = PublicationDAO.class;
  }

  // --------------------------------------------------------------------------------
  // PostgresBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(NewsDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY, ");
    strbuf.append(NewsDTOImpl.TYPE);
    strbuf.append(" INT,             ");
    strbuf.append(NewsDTOImpl.SIZE);
    strbuf.append(" INT              ");

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertCollst(IDTO dto) //
      throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(NewsDTOImpl.ID);
    strbuf.append(", ");
    strbuf.append(NewsDTOImpl.TYPE);
    strbuf.append(", ");
    strbuf.append(NewsDTOImpl.SIZE);

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    NewsDTOImpl newsDTOImpl = (NewsDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    // XXX: MySQL does not need to include the id if auto incremental
    //    strbuf.append(NewsDTOImpl.getId());
    //    strbuf.append(", ");
    strbuf.append(newsDTOImpl.getId());
    strbuf.append(", ");
    strbuf.append(newsDTOImpl.getType());
    strbuf.append(", ");
    strbuf.append(newsDTOImpl.getSize());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void insert(IDTO dto) throws Exception {
    internalInsert(dto);
  }

  @Override
  public void update(IDTO dto) throws Exception {
    internalUpdate(dto);
  }

  // --------------------------------------------------------------------------------

  protected String createUpdateValues(IDTO dto) //
      throws Exception {

    NewsDTOImpl newsDTOImpl = (NewsDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(NewsDTOImpl.TYPE);
    strbuf.append(" = ");
    strbuf.append(newsDTOImpl.getType());

    strbuf.append(", ");

    strbuf.append(NewsDTOImpl.SIZE);
    strbuf.append(" = ");
    strbuf.append(newsDTOImpl.getSize());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected NewsDTOImpl internalResultSetToDTO(ResultSet rs, IDTO dto) throws Exception {
    NewsDTOImpl ret = (NewsDTOImpl) dto;

    //    ret.setId/*  */(rs.getInt(NewsDTOImpl.ID));
    ret.setType/**/(rs.getInt(NewsDTOImpl.TYPE));
    ret.setSize/**/(rs.getInt(NewsDTOImpl.SIZE));

    return ret;
  }

  @Override
  public List<IDTO> listAll(int lim, int off) throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append("SELECT * FROM ");
    strbuf.append(this.getTableName());
    strbuf.append(", ");

    IDAO dao = //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        daoParentClass, connectionBean);
    strbuf.append(dao.getTableName());

    strbuf.append(" WHERE ");
    strbuf.append(dao.getTableName() + "." + PublicationDTO.ID + " = " + this.getTableName() + "." + NewsDTO.ID);

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

  @Override
  public List<IDTO> listBy(String key, Object val) throws Exception {

    if (key == null || val == null) {
      throw new IllegalArgumentException("key == null || val == null");
    }

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("SELECT * FROM ");
    strbuf.append(this.getTableName());
    strbuf.append(", ");

    IDAO dao = //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        daoParentClass, connectionBean);
    strbuf.append(dao.getTableName());

    strbuf.append(" WHERE ");
    strbuf.append(key);
    strbuf.append(" = ");
    strbuf.append(val);

    strbuf.append(" AND ");
    strbuf.append(dao.getTableName() + "." + PublicationDTO.ID + " = " + this.getTableName() + "." + NewsDTO.ID);

    System.err.println(strbuf.toString());

    ResultSet rs = //
    connection.createStatement().executeQuery(strbuf.toString());

    List<IDTO> ret = new ArrayList<IDTO>();

    while (rs.next()) {
      ret.add(resultSetToDTO(rs));
    }

    return ret;
  }

}
