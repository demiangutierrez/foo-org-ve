package dao.example.base;

/**
 * @author Demián Gutierrez
 */
public interface BookDTO extends PublicationDTO {

  public int/* */getSpeed();

  public void/**/setSpeed(int speed);

  // --------------------------------------------------------------------------------

  public int/* */getRating();

  public void/**/setRating(int rating);
}
