package dao.example.hibernate;

import dao.base.impl.BaseDTO;
import dao.example.base.PublicationDAO;
import dao.example.base.PublicationDTO;

/**
 * @author Demián Gutierrez
 */
public class PublicationDTOImpl extends BaseDTO implements PublicationDTO {

  private String publAtt1;
  private String publAtt2;

  // --------------------------------------------------------------------------------

  public PublicationDTOImpl() {
    // TODO:
    super(PublicationDAO.class);
  }

  // --------------------------------------------------------------------------------

  public String getPublAtt1() {
    return publAtt1;
  }

  public void setPublAtt1(String publAtt1) {
    this.publAtt1 = publAtt1;
  }

  // --------------------------------------------------------------------------------

  public String getPublAtt2() {
    return publAtt2;
  }

  public void setPublAtt2(String publAtt2) {
    this.publAtt2 = publAtt2;
  }
}
