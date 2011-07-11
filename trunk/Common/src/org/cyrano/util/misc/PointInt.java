package org.cyrano.util.misc;

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

  // --------------------------------------------------------------------------------

  @Override
  public int hashCode() {
    final int prime = 31;

    int result = 1;

    result = prime * result + x;
    result = prime * result + y;

    return result;
  }

  // --------------------------------------------------------------------------------

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    PointInt pnt = (PointInt) obj;

    if (x != pnt.x) {
      return false;
    }

    if (y != pnt.y) {
      return false;
    }

    return true;
  }
}
