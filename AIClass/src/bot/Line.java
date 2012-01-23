package bot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

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

    double m = (p2.y - p1.y) / (p2.x - p1.x);
    double n = p1.y - m * p1.x;

    double s;

    if ((p2.x - p1.x) != 0) {
      s = (m * ray.p0.x + n - ray.p0.y) / (ray.uy() - m * ray.ux());
    } else {
      s = (p1.x - ray.p0.x) / ray.ux();
    }

    double x = ray.calcX(s);
    double y = ray.calcY(s);

    double minX = Math.min(p1.x, p2.x);
    double maxX = Math.max(p1.x, p2.x);
    double minY = Math.min(p1.y, p2.y);
    double maxY = Math.max(p1.y, p2.y);

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
        (int) p1.x, (int) p1.y, //
        (int) p2.x, (int) p2.y);

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
