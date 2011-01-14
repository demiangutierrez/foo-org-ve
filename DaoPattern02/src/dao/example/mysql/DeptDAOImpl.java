package dao.example.mysql;

import java.sql.ResultSet;

import dao.base.api.IDTO;
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;

/**
 * @author Demián Gutierrez
 */
// This class should have package visibility,
// but it's used in Test0 directly for educational purposes,
// so we need to make it public.
// It's also a nice example on when and why use package visibility.
public//
class DeptDAOImpl extends MySQLBaseDAO implements DeptDAO {

  public DeptDAOImpl() {
    super(DeptDTO.class);
  }

  // --------------------------------------------------------------------------------
  // MySQLBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(DeptDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY AUTO_INCREMENT, ");
    strbuf.append(DeptDTOImpl.DEPT_ATT_1);
    strbuf.append(" VARCHAR(50), ");
    strbuf.append(DeptDTOImpl.DEPT_ATT_2);
    strbuf.append(" VARCHAR(50)  ");

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertCollst(IDTO dto) //
      throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append(DeptDTOImpl.DEPT_ATT_1);
    strbuf.append(", ");
    strbuf.append(DeptDTOImpl.DEPT_ATT_2);

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    DeptDTO deptDTO = (DeptDTO) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(singleQuotes(deptDTO.getDeptAtt1()));
    strbuf.append(", ");
    strbuf.append(singleQuotes(deptDTO.getDeptAtt2()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected String createUpdateValues(IDTO dto) //
      throws Exception {

    DeptDTO deptDTO = (DeptDTO) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(DeptDTOImpl.DEPT_ATT_1);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(deptDTO.getDeptAtt1()));

    strbuf.append(", ");

    strbuf.append(DeptDTOImpl.DEPT_ATT_2);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(deptDTO.getDeptAtt2()));

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected IDTO internalResultSetToDTO(ResultSet rs, IDTO dto) throws Exception {
    DeptDTOImpl ret = (DeptDTOImpl) dto;

    ret.setId/*  */(rs.getInt(DeptDTOImpl.ID));

    ret.setDeptAtt1(rs.getString(DeptDTOImpl.DEPT_ATT_1));
    ret.setDeptAtt2(rs.getString(DeptDTOImpl.DEPT_ATT_2));

    return ret;
  }
}
