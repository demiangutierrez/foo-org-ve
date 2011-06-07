package org.cyrano.jogl.morton;

import java.util.Random;

import org.cyrano.util.PointAbs;
import org.cyrano.util.PointDbl;
import org.cyrano.util.PointInt;

public class PointGen {

  public static final int RANDOM_AMOUNT = 50;

  public static double DBL_MAX_X = 0;
  public static double DBL_MAX_Y = 0;

  public static final int/**/INT_MAX_X = 512;
  public static final int/**/INT_MAX_Y = 512;

  public static final boolean RANDOM = false;
  public static final boolean LESSTZ = false;
  public static final boolean USEINT = false;

  // --------------------------------------------------------------------------------

  private PointSet pointSet;

  private int scrW = -1;
  private int scrH = -1;

  // --------------------------------------------------------------------------------

  public PointGen() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void initPointList() {
    if (USEINT) {
      initPointIntList();
    } else {
      initPointDblList();
    }
  }

  // --------------------------------------------------------------------------------

  private void initPointIntList() {
    pointSet = new PointSet();

    if (RANDOM) {
      Random r = new Random(0);

      for (int i = 0; i < RANDOM_AMOUNT; i++) {
        int x = (int) (r.nextDouble() * INT_MAX_X);
        int y = (int) (r.nextDouble() * INT_MAX_Y);

        if (LESSTZ) {
          x -= (INT_MAX_X / 2);
          y -= (INT_MAX_Y / 2);
        }

        pointSet.addPoint(new PointInt(x, y));
      }
    } else {
      // ----------------------------------------
      // XXX: improve parameterization
      // ----------------------------------------
      if (LESSTZ) {
        for (int i = -INT_MAX_X / 2; i < INT_MAX_X / 2; i += (INT_MAX_X / 16)) {
          for (int j = -INT_MAX_Y / 2; j < INT_MAX_Y / 2; j += (INT_MAX_Y / 16)) {
            pointSet.addPoint(new PointInt(i, j));
          }
        }
      } else {
        for (int i = 0; i < INT_MAX_X; i += (INT_MAX_X / 16)) {
          for (int j = 0; j < INT_MAX_Y; j += (INT_MAX_Y / 16)) {
            pointSet.addPoint(new PointInt(i, j));
          }
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void initPointDblList() {
    pointSet = new PointSet();

    if (RANDOM) {
      Random r = new Random(0);

      for (int i = 0; i < RANDOM_AMOUNT; i++) {
        double x = r.nextDouble() * DBL_MAX_X;
        double y = r.nextDouble() * DBL_MAX_Y;

        if (LESSTZ) {
          x -= (DBL_MAX_X / 2);
          y -= (DBL_MAX_Y / 2);
        }

        pointSet.addPoint(new PointDbl(x, y));
      }
    } else {
      // ----------------------------------------
      // XXX: I'm still not happy with this!!!
      // ----------------------------------------
      if (LESSTZ) {
        for (int i = -INT_MAX_X / 2; i < INT_MAX_X / 2; i += (INT_MAX_X / 16)) {
          for (int j = -INT_MAX_Y / 2; j < INT_MAX_Y / 2; j += (INT_MAX_Y / 16)) {
            // those are going to be big numbers!!!
            double x = Double.longBitsToDouble(((long) i) << 23);
            double y = Double.longBitsToDouble(((long) j) << 23);

            System.err.println("*****************************************************");
            System.err.println(IEEE754Util.exponent(x) + ";" + IEEE754Util.mantissa(x));
            System.err.println(IEEE754Util.exponent(y) + ";" + IEEE754Util.mantissa(y));

            DBL_MAX_X = x > DBL_MAX_X ? x : DBL_MAX_X;
            DBL_MAX_Y = y > DBL_MAX_Y ? y : DBL_MAX_Y;

            System.err.println(DBL_MAX_X + ";" + DBL_MAX_Y);

            pointSet.addPoint(new PointDbl(x, y));
          }
        }
      } else {
        for (int i = 0; i < INT_MAX_X; i += (INT_MAX_X / 16)) {
          for (int j = 0; j < INT_MAX_Y; j += (INT_MAX_Y / 16)) {
            // those are going to be big numbers!!!
            double x = Double.longBitsToDouble(((long) i) << 30);
            double y = Double.longBitsToDouble(((long) j) << 30);

            System.err.println("*****************************************************");
            System.err.println(IEEE754Util.exponent(x) + ";" + IEEE754Util.mantissa(x));
            System.err.println(IEEE754Util.exponent(y) + ";" + IEEE754Util.mantissa(y));

            DBL_MAX_X = x > DBL_MAX_X ? x : DBL_MAX_X;
            DBL_MAX_Y = y > DBL_MAX_Y ? y : DBL_MAX_Y;

            System.err.println(DBL_MAX_X + ";" + DBL_MAX_Y);

            pointSet.addPoint(new PointDbl(x, y));
          }
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  public int getScrX(PointAbs pointAbs) {
    assert (scrW != -1);

    if (USEINT) {
      return (int) (pointAbs.getDX() / INT_MAX_X * scrW);
    } else {
      return (int) (pointAbs.getDX() / DBL_MAX_X * scrW);
    }
  }

  // --------------------------------------------------------------------------------

  public void setScrX(PointAbs pointAbs, double x) {
    assert (scrW != -1);

    if (USEINT) {
      pointAbs.setIX((int) (x / scrW * INT_MAX_X));
    } else {
      pointAbs.setDX(/*   */x / scrW * DBL_MAX_X);
    }
  }

  // --------------------------------------------------------------------------------

  public int getScrY(PointAbs pointAbs) {
    assert (scrH != -1);

    if (USEINT) {
      return (int) (pointAbs.getDY() / INT_MAX_Y * scrH);
    } else {
      return (int) (pointAbs.getDY() / DBL_MAX_Y * scrH);
    }
  }

  // --------------------------------------------------------------------------------

  public void setScrY(PointAbs pointAbs, double y) {
    assert (scrH != -1);

    if (USEINT) {
      pointAbs.setIY((int) (y / scrH * INT_MAX_Y));
    } else {
      pointAbs.setDY(/*   */y / scrH * DBL_MAX_Y);
    }
  }

  // --------------------------------------------------------------------------------

  public void setScrWH(int scrW, int scrH) {
    this.scrW = scrW;
    this.scrH = scrH;
  }

  // --------------------------------------------------------------------------------

  public PointSet getPointSet() {
    return pointSet;
  }
}
