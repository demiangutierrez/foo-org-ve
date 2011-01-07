package org.cyrano.boxcollision.test2;

import java.awt.Graphics2D;

public class Asteroid extends BoxImpl {

  public int size = 3;

  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(color);
    g2d.drawRect(minX(), minY(), getW(), getH());
  }
}
