package org.cyrano.util;

import java.text.NumberFormat;

public class PointInt extends PointAbs {

  public int x;
  public int y;

  // --------------------------------------------------------------------------------

  public PointInt() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public PointInt(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // --------------------------------------------------------------------------------

  public/*   */int getIX() {
    return x;
  }

  public/*  */void setIX(int/**/x) {
    this.x = x;
  }

  // --------------------------------------------------------------------------------

  public/*   */int getIY() {
    return y;
  }

  public/*  */void setIY(int/**/y) {
    this.y = y;
  }

  // --------------------------------------------------------------------------------

  public/**/double getDX() {
    return x;
  }

  public/*  */void setDX(double x) {
    this.x = (int) x;
  }

  // --------------------------------------------------------------------------------

  public/**/double getDY() {
    return y;
  }

  public/*  */void setDY(double y) {
    this.y = (int) y;
  }

  // --------------------------------------------------------------------------------

  @Override
  public String toString() {
    NumberFormat nf = NumberFormat.getInstance();

    nf.setMinimumIntegerDigits(4);
    nf.setMaximumIntegerDigits(4);

    nf.setGroupingUsed(false);

    return "[" + nf.format(x) + " , " + nf.format(y) + "]";
  }
}
