package org.cyrano.jogl.base;

import static java.lang.Math.*;

/**
 * @author Demián Gutierrez
 */
public class Pol2Cart {

  public static final int X = 0;
  public static final int Y = 1;
  public static final int Z = 2;

  public static final int AZ = 0;
  public static final int EL = 1;
  public static final int RD = 2;

  // --------------------------------------------------------------------------------

  public static double[] polToCrt(double[] pol) {
    double[] crt = new double[3];

    // --------------------------------------------------------------------------------
    // from:
    // http://www.mathworks.com/help/techdoc/ref/sph2cart.html
    // --------------------------------------------------------------------------------
    crt[X] = pol[RD] * cos(pol[EL]) * cos(pol[AZ]);
    crt[Y] = pol[RD] * cos(pol[EL]) * sin(pol[AZ]);
    crt[Z] = pol[RD] * sin(pol[EL]);

    return crt;
  }

  // --------------------------------------------------------------------------------

  public static double[] crtToPol(double[] crt) {
    double[] pol = new double[3];

    // --------------------------------------------------------------------------------
    // from:
    // http://www.mathworks.com/help/techdoc/ref/cart2sph.html
    // --------------------------------------------------------------------------------
    pol[AZ] = atan2(crt[Y], crt[X]);
    pol[EL] = atan2(crt[Z], sqrt(crt[X] * crt[X] + crt[Y] * crt[Y]));
    pol[RD] = sqrt(crt[X] * crt[X] + crt[Y] * crt[Y] + crt[Z] * crt[Z]);

    return pol;
  }

  // --------------------------------------------------------------------------------

  public static double theta2ToAz(double theta2) {
    return theta2;
  }

  public static double theta1ToEl(double theta1) {
    return PI / 2 - theta1;
  }
}
