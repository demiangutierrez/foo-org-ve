package org.cyrano.swing.spline.B;

import java.util.List;

import org.cyrano.util.draw.CtrlPoint;
import org.cyrano.util.draw.CtrlPoint.Axis;

public class BSpline {

  public enum Mode {
    OPN, CLS
  }

  // --------------------------------------------------------------------------------

  // N     -> the number of ctrlpoints
  // k     -> the number of ctrlpoints that affect a subset of the curve
  // k - 1 -> the degree of the curve

  //  private static double nfunc(int i, int j, double[] tvect, double tparm) {
  //    if (j == 0) {
  //      return tvect[i] <= tparm && tparm < tvect[i + 1] ? 1 : 0;
  //    }
  //
  //    double a = (tparm            - tvect[i    ]) /
  //               (tvect[i + 1]     - tvect[i    ]);
  //    double b = (tvect[i + j + 1] - tparm       ) /
  //               (tvect[i + j + 1] - tvect[i + 1]);
  //
  //    return a * nfunc(i,     j - 1, tvect, tparm) +
  //           b * nfunc(i + 1, j - 1, tvect, tparm);
  //  }

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

    //    if (Double.isNaN(a)) {
    //      System.err.println("a is nan, i = " + i + ", k = " + k);
    //    }
    //
    //    if (Double.isInfinite(a)) {
    //      System.err.println("a is infinite, i = " + i + ", k = " + k);
    //    }
    //
    //    if (Double.isNaN(b)) {
    //      System.err.println("b is nan, i = " + i + ", k = " + k);
    //    }
    //
    //    if (Double.isInfinite(b)) {
    //      System.err.println("b is infinite, i = " + i + ", k = " + k);
    //    }
    //
    //    if (Double.isNaN(ret)) {
    //      System.err.println("ret is nan, i = " + i + ", k = " + k);
    //    }

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

    double ret2 = 0;

    for (int i = 0; i < ctrlPointList.size(); i++) {
      CtrlPoint ctrlPoint = ctrlPointList.get(i);

      double axisVal = axis.getValue(ctrlPoint);

      double nf = nfunc(i, 4, tvect, param);

      ret2 += nf;

      ret += axisVal * nf;
    }

    System.err.println("ret2: shoude be 1: " + ret2);

    return ret;
  }
}
