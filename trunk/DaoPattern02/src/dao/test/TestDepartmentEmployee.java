package dao.test;

import java.util.Iterator;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DepartmentDAO;
import dao.example.base.DepartmentDTO;
import dao.example.base.EmployeeDAO;
import dao.example.base.EmployeeDTO;

/**
 * @author Demián Gutierrez
 */
public class TestDepartmentEmployee extends TestCase {

  private boolean compareDTODeep(DepartmentDTO departmentDTO1, DepartmentDTO departmentDTO2) {
    boolean ret = true;

    ret = ret && departmentDTO1.getId() == departmentDTO2.getId();
    ret = ret && departmentDTO1.getName().equals(departmentDTO2.getName());
    ret = ret && departmentDTO1.getDescription().equals(departmentDTO2.getDescription());

    if (departmentDTO1.getEmployeeDTOList().size() != //
    departmentDTO2.getEmployeeDTOList().size()) {
      return false;
    }

    Iterator<EmployeeDTO> itt1 = departmentDTO1.getEmployeeDTOList().iterator();
    Iterator<EmployeeDTO> itt2 = departmentDTO2.getEmployeeDTOList().iterator();

    while (itt1.hasNext() && itt2.hasNext()) {
      EmployeeDTO employeeDTO1 = (EmployeeDTO) itt1.next();
      EmployeeDTO employeeDTO2 = (EmployeeDTO) itt2.next();

      compareDTOShallow(employeeDTO1, employeeDTO2);
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  private boolean compareDTODeep(EmployeeDTO employeeDTO1, EmployeeDTO employeeDTO2) {
    boolean ret = true;

    ret = ret && employeeDTO1.getId() == employeeDTO2.getId();
    ret = ret && employeeDTO1.getFrstName().equals(employeeDTO2.getFrstName());
    ret = ret && employeeDTO1.getLastName().equals(employeeDTO2.getLastName());

    //    Reference<DepartmentDTO> ref1 = employeeDTO1.getDepartmentRef();
    //    Reference<DepartmentDTO> ref2 = employeeDTO2.getDepartmentRef();
    DepartmentDTO ref1 = employeeDTO1.getDepartmentDTORef();
    DepartmentDTO ref2 = employeeDTO2.getDepartmentDTORef();

    //    ret = ret && ref1.getRefIdent() == ref2.getRefIdent();
    // It's getting done in compareDTOShallow. May be we should cast to DTO and test with it
    ret = ret && ref1.getId() == ref2.getId();

    //    compareDTOShallow(ref1.getRefValue(), ref2.getRefValue());
    compareDTOShallow(ref1, ref2);

    return ret;
  }

  // --------------------------------------------------------------------------------

  private boolean compareDTOShallow(DepartmentDTO departmentDTO1, DepartmentDTO departmentDTO2) {
    boolean ret = true;

    ret = ret && departmentDTO1.getId() == departmentDTO2.getId();
    ret = ret && departmentDTO1.getName().equals(departmentDTO2.getName());
    ret = ret && departmentDTO1.getDescription().equals(departmentDTO2.getDescription());

    return ret;
  }

  // --------------------------------------------------------------------------------

  private boolean compareDTOShallow(EmployeeDTO employeeDTO1, EmployeeDTO employeeDTO2) {
    boolean ret = true;

    ret = ret && employeeDTO1.getId() == employeeDTO2.getId();
    ret = ret && employeeDTO1.getFrstName().equals(employeeDTO2.getFrstName());
    ret = ret && employeeDTO1.getLastName().equals(employeeDTO2.getLastName());

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testRefOnInsert() throws Exception {
    ConnectionBean connectionBean;

    DepartmentDAO departmentDAO;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    // ----------------------------------------
    // INSERT Departments
    // ----------------------------------------

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName111");
    departmentDTO1.setDescription("FooDescription111");
    departmentDAO.insert(departmentDTO1);
    assertEquals(departmentDTO1.getId(), 1);

    DepartmentDTO departmentDTO2 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO2.setName("FooName222");
    departmentDTO2.setDescription("FooDescription222");
    departmentDAO.insert(departmentDTO2);
    assertEquals(departmentDTO2.getId(), 2);

    // ----------------------------------------
    // INSERT Employees
    // ----------------------------------------

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FrstName111");
    employeeDTO1.setLastName("LastName111");
    //employeeDTO1.setDepartmentRef(departmentDTO1); // ----------> LINK
    employeeDTO1.setDepartmentDTORef(departmentDTO1); // ----------> LINK
    departmentDTO1.getEmployeeDTOList().add(employeeDTO1); // -----> LINK
    employeeDAO.insert(employeeDTO1);
    assertEquals(employeeDTO1.getId(), 1);

    EmployeeDTO employeeDTO2 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO2.setFrstName("FrstName222");
    employeeDTO2.setLastName("LastName222");
    //employeeDTO2.setDepartmentRef(departmentDTO1); // ----------> LINK
    employeeDTO2.setDepartmentDTORef(departmentDTO1); // ----------> LINK
    departmentDTO1.getEmployeeDTOList().add(employeeDTO2); // -----> LINK
    employeeDAO.insert(employeeDTO2);
    assertEquals(employeeDTO2.getId(), 2);

    EmployeeDTO employeeDTO3 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO3.setFrstName("FrstName333");
    employeeDTO3.setLastName("LastName333");
    //employeeDTO3.setDepartmentRef(departmentDTO2); // ----------> LINK
    employeeDTO3.setDepartmentDTORef(departmentDTO2); // ----------> LINK
    departmentDTO2.getEmployeeDTOList().add(employeeDTO3); // -----> LINK
    employeeDAO.insert(employeeDTO3);
    assertEquals(employeeDTO3.getId(), 3);

    EmployeeDTO employeeDTO4 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO4.setFrstName("FrstName444");
    employeeDTO4.setLastName("LastName444");
    //employeeDTO4.setDepartmentRef(departmentDTO2); // ----------> LINK
    employeeDTO4.setDepartmentDTORef(departmentDTO2); // ----------> LINK
    departmentDTO2.getEmployeeDTOList().add(employeeDTO4); // -----> LINK
    employeeDAO.insert(employeeDTO4);
    assertEquals(employeeDTO4.getId(), 4);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / CHECK Departments
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    DepartmentDTO departmentDTOX = (DepartmentDTO) departmentDAO.loadById(1);

    // XXX: Not needed anymore, it loads lazy on request
    //departmentDAO.loadEmployeeList(departmentDTOX);
    assertTrue(compareDTODeep(departmentDTO1, departmentDTOX));

    DepartmentDTO departmentDTOY = (DepartmentDTO) departmentDAO.loadById(2);

    // XXX: Not needed anymore, it loads lazy on request
    //departmentDAO.loadEmployeeList(departmentDTOY);
    assertTrue(compareDTODeep(departmentDTO2, departmentDTOY));

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / CHECK Employees
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    EmployeeDTO employeeDTOX = (EmployeeDTO) employeeDAO.loadById(1);

    // XXX: Not needed anymore, it loads lazy on request
    //employeeDAO.loadDepartmentRef(employeeDTOX);
    assertTrue(compareDTODeep(employeeDTO1, employeeDTOX));

    EmployeeDTO employeeDTOY = (EmployeeDTO) employeeDAO.loadById(2);

    // XXX: Not needed anymore, it loads lazy on request
    //employeeDAO.loadDepartmentRef(employeeDTOY);
    assertTrue(compareDTODeep(employeeDTO2, employeeDTOY));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testRefOnUpdate() throws Exception {
    ConnectionBean connectionBean;

    DepartmentDAO departmentDAO;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    // ----------------------------------------
    // INSERT Departments
    // ----------------------------------------

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO1 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO1.setName("FooName111");
    departmentDTO1.setDescription("FooDescription111");
    departmentDAO.insert(departmentDTO1);
    assertEquals(departmentDTO1.getId(), 1);

    DepartmentDTO departmentDTO2 = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO2.setName("FooName222");
    departmentDTO2.setDescription("FooDescription222");
    departmentDAO.insert(departmentDTO2);
    assertEquals(departmentDTO2.getId(), 2);

    // ----------------------------------------
    // INSERT Employees
    // ----------------------------------------

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    // d1 -> e1
    EmployeeDTO employeeDTO1 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO1.setFrstName("FrstName111");
    employeeDTO1.setLastName("LastName111");
    //employeeDTO1.setDepartmentRef(departmentDTO1); // ----------> LINK
    employeeDTO1.setDepartmentDTORef(departmentDTO1); // ----------> LINK
    departmentDTO1.getEmployeeDTOList().add(employeeDTO1); // -----> LINK
    employeeDAO.insert(employeeDTO1);
    assertEquals(employeeDTO1.getId(), 1);

    // d2 -> e2
    EmployeeDTO employeeDTO2 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO2.setFrstName("FrstName222");
    employeeDTO2.setLastName("LastName222");
    //employeeDTO2.setDepartmentRef(departmentDTO2); // ----------> LINK
    employeeDTO2.setDepartmentDTORef(departmentDTO2); // ----------> LINK
    departmentDTO2.getEmployeeDTOList().add(employeeDTO2); // -----> LINK
    employeeDAO.insert(employeeDTO2);
    assertEquals(employeeDTO2.getId(), 2);

    // e3 -> (No dep)
    EmployeeDTO employeeDTO3 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO3.setFrstName("FrstName333");
    employeeDTO3.setLastName("LastName333");
    //employeeDTO3.setDepartmentRef(departmentDTO1); // ----------> LINK
    //departmentDTO2.getEmployeeList().add(employeeDTO3); // -----> LINK
    employeeDAO.insert(employeeDTO3);
    assertEquals(employeeDTO3.getId(), 3);

    // e4 -> (No dep)
    EmployeeDTO employeeDTO4 = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO4.setFrstName("FrstName444");
    employeeDTO4.setLastName("LastName444");
    //employeeDTO4.setDepartmentRef(departmentDTO2); // ----------> LINK
    //departmentDTO2.getEmployeeList().add(employeeDTO4); // -----> LINK
    employeeDAO.insert(employeeDTO4);
    assertEquals(employeeDTO4.getId(), 4);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / UPDATE Departments
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    // d1->(e1)
    DepartmentDTO departmentDTOa = (DepartmentDTO) departmentDAO.loadById(1);

    // XXX: Not needed anymore, it loads lazy on request
    //departmentDAO.loadEmployeeList(departmentDTOa);
    assertTrue(compareDTODeep(departmentDTO1, departmentDTOa));

    // d2->(e2)
    DepartmentDTO departmentDTOb = (DepartmentDTO) departmentDAO.loadById(2);
    // XXX: Not needed anymore, it loads lazy on request
    //departmentDAO.loadEmployeeList(departmentDTOb);
    assertTrue(compareDTODeep(departmentDTO2, departmentDTOb));

    // Load 1 & 2 just to compare in the next part
    EmployeeDTO employeeDTOa = (EmployeeDTO) employeeDAO.loadById(1);
    // XXX: To avoid a lazy load like problem below when comparing
    employeeDTOa.getDepartmentDTORef();
    // XXX: Not needed anymore, it loads lazy on request
    //employeeDAO.loadDepartmentRef(employeeDTOa);
    EmployeeDTO employeeDTOb = (EmployeeDTO) employeeDAO.loadById(2);
    // XXX: To avoid a lazy load like problem below when comparing
    employeeDTOb.getDepartmentDTORef();
    // XXX: Not needed anymore, it loads lazy on request
    //employeeDAO.loadDepartmentRef(employeeDTOb);

    // d1->(e1, e3)
    EmployeeDTO employeeDTOc = (EmployeeDTO) employeeDAO.loadById(3);
    //employeeDTOc.setDepartmentRef(departmentDTOa); // ----------> LINK
    employeeDTOc.setDepartmentDTORef(departmentDTOa); // ----------> LINK
    // If I don't add I can't compare below
    departmentDTOa.getEmployeeDTOList().add(employeeDTOc); // -----> LINK
    employeeDAO.update(employeeDTOc);

    // d2->(e2, e4)
    EmployeeDTO employeeDTOd = (EmployeeDTO) employeeDAO.loadById(4);
    //employeeDTOd.setDepartmentRef(departmentDTOb); // ----------> LINK
    employeeDTOd.setDepartmentDTORef(departmentDTOb); // ----------> LINK
    // If I don't add I can't compare below
    departmentDTOb.getEmployeeDTOList().add(employeeDTOd); // -----> LINK
    employeeDAO.update(employeeDTOd);

    // d3->Empty
    // d4->Empty

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / CHECK Departments
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    DepartmentDTO departmentDTOX = (DepartmentDTO) departmentDAO.loadById(1);
    // XXX: Not needed anymore, it loads lazy on request
    //departmentDAO.loadEmployeeList(departmentDTOX);
    assertTrue(compareDTODeep(departmentDTOa, departmentDTOX));

    DepartmentDTO departmentDTOY = (DepartmentDTO) departmentDAO.loadById(2);
    // XXX: Not needed anymore, it loads lazy on request
    //departmentDAO.loadEmployeeList(departmentDTOY);
    assertTrue(compareDTODeep(departmentDTOb, departmentDTOY));

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / CHECK Employees
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    EmployeeDTO employeeDTOX = (EmployeeDTO) employeeDAO.loadById(1);
    // XXX: Not needed anymore, it loads lazy on request
    //employeeDAO.loadDepartmentRef(employeeDTOX);
    assertTrue(compareDTODeep(employeeDTOa, employeeDTOX));

    EmployeeDTO employeeDTOY = (EmployeeDTO) employeeDAO.loadById(2);
    // XXX: Not needed anymore, it loads lazy on request
    //employeeDAO.loadDepartmentRef(employeeDTOY);
    assertTrue(compareDTODeep(employeeDTOb, employeeDTOY));

    EmployeeDTO employeeDTOZ = (EmployeeDTO) employeeDAO.loadById(3);
    // XXX: Not needed anymore, it loads lazy on request
    //employeeDAO.loadDepartmentRef(employeeDTOZ);
    assertTrue(compareDTODeep(employeeDTOc, employeeDTOZ));

    EmployeeDTO employeeDTOW = (EmployeeDTO) employeeDAO.loadById(4);
    // XXX: Not needed anymore, it loads lazy on request
    //employeeDAO.loadDepartmentRef(employeeDTOW);
    assertTrue(compareDTODeep(employeeDTOd, employeeDTOW));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testRefCrash() throws Exception {
    ConnectionBean connectionBean;

    DepartmentDAO departmentDAO;
    EmployeeDAO employeeDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    // ----------------------------------------
    // INSERT Departments
    // ----------------------------------------

    departmentDAO = (DepartmentDAO) fd.getDAO( //
        DepartmentDAO.class, connectionBean);

    departmentDAO.createTable();

    DepartmentDTO departmentDTO = (DepartmentDTO) fd.getDTO( //
        DepartmentDTO.class, connectionBean);

    departmentDTO.setName("FooName111");
    departmentDTO.setDescription("FooDescription111");
    departmentDAO.insert(departmentDTO);
    assertEquals(departmentDTO.getId(), 1);

    // ----------------------------------------
    // INSERT Employees
    // ----------------------------------------

    employeeDAO = (EmployeeDAO) fd.getDAO( //
        EmployeeDAO.class, connectionBean);

    employeeDAO.createTable();

    // d1 -> e1
    EmployeeDTO employeeDTO = (EmployeeDTO) fd.getDTO( //
        EmployeeDTO.class, connectionBean);

    employeeDTO.setFrstName("FrstName111");
    employeeDTO.setLastName("LastName111");
    employeeDTO.setDepartmentDTORef(departmentDTO); // ----------> LINK
    departmentDTO.getEmployeeDTOList().add(employeeDTO); // -----> LINK
    employeeDAO.insert(employeeDTO);
    assertEquals(employeeDTO.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }
}
