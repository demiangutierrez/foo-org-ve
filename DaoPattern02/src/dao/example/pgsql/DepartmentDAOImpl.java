package dao.example.pgsql;

import java.sql.ResultSet;

import dao.base.api.IDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;

/**
 * @author Demián Gutierrez
 */
class DeptDAOImpl extends PgSQLBaseDAO implements DeptDAO {

  public DeptDAOImpl() {
    super(DeptDTOImpl.class);
  }

  // --------------------------------------------------------------------------------
  // PostgresBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(DeptDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY, ");
    strbuf.append(DeptDTOImpl.NAME);
    strbuf.append(" VARCHAR(100),    ");
    strbuf.append(DeptDTOImpl.DESCRIPTION);
    strbuf.append(" VARCHAR(100)     ");

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    DeptDTO deptDTO = (DeptDTO) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(deptDTO.getId());
    strbuf.append(", ");
    strbuf.append(singleQuotes(deptDTO.getName()));
    strbuf.append(", ");
    strbuf.append(singleQuotes(deptDTO.getDescription()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected String createUpdateValues(IDTO dto) //
      throws Exception {

    DeptDTO deptDTO = (DeptDTO) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(DeptDTOImpl.NAME);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(deptDTO.getName()));

    strbuf.append(", ");

    strbuf.append(DeptDTOImpl.DESCRIPTION);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(deptDTO.getDescription()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected IDTO resultSetToDTO(ResultSet rs) throws Exception {
    DeptDTOImpl ret = //
    (DeptDTOImpl) dtaSession.getDtaByKey( //
        DeptDTOImpl.class, rs.getInt(DeptDTOImpl.ID));

    if (ret != null) {
      return ret;
    }

    ret = (DeptDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
        getDTO(DeptDTO.class, connectionBean);

    ret.setId/*     */(rs.getInt(DeptDTOImpl.ID));
    ret.setName/*   */(rs.getString(DeptDTOImpl.NAME));
    ret.setDescription(rs.getString(DeptDTOImpl.DESCRIPTION));

    return (DeptDTOImpl) dtaSession.add(ret);
  }
}
