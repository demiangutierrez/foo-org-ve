package dao.example.mysql;

import dao.example.base.NewsDTO;

/**
 * @author Demián Gutierrez
 */
public class NewsDTOImpl extends PublicationDTOImpl implements NewsDTO {

  public static final String TYPE = "type";
  public static final String SIZE = "size";

  // --------------------------------------------------------------------------------

  private int type;

  private int size;

  // --------------------------------------------------------------------------------

  public NewsDTOImpl() {
    //super(BookDAO.class);
  }

  // --------------------------------------------------------------------------------

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  // --------------------------------------------------------------------------------

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
