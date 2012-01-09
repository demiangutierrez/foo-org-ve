package ray2D.simple;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ray implements Geometry {

  public List<CtrlPoint> ctrlPointList = //
  new ArrayList<CtrlPoint>();

  public CtrlPoint p0 = new CtrlPoint();
  public CtrlPoint pu = new CtrlPoint();

  public Color color;

  private List<TraceBean> traceBeanList = //
  new ArrayList<TraceBean>();

  // --------------------------------------------------------------------------------

  public Ray() {
    ctrlPointList.add(p0);
    ctrlPointList.add(pu);
  }

  // --------------------------------------------------------------------------------

  public double calcX(double s) {
    return p0.x + ux() * s;
  }

  public double calcY(double s) {
    return p0.y + uy() * s;
  }

  // --------------------------------------------------------------------------------

  public double ux() {
    double ux = pu.x - p0.x;
    double uy = pu.y - p0.y;

    return ux / Math.sqrt(ux * ux + uy * uy);
  }

  // --------------------------------------------------------------------------------

  public double uy() {
    double ux = pu.x - p0.x;
    double uy = pu.y - p0.y;

    return uy / Math.sqrt(ux * ux + uy * uy);
  }

  // --------------------------------------------------------------------------------

  @Override
  public double distanceOf(Ray ray) {
    throw new UnsupportedOperationException();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void draw(Graphics2D g2d) {

    // ----------------------------------------

    g2d.setColor(color);

    g2d.drawLine( //
        (int) p0.x, (int) p0.y, //
        (int) pu.x, (int) pu.y);

    for (CtrlPoint ctrlPoint : ctrlPointList) {
      ctrlPoint.draw(g2d);
    }

    // ----------------------------------------

    TraceBean prevTraceBean = null;

    for (TraceBean currTraceBean : traceBeanList) {
      drawPrevCurrLine(g2d, prevTraceBean, currTraceBean);
      drawPrevCurrMark(g2d, prevTraceBean, currTraceBean);

      prevTraceBean = currTraceBean;
    }
  }

  // --------------------------------------------------------------------------------

  private void drawPrevCurrLine(Graphics2D g2d, //
      TraceBean prevTraceBean, TraceBean currTraceBean) {

    double curX = calcX(currTraceBean.dist);
    double curY = calcY(currTraceBean.dist);

    Stroke prev = g2d.getStroke();

    Stroke dash = new BasicStroke(1.0f, //
        BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, //
        1.0f, new float[]{1.0f, 3.0f}, 0.0f);

    g2d.setStroke(dash);

    if (prevTraceBean != null) {
      double preX = calcX(prevTraceBean.dist);
      double preY = calcY(prevTraceBean.dist);

      g2d.setColor(Color.CYAN);
      g2d.drawLine( //
          (int) preX, (int) preY, //
          (int) curX, (int) curY);
    } else {
      g2d.setColor(Color.YELLOW);
      g2d.drawLine( //
          (int) pu.x, (int) pu.y, //
          (int) curX, (int) curY);
    }

    g2d.setStroke(prev);
  }

  // --------------------------------------------------------------------------------

  private void drawPrevCurrMark(Graphics2D g2d, //
      TraceBean prevTraceBean, TraceBean currTraceBean) {

    double x = calcX(currTraceBean.dist);
    double y = calcY(currTraceBean.dist);

    if (prevTraceBean == null) {
      g2d.setColor(Color.YELLOW);

      g2d.drawLine(//
          (int) (x - CtrlPoint.SIDE_SIZE / 2), //
          (int) (y - CtrlPoint.SIDE_SIZE / 2), //
          (int) (x + CtrlPoint.SIDE_SIZE / 2), //
          (int) (y + CtrlPoint.SIDE_SIZE / 2));
      g2d.drawLine(//
          (int) (x - CtrlPoint.SIDE_SIZE / 2), //
          (int) (y + CtrlPoint.SIDE_SIZE / 2), //
          (int) (x + CtrlPoint.SIDE_SIZE / 2), //
          (int) (y - CtrlPoint.SIDE_SIZE / 2));

      g2d.drawLine(//
          (int) (x - CtrlPoint.SIDE_SIZE / 2), (int) y, //
          (int) (x + CtrlPoint.SIDE_SIZE / 2), (int) y);
      g2d.drawLine(//
          (int) x, (int) (y + CtrlPoint.SIDE_SIZE / 2), //
          (int) x, (int) (y - CtrlPoint.SIDE_SIZE / 2));
    } else {
      g2d.setColor(Color.CYAN);

      g2d.drawOval( //
          (int) (x - CtrlPoint.SIDE_SIZE / 4), //
          (int) (y - CtrlPoint.SIDE_SIZE / 4), //
          (int) CtrlPoint.SIDE_SIZE / 2, //
          (int) CtrlPoint.SIDE_SIZE / 2);
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public List<CtrlPoint> getCtrlPointList() {
    return ctrlPointList;
  }

  // --------------------------------------------------------------------------------

  public void trace(List<Line> lineList) {
    traceBeanList.clear();

    for (Line line : lineList) {
      double dist = line.distanceOf(this);

      if (dist < 0 || Double.isNaN(dist)) {
        continue;
      }

      TraceBean traceBean = new TraceBean();
      traceBean.geom = line;
      traceBean.dist = dist;
      traceBeanList.add(traceBean);
    }

    Collections.sort(traceBeanList, new Comparator<TraceBean>() {
      public int compare(TraceBean o1, TraceBean o2) {
        return o1.dist < o2.dist ? -1 : 1;
      };
    });
  }
}
