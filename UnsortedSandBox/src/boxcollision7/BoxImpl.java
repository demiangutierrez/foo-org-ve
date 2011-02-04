package boxcollision7;

import java.awt.Color;

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
}
