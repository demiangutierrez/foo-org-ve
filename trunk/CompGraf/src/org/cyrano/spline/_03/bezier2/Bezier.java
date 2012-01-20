package org.cyrano.spline._03.bezier2;

import java.util.List;

import org.cyrano.util.draw.CtrlPoint;
import org.cyrano.util.draw.CtrlPoint.Axis;

public class Bezier {

  // --------------------------------------------------------------------------------
  // check:
  // http://en.wikipedia.org/wiki/B%C3%A9zier_curve
  // http://mathworld.wolfram.com/BezierCurve.html
  // --------------------------------------------------------------------------------

  private static long fct(int n) {
    if (n == 0 || n == 1) {
      return 1;
    }

    long ret = 1;

    for (int i = n; i > 1; i--) {
      ret *= i;
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  private static long nCk(int n, int k) {
    return fct(n) / (fct(n - k) * fct(k));
  }

  // --------------------------------------------------------------------------------

  public static double solve( //
      List<CtrlPoint> ctrlPointList, Axis axis, double param) {

    double ret = 0;

    int n = ctrlPointList.size() - 1;

    for (int i = 0; i < ctrlPointList.size(); i++) {
      CtrlPoint ctrlPoint = ctrlPointList.get(i);

      double axisVal = axis.getValue(ctrlPoint);

      int tspow = /**/i;
      int tipow = n - i;

      double ts = /**/param;
      double ti = 1 - param;

      double bf = nCk(n, i) * Math.pow(ti, tipow) * Math.pow(ts, tspow);

      ret += axisVal * bf;
    }

    return ret;
  }
}
