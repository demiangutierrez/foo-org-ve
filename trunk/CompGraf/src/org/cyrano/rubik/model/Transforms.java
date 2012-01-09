package org.cyrano.rubik.model;

public class Transforms {

  private static double[][] xRot(double theta) {
    double[][] ret = { //
    /**/{+Math.cos(theta), -Math.sin(theta), 0, 0}, //
        {+Math.sin(theta), +Math.cos(theta), 0, 0}, //
        {/*           */0, /*           */0, 1, 0}, //
        {/*           */0, /*           */0, 0, 1}};

    return ret;
  }

  private static final int[][] X_CC = { //
  /**/{+1, +0, +0}, //
      {+0, +0, -1}, //
      {+0, +1, +0}};

  private static final int[][] X_CW = { //
  /**/{+1, +0, +0}, //
      {+0, +0, +1}, //
      {+0, -1, +0}};

  private static double[][] yRot(double theta) {
    double[][] ret = { //
    /**/{+Math.cos(theta), 0, +Math.sin(theta), 0}, //
        {/*           */0, 1, /*           */0, 0}, //
        {-Math.sin(theta), 0, +Math.cos(theta), 0}, //
        {/*           */0, 0, /*           */0, 1}};

    return ret;
  }

  private static final int[][] Y_CC = { //
  /**/{+0, +0, +1}, //
      {+0, +1, +0}, //
      {-1, +0, +0}};

  private static final int[][] Y_CW = { //
  /**/{+0, +0, -1}, //
      {+0, +1, +0}, //
      {+1, +0, +0}};

  private static double[][] zRot(double theta) {
    double[][] ret = { //
    /**/{1, /*           */0, /*           */0, 0}, //
        {0, +Math.cos(theta), -Math.sin(theta), 0}, //
        {0, +Math.sin(theta), +Math.cos(theta), 0}, //
        {0, /*           */0, /*           */0, 1}};

    return ret;
  }

  private static final int[][] Z_CC = { //
  /**/{+0, -1, +0}, //
      {+1, +0, +0}, //
      {+0, +0, +1}};

  private static final int[][] Z_CW = { //
  /**/{+0, +1, +0}, //
      {-1, +0, +0}, //
      {+0, +0, +1}};

  // --------------------------------------------------------------------------------

  public static int[][] getTransform(Axis axis, Turn turn) {
    switch (turn) {
      case CW :
        return getTransformCW(axis);
      case CC :
        return getTransformCC(axis);

      default :
        throw new IllegalArgumentException(turn.toString());
    }
  }

  // --------------------------------------------------------------------------------

  private static int[][] getTransformCW(Axis axis) {
    switch (axis) {
      case X_POS :
      case X_NEG :
        return X_CW;

      case Y_POS :
      case Y_NEG :
        return Y_CW;

      case Z_POS :
      case Z_NEG :
        return Z_CW;

      default :
        throw new IllegalArgumentException(axis.toString());
    }
  }

  // --------------------------------------------------------------------------------

  private static int[][] getTransformCC(Axis axis) {
    switch (axis) {
      case X_POS :
      case X_NEG :
        return X_CC;

      case Y_POS :
      case Y_NEG :
        return Y_CC;

      case Z_POS :
      case Z_NEG :
        return Z_CC;

      default :
        throw new IllegalArgumentException(axis.toString());
    }
  }
}
