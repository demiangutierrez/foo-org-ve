package org.cyrano.boxcollision.test1;

import java.awt.Color;
import java.awt.Graphics2D;

import org.cyrano.boxcollision.base.Box;

/**
 * @author Demián Gutierrez
 */
public class BoxImpl implements Box {

  public boolean/**/mv;

  public int/* */cx;
  public int/* */cy;

  public int/* */bw;
  public int/* */bh;

  public int/* */vx;
  public int/* */vy;

  //  public double/* */ax;
  //  public double/* */ay;

  public int/* */sx;
  public int/* */sy;

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

  public void updatePos(double dt) {
    if (!mv) {
      return;
    }

    cx = cx + (int) (vx * dt);
    cy = cy + (int) (vy * dt);
  }

  // --------------------------------------------------------------------------------

  @Override
  public int getW() {
    return bw;
  }

  @Override
  public int getH() {
    return bh;
  }

  @Override
  public int minX() {
    return cx;
  }

  @Override
  public int minY() {
    return cy;
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
    return cx + bw;
  }

  @Override
  public int maxY() {
    return cy + bh;
  }

  // --------------------------------------------------------------------------------

  @Override
  public int velX() {
    return vx;
  }

  @Override
  public int velY() {
    return vy;
  }

  @Override
  public boolean collides(Box box) {
    return true;
  }

  @Override
  public String toString() {
    StringBuffer ret = new StringBuffer();

    ret.append("->mv: ");
    ret.append(mv);

    ret.append(", cx: ");
    ret.append(cx);

    ret.append(", cy: ");
    ret.append(cy);

    ret.append(", bw: ");
    ret.append(bw);

    ret.append(", bh: ");
    ret.append(bh);

    ret.append(", vx: ");
    ret.append(vx);

    ret.append(", vy: ");
    ret.append(vy);

    ret.append(", sx: ");
    ret.append(sx);

    ret.append(", sy: ");
    ret.append(sy);

    return ret.toString();
  }

  @Override
  public void draw(Graphics2D g2d) {
  }
}
