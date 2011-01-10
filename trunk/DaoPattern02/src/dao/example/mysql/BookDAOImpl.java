package dao.example.mysql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.BookDAO;
import dao.example.base.BookDTO;
import dao.example.base.PublicationDAO;
import dao.example.base.PublicationDTO;

/**
 * @author Demián Gutierrez
 */
public class BookDAOImpl extends MySQLBaseDAO implements BookDAO {

  public BookDAOImpl() {
    super(BookDTO.class);
    daoParentClass = PublicationDAO.class;
  }

  // --------------------------------------------------------------------------------
  // PostgresBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(BookDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY, ");
    strbuf.append(BookDTOImpl.SPEED);
    strbuf.append(" INT,    ");
    strbuf.append(BookDTOImpl.RATING);
    strbuf.append(" INT     ");

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertCollst(IDTO dto) //
      throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(BookDTOImpl.ID);
    strbuf.append(", ");
    strbuf.append(BookDTOImpl.SPEED);
    strbuf.append(", ");
    strbuf.append(BookDTOImpl.RATING);

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    BookDTOImpl bookDTOImpl = (BookDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    // XXX: MySQL does not need to include the id if auto incremental
    //    strbuf.append(bookDTOImpl.getId());
    //    strbuf.append(", ");
    strbuf.append(bookDTOImpl.getId());
    strbuf.append(", ");
    strbuf.append(bookDTOImpl.getSpeed());
    strbuf.append(", ");
    strbuf.append(bookDTOImpl.getRating());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void insert(IDTO dto) throws Exception {
    internalInsert(dto);
  }

  // --------------------------------------------------------------------------------

  protected String createUpdateValues(IDTO dto) //
      throws Exception {

    BookDTOImpl bookDTOImpl = (BookDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(BookDTOImpl.SPEED);
    strbuf.append(" = ");
    strbuf.append(bookDTOImpl.getSpeed());

    strbuf.append(", ");

    strbuf.append(BookDTOImpl.RATING);
    strbuf.append(" = ");
    strbuf.append(bookDTOImpl.getRating());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  //  protected BookDTOImpl resultSetToDTO(ResultSet rs) throws Exception {
  //    BookDTOImpl ret = //
  //    (BookDTOImpl) dtaSession.getDtaByKey( //
  //        BookDTOImpl.class, rs.getInt(BookDTOImpl.ID));
  //
  //    if (ret != null) {
  //      return ret;
  //    }
  //
  //    ret = (BookDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
  //        getDTO(BookDTO.class, connectionBean);
  //
  //    ret.setId/*    */(rs.getInt(BookDTOImpl.ID));
  //    ret.setSpeed/* */(rs.getInt(BookDTOImpl.SPEED));
  //    ret.setRating/**/(rs.getInt(BookDTOImpl.RATING));
  //
  //    return (BookDTOImpl) dtaSession.add(ret);
  //  }

  protected BookDTOImpl internalResultSetToDTO(ResultSet rs, IDTO dto) throws Exception {
    BookDTOImpl ret = (BookDTOImpl) dto;

    //    ret.setId/*    */(rs.getInt(BookDTOImpl.ID));
    ret.setSpeed/* */(rs.getInt(BookDTOImpl.SPEED));
    ret.setRating/**/(rs.getInt(BookDTOImpl.RATING));

    return ret;
  }

  // --------------------------------------------------------------------------------

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
    strbuf.append(dao.getTableName() + "." + PublicationDTO.ID + " = " + this.getTableName() + "." + BookDTO.ID);

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
    strbuf.append(dao.getTableName() + "." + PublicationDTO.ID + " = " + this.getTableName() + "." + BookDTO.ID);

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
