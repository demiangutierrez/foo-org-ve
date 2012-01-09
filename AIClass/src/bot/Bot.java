package bot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Bot implements Geometry {

  public static final int DIAMETER = 40;

  public List<CtrlPoint> ctrlPointList = //
  new ArrayList<CtrlPoint>();

  public CtrlPoint p1 = new CtrlPoint();

  public List<Ray> rayList = new ArrayList<Ray>();

  // --------------------------------------------------------------------------------

  public Bot() {
    ctrlPointList.add(p1);
  }

  public void createRays() {
    for (int i = 0; i < 360; i += 30) {
      Ray ray = new Ray();
      ray.p0.x = p1.x;
      ray.p0.y = p1.y;
      ray.pu.x = p1.x + Math.cos(i * 2 * Math.PI / 360);
      ray.pu.y = p1.y + Math.sin(i * 2 * Math.PI / 360);
      ray.color = Color.GREEN;
      rayList.add(ray);
    }
  }

  public void updateRays() {
    for (Ray ray : rayList) {
      ray.pu.x = ray.pu.x + (p1.x - ray.p0.x);
      ray.pu.y = ray.pu.y + (p1.y - ray.p0.y);

      ray.p0.x = p1.x;
      ray.p0.y = p1.y;
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public double distanceOf(Ray ray) {
    throw new UnsupportedOperationException();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(Color.YELLOW);

    g2d.drawOval( //
        (int) (p1.x - DIAMETER / 2), //
        (int) (p1.y - DIAMETER / 2), //
        DIAMETER, //
        DIAMETER);

    for (Ray ray : rayList) {
      ray.draw(g2d);
    }

    for (CtrlPoint ctrlPoint : ctrlPointList) {
      ctrlPoint.draw(g2d);
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public List<CtrlPoint> getCtrlPointList() {
    return ctrlPointList;
  }

  public void trace(List<Line> lineList) {
    for (Ray ray : rayList) {
      ray.trace(lineList);
    }
  }
}
