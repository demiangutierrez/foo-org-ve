package org.cyrano.boxcollision.base;

import java.awt.Graphics2D;

public interface Box {

  public int getW();

  public int getH();

  // ----------------------------------------

  public int minX();

  public int minY();

  // ----------------------------------------

  public int midX();

  public int midY();

  // ----------------------------------------

  public int maxX();

  public int maxY();

  // ----------------------------------------

  public int velX();

  public int velY();

  // ----------------------------------------

  public boolean collides(Box box);

  public void draw(Graphics2D g2d);

}
