package dao.example.base;

import dao.base.api.IDTO;

/**
 * @author Demián Gutierrez
 */
public interface ProfDTO extends IDTO {

  public static final String PROF_ATT_1 = "profAtt1";
  public static final String PROF_ATT_2 = "profAtt2";

  // --------------------------------------------------------------------------------

  public static final String DEPT_DTO_REF = "deptDTORef";

  // --------------------------------------------------------------------------------

  public String getProfAtt1();

  public void setProfAtt1(String profAtt1);

  // --------------------------------------------------------------------------------

  public String getProfAtt2();

  public void setProfAtt2(String profAtt2);

  // --------------------------------------------------------------------------------

  public DeptDTO getDeptDTORef();

  public void setDeptDTORef(DeptDTO deptDTORef);
}
