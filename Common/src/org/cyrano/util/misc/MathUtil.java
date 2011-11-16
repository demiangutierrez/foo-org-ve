package org.cyrano.util.misc;

public class MathUtil {

  public static boolean betweenO(double a, double x, double b) {
    return a < /* */x && x < /* */b;
  }

  // --------------------------------------------------------------------------------

  public static boolean betweenC(double a, double x, double b) {
    return a <= /**/x && x <= /**/b;
  }

  // --------------------------------------------------------------------------------

  public static double mod(double[] v) {
    return Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
  }

  // --------------------------------------------------------------------------------

  public static double[] nor(double[] v) {
    double[] r = {0, 0, 0};

    double m = mod(v);

    r[0] = v[0] / m;
    r[1] = v[1] / m;
    r[2] = v[2] / m;

    return r;
  }

  // --------------------------------------------------------------------------------

  public static double[] cross(double[] a, double[] b) {
    double[] r = {0, 0, 0};

    r[0] = a[1] * b[2] - a[2] * b[1];
    r[1] = a[2] * b[0] - a[0] * b[2];
    r[2] = a[0] * b[1] - a[1] * b[0];

    return r;
  }
}
