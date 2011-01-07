package dao.example.base;

import dao.base.api.IDTO;

/**
 * @author Demián Gutierrez
 */
public interface TireDTO extends IDTO {

  public abstract int/* */getSpeed();

  public abstract void/**/setSpeed(int speed);

  // --------------------------------------------------------------------------------

  public abstract int/* */getRating();

  public abstract void/**/setRating(int rating);
}
