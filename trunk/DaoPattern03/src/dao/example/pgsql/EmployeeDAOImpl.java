package dao.example.pgsql;

import dao.base.impl.BaseDAO;
import dao.example.base.EmployeeDAO;
import dao.example.base.EmployeeDTO;

/**
 * @author Demián Gutierrez
 */
class EmployeeDAOImpl extends BaseDAO implements EmployeeDAO {

  public EmployeeDAOImpl() {
    super(EmployeeDTO.class);
  }
}
