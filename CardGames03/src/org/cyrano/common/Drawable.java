package org.cyrano.common;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 * @author Demián Gutierrez
 */
public interface Drawable {

  public boolean contains(Point point);

  // --------------------------------------------------------------------------------

  public void moveDt(int dx, int dy);

  public void moveTo(int cx, int cy);

  public void draw(Graphics2D g2d);

  // --------------------------------------------------------------------------------

  public Rectangle getRectangle();

  // --------------------------------------------------------------------------------

  public void mouseClicked(MouseEvent evt);
}
