package dao.example.base;

import dao.base.api.IDTO;

/**
 * @author Demián Gutierrez
 */
public interface PublicationDTO extends IDTO {

  public static final String PUBL_ATT_1 = "publAtt1";
  public static final String PUBL_ATT_2 = "publAtt2";

  // --------------------------------------------------------------------------------

  public String getPublAtt1();

  public void setPublAtt1(String publAtt1);

  // --------------------------------------------------------------------------------

  public String getPublAtt2();

  public void setPublAtt2(String publAtt2);
}
