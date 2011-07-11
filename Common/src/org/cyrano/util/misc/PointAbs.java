package org.cyrano.util.misc;

public abstract class PointAbs {

  // --------------------------------------------------------------------------------

  public static double dist(PointAbs p1, PointAbs p2) {
    return Math.sqrt( //
        /**/(p1.getDX() - p2.getDX()) * (p1.getDX() - p2.getDX()) + //
            (p1.getDY() - p2.getDY()) * (p1.getDY() - p2.getDY()));
  }

  // --------------------------------------------------------------------------------

  public abstract/*   */int getIX();

  public abstract/*  */void setIX(int/**/x);

  // --------------------------------------------------------------------------------

  public abstract/*   */int getIY();

  public abstract/*  */void setIY(int/**/y);

  // --------------------------------------------------------------------------------

  public abstract/**/double getDX();

  public abstract/*  */void setDX(double x);

  // --------------------------------------------------------------------------------

  public abstract/**/double getDY();

  public abstract/*  */void setDY(double y);
}
