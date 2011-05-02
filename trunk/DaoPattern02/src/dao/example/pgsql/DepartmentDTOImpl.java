//package dao.example.pgsql;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import dao.base.impl.BaseDTO;
//import dao.example.base.AbstractFactoryDAO;
//import dao.example.base.DeptDAO;
//import dao.example.base.DeptDTO;
//import dao.example.base.ProfDAO;
//import dao.example.base.ProfDTO;
//
///**
// * @author Demián Gutierrez
// */
//class DeptDTOImpl extends BaseDTO implements DeptDTO {
//
//  public static final String NAME/*    */= "name";
//  public static final String DESCRIPTION = "description";
//
//  // --------------------------------------------------------------------------------
//
//  private String name;
//  private String description;
//
//  // --------------------------------------------------------------------------------
//
//  private List<ProfDTO> profDTOList;
//
//  // --------------------------------------------------------------------------------
//
//  public DeptDTOImpl() {
//    super(DeptDAO.class);
//  }
//
//  // --------------------------------------------------------------------------------
//  // DeptDTO
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public String getName() {
//    return name;
//  }
//
//  @Override
//  public void setName(String name) {
//    this.name = name;
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  public String getDescription() {
//    return description;
//  }
//
//  @Override
//  public void setDescription(String description) {
//    this.description = description;
//  }
//
//  // --------------------------------------------------------------------------------
//
//  @Override
//  @SuppressWarnings("unchecked")
//  public List<ProfDTO> getProfDTOList() {
//    if (profDTOList == null) {
//      try {
//
//        // Lazy load the list
//        ProfDAO profDAO = (ProfDAO) AbstractFactoryDAO.getFactoryDAO(). //
//            getDAO(ProfDAO.class, connectionBean);
//
//        profDTOList = new ArrayList<ProfDTO>();
//        profDTOList.addAll((Collection<? extends ProfDTO>) //
//            profDAO.listBy(ProfDTOImpl.DEPARTMENT_ID, id));
//
//      } catch (Exception e) {
//        throw new RuntimeException(e);
//      }
//    }
//
//    return profDTOList;
//  }
//
//  @Override
//  public void setProfDTOList(List<ProfDTO> profDTOList) {
//    throw new UnsupportedOperationException();
//  }
//}
