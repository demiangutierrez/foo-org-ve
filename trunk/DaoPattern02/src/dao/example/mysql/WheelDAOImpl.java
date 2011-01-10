package dao.example.mysql;

import java.sql.ResultSet;

import dao.base.api.IDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.WheelDAO;
import dao.example.base.WheelDTO;

/**
 * @author Demián Gutierrez
 */
public class WheelDAOImpl extends PartDAOImpl implements WheelDAO {

  public WheelDAOImpl() {
    //super(WheelDTOImpl.class);
    dtoClass = WheelDTOImpl.class;
  }

  // --------------------------------------------------------------------------------
  // PostgresBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(WheelDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY, ");
    strbuf.append(WheelDTOImpl.COLOR);
    strbuf.append(" VARCHAR(100),    ");
    strbuf.append(WheelDTOImpl.SIZE);
    strbuf.append(" INT              ");

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertCollst(IDTO dto) //
      throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(WheelDTOImpl.ID);
    strbuf.append(", ");
    strbuf.append(WheelDTOImpl.COLOR);
    strbuf.append(", ");
    strbuf.append(WheelDTOImpl.SIZE);

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    WheelDTOImpl wheelDTOImpl = (WheelDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    // XXX: MySQL does not need to include the id if auto incremental
    //    strbuf.append(WheelDTOImpl.getId());
    //    strbuf.append(", ");
    strbuf.append(wheelDTOImpl.getId());
    strbuf.append(", ");
    strbuf.append(singleQuotes(wheelDTOImpl.getColor()));
    strbuf.append(", ");
    strbuf.append(wheelDTOImpl.getSize());

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

    WheelDTOImpl wheelDTOImpl = (WheelDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(WheelDTOImpl.COLOR);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(wheelDTOImpl.getColor()));

    strbuf.append(", ");

    strbuf.append(WheelDTOImpl.SIZE);
    strbuf.append(" = ");
    strbuf.append(wheelDTOImpl.getSize());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected WheelDTOImpl resultSetToDTO(ResultSet rs) throws Exception {
    WheelDTOImpl ret = //
    (WheelDTOImpl) dtaSession.getDtaByKey( //
        WheelDTOImpl.class, rs.getInt(WheelDTOImpl.ID));

    if (ret != null) {
      return ret;
    }

    ret = (WheelDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
        getDTO(WheelDTO.class, connectionBean);

    ret.setId/*   */(rs.getInt/*   */(WheelDTOImpl.ID));
    ret.setColor/**/(rs.getString/**/(WheelDTOImpl.COLOR));
    ret.setSize/* */(rs.getInt/*   */(WheelDTOImpl.SIZE));

    return (WheelDTOImpl) dtaSession.add(ret);
  }
}
