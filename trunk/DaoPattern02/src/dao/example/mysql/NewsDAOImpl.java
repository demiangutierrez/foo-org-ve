package dao.example.mysql;

import java.sql.ResultSet;

import dao.base.api.IDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.PublicationDAO;
import dao.example.base.NewsDAO;
import dao.example.base.NewsDTO;

/**
 * @author Demián Gutierrez
 */
public class NewsDAOImpl extends MySQLBaseDAO implements NewsDAO {

  public NewsDAOImpl() {
    super(NewsDTOImpl.class);
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

  protected NewsDTOImpl resultSetToDTO(ResultSet rs) throws Exception {
    NewsDTOImpl ret = //
    (NewsDTOImpl) dtaSession.getDtaByKey( //
        NewsDTOImpl.class, rs.getInt(NewsDTOImpl.ID));

    if (ret != null) {
      return ret;
    }

    ret = (NewsDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
        getDTO(NewsDTO.class, connectionBean);

    ret.setId/*  */(rs.getInt(NewsDTOImpl.ID));
    ret.setType/**/(rs.getInt(NewsDTOImpl.TYPE));
    ret.setSize/**/(rs.getInt(NewsDTOImpl.SIZE));

    return (NewsDTOImpl) dtaSession.add(ret);
  }
}
