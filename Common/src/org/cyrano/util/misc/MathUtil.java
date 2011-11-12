package org.cyrano.util.misc;

public class MathUtil {

  public static boolean betweenO(double a, double x, double b) {
    return a < /* */x && x < /* */b;
  }

  public static boolean betweenC(double a, double x, double b) {
    return a <= /**/x && x <= /**/b;
  }

  public static double mod(double[] val) {
    double ret = 0;

    for (int i = 0; i < val.length; i++) {
      ret += val[i] * val[i];
    }

    return Math.sqrt(ret);
  }
}
