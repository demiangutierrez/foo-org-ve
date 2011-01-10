package dao.example.mysql;

import java.sql.ResultSet;

import dao.base.api.IDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.PublicationDAO;
import dao.example.base.BookDAO;
import dao.example.base.BookDTO;

/**
 * @author Demián Gutierrez
 */
public class BookDAOImpl extends MySQLBaseDAO implements BookDAO {

  public BookDAOImpl() {
    super(BookDTOImpl.class);
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

  protected BookDTOImpl resultSetToDTO(ResultSet rs) throws Exception {
    BookDTOImpl ret = //
    (BookDTOImpl) dtaSession.getDtaByKey( //
        BookDTOImpl.class, rs.getInt(BookDTOImpl.ID));

    if (ret != null) {
      return ret;
    }

    ret = (BookDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
        getDTO(BookDTO.class, connectionBean);

    ret.setId/*    */(rs.getInt(BookDTOImpl.ID));
    ret.setSpeed/* */(rs.getInt(BookDTOImpl.SPEED));
    ret.setRating/**/(rs.getInt(BookDTOImpl.RATING));

    return (BookDTOImpl) dtaSession.add(ret);
  }
}
