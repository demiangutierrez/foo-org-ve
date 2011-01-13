package dao.example.base;

import java.util.List;

import dao.base.api.IDTO;

/**
 * @author Demián Gutierrez
 */
public interface DepartmentDTO extends IDTO {

  public static final String NAME/*    */= "name";
  public static final String DESCRIPTION = "description";

  // --------------------------------------------------------------------------------

  public String/**/getName();

  public void/*  */setName(String name);

  // --------------------------------------------------------------------------------

  public String/**/getDescription();

  public void/*  */setDescription(String description);

  // --------------------------------------------------------------------------------

  public List<EmployeeDTO> getEmployeeDTOList();

  public void/*          */setEmployeeDTOList(List<EmployeeDTO> employeeDTOList);
}
