package dao.example.mysql;

import dao.base.impl.BaseDTO;
import dao.base.impl.Reference;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;
import dao.example.base.ProfDAO;
import dao.example.base.ProfDTO;

/**
 * @author Demián Gutierrez
 */
class ProfDTOImpl extends BaseDTO implements ProfDTO {

  private String profAtt1;
  private String profAtt2;

  // --------------------------------------------------------------------------------

  private final Reference<DeptDTO> deptDTORef = //
  new Reference<DeptDTO>();

  // --------------------------------------------------------------------------------

  public ProfDTOImpl() {
    super(ProfDAO.class);
  }

  // --------------------------------------------------------------------------------
  // ProfDTO
  // --------------------------------------------------------------------------------

  @Override
  public String getProfAtt1() {
    return profAtt1;
  }

  @Override
  public void setProfAtt1(String profAtt1) {
    this.profAtt1 = profAtt1;
  }

  // --------------------------------------------------------------------------------

  @Override
  public String getProfAtt2() {
    return profAtt2;
  }

  @Override
  public void setProfAtt2(String profAtt2) {
    this.profAtt2 = profAtt2;
  }

  // --------------------------------------------------------------------------------

  @Override
  public DeptDTO getDeptDTORef() {
    if (deptDTORef.getRefValue() == null) {
      if (deptDTORef.getRefIdent() != 0) {
        try {

          // Lazy load the deptDTORef
          DeptDAO deptDAO = (DeptDAO) AbstractFactoryDAO.getFactoryDAO(). //
              getDAO(DeptDAO.class, connectionBean);

          deptDTORef.setRefValue( //
              (DeptDTO) deptDAO.loadById(deptDTORef.getRefIdent()));

        } catch (Exception e) {
          e.printStackTrace();
          throw new RuntimeException(e);
        }
      }
    }

    return deptDTORef.getRefValue();
  }

  @Override
  public void setDeptDTORef(DeptDTO deptDTORef) {
    this.deptDTORef.setRefValue(deptDTORef);
  }

  // --------------------------------------------------------------------------------

  public Reference<DeptDTO> getInternalDeptDTORef() {
    return deptDTORef;
  }
}
