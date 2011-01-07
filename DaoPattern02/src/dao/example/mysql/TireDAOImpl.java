package dao.example.mysql;

import java.sql.ResultSet;

import dao.base.api.IDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.TireDAO;
import dao.example.base.TireDTO;

/**
 * @author Demián Gutierrez
 */
public class TireDAOImpl extends PartDAOImpl implements TireDAO {

  public TireDAOImpl() {
    //super(TireDTOImpl.class);
  }

  // --------------------------------------------------------------------------------
  // PostgresBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(TireDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY AUTO_INCREMENT, ");
    strbuf.append(TireDTOImpl.SPEED);
    strbuf.append(" INT,    ");
    strbuf.append(TireDTOImpl.RATING);
    strbuf.append(" INT     ");

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertCollst(IDTO dto) //
      throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(TireDTOImpl.SPEED);
    strbuf.append(", ");
    strbuf.append(TireDTOImpl.RATING);

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    TireDTOImpl tireDTOImpl = (TireDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    // XXX: MySQL does not need to include the id if auto incremental
    //    strbuf.append(tireDTOImpl.getId());
    //    strbuf.append(", ");
    strbuf.append(tireDTOImpl.getSpeed());
    strbuf.append(", ");
    strbuf.append(tireDTOImpl.getRating());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void insert(IDTO dto) throws Exception {
    super.internalInsert(dto);
  }

  // --------------------------------------------------------------------------------

  protected String createUpdateValues(IDTO dto) //
      throws Exception {

    TireDTOImpl tireDTOImpl = (TireDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(TireDTOImpl.SPEED);
    strbuf.append(" = ");
    strbuf.append(tireDTOImpl.getSpeed());

    strbuf.append(", ");

    strbuf.append(TireDTOImpl.RATING);
    strbuf.append(" = ");
    strbuf.append(tireDTOImpl.getRating());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected TireDTOImpl resultSetToDTO(ResultSet rs) throws Exception {
    TireDTOImpl ret = //
    (TireDTOImpl) dtaSession.getDtaByKey( //
        TireDTOImpl.class, rs.getInt(TireDTOImpl.ID));

    if (ret != null) {
      return ret;
    }

    ret = (TireDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
        getDTO(TireDTO.class, connectionBean);

    ret.setId/*    */(rs.getInt(TireDTOImpl.ID));
    ret.setSpeed/* */(rs.getInt(TireDTOImpl.SPEED));
    ret.setRating/**/(rs.getInt(TireDTOImpl.RATING));

    return (TireDTOImpl) dtaSession.add(ret);
  }
}
