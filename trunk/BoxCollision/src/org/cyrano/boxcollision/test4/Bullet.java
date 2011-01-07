package org.cyrano.boxcollision.test4;

import java.awt.Graphics2D;
import java.util.List;

import org.cyrano.boxcollision.base.Box;

public class Bullet extends BoxImpl {

  private double time = 5;

  @Override
  public boolean collides(Box box) {
    return true;
  }

  @Override
  public void updatePos(double dt, List<BoxImpl> drawBoxList) {

    time -= dt;

    if (time <= 0) {
      drawBoxList.remove(this);
    } else {
      super.updatePos(dt, drawBoxList);
    }
  }

  public void draw(Graphics2D g2d) {
    g2d.setColor(color);
    g2d.fillOval(minX(), minY(), getW(), getH());
  }
}
