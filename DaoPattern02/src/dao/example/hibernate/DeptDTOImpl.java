package dao.example.hibernate;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import dao.base.impl.BaseHibeDTO;
import dao.example.base.DeptDTO;
import dao.example.base.ProfDTO;

/**
 * @author Demián Gutierrez
 */
@Entity
@Table(name = "t_dept")
@Proxy(lazy = false)
class DeptDTOImpl extends BaseHibeDTO implements DeptDTO {

  private String deptAtt1;
  private String deptAtt2;

  // --------------------------------------------------------------------------------

  private List<ProfDTO> profDTOList;

  // --------------------------------------------------------------------------------

  public DeptDTOImpl() {
    // Empty
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
  @OneToMany(mappedBy = "deptDTORef", targetEntity = ProfDTOImpl.class)
  @LazyCollection(LazyCollectionOption.TRUE)
  @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
  public List<ProfDTO> getProfDTOList() {
    return profDTOList;
  }

  @Override
  public void setProfDTOList(List<ProfDTO> profDTOList) {
    this.profDTOList = profDTOList;
  }
}
