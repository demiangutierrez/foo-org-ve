package dao.example.pgsql;

import java.util.ArrayList;
import java.util.List;

import dao.base.impl.BaseDTO;
import dao.example.base.DepartmentDAO;
import dao.example.base.DepartmentDTO;
import dao.example.base.EmployeeDTO;

/**
 * @author Demián Gutierrez
 */
public class DepartmentDTOImpl extends BaseDTO implements DepartmentDTO {

  public static final String NAME/*    */= "name";
  public static final String DESCRIPTION = "description";

  // --------------------------------------------------------------------------------

  private String name;
  private String description;

  // --------------------------------------------------------------------------------

  private List<EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>();

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
  public List<EmployeeDTO> getEmployeeDTOList() {
    return employeeDTOList;
  }

  @Override
  public void setEmployeeDTOList(List<EmployeeDTO> employeeDTOList) {
    this.employeeDTOList = employeeDTOList;
  }
}
