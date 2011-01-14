package dao.example.mysql;

import java.sql.ResultSet;

import dao.base.api.IDTO;
import dao.base.impl.Reference;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;
import dao.example.base.ProfDAO;
import dao.example.base.ProfDTO;

/**
 * @author Demián Gutierrez
 */
class ProfDAOImpl extends MySQLBaseDAO implements ProfDAO {

  public ProfDAOImpl() {
    super(ProfDTO.class);
  }

  // --------------------------------------------------------------------------------
  // MySQLBaseDAO
  // --------------------------------------------------------------------------------

  @Override
  protected String createTableColumns() //
      throws Exception {

    StringBuffer strbuf = new StringBuffer();

    DeptDAO deptDAO = (DeptDAO) //
    AbstractFactoryDAO.getFactoryDAO().getDAO( //
        DeptDAO.class, connectionBean);

    strbuf.append(ProfDTOImpl.ID);
    strbuf.append(" INT PRIMARY KEY AUTO_INCREMENT, ");
    strbuf.append(ProfDTOImpl.PROF_ATT_1);
    strbuf.append(" VARCHAR(50),   ");
    strbuf.append(ProfDTOImpl.PROF_ATT_2);
    strbuf.append(" VARCHAR(50),   ");
    strbuf.append(ProfDTOImpl.DEPT_DTO_REF);
    strbuf.append(" INT REFERENCES ");
    strbuf.append(deptDAO.getTableName());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertCollst(IDTO dto) //
      throws Exception {

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(ProfDTOImpl.PROF_ATT_1);
    strbuf.append(", ");
    strbuf.append(ProfDTOImpl.PROF_ATT_2);
    strbuf.append(", ");
    strbuf.append(ProfDTOImpl.DEPT_DTO_REF);

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected String createInsertValues(IDTO dto) //
      throws Exception {

    ProfDTOImpl profDTOImpl = (ProfDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(singleQuotes(profDTOImpl.getProfAtt1()));
    strbuf.append(", ");
    strbuf.append(singleQuotes(profDTOImpl.getProfAtt2()));
    strbuf.append(", ");

    Reference<DeptDTO> ref = profDTOImpl.getInternalDeptDTORef();
    ref.checkInsert();
    strbuf.append(ref.getIdAsString());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  protected String createUpdateValues(IDTO dto) //
      throws Exception {

    ProfDTOImpl profDTOImpl = (ProfDTOImpl) dto;

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(ProfDTOImpl.PROF_ATT_1);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(profDTOImpl.getProfAtt1()));

    strbuf.append(", ");

    strbuf.append(ProfDTOImpl.PROF_ATT_2);
    strbuf.append(" = ");
    strbuf.append(singleQuotes(profDTOImpl.getProfAtt2()));

    strbuf.append(", ");

    strbuf.append(ProfDTOImpl.DEPT_DTO_REF);
    strbuf.append(" = ");

    Reference<DeptDTO> ref = profDTOImpl.getInternalDeptDTORef();
    ref.checkUpdate();
    strbuf.append(ref.getIdAsString());

    return strbuf.toString();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected IDTO internalResultSetToDTO(ResultSet rs, IDTO dto) //
      throws Exception {

    ProfDTOImpl ret = (ProfDTOImpl) dto;

    ret.setId/*  */(rs.getInt(ProfDTOImpl.ID));

    ret.setProfAtt1(rs.getString(ProfDTOImpl.PROF_ATT_1));
    ret.setProfAtt2(rs.getString(ProfDTOImpl.PROF_ATT_2));

    Reference<DeptDTO> ref = ret.getInternalDeptDTORef();
    ref.setRefIdent(rs.getInt(ProfDTOImpl.DEPT_DTO_REF));
    ref.setRefValue(null);

    return ret;
  }
}
