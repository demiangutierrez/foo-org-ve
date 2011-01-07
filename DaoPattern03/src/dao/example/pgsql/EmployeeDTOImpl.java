package dao.example.pgsql;

import dao.base.impl.BaseDTO;
import dao.example.base.DepartmentDTO;
import dao.example.base.EmployeeDAO;
import dao.example.base.EmployeeDTO;

/**
 * @author Demián Gutierrez
 */
public class EmployeeDTOImpl extends BaseDTO implements EmployeeDTO {

  public static final String FRST_NAME/* */= "frstName";
  public static final String LAST_NAME/* */= "lastName";
  public static final String DEPARTMENT_ID = "departmentId";

  // --------------------------------------------------------------------------------

  private String frstName;
  private String lastName;

  //  private final Reference<DepartmentDTO> departmentRef = //
  //  new Reference<DepartmentDTO>();

  private DepartmentDTO departmentDTORef;

  // --------------------------------------------------------------------------------

  public EmployeeDTOImpl() {
    super(EmployeeDAO.class);
  }

  // --------------------------------------------------------------------------------
  // EmployeeDO
  // --------------------------------------------------------------------------------

  @Override
  public String getFrstName() {
    return frstName;
  }

  @Override
  public void setFrstName(String frstName) {
    this.frstName = frstName;
  }

  // --------------------------------------------------------------------------------

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  // --------------------------------------------------------------------------------

  @Override
  public DepartmentDTO getDepartmentDTORef() {
    //    if (departmentRef.getRefValue() == null) {
    //      if (departmentRef.getRefIdent() != 0) {
    //        try {
    //
    //          // Lazy load the departmentDTORef
    //          DepartmentDAO departmentDAO = (DepartmentDAO) AbstractFactoryDAO.getFactoryDAO(). //
    //              getDAO(DepartmentDAO.class, connectionBean);
    //
    //          departmentRef.setRefValue( //
    //              (DepartmentDTO) departmentDAO.loadById(departmentRef.getRefIdent()));
    //
    //        } catch (Exception e) {
    //          e.printStackTrace();
    //          throw new RuntimeException(e);
    //        }
    //      }
    //    }
    //
    //    return departmentRef.getRefValue();
    return departmentDTORef;
  }

  @Override
  public void setDepartmentDTORef(DepartmentDTO departmentDTORef) {
    this.departmentDTORef = departmentDTORef;
  }

  // --------------------------------------------------------------------------------

  //  public Reference<DepartmentDTO> getDepartmentRef() {
  //    return departmentDTORef;
  //  }

  //  public void setDepartmentRef(Reference<DepartmentDTO> departmentRef) {
  //    this.departmentRef = departmentRef;
  //  }
}
