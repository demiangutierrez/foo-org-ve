//package dao.example.pgsql;
//
//import dao.base.impl.BaseDTO;
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
//class ProfDTOImpl extends BaseDTO implements ProfDTO {
//
//  public static final String FRST_NAME/* */= "frstName";
//  public static final String LAST_NAME/* */= "lastName";
//  public static final String DEPARTMENT_ID = "deptId";
//
//  // --------------------------------------------------------------------------------
//
//  private String frstName;
//  private String lastName;
//
//  private final Reference<DeptDTO> deptRef = //
//  new Reference<DeptDTO>();
//
//  // --------------------------------------------------------------------------------
//
//  public ProfDTOImpl() {
//    super(ProfDAO.class);
//  }
//
//  // --------------------------------------------------------------------------------
//  // ProfDTO
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public String getFrstName() {
//    return frstName;
//  }
//
//  @Override
//  public void setFrstName(String frstName) {
//    this.frstName = frstName;
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public String getLastName() {
//    return lastName;
//  }
//
//  @Override
//  public void setLastName(String lastName) {
//    this.lastName = lastName;
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public DeptDTO getDeptDTORef() {
//    if (deptRef.getRefValue() == null) {
//      if (deptRef.getRefIdent() != 0) {
//        try {
//
//          // Lazy load the deptDTORef
//          DeptDAO deptDAO = (DeptDAO) AbstractFactoryDAO.getFactoryDAO(). //
//              getDAO(DeptDAO.class, connectionBean);
//
//          deptRef.setRefValue( //
//              (DeptDTO) deptDAO.loadById(deptRef.getRefIdent()));
//
//        } catch (Exception e) {
//          e.printStackTrace();
//          throw new RuntimeException(e);
//        }
//      }
//    }
//
//    return deptRef.getRefValue();
//  }
//
//  @Override
//  public void setDeptDTORef(DeptDTO deptDTORef) {
//    this.deptRef.setRefValue(deptDTORef);
//  }
//
//  // --------------------------------------------------------------------------------
//
//  public Reference<DeptDTO> getDeptRef() {
//    return deptRef;
//  }
//
//  //  public void setDeptRef(Reference<DeptDTO> deptRef) {
//  //    this.deptRef = deptRef;
//  //  }
//}
