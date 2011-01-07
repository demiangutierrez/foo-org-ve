package dao.example.mysql;

import dao.example.base.TireDTO;

public class TireDTOImpl extends PartDTOImpl /*BaseDTO*/implements TireDTO {

  public static final String SPEED/* */= "speed";
  public static final String RATING/**/= "rating";

  // --------------------------------------------------------------------------------

  private int speed;
  private int rating;

  // --------------------------------------------------------------------------------

  public TireDTOImpl() {
    //super(TireDAO.class);
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
