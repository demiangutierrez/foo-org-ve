package org.cyrano.index;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface Boundable {

  public void paint(Graphics2D g2d);

  public Rectangle getBounds();

  public boolean isStatic();
}
