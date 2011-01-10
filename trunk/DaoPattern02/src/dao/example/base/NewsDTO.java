package dao.example.base;

/**
 * @author Demián Gutierrez
 */
public interface NewsDTO extends PublicationDTO {

  public int getType();

  public void setType(int type);

  // --------------------------------------------------------------------------------

  public int/* */getSize();

  public void/**/setSize(int size);
}
