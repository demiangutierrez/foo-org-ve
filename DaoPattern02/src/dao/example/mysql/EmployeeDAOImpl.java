package dao.example.mysql;

import java.sql.ResultSet;

import dao.base.api.IDTO;
import dao.base.impl.Reference;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DepartmentDAO;
import dao.example.base.DepartmentDTO;
import dao.example.base.EmployeeDAO;
import dao.example.base.EmployeeDTO;

/**
 * @author Demián Gutierrez
 */
class EmployeeDAOImpl extends MySQLBaseDAO implements EmployeeDAO {

  public EmployeeDAOImpl() {
    super(EmployeeDTOImpl.class);
  }

  // --------------------------------------------------------------------------------
  // PostgresBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    DepartmentDAO departmentDAO = (DepartmentDAO) //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        DepartmentDAO.class, connectionBean);

    strbuf.append(EmployeeDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY AUTO_INCREMENT, ");
    strbuf.append(EmployeeDTOImpl.FRST_NAME);
    strbuf.append(" VARCHAR(100),    ");
    strbuf.append(EmployeeDTOImpl.LAST_NAME);
    strbuf.append(" VARCHAR(100),    ");
    strbuf.append(EmployeeDTOImpl.DEPARTMENT_ID);
    strbuf.append(" INT REFERENCES   ");
    strbuf.append(departmentDAO.getTableName());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertCollst(IDTO dto) //
      throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(EmployeeDTOImpl.FRST_NAME);
    strbuf.append(", ");
    strbuf.append(EmployeeDTOImpl.LAST_NAME);
    strbuf.append(", ");
    strbuf.append(EmployeeDTOImpl.DEPARTMENT_ID);

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    EmployeeDTOImpl employeeDTOImpl = (EmployeeDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    // XXX: MySQL does not need to include the id if auto incremental
    //    strbuf.append(employeeDTOImpl.getId());
    //    strbuf.append(", ");
    strbuf.append(singleQuotes(employeeDTOImpl.getFrstName()));
    strbuf.append(", ");
    strbuf.append(singleQuotes(employeeDTOImpl.getLastName()));
    strbuf.append(", ");

    Reference<DepartmentDTO> ref = employeeDTOImpl.getDepartmentRef();
    ref.checkInsert();
    strbuf.append(ref.getIdAsString());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected String createUpdateValues(IDTO dto) //
      throws Exception {

    EmployeeDTOImpl employeeDTOImpl = (EmployeeDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(EmployeeDTOImpl.FRST_NAME);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(employeeDTOImpl.getFrstName()));

    strbuf.append(", ");

    strbuf.append(EmployeeDTOImpl.LAST_NAME);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(employeeDTOImpl.getLastName()));

    strbuf.append(", ");

    strbuf.append(EmployeeDTOImpl.DEPARTMENT_ID);
    strbuf.append(" = ");

    Reference<DepartmentDTO> ref = employeeDTOImpl.getDepartmentRef();
    ref.checkUpdate();
    strbuf.append(ref.getIdAsString());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected EmployeeDTOImpl resultSetToDTO(ResultSet rs) throws Exception {
    EmployeeDTOImpl ret = //
    (EmployeeDTOImpl) dtaSession.getDtaByKey( //
        EmployeeDTOImpl.class, rs.getInt(EmployeeDTOImpl.ID));

    if (ret != null) {
      return ret;
    }

    ret = (EmployeeDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
        getDTO(EmployeeDTO.class, connectionBean);

    ret.setId/*      */(rs.getInt(EmployeeDTOImpl.ID));
    ret.setFrstName/**/(rs.getString(EmployeeDTOImpl.FRST_NAME));
    ret.setLastName/**/(rs.getString(EmployeeDTOImpl.LAST_NAME));

    Reference<DepartmentDTO> ref = ret.getDepartmentRef();
    ref.setRefIdent(rs.getInt(EmployeeDTOImpl.DEPARTMENT_ID));
    ref.setRefValue(null);

    return (EmployeeDTOImpl) dtaSession.add(ret);
  }
}
