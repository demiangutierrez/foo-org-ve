package org.cyrano.swing.spline.cubic;

import java.util.List;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.DecompositionSolver;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealVector;
import org.cyrano.util.draw.CtrlPoint;

public class Spline {

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

  public static void solve(List<CtrlPoint> ctrlPointList, double[][] spline, Mode mode, Axis axis) {

    // ---------------------------------------------
    // http://mathworld.wolfram.com/CubicSpline.html
    // ---------------------------------------------
    // n     seg (0 .. n)
    // n + 1 pts
    // ---------------------------------------------
    // prepare inputs
    // ---------------------------------------------

    int n = ctrlPointList.size() - 1;

    // ----------------------------------------

    double[][] m = new double[n + 1][n + 1];

    for (int j = 1; j <= n - 1; j++) {
      m[j][j - 1] = 1;
      m[j][j + 0] = 4;
      m[j][j + 1] = 1;
    }

    //@begnf
    switch (mode) {
      case OPN :
        m[0][  0  ] = 2;
        m[0][  1  ] = 1;
        m[n][n - 1] = 1;
        m[n][n + 0] = 2;
        break;
      case CLS :
        m[0][  0  ] = 4;
        m[0][  1  ] = 1;
        m[0][  n  ] = 1;
        m[n][n - 1] = 1;
        m[n][n + 0] = 4;
        m[n][0 + 0] = 1;
        break;
    }
    //@endnf

    // ----------------------------------------

    double[] ca = new double[n + 1];
    double[] cb = new double[n + 1];

    for (int i = 0; i <= (n - 1); i++) {
      CtrlPoint a = ctrlPointList.get(i + 1); // 1 .. n
      CtrlPoint b = ctrlPointList.get(i + 0); // 0 .. n - 1

      ca[i + 0] = axis.getValue(a);
      cb[i + 1] = axis.getValue(b);
    }

    switch (mode) {
      case OPN :
        ca[n] = axis.getValue(ctrlPointList.get(n));
        cb[0] = axis.getValue(ctrlPointList.get(0));
        break;
      case CLS :
        ca[n] = axis.getValue(ctrlPointList.get(0));
        cb[0] = axis.getValue(ctrlPointList.get(n));
        break;
    }

    double[] ct = new double[n + 1];

    for (int i = 0; i <= n; i++) {
      ct[i] = 3 * (ca[i] - cb[i]);
    }

    // ----------------------------------------
    // solve the system
    // ----------------------------------------

    DecompositionSolver solver = new LUDecompositionImpl( //
        new Array2DRowRealMatrix(m, false)).getSolver();

    RealVector solv = solver.solve(new ArrayRealVector(ct, false));

    // ----------------------------------------
    // set the coefficients
    // ----------------------------------------

    for (int j = 0; j <= n; j++) {
      spline[j][0] = axis.getValue(ctrlPointList.get(j)); // --> a
      spline[j][1] = solv.getEntry(j); // ---------------------> b
    }

    for (int j = 0; j <= (n); j++) {
      CtrlPoint Cjplus1 = ctrlPointList.get((j + 1) % (n + 1));
      CtrlPoint Cjplus0 = ctrlPointList.get((j + 0));

      double Yjplus1 = axis.getValue(Cjplus1);
      double Yjplus0 = axis.getValue(Cjplus0);

      double Djplus1 = solv.getEntry((j + 1) % (n + 1));
      double Djplus0 = solv.getEntry((j + 0));

      spline[j][2] = 3 * (Yjplus1 - Yjplus0) - 2 * Djplus0 - Djplus1; // c
      spline[j][3] = 2 * (Yjplus0 - Yjplus1) + 1 * Djplus0 + Djplus1; // d
    }
  }
}
