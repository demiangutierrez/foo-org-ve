package dao.example.base;

import dao.base.api.IDTO;

/**
 * @author Demián Gutierrez
 */
public interface WheelDTO extends IDTO {

  public String/**/getColor();

  public void/*  */setColor(String color);

  // --------------------------------------------------------------------------------

  public int/* */getSize();

  public void/**/setSize(int size);
}
