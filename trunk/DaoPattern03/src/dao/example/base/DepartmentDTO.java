package dao.example.base;

import java.util.List;

import dao.annotation.Entity;
import dao.annotation.OneToMany;
import dao.annotation.SQLType;
import dao.base.api.IDTO;

/**
 * @author Demián Gutierrez
 */
@Entity(value = "department")
public interface DepartmentDTO extends IDTO {

  @SQLType("VARCHAR(100)")
  public String/**/getName();

  public void/*  */setName(String name);

  // --------------------------------------------------------------------------------

  @SQLType("VARCHAR(100)")
  public String/**/getDescription();

  public void/*  */setDescription(String description);

  // --------------------------------------------------------------------------------

  @OneToMany(mappedBy = "departmentDTORef", dtoClass = EmployeeDTO.class)
  public List<EmployeeDTO> getEmployeeDTOList();

  public void/*          */setEmployeeDTOList(List<EmployeeDTO> employeeDTOList);
}
