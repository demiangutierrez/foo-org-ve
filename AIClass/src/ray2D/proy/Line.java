package ray2D.proy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.util.draw.CtrlPoint;

public class Line implements Geometry {

  public List<CtrlPoint> ctrlPointList = //
  new ArrayList<CtrlPoint>();

  public CtrlPoint p1 = new CtrlPoint();
  public CtrlPoint p2 = new CtrlPoint();

  public Color color;

  // --------------------------------------------------------------------------------

  public Line() {
    ctrlPointList.add(p1);
    ctrlPointList.add(p2);
  }

  // --------------------------------------------------------------------------------

  @Override
  public double distanceOf(Ray ray) {
    // x = p0x + s * ux
    // y = p0y + s * uy

    double m = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
    double n = p1.getY() - m * p1.getX();

    double s;

    if ((p2.getX() - p1.getX()) != 0) {
      s = (m * ray.p0.getX() + n - ray.p0.getY()) / (ray.uy() - m * ray.ux());
    } else {
      s = (p1.getX() - ray.p0.getX()) / ray.ux();
    }

    double x = ray.calcX(s);
    double y = ray.calcY(s);

    double minX = Math.min(p1.getX(), p2.getX());
    double maxX = Math.max(p1.getX(), p2.getX());
    double minY = Math.min(p1.getY(), p2.getY());
    double maxY = Math.max(p1.getY(), p2.getY());

    if (minX != maxX && (x < minX || x > maxX)) {
      s = Double.NaN;
    }

    if (minY != maxY && (y < minY || y > maxY)) {
      s = Double.NaN;
    }

    return s;
  }

  // --------------------------------------------------------------------------------

  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(color);

    g2d.drawLine( //
        (int) p1.getX(), (int) p1.getY(), //
        (int) p2.getX(), (int) p2.getY());

    for (CtrlPoint ctrlPoint : ctrlPointList) {
      ctrlPoint.draw(g2d);
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public List<CtrlPoint> getCtrlPointList() {
    return ctrlPointList;
  }
}
