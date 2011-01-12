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
  // MySQLBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() //
      throws Exception {

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(NewsDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY, ");
    strbuf.append(NewsDTOImpl.NEWS_ATT_1);
    strbuf.append(" VARCHAR(50), ");
    strbuf.append(NewsDTOImpl.NEWS_ATT_2);
    strbuf.append(" VARCHAR(50)  ");

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertCollst(IDTO dto) //
      throws Exception {

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(NewsDTOImpl.ID);
    strbuf.append(", ");
    strbuf.append(NewsDTOImpl.NEWS_ATT_1);
    strbuf.append(", ");
    strbuf.append(NewsDTOImpl.NEWS_ATT_2);

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    NewsDTOImpl newsDTOImpl = (NewsDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    // -------------------------------------------
    // In this case the id is not auto-incremental
    // -------------------------------------------

    strbuf.append(newsDTOImpl.getId());
    strbuf.append(", ");
    strbuf.append(singleQuotes(newsDTOImpl.getNewsAtt1()));
    strbuf.append(", ");
    strbuf.append(singleQuotes(newsDTOImpl.getNewsAtt2()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected String createUpdateValues(IDTO dto) //
      throws Exception {

    NewsDTOImpl newsDTOImpl = (NewsDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(NewsDTOImpl.NEWS_ATT_1);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(newsDTOImpl.getNewsAtt1()));

    strbuf.append(", ");

    strbuf.append(NewsDTOImpl.NEWS_ATT_2);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(newsDTOImpl.getNewsAtt2()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------
  // IDAO
  // --------------------------------------------------------------------------------

  @Override
  public List<IDTO> listAll(int lim, int off) throws Exception {

    StringBuffer strbuf = new StringBuffer();

    IDAO par0 = //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        daoParentClass, connectionBean);

    strbuf.append("SELECT * FROM ");
    strbuf.append(this.getTableName());
    strbuf.append(", ");
    strbuf.append(par0.getTableName());

    strbuf.append(" WHERE ");
    strbuf.append( //
        /**/par0.getTableName() + "." + PublicationDTO.ID + //
            " = " + //
            this.getTableName() + "." + NewsDTO.ID);

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
  public List<IDTO> listBy(String key, Object val) throws Exception {

    if (key == null || val == null) {
      throw new IllegalArgumentException("key == null || val == null");
    }

    StringBuffer strbuf = new StringBuffer();

    IDAO par0 = //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        daoParentClass, connectionBean);
    strbuf.append(par0.getTableName());

    strbuf.append("SELECT * FROM ");
    strbuf.append(this.getTableName());
    strbuf.append(", ");

    strbuf.append(" WHERE ");
    strbuf.append(key);
    strbuf.append(" = ");
    strbuf.append(val);

    strbuf.append(" AND ");
    strbuf.append( //
        /**/par0.getTableName() + "." + PublicationDTO.ID + //
            " = " + //
            this.getTableName() + "." + NewsDTO.ID);

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

  protected NewsDTOImpl internalResultSetToDTO(ResultSet rs, IDTO dto) //
      throws Exception {

    NewsDTOImpl ret = (NewsDTOImpl) dto;

    // ---------------------------------------------
    // Id comes from DTO parent class in this case
    // ret.setId(rs.getInt(NewsDTOImpl.ID));
    // ---------------------------------------------

    ret.setNewsAtt1(rs.getString(NewsDTOImpl.NEWS_ATT_1));
    ret.setNewsAtt2(rs.getString(NewsDTOImpl.NEWS_ATT_2));

    return ret;
  }
}
