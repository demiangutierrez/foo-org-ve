package relaciones.misc.componente;

import javax.persistence.Embeddable;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jul 2, 2008
 */
@Embeddable
public class Componente {

  private int x;
  private int y;

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
}