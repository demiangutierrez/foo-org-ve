package dao.example.pgsql;

import dao.base.impl.BaseDAO;
import dao.example.base.DepartmentDAO;
import dao.example.base.DepartmentDTO;

/**
 * @author Demián Gutierrez
 */
class DepartmentDAOImpl extends BaseDAO implements DepartmentDAO {

  public DepartmentDAOImpl() {
    super(DepartmentDTO.class);
  }
}
