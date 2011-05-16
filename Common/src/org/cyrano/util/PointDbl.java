package org.cyrano.util;

import java.text.NumberFormat;

public class PointDbl extends PointAbs {

  public double x;
  public double y;

  // --------------------------------------------------------------------------------

  public PointDbl() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public PointDbl(double x, double y) {
    this.x = x;
    this.y = y;
  }

  // --------------------------------------------------------------------------------

  public/*   */int getIX() {
    return (int) x;
  }

  public/*  */void setIX(int/**/x) {
    this.x = x;
  }

  // --------------------------------------------------------------------------------

  public/*   */int getIY() {
    return (int) y;
  }

  public/*  */void setIY(int/**/y) {
    this.y = y;
  }

  // --------------------------------------------------------------------------------

  public/**/double getDX() {
    return x;
  }

  public/*  */void setDX(double x) {
    this.x = x;
  }

  // --------------------------------------------------------------------------------

  public/**/double getDY() {
    return y;
  }

  public/*  */void setDY(double y) {
    this.y = y;
  }

  // --------------------------------------------------------------------------------

  @Override
  public String toString() {
    NumberFormat nf = NumberFormat.getInstance();

    nf.setMinimumFractionDigits(2);
    nf.setMaximumFractionDigits(2);

    nf.setMinimumIntegerDigits(4);
    nf.setMaximumIntegerDigits(4);

    nf.setGroupingUsed(false);

    return "[" + nf.format(x) + " , " + nf.format(y) + "]";
  }
}
