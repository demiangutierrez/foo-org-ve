package dao.example.base;

import java.util.List;

import dao.base.api.IDTO;

/**
 * @author Demián Gutierrez
 */
public interface DeptDTO extends IDTO {

  public static final String DEPT_ATT_1 = "deptAtt1";
  public static final String DEPT_ATT_2 = "deptAtt2";

  // --------------------------------------------------------------------------------

  public static final String PROF_DTO_LIST = "profDTOList";

  // --------------------------------------------------------------------------------

  public String getDeptAtt1();

  public void setDeptAtt1(String deptAtt1);

  // --------------------------------------------------------------------------------

  public String getDeptAtt2();

  public void setDeptAtt2(String deptAtt2);

  // --------------------------------------------------------------------------------

  public List<ProfDTO> getProfDTOList();

  public void setProfDTOList(List<ProfDTO> profDTOList);
}
