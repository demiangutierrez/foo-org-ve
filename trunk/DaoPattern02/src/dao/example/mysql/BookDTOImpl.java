package dao.example.mysql;

import dao.example.base.BookDTO;

/**
 * @author Demián Gutierrez
 */
public class BookDTOImpl extends PublicationDTOImpl implements BookDTO {

  public static final String SPEED/* */= "speed";
  public static final String RATING/**/= "rating";

  // --------------------------------------------------------------------------------

  private int speed;
  private int rating;

  // --------------------------------------------------------------------------------

  public BookDTOImpl() {
    //super(BookDAO.class);
  }

  // --------------------------------------------------------------------------------

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  // --------------------------------------------------------------------------------

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }
}
