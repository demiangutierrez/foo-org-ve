package org.cyrano.jogl.util;

//--------------------------------------------------------------------------------
// BEWARE: this is probably a "noob" 3D affine transforms implementation!
//--------------------------------------------------------------------------------

/**
 * @author Demián Gutierrez
 */
public class MatrixOps {

  public static double deg2rad(double deg) {
    // 180 -> PI
    // deg -> rad
    return deg * Math.PI / 180;
  }

  // --------------------------------------------------------------------------------

  public static Matrix matrixMult(Matrix a, Matrix b) {
    assert (a.gCs() == b.gFs());

    /**/int comsize = a.gCs();
    //  int comsize = b.gFs();

    Matrix ret = new Matrix(a.gFs(), b.gCs());

    for (int cj = 0; cj < ret.gCs(); cj++) {
      for (int fi = 0; fi < ret.gFs(); fi++) {
        double sum = 0;

        for (int k = 0; k < comsize; k++) {
          sum += a.get(fi, k) * b.get(k, cj);
        }

        ret.set(fi, cj, sum);
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static void loadIdentity(Matrix m) {
    assert (m.gFs() == m.gCs());

    for (int cj = 0; cj < m.gCs(); cj++) {
      for (int fi = 0; fi < m.gFs(); fi++) {
        m.set(fi, cj, fi == cj ? 1 : 0);
      }
    }
  }

  // --------------------------------------------------------------------------------

  public static Matrix rotateX(double deg) {
    Matrix ret = new Matrix(4, 4);

    double c = Math.cos(deg2rad(deg));
    double s = Math.sin(deg2rad(deg));

    double[] data = new double[]{ //
    /**/+1, +0, +0, +0, //
        +0, +c, -s, +0, //
        +0, +s, +c, +0, //
        +0, +0, +0, +1};

    ret.setData(data);

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static Matrix rotateY(double deg) {
    Matrix ret = new Matrix(4, 4);

    double c = Math.cos(deg2rad(deg));
    double s = Math.sin(deg2rad(deg));

    double[] data = new double[]{ //
    /**/+c, +0, +s, +0, //
        +0, +1, +0, +0, //
        -s, +0, +c, +0, //
        +0, +0, +0, +1};

    ret.setData(data);

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static Matrix rotateZ(double deg) {
    Matrix ret = new Matrix(4, 4);

    double c = Math.cos(deg2rad(deg));
    double s = Math.sin(deg2rad(deg));

    double[] data = new double[]{ //
    /**/+c, -s, +0, +0, //
        +s, +c, +0, +0, //
        +0, +0, +1, +0, //
        +0, +0, +0, +1};

    ret.setData(data);

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static Matrix rotate(double deg, double x, double y, double z) {
    Matrix ret = new Matrix(4, 4);

    double c = Math.cos(deg2rad(deg));
    double s = Math.sin(deg2rad(deg));

    double t = 1 - c;

    ret.set(0, 0, t * x * x + c);
    ret.set(1, 0, t * x * y - s * z);
    ret.set(2, 0, t * x * z + s * y);
    ret.set(3, 0, 0);

    ret.set(0, 1, t * x * y + s * z);
    ret.set(1, 1, t * y * y + c);
    ret.set(2, 1, t * y * z - s * x);
    ret.set(3, 1, 0);

    ret.set(0, 2, t * x * z - s * y);
    ret.set(1, 2, t * y * z + s * x);
    ret.set(2, 2, t * z * z + c);
    ret.set(3, 2, 0);

    ret.set(0, 3, 0);
    ret.set(1, 3, 0);
    ret.set(2, 3, 0);
    ret.set(3, 3, 1);

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static Matrix translate(double tx, double ty, double tz) {
    Matrix ret = new Matrix(4, 4);

    double[] data = new double[]{ //
    /**/1, 0, 0, tx, //
        0, 1, 0, ty, //
        0, 0, 1, tz, //
        0, 0, 0, 1};

    ret.setData(data);

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static Matrix crossProduct(Matrix v1, Matrix v2) {
    Matrix ret = new Matrix(4, 1);

    ret.set(0, 0, v1.get(1, 0) * v2.get(2, 0) - v2.get(1, 0) * v1.get(2, 0));
    ret.set(1, 0, v1.get(2, 0) * v2.get(0, 0) - v2.get(2, 0) * v1.get(0, 0));
    ret.set(2, 0, v1.get(0, 0) * v2.get(1, 0) - v2.get(0, 0) * v1.get(1, 0));
    ret.set(3, 0, 0);

    return ret;
  }
}
