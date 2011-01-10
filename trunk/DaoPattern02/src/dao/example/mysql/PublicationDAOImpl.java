package dao.example.mysql;

import java.sql.ResultSet;
import java.util.List;

import acme.bloodtime.dao.ArticuloDO;

import dao.base.api.IDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.PublicationDAO;
import dao.example.base.PublicationDTO;
import dao.example.base.BookDAO;
import dao.example.base.BookDTO;
import dao.example.base.NewsDAO;
import dao.example.base.NewsDTO;

/**
 * @author Demián Gutierrez
 */
public class PublicationDAOImpl extends MySQLBaseDAO implements PublicationDAO {

  public PublicationDAOImpl() {
    super(PublicationDTOImpl.class);
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
    BookDAO tDAO = (BookDAO) //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        BookDAO.class, connectionBean);
    NewsDAO newsDAO = (NewsDAO) //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        NewsDAO.class, connectionBean);

    StringBuffer strbuf = new StringBuffer();

    //    strbuf.append("SELECT articulo.*, 1 as tipo FROM ");
    //    strbuf.append(getTableName() + "," + armaDAO.getTableName());
    //    strbuf.append(" WHERE  " + ArticuloDO.ID + "=" + getTableName() + ArticuloDO.ID);
    //    strbuf.append(" UNION ");
    // B
    // Magazine

    strbuf.append("SELECT " + getTableName() + ".*, 1 as tipo FROM ");
    strbuf.append(getTableName() + "," + tDAO.getTableName());
    strbuf.append(" WHERE  " + PublicationDTO.ID + "=" + id);
    strbuf.append(" AND " + tDAO.getTableName() + BookDTO.ID + " = " + PublicationDTO.ID);

    strbuf.append(" UNION ");

    strbuf.append("SELECT " + getTableName() + ".*, 2 as tipo FROM ");
    strbuf.append(getTableName() + "," + tDAO.getTableName());
    strbuf.append(" WHERE  " + PublicationDTO.ID + "=" + id);
    strbuf.append(" AND " + newsDAO.getTableName() + NewsDTO.ID + " = " + PublicationDTO.ID);
  }

  // --------------------------------------------------------------------------------

  protected PublicationDTOImpl resultSetToDTO(ResultSet rs) throws Exception {
    PublicationDTOImpl ret = //
    (PublicationDTOImpl) dtaSession.getDtaByKey( //
        PublicationDTOImpl.class, rs.getInt(PublicationDTOImpl.ID));

    if (ret != null) {
      return ret;
    }

    ret = (PublicationDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
        getDTO(PublicationDTO.class, connectionBean);

    ret.setId/*          */(rs.getInt(PublicationDTOImpl.ID));
    ret.setManufacturer/**/(rs.getString(PublicationDTOImpl.MANUFACTURER));
    ret.setNumber/*      */(rs.getString(PublicationDTOImpl.NUMBER));
    ret.setDescription/* */(rs.getString(PublicationDTOImpl.DESCRIPTION));

    return (PublicationDTOImpl) dtaSession.add(ret);
  }
}
