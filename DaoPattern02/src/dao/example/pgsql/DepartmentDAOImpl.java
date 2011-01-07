package dao.example.pgsql;

import java.sql.ResultSet;

import dao.base.api.IDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DepartmentDAO;
import dao.example.base.DepartmentDTO;

/**
 * @author Demián Gutierrez
 */
class DepartmentDAOImpl extends PgSQLBaseDAO implements DepartmentDAO {

  public DepartmentDAOImpl() {
    super(DepartmentDTOImpl.class);
  }

  // --------------------------------------------------------------------------------
  // PostgresBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(DepartmentDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY, ");
    strbuf.append(DepartmentDTOImpl.NAME);
    strbuf.append(" VARCHAR(100),    ");
    strbuf.append(DepartmentDTOImpl.DESCRIPTION);
    strbuf.append(" VARCHAR(100)     ");

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    DepartmentDTO departmentDTO = (DepartmentDTO) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(departmentDTO.getId());
    strbuf.append(", ");
    strbuf.append(singleQuotes(departmentDTO.getName()));
    strbuf.append(", ");
    strbuf.append(singleQuotes(departmentDTO.getDescription()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected String createUpdateValues(IDTO dto) //
      throws Exception {

    DepartmentDTO departmentDTO = (DepartmentDTO) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(DepartmentDTOImpl.NAME);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(departmentDTO.getName()));

    strbuf.append(", ");

    strbuf.append(DepartmentDTOImpl.DESCRIPTION);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(departmentDTO.getDescription()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected IDTO resultSetToDTO(ResultSet rs) throws Exception {
    DepartmentDTOImpl ret = //
    (DepartmentDTOImpl) dtaSession.getDtaByKey( //
        DepartmentDTOImpl.class, rs.getInt(DepartmentDTOImpl.ID));

    if (ret != null) {
      return ret;
    }

    ret = (DepartmentDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
        getDTO(DepartmentDTO.class, connectionBean);

    ret.setId/*     */(rs.getInt(DepartmentDTOImpl.ID));
    ret.setName/*   */(rs.getString(DepartmentDTOImpl.NAME));
    ret.setDescription(rs.getString(DepartmentDTOImpl.DESCRIPTION));

    return (DepartmentDTOImpl) dtaSession.add(ret);
  }
}
