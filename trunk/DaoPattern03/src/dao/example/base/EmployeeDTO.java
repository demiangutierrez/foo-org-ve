package dao.example.base;

import dao.annotation.Entity;
import dao.annotation.ManyToOne;
import dao.annotation.SQLType;
import dao.base.api.IDTO;

/**
 * @author Demián Gutierrez
 */
@Entity(value = "employee")
public interface EmployeeDTO extends IDTO {

  @SQLType("VARCHAR(100)")
  public String/**/getFrstName();

  public void/*  */setFrstName(String frstName);

  // --------------------------------------------------------------------------------

  @SQLType("VARCHAR(100)")
  public String/**/getLastName();

  public void/*  */setLastName(String lastName);

  // --------------------------------------------------------------------------------

  @ManyToOne(mappedBy = "employeeDTOList", dtoClass = DepartmentDTO.class)
  public DepartmentDTO getDepartmentDTORef();

  public void/*      */setDepartmentDTORef(DepartmentDTO departmentDTORef);
}
