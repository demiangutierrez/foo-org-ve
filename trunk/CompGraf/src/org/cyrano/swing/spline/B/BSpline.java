package org.cyrano.swing.spline.B;

import java.util.List;

import org.cyrano.swing.spline.B.Bezier.Axis;
import org.cyrano.util.draw.CtrlPoint;

public class BSpline {

  public enum Mode {
    OPN, CLS
  }

  // --------------------------------------------------------------------------------

  public enum Axis {
    X, Y;

    public double getValue(CtrlPoint ctrlPoint) {
      switch (this) {
        case X :
          return ctrlPoint.getX();
        case Y :
          return ctrlPoint.getY();

        default :
          throw new IllegalStateException();
      }
    }
  }

  // --------------------------------------------------------------------------------

  public static double getTVect(double[] tvect, int i) {
    if (i < 0) {
      return tvect[0];
    }

    if (i >= tvect.length) {
      return tvect[tvect.length - 1];
    }

    return tvect[i];
  }

  public static double nfunc(int i, int j, double[] tvect, double tparm) {
    if (j <= 0) {
      //return tvect[i] <= tparm && tparm < tvect[i + 1] ? 1 : 0;
      return getTVect(tvect, i) <= tparm && tparm < getTVect(tvect, i + 1) ? 1 : 0;
    }

    //    double a = (tparm            - tvect[i    ]) /
    //        (tvect[i     + 1] - tvect[i    ]);
    //double b = (tvect[i + j + 1] - tparm       ) /
    //        (tvect[i + j + 1] - tvect[i + 1]);
    double a = (tparm - tvect[i]) / (getTVect(tvect, i + 1) - getTVect(tvect, i));
    double b = (getTVect(tvect, i + j + 1) - tparm) / (getTVect(tvect, i + j + 1) - getTVect(tvect, i + 1));

//@begnf
    return a * nfunc(i,     j - 1, tvect, tparm) +
           b * nfunc(i + 1, j - 1, tvect, tparm);
    //@endnf
  }

  public static double solve( //
      List<CtrlPoint> ctrlPointList, Axis axis, double param) {

    double ret = 0;

    double[] tvect = new double[9];

//    tvect[0] = 0.5;

    for (int i = 0; i < tvect.length; i++) {
      tvect[i] = i * 1.0 / (tvect.length - 1);
    }

    for (int i = 0; i < ctrlPointList.size(); i++) {
      CtrlPoint ctrlPoint = ctrlPointList.get(i);
      double axisVal = axis.getValue(ctrlPoint);

      int m = tvect.length - 1 - ctrlPointList.size() - 1 - 1;

      ret += axisVal * nfunc(i, m, tvect, param);
    }

    return ret;
  }
}
