package dao.example.base;

import dao.base.api.IDTO;

/**
 * @author Demián Gutierrez
 */
public interface EmployeeDTO extends IDTO {

  public String/**/getFrstName();

  public void/*  */setFrstName(String frstName);

  // --------------------------------------------------------------------------------

  public String/**/getLastName();

  public void/*  */setLastName(String lastName);

  // --------------------------------------------------------------------------------

  public DepartmentDTO getDepartmentDTORef();

  public void/*      */setDepartmentDTORef(DepartmentDTO departmentDTORef);
}
