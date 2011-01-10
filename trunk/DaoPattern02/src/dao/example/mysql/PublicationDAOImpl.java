package dao.example.mysql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.base.api.IDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.BookDAO;
import dao.example.base.BookDTO;
import dao.example.base.NewsDAO;
import dao.example.base.NewsDTO;
import dao.example.base.PublicationDAO;
import dao.example.base.PublicationDTO;

/**
 * @author Demián Gutierrez
 */
public class PublicationDAOImpl extends MySQLBaseDAO implements PublicationDAO {

  public PublicationDAOImpl() {
    super(PublicationDTO.class);
  }

  // --------------------------------------------------------------------------------
  // PostgresBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(PublicationDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY AUTO_INCREMENT, ");
    strbuf.append(PublicationDTOImpl.MANUFACTURER);
    strbuf.append(" VARCHAR(100),    ");
    strbuf.append(PublicationDTOImpl.NUMBER);
    strbuf.append(" VARCHAR(100),    ");
    strbuf.append(PublicationDTOImpl.DESCRIPTION);
    strbuf.append(" VARCHAR(100)     ");

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertCollst(IDTO dto) //
      throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(PublicationDTOImpl.MANUFACTURER);
    strbuf.append(", ");
    strbuf.append(PublicationDTOImpl.NUMBER);
    strbuf.append(", ");
    strbuf.append(PublicationDTOImpl.DESCRIPTION);

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    PublicationDTOImpl publicationDTOImpl = (PublicationDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    // XXX: MySQL does not need to include the id if auto incremental
    //    strbuf.append(publicationDTOImpl.getId());
    //    strbuf.append(", ");
    strbuf.append(singleQuotes(publicationDTOImpl.getManufacturer()));
    strbuf.append(", ");
    strbuf.append(singleQuotes(publicationDTOImpl.getNumber()));
    strbuf.append(", ");
    strbuf.append(singleQuotes(publicationDTOImpl.getDescription()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void insert(IDTO dto) throws Exception {
    if (dto instanceof BookDTO) {
      BookDAO bookDAO = (BookDAO) //
      AbstractFactoryDAO.getFactoryDAO().getDAO( //
          BookDAO.class, connectionBean);

      bookDAO.insert(dto);

      return;
    }

    if (dto instanceof NewsDTO) {
      NewsDAO newsDAO = (NewsDAO) //
      AbstractFactoryDAO.getFactoryDAO().getDAO( //
          NewsDAO.class, connectionBean);

      newsDAO.insert(dto);

      return;
    }

    // ---------------------------------------------
    // Others if (dto instanceof xxxDTO) if required
    // ---------------------------------------------
  }

  @Override
  public void update(IDTO dto) throws Exception {
    if (dto instanceof BookDTO) {
      BookDAO bookDAO = (BookDAO) //
      AbstractFactoryDAO.getFactoryDAO().getDAO( //
          BookDAO.class, connectionBean);

      bookDAO.update(dto);

      return;
    }

    if (dto instanceof NewsDTO) {
      NewsDAO newsDAO = (NewsDAO) //
      AbstractFactoryDAO.getFactoryDAO().getDAO( //
          NewsDAO.class, connectionBean);

      newsDAO.update(dto);

      return;
    }

    // ---------------------------------------------
    // Others if (dto instanceof xxxDTO) if required
    // ---------------------------------------------
  }

  @Override
  public void delete(IDTO dto) throws Exception {
    if (dto instanceof BookDTO) {
      BookDAO bookDAO = (BookDAO) //
      AbstractFactoryDAO.getFactoryDAO().getDAO( //
          BookDAO.class, connectionBean);

      bookDAO.delete(dto);

      return;
    }

    if (dto instanceof NewsDTO) {
      NewsDAO newsDAO = (NewsDAO) //
      AbstractFactoryDAO.getFactoryDAO().getDAO( //
          NewsDAO.class, connectionBean);

      newsDAO.delete(dto);

      return;
    }

    // ---------------------------------------------
    // Others if (dto instanceof xxxDTO) if required
    // ---------------------------------------------
  }

  // --------------------------------------------------------------------------------

  protected String createUpdateValues(IDTO dto) //
      throws Exception {

    PublicationDTOImpl publicationDTOImpl = (PublicationDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(PublicationDTOImpl.MANUFACTURER);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(publicationDTOImpl.getManufacturer()));

    strbuf.append(", ");

    strbuf.append(PublicationDTOImpl.NUMBER);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(publicationDTOImpl.getNumber()));

    strbuf.append(", ");

    strbuf.append(PublicationDTOImpl.DESCRIPTION);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(publicationDTOImpl.getDescription()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  public List<IDTO> listAll(int lim, int off) throws Exception {
    BookDAO bookDAO = (BookDAO) //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        BookDAO.class, connectionBean);

    NewsDAO newsDAO = (NewsDAO) //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        NewsDAO.class, connectionBean);

    StringBuffer strbuf = new StringBuffer();

    // SELECT publication.*, 'dao.example.base.BookDTO' AS dtoClass FROM
    //   publication, book
    //   WHERE
    //     book.id == publication.id
    strbuf.append("SELECT " + getTableName() + ".*, '" + BookDTO.class.getName() + "' AS dtoClass FROM ");
    strbuf.append(this.getTableName() + ", " + bookDAO.getTableName());
    strbuf.append(" WHERE  " + //
        bookDAO.getTableName() + "." + BookDTO.ID + " = " + this.getTableName() + "." + PublicationDTO.ID);

    strbuf.append(" UNION ");

    // SELECT publication.*, 'dao.example.base.NewsDTO' AS dtoClass FROM
    //   publication, news
    //   WHERE
    //     news.id == publication.id
    strbuf.append("SELECT " + getTableName() + ".*, '" + NewsDTO.class.getName() + "' AS dtoClass FROM ");
    strbuf.append(this.getTableName() + ", " + newsDAO.getTableName());
    strbuf.append(" WHERE  " + //
        newsDAO.getTableName() + "." + NewsDTO.ID + " = " + this.getTableName() + "." + PublicationDTO.ID);

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
    BookDAO bookDAO = (BookDAO) //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        BookDAO.class, connectionBean);

    NewsDAO newsDAO = (NewsDAO) //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        NewsDAO.class, connectionBean);

    StringBuffer strbuf = new StringBuffer();

    // SELECT publication.*, 'dao.example.base.BookDTO' AS dtoClass FROM
    //   publication, book
    //   WHERE
    //     book.id == publication.id
    strbuf.append("SELECT " + getTableName() + ".*, '" + BookDTO.class.getName() + "' AS dtoClass FROM ");
    strbuf.append(this.getTableName() + ", " + bookDAO.getTableName());
    strbuf.append(" WHERE  " + //
        bookDAO.getTableName() + "." + BookDTO.ID + " = " + this.getTableName() + "." + PublicationDTO.ID);
    strbuf.append(" AND ");
    strbuf.append(key);
    strbuf.append(" = ");
    strbuf.append(val);

    strbuf.append(" UNION ");

    // SELECT publication.*, 'dao.example.base.NewsDTO' AS dtoClass FROM
    //   publication, news
    //   WHERE
    //     news.id == publication.id
    strbuf.append("SELECT " + getTableName() + ".*, '" + NewsDTO.class.getName() + "' AS dtoClass FROM ");
    strbuf.append(this.getTableName() + ", " + newsDAO.getTableName());
    strbuf.append(" WHERE  " + //
        newsDAO.getTableName() + "." + NewsDTO.ID + " = " + this.getTableName() + "." + PublicationDTO.ID);
    strbuf.append(" AND ");
    strbuf.append(key);
    strbuf.append(" = ");
    strbuf.append(val);

    //    if (lim >= 0 && off >= 0) {
    //      strbuf.append(" LIMIT  ");
    //      strbuf.append(lim);
    //      strbuf.append(" OFFSET ");
    //      strbuf.append(off);
    //    }

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

  protected PublicationDTOImpl resultSetToDTO(ResultSet rs) throws Exception {
    PublicationDTOImpl ret = //
    (PublicationDTOImpl) dtaSession.getDtaByKey( //
        PublicationDTOImpl.class, rs.getInt(PublicationDTOImpl.ID));

    if (ret != null) {
      return ret;
    }

    if (rs.getString("dtoClass").equals(BookDTO.class.getName())) {
      BookDAO bookDAO = (BookDAO) //
      AbstractFactoryDAO.getFactoryDAO().getDAO( //
          BookDAO.class, connectionBean);

      ret = (PublicationDTOImpl) bookDAO.loadById( //
          rs.getInt(PublicationDTO.ID));

      return (PublicationDTOImpl) dtaSession.add(ret);
    }

    if (rs.getString("dtoClass").equals(NewsDTO.class.getName())) {
      NewsDAO newsDAO = (NewsDAO) //
      AbstractFactoryDAO.getFactoryDAO().getDAO( //
          NewsDAO.class, connectionBean);

      ret = (PublicationDTOImpl) newsDAO.loadById( //
          rs.getInt(PublicationDTO.ID));

      return (PublicationDTOImpl) dtaSession.add(ret);
    }

    throw new IllegalArgumentException(rs.getString("dtoClass"));
  }

  // --------------------------------------------------------------------------------

  protected PublicationDTOImpl internalResultSetToDTO(ResultSet rs, IDTO dto) throws Exception {
    PublicationDTOImpl ret = (PublicationDTOImpl) dto;

    ret.setId/*  */(rs.getInt(PublicationDTOImpl.ID));
    ret.setManufacturer(rs.getString(PublicationDTOImpl.MANUFACTURER));
    ret.setNumber(rs.getString(PublicationDTOImpl.NUMBER));
    ret.setDescription(rs.getString(PublicationDTOImpl.DESCRIPTION));

    return ret;
  }
}
