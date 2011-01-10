package dao.example.mysql;

import dao.base.impl.BaseDTO;
import dao.example.base.PublicationDAO;
import dao.example.base.PublicationDTO;

/**
 * @author Demián Gutierrez
 */
public class PublicationDTOImpl extends BaseDTO implements PublicationDTO {

  public static final String MANUFACTURER/**/= "manufacturer";
  public static final String NUMBER/*      */= "number";
  public static final String DESCRIPTION/* */= "description";

  // --------------------------------------------------------------------------------

  private String manufacturer;
  private String number;
  private String description;

  // --------------------------------------------------------------------------------

  public PublicationDTOImpl() {
    super(PublicationDAO.class);
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
