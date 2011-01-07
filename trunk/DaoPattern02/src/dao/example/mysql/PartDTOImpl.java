package dao.example.mysql;

import dao.base.impl.BaseDTO;
import dao.example.base.PartDAO;
import dao.example.base.PartDTO;

/**
 * @author Demián Gutierrez
 */
public class PartDTOImpl extends BaseDTO implements PartDTO {

  public static final String MANUFACTURER/**/= "manufacturer";
  public static final String NUMBER/*      */= "number";
  public static final String DESCRIPTION/* */= "description";

  // --------------------------------------------------------------------------------

  private String manufacturer;
  private String number;
  private String description;

  // --------------------------------------------------------------------------------

  public PartDTOImpl() {
    super(PartDAO.class);
  }

  // --------------------------------------------------------------------------------

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  // --------------------------------------------------------------------------------

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  // --------------------------------------------------------------------------------

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
