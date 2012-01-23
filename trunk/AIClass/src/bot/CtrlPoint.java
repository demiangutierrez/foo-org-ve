package bot;

import java.awt.Color;
import java.awt.Graphics2D;

public class CtrlPoint {

  public static final int SIDE_SIZE = 10;

  // --------------------------------------------------------------------------------

  public enum Type {
    RECT, OVAL, STAR
  }

  // --------------------------------------------------------------------------------

  public boolean fixed;

  public Color color;

  public Type type = Type.RECT;

  public double x;
  public double y;

  // --------------------------------------------------------------------------------

  public boolean contains(double x, double y) {
    return //
    /**/x >= this.x - SIDE_SIZE / 2 && x <= this.x + SIDE_SIZE / 2 && //
        y >= this.y - SIDE_SIZE / 2 && y <= this.y + SIDE_SIZE / 2;
  }

  // --------------------------------------------------------------------------------

  public void draw(Graphics2D g2d) {

    if (color != null) {
      g2d.setColor(color);
    }

    switch (type) {
      case RECT :
        g2d.drawRect( //
            (int) (x - SIDE_SIZE / 2), //
            (int) (y - SIDE_SIZE / 2), //
            SIDE_SIZE, SIDE_SIZE);
        break;
      case OVAL :
        g2d.drawOval( //
            (int) (x - SIDE_SIZE / 2), //
            (int) (y - SIDE_SIZE / 2), //
            SIDE_SIZE, SIDE_SIZE);
        break;
      case STAR :
        g2d.drawLine(//
            (int) (x - CtrlPoint.SIDE_SIZE / 2), //
            (int) (y - CtrlPoint.SIDE_SIZE / 2), //
            (int) (x + CtrlPoint.SIDE_SIZE / 2), //
            (int) (y + CtrlPoint.SIDE_SIZE / 2));
        g2d.drawLine(//
            (int) (x - CtrlPoint.SIDE_SIZE / 2), //
            (int) (y + CtrlPoint.SIDE_SIZE / 2), //
            (int) (x + CtrlPoint.SIDE_SIZE / 2), //
            (int) (y - CtrlPoint.SIDE_SIZE / 2));

        g2d.drawLine(//
            (int) (x - CtrlPoint.SIDE_SIZE / 2), (int) y, //
            (int) (x + CtrlPoint.SIDE_SIZE / 2), (int) y);
        g2d.drawLine(//
            (int) x, (int) (y + CtrlPoint.SIDE_SIZE / 2), //
            (int) x, (int) (y - CtrlPoint.SIDE_SIZE / 2));
        break;
      default :
        throw new IllegalArgumentException(type.toString());
    }
  }
}
