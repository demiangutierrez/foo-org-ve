package org.cyrano.jogl._23.raytracing;

import org.cyrano.util.geometry.Matrix;
import org.cyrano.util.geometry.MatrixOps;

public class Ray {

  public double xp0;
  public double yp0;
  public double zp0;

  public double xpu;
  public double ypu;
  public double zpu;

  // --------------------------------------------------------------------------------

  public Ray( //
      double xp0, double yp0, double zp0, //
      double xpu, double ypu, double zpu) {

    this.xp0 = xp0;
    this.yp0 = yp0;
    this.zp0 = zp0;

    this.xpu = xpu;
    this.ypu = ypu;
    this.zpu = zpu;
  }

  // --------------------------------------------------------------------------------

  public double calcX(double s) {
    return xp0 + ux() * s;
  }

  public double calcY(double s) {
    return yp0 + uy() * s;
  }

  public double calcZ(double s) {
    return zp0 + uz() * s;
  }

  // --------------------------------------------------------------------------------

  public double ux() {
    double ux = xpu - xp0;
    double uy = ypu - yp0;
    double uz = zpu - zp0;

    return ux / Math.sqrt(ux * ux + uy * uy + uz * uz);
  }

  // --------------------------------------------------------------------------------

  public double uy() {
    double ux = xpu - xp0;
    double uy = ypu - yp0;
    double uz = zpu - zp0;

    return uy / Math.sqrt(ux * ux + uy * uy + uz * uz);
  }

  // --------------------------------------------------------------------------------

  public double uz() {
    double ux = xpu - xp0;
    double uy = ypu - yp0;
    double uz = zpu - zp0;

    return uz / Math.sqrt(ux * ux + uy * uy + uz * uz);
  }

  // --------------------------------------------------------------------------------

  public double calcIntersects(double[] quad1) {
    Matrix m1 = new Matrix(3, 1);
    m1.set(0, 0, quad1[0]);
    m1.set(1, 0, quad1[1]);
    m1.set(2, 0, quad1[2]);

    Matrix m2 = new Matrix(3, 1);
    m2.set(0, 0, quad1[3] - quad1[0]);
    m2.set(1, 0, quad1[4] - quad1[1]);
    m2.set(2, 0, quad1[5] - quad1[2]);

    Matrix m3 = new Matrix(3, 1);
    m3.set(0, 0, quad1[6] - quad1[0]);
    m3.set(1, 0, quad1[7] - quad1[1]);
    m3.set(2, 0, quad1[8] - quad1[2]);

    Matrix n = MatrixOps.crossProduct(m2, m3);

    double c = n.get(0, 0) * quad1[0] + n.get(1, 0) * quad1[1] + n.get(2, 0) * quad1[2];
    double a = n.get(0, 0) * xp0/*  */+ n.get(1, 0) * yp0/*  */+ n.get(2, 0) * zp0;
    double b = n.get(0, 0) * ux()/* */+ n.get(1, 0) * uy()/* */+ n.get(2, 0) * uz();

    return (c - a) / b;
  }
}
