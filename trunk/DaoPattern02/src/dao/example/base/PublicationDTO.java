package dao.example.base;

import dao.base.api.IDTO;

/**
 * @author Demián Gutierrez
 */
public interface PublicationDTO extends IDTO {

  public abstract String/**/getNumber();

  public abstract void/*  */setNumber(String number);

  // --------------------------------------------------------------------------------

  public abstract String/**/getManufacturer();

  public abstract void/*  */setManufacturer(String manufacturer);

  // --------------------------------------------------------------------------------

  public abstract String/**/getDescription();

  public abstract void/*  */setDescription(String description);
}
