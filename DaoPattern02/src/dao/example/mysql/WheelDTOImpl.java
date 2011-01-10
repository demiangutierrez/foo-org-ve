package dao.example.mysql;

import dao.example.base.WheelDTO;

/**
 * @author Demián Gutierrez
 */
public class WheelDTOImpl extends PartDTOImpl implements WheelDTO {

  public static final String COLOR/**/= "color";
  public static final String SIZE/* */= "size";

  // --------------------------------------------------------------------------------

  private String color;

  private int size;

  // --------------------------------------------------------------------------------

  public WheelDTOImpl() {
    //super(TireDAO.class);
  }

  // --------------------------------------------------------------------------------

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  // --------------------------------------------------------------------------------

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
