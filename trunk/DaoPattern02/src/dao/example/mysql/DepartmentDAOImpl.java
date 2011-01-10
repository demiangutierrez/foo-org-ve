package dao.example.mysql;

import java.sql.ResultSet;

import dao.base.api.IDTO;
import dao.example.base.DepartmentDAO;
import dao.example.base.DepartmentDTO;

/**
 * @author Demián Gutierrez
 */
// This class should have package visibility,
// but it's used in Test0 directly for educational purposes,
// so we need to make it public.
// It's also a nice example on when and why use package visibility.
public//
class DepartmentDAOImpl extends MySQLBaseDAO implements DepartmentDAO {

  public DepartmentDAOImpl() {
    super(DepartmentDTO.class);
  }

  // --------------------------------------------------------------------------------
  // PostgresBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(DepartmentDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY AUTO_INCREMENT, ");
    strbuf.append(DepartmentDTOImpl.NAME);
    strbuf.append(" VARCHAR(100),    ");
    strbuf.append(DepartmentDTOImpl.DESCRIPTION);
    strbuf.append(" VARCHAR(100)     ");

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertCollst(IDTO dto) //
      throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(DepartmentDTOImpl.NAME);
    strbuf.append(", ");
    strbuf.append(DepartmentDTOImpl.DESCRIPTION);

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    DepartmentDTO departmentDTO = (DepartmentDTO) dto;

    StringBuffer strbuf = new StringBuffer();

    // XXX: MySQL does not need to include the id if auto incremental
    //    strbuf.append(departmentDTO.getId());
    //    strbuf.append(", ");
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

  //  protected IDTO resultSetToDTO(ResultSet rs) throws Exception {
  //    DepartmentDTOImpl ret = //
  //    (DepartmentDTOImpl) dtaSession.getDtaByKey( //
  //        DepartmentDTOImpl.class, rs.getInt(DepartmentDTOImpl.ID));
  //
  //    if (ret != null) {
  //      return ret;
  //    }
  //
  //    ret = (DepartmentDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
  //        getDTO(DepartmentDTO.class, connectionBean);
  //
  //    ret.setId/*     */(rs.getInt(DepartmentDTOImpl.ID));
  //    ret.setName/*   */(rs.getString(DepartmentDTOImpl.NAME));
  //    ret.setDescription(rs.getString(DepartmentDTOImpl.DESCRIPTION));
  //
  //    return (DepartmentDTOImpl) dtaSession.add(ret);
  //  }

  @Override
  protected IDTO internalResultSetToDTO(ResultSet rs, IDTO dto) throws Exception {
    DepartmentDTOImpl ret = (DepartmentDTOImpl) dto;

    ret.setId/*     */(rs.getInt(DepartmentDTOImpl.ID));
    ret.setName/*   */(rs.getString(DepartmentDTOImpl.NAME));
    ret.setDescription(rs.getString(DepartmentDTOImpl.DESCRIPTION));

    return ret;
  }
}
