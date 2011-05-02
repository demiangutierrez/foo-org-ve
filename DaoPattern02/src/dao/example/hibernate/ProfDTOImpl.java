package dao.example.hibernate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import dao.base.impl.BaseHibeDTO;
import dao.example.base.DeptDTO;
import dao.example.base.ProfDTO;

/**
 * @author Demián Gutierrez
 */
@Entity
@Table(name = "t_prof")
@Proxy(lazy = false)
class ProfDTOImpl extends BaseHibeDTO implements ProfDTO {

  private String profAtt1;
  private String profAtt2;

  // --------------------------------------------------------------------------------

  private DeptDTO deptDTORef;

  // --------------------------------------------------------------------------------

  public ProfDTOImpl() {
    // Empty
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
  @ManyToOne(targetEntity = DeptDTOImpl.class)
  public DeptDTO getDeptDTORef() {
    return deptDTORef;
  }

  @Override
  public void setDeptDTORef(DeptDTO deptDTORef) {
    this.deptDTORef = deptDTORef;
  }
}
