package ray2D.simple;

import java.awt.Graphics2D;
import java.util.List;

public interface Geometry {

  public double distanceOf(Ray ray);

  public void draw(Graphics2D g2d);

  public List<CtrlPoint> getCtrlPointList();
}
