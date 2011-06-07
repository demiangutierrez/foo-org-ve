package org.cyrano.jogl.morton;

import org.cyrano.util.StringUtil;

public class IEEE754Util {

  // --------------------------------------------------------------------------------

  public static long exponent(double d) {
    long l = Double.doubleToRawLongBits(d);

    long r = l & 0x7ff0000000000000L;

    r = (r >> 52) - 1023;

    return r;
  }

  public static long mantissa(double d) {
    return (Double.doubleToRawLongBits(d) & 0x000fffffffffffffL) | 0x0010000000000000L;
  }

  public static String doubleToBitString(double d) {
    return StringUtil.fillto( //
        '0', 64, Long.toBinaryString(Double.doubleToLongBits(d)));
  }

  public static void printDouble(double d) {
    System.err.println(doubleToBitString(d) + ";" + d + "; e=" + exponent(d) + "; m=" + mantissa(d));
  }

  public static void main(String[] args) {
    double d1 = 2.34E+10;
    double d2 = 2.34E-10;
    double d3 = 1.0;
    double d4 = Double.MIN_VALUE;
    double d5 = Double.MAX_VALUE;
    double d6 = 1024;
    double d7 = 1025;
    double d8 = 0.5;

    System.err.println(StringUtil.fillto( //
        '0', 64, Long.toBinaryString(0x7ff0000000000000L)));
    System.err.println(StringUtil.fillto( //
        '0', 64, Long.toBinaryString(0x000fffffffffffffL)));

    printDouble(d1);
    printDouble(d2);
    printDouble(d3);
    printDouble(d4);
    printDouble(d5);
    printDouble(d6);
    printDouble(d7);
    printDouble(d8);

  }
}
