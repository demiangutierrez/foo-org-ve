package dao.example.mysql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.base.impl.BaseDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DepartmentDAO;
import dao.example.base.DepartmentDTO;
import dao.example.base.EmployeeDAO;
import dao.example.base.EmployeeDTO;

/**
 * @author Demián Gutierrez
 */
// This class should have package visibility,
// but it's used in Test0 directly for educational purposes,
// so we need to make it public.
// It's also a nice example on when and why use package visibility.
public//
class DepartmentDTOImpl extends BaseDTO implements DepartmentDTO {

  public static final String NAME/*    */= "name";
  public static final String DESCRIPTION = "description";

  // --------------------------------------------------------------------------------

  private String name;
  private String description;

  // --------------------------------------------------------------------------------

  private List<EmployeeDTO> employeeDTOList;

  // --------------------------------------------------------------------------------

  public DepartmentDTOImpl() {
    super(DepartmentDAO.class);
  }

  // --------------------------------------------------------------------------------
  // DepartmentDTO
  // --------------------------------------------------------------------------------

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  // --------------------------------------------------------------------------------

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  // --------------------------------------------------------------------------------

  @Override
  @SuppressWarnings("unchecked")
  public List<EmployeeDTO> getEmployeeDTOList() {
    if (employeeDTOList == null) {
      try {

        // Lazy load the list
        EmployeeDAO employeeDAO = (EmployeeDAO) AbstractFactoryDAO.getFactoryDAO(). //
            getDAO(EmployeeDAO.class, connectionBean);

        employeeDTOList = new ArrayList<EmployeeDTO>();
        employeeDTOList.addAll((Collection<? extends EmployeeDTO>) //
            employeeDAO.listBy(EmployeeDTOImpl.DEPARTMENT_ID, id));

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    return employeeDTOList;
  }

  @Override
  public void setEmployeeDTOList(List<EmployeeDTO> employeeDTOList) {
    throw new UnsupportedOperationException();
  }
}
