package org.cyrano.boxcollision.test4;

import java.awt.Color;
import java.util.List;

import org.cyrano.boxcollision.base.Box;

public abstract class BoxImpl implements Box {

  public boolean/**/mv;

  public double/* */cx;
  public double/* */cy;

  public double/* */bw;
  public double/* */bh;

  public double/* */vx;
  public double/* */vy;

  public double/* */ax;
  public double/* */ay;

  public double/* */sx;
  public double/* */sy;

  public Color color;

  public String id;

  // --------------------------------------------------------------------------------

  public BoxImpl() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void calcV() {
    vx = sx - cx;
    vy = sy - cy;
  }

  // --------------------------------------------------------------------------------

  public void updatePos(double dt, List<BoxImpl> drawBoxList) {
    if (!mv) {
      return;
    }

    cx = ((cx + vx * dt) + 700) % 700;
    cy = ((cy + vy * dt) + 500) % 500;
  }

  // --------------------------------------------------------------------------------

  @Override
  public int getW() {
    return (int) bw;
  }

  @Override
  public int getH() {
    return (int) bh;
  }

  @Override
  public int minX() {
    return (int) cx;
  }

  @Override
  public int minY() {
    return (int) cy;
  }

  // --------------------------------------------------------------------------------

  @Override
  public int midX() {
    return (int) (cx + bw / 2);
  }

  @Override
  public int midY() {
    return (int) (cy + bh / 2);
  }

  // --------------------------------------------------------------------------------

  @Override
  public int maxX() {
    return (int) (cx + bw);
  }

  @Override
  public int maxY() {
    return (int) (cy + bh);
  }

  // --------------------------------------------------------------------------------

  @Override
  public int velX() {
    return (int) vx;
  }

  @Override
  public int velY() {
    return (int) vy;
  }

  @Override
  public String toString() {
    return minX() + ";" + minY() + ";" + maxX() + ";" + maxY() + ";" + vx + ";" + vy;
  }

  @Override
  public boolean collides(Box box) {
    return true;
  }
}
