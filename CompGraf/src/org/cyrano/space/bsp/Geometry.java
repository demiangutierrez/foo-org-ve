package org.cyrano.space.bsp;

import java.awt.Graphics2D;
import java.util.List;

import org.cyrano.util.draw.CtrlPoint;

public interface Geometry {

  public double distanceOf(Ray ray);

  public void draw(Graphics2D g2d);

  public List<CtrlPoint> getCtrlPointList();
}
