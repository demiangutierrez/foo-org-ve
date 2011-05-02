//package dao.example.pgsql;
//
//import java.sql.ResultSet;
//
//import dao.base.api.IDTO;
//import dao.base.impl.Reference;
//import dao.example.base.AbstractFactoryDAO;
//import dao.example.base.DeptDAO;
//import dao.example.base.DeptDTO;
//import dao.example.base.ProfDAO;
//import dao.example.base.ProfDTO;
//
///**
// * @author Demián Gutierrez
// */
//class ProfDAOImpl extends PgSQLBaseDAO implements ProfDAO {
//
//  public ProfDAOImpl() {
//    super(ProfDTOImpl.class);
//  }
//
//  // --------------------------------------------------------------------------------
//  // PostgresBaseDAO
//  // --------------------------------------------------------------------------------
//
//  @Override
//  protected String createTableColumns() throws Exception {
//    StringBuffer strbuf = new StringBuffer();
//
//    DeptDAO deptDAO = (DeptDAO) //
//    AbstractFactoryDAO.getFactoryDAO().getDAO( //
//        DeptDAO.class, connectionBean);
//
//    strbuf.append(ProfDTOImpl.ID);
//    strbuf.append(" INT PRIMARY KEY, ");
//    strbuf.append(ProfDTOImpl.FRST_NAME);
//    strbuf.append(" VARCHAR(100),    ");
//    strbuf.append(ProfDTOImpl.LAST_NAME);
//    strbuf.append(" VARCHAR(100),    ");
//    strbuf.append(ProfDTOImpl.DEPARTMENT_ID);
//    strbuf.append(" INT REFERENCES   ");
//    strbuf.append(deptDAO.getTableName());
//
//    return strbuf.toString();
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  protected String createInsertValues(IDTO dto) //
//      throws Exception {
//
//    ProfDTOImpl profDTOImpl = (ProfDTOImpl) dto;
//
//    StringBuffer strbuf = new StringBuffer();
//
//    strbuf.append(profDTOImpl.getId());
//    strbuf.append(", ");
//    strbuf.append(singleQuotes(profDTOImpl.getFrstName()));
//    strbuf.append(", ");
//    strbuf.append(singleQuotes(profDTOImpl.getLastName()));
//    strbuf.append(", ");
//
//    Reference<DeptDTO> ref = profDTOImpl.getDeptRef();
//    ref.checkInsert();
//    strbuf.append(ref.getIdAsString());
//
//    return strbuf.toString();
//  }
//
//  // --------------------------------------------------------------------------------
//
//  protected String createUpdateValues(IDTO dto) //
//      throws Exception {
//
//    ProfDTOImpl profDTOImpl = (ProfDTOImpl) dto;
//
//    StringBuffer strbuf = new StringBuffer();
//
//    strbuf.append(ProfDTOImpl.FRST_NAME);
//    strbuf.append(" = ");
//    strbuf.append(singleQuotes(profDTOImpl.getFrstName()));
//
//    strbuf.append(", ");
//
//    strbuf.append(ProfDTOImpl.LAST_NAME);
//    strbuf.append(" = ");
//    strbuf.append(singleQuotes(profDTOImpl.getLastName()));
//
//    strbuf.append(", ");
//
//    strbuf.append(ProfDTOImpl.DEPARTMENT_ID);
//    strbuf.append(" = ");
//
//    Reference<DeptDTO> ref = profDTOImpl.getDeptRef();
//    ref.checkUpdate();
//    strbuf.append(ref.getIdAsString());
//
//    return strbuf.toString();
//  }
//
//  // --------------------------------------------------------------------------------
//
//  protected ProfDTOImpl resultSetToDTO(ResultSet rs) throws Exception {
//    ProfDTOImpl ret = //
//    (ProfDTOImpl) dtaSession.getDtaByKey( //
//        ProfDTOImpl.class, rs.getInt(ProfDTOImpl.ID));
//
//    if (ret != null) {
//      return ret;
//    }
//
//    ret = (ProfDTOImpl) AbstractFactoryDAO.getFactoryDAO(). //
//        getDTO(ProfDTO.class, connectionBean);
//
//    ret.setId/*      */(rs.getInt(ProfDTOImpl.ID));
//    ret.setFrstName/**/(rs.getString(ProfDTOImpl.FRST_NAME));
//    ret.setLastName/**/(rs.getString(ProfDTOImpl.LAST_NAME));
//
//    Reference<DeptDTO> ref = ret.getDeptRef();
//    ref.setRefIdent(rs.getInt(ProfDTOImpl.DEPARTMENT_ID));
//    ref.setRefValue(null);
//
//    return (ProfDTOImpl) dtaSession.add(ret);
//  }
//}
