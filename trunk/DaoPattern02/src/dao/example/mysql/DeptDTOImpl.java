package dao.example.mysql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.base.impl.BaseDTO;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;
import dao.example.base.ProfDAO;
import dao.example.base.ProfDTO;

/**
 * @author Demián Gutierrez
 */
// This class should have package visibility,
// but it's used in Test0 directly for educational purposes,
// so we need to make it public.
// It's also a nice example on when and why use package visibility.
public//
class DeptDTOImpl extends BaseDTO implements DeptDTO {

  private String deptAtt1;
  private String deptAtt2;

  // --------------------------------------------------------------------------------

  private List<ProfDTO> profDTOList;

  // --------------------------------------------------------------------------------

  public DeptDTOImpl() {
    super(DeptDAO.class);
  }

  // --------------------------------------------------------------------------------
  // DeptDTO
  // --------------------------------------------------------------------------------

  @Override
  public String getDeptAtt1() {
    return deptAtt1;
  }

  @Override
  public void setDeptAtt1(String deptAtt1) {
    this.deptAtt1 = deptAtt1;
  }

  // --------------------------------------------------------------------------------

  @Override
  public String getDeptAtt2() {
    return deptAtt2;
  }

  @Override
  public void setDeptAtt2(String deptAtt2) {
    this.deptAtt2 = deptAtt2;
  }

  // --------------------------------------------------------------------------------

  @Override
  @SuppressWarnings("unchecked")
  public List<ProfDTO> getProfDTOList() {
    if (profDTOList == null) {
      try {

        // Lazy load the list
        ProfDAO profDAO = (ProfDAO) AbstractFactoryDAO.getFactoryDAO(). //
            getDAO(ProfDAO.class, connectionBean);

        profDTOList = new ArrayList<ProfDTO>();
        profDTOList.addAll((Collection<? extends ProfDTO>) //
            profDAO.listBy(ProfDTOImpl.DEPT_DTO_REF, id));

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    return profDTOList;
  }

  @Override
  public void setProfDTOList(List<ProfDTO> profDTOList) {
    throw new UnsupportedOperationException();
  }
}
