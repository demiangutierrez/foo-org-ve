package org.cyrano.common;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public interface Drawable {

  public boolean contains(Point point);

  // --------------------------------------------------------------------------------

  public void moveDt(int dx, int dy);

  public void moveTo(int cx, int cy);

  public void draw(Graphics2D g2d);

  // --------------------------------------------------------------------------------

  public Rectangle getRectangle();
}
