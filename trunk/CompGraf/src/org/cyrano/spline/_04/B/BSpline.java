package org.cyrano.spline._04.B;

import java.util.List;

import org.cyrano.util.draw.CtrlPoint;
import org.cyrano.util.draw.CtrlPoint.Axis;

public class BSpline {

  // --------------------------------------------------------------------------------
  // check:
  // http://mathworld.wolfram.com/B-Spline.html
  // Kelly Dempski - Focus on Curves and Surfaces
  // --------------------------------------------------------------------------------

  // N     -> the number of ctrlpoints
  // k     -> the number of ctrlpoints that affect a subset of the curve
  // k - 1 -> the degree of the curve

  private static double nfunc(int i, int k, double[] tvect, double tparm) {
    if (k == 1) {
      return tvect[i] <= tparm && tparm < tvect[i + 1] ? 1 : 0;
    }

    double a = 0;
    double b = 0;

    //@begnf
    if (Math.abs(tvect[i + k - 1] - tvect[i    ]) > 0.001) {
      a = (tparm            - tvect[i    ]) /
          (tvect[i + k - 1] - tvect[i    ]);
    }
    if (Math.abs(tvect[i + k    ] - tvect[i + 1]) > 0.001) {
      b = (tvect[i + k    ] - tparm       ) /
          (tvect[i + k    ] - tvect[i + 1]);
    }

    double ret = a * nfunc(i,     k - 1, tvect, tparm) +
                 b * nfunc(i + 1, k - 1, tvect, tparm);
    //@endnf

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static double solve( //
      List<CtrlPoint> ctrlPointList, Axis axis, double param) {

    double ret = 0;

    double[] tvect = new double[]{ //
    /**/0, 0, 0, 0, //
        1 / 4f, 1 / 2f, 3 / 4f, //
        1, 1, 1, 1};

    for (int i = 0; i < ctrlPointList.size(); i++) {
      CtrlPoint ctrlPoint = ctrlPointList.get(i);

      double axisVal = axis.getValue(ctrlPoint);

      double nf = nfunc(i, 4, tvect, param);

      ret += axisVal * nf;
    }

    return ret;
  }
}
