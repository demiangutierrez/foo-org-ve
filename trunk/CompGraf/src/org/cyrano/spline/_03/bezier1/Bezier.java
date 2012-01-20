package org.cyrano.spline._03.bezier1;

import java.util.List;

import org.cyrano.util.draw.CtrlPoint;
import org.cyrano.util.draw.CtrlPoint.Axis;

public class Bezier {

  // --------------------------------------------------------------------------------
  // check:
  // http://en.wikipedia.org/wiki/B%C3%A9zier_curve
  // http://mathworld.wolfram.com/BezierCurve.html
  // --------------------------------------------------------------------------------

  public static double solve(List<CtrlPoint> ctrlPointList, Axis axis, double param) {
    if (ctrlPointList.size() != 4) {
      throw new IllegalStateException( //
          Integer.toString(ctrlPointList.size()));
    }

    CtrlPoint p0 = ctrlPointList.get(0);
    CtrlPoint p1 = ctrlPointList.get(1);
    CtrlPoint p2 = ctrlPointList.get(2);
    CtrlPoint p3 = ctrlPointList.get(3);

    double ts = /**/param;
    double ti = 1 - param;

    double a = 1 * ti * ti * ti * axis.getValue(p0);
    double b = 3 * ti * ti * ts * axis.getValue(p1);
    double c = 3 * ti * ts * ts * axis.getValue(p2);
    double d = 1 * ts * ts * ts * axis.getValue(p3);

    return a + b + c + d;
  }
}
