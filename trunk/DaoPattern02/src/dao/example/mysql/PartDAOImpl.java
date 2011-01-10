package dao.example.mysql;

import java.sql.ResultSet;

import dao.base.api.IDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.PartDAO;
import dao.example.base.PartDTO;
import dao.example.base.TireDAO;
import dao.example.base.TireDTO;

/**
 * @author Demián Gutierrez
 */
public class PartDAOImpl extends MySQLBaseDAO implements PartDAO {

  public PartDAOImpl() {
    super(PartDTOImpl.class);
  }

  // --------------------------------------------------------------------------------
  // PostgresBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(PartDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY AUTO_INCREMENT, ");
    strbuf.append(PartDTOImpl.MANUFACTURER);
    strbuf.append(" VARCHAR(100),    ");
    strbuf.append(PartDTOImpl.NUMBER);
    strbuf.append(" VARCHAR(100),    ");
    strbuf.append(PartDTOImpl.DESCRIPTION);
    strbuf.append(" VARCHAR(100)     ");

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertCollst(IDTO dto) //
      throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(PartDTOImpl.MANUFACTURER);
    strbuf.append(", ");
    strbuf.append(PartDTOImpl.NUMBER);
    strbuf.append(", ");
    strbuf.append(PartDTOImpl.DESCRIPTION);

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    PartDTOImpl partDTOImpl = (PartDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    // XXX: MySQL does not need to include the id if auto incremental
    //    strbuf.append(partDTOImpl.getId());
    //    strbuf.append(", ");
    strbuf.append(singleQuotes(partDTOImpl.getManufacturer()));
    strbuf.append(", ");
    strbuf.append(singleQuotes(partDTOImpl.getNumber()));
    strbuf.append(", ");
    strbuf.append(singleQuotes(partDTOImpl.getDescription()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void insert(IDTO dto) throws Exception {
    if (dto instanceof TireDTO) {
      TireDAO tireDAO = (TireDAO) //
      AbstractFactoryDAO.getFactoryDAO().getDAO( //
          TireDAO.class, connectionBean);

      tireDAO.insert(dto);

      return;
    }

    // ---------------------------------------------
    // Others if (dto instanceof xxxDTO) if required
    // ---------------------------------------------
  }

  // --------------------------------------------------------------------------------

  protected String createUpdateValues(IDTO dto) //
      throws Exception {

    PartDTOImpl partDTOImpl = (PartDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(PartDTOImpl.MANUFACTURER);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(partDTOImpl.getManufacturer()));

    strbuf.append(", ");

    strbuf.append(PartDTOImpl.NUMBER);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(partDTOImpl.getNumber()));

    strbuf.append(", ");

    strbuf.append(PartDTOImpl.DESCRIPTION);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(partDTOImpl.getDescription()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected PartDTOImpl resultSetToDTO(ResultSet rs) throws Exception {
    PartDTOImpl ret = //
    (PartDTOImpl) dtaSession.getDtaByKey( //
        PartDTOImpl.class, rs.getInt(PartDTOImpl.ID));

    if (ret != null) {
      return ret;
    }

    ret = (PartDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
        getDTO(PartDTO.class, connectionBean);

    ret.setId/*          */(rs.getInt(PartDTOImpl.ID));
    ret.setManufacturer/**/(rs.getString(PartDTOImpl.MANUFACTURER));
    ret.setNumber/*      */(rs.getString(PartDTOImpl.NUMBER));
    ret.setDescription/* */(rs.getString(PartDTOImpl.DESCRIPTION));

    return (PartDTOImpl) dtaSession.add(ret);
  }
}
