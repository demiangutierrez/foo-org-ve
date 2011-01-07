package org.cyrano.boxcollision.test3;

import java.awt.Graphics2D;

import org.cyrano.boxcollision.base.Box;

public class Bullet extends BoxImpl {

  @Override
  public boolean collides(Box box) {
    return true;
  }

  public void draw(Graphics2D g2d) {
    g2d.setColor(color);
    g2d.fillOval(minX(), minY(), getW(), getH());
  }
}
