package org.cyrano.jogl._11.light_2;

import javax.media.opengl.GL;

import org.cyrano.util.misc.MathUtil;

public class Icosahedron {

  public static double X = 0.525731112119133606f;
  public static double Z = 0.850650808352039932f;

  //@begnf
  public static double[][] vdata = {
      {-X, +0, +Z}, {+X, +0, +Z}, {-X, +0, -Z}, {+X, +0, -Z},    
      {+0, +Z, +X}, {+0, +Z, -X}, {+0, -Z, +X}, {+0, -Z, -X}, 
      {+Z, +X, +0}, {-Z, +X, +0}, {+Z, -X, +0}, {-Z, -X, +0}};

  public static int   [][] tindx = {
      { 0,  4,  1}, { 0,  9,  4}, { 9,  5,  4}, { 4,  5,  8},
      { 4,  8,  1}, { 8, 10,  1}, { 8,  3, 10}, { 5,  3,  8},
      { 5,  2,  3}, { 2,  7,  3}, { 7, 10,  3}, { 7,  6, 10},
      { 7, 11,  6}, {11,  0,  6}, { 0,  1,  6}, { 6,  1, 10},
      { 9,  0, 11}, { 9, 11,  2}, { 9,  2,  5}, { 7,  2, 11}};
  //@endnf

  // --------------------------------------------------------------------------------

  public static boolean triaNormals;
  public static boolean drawNormals;

  // --------------------------------------------------------------------------------

  private Icosahedron() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static double[] surfColor = {1, 1, 1, 1};
  public static double[] normColor1 = {0, 1, 0, 1};;
  public static double[] normColor2 = {0, 1, 1, 1};;
  public static double[] pointColor = {1, 0, 0, 1};;

  // --------------------------------------------------------------------------------

  public static void drawIcosahedron(GL gl, int depth) {
    for (int i = 0; i < tindx.length; i++) {
      subdivide(gl, //
          vdata[tindx[i][0]], //
          vdata[tindx[i][1]], //
          vdata[tindx[i][2]], depth);
    }
  }

  // --------------------------------------------------------------------------------

  private static void subdivide(GL gl, //
      double[] vA0, double[] vB1, double[] vC2, int depth) {

    double[] vAB = new double[3];
    double[] vBC = new double[3];
    double[] vCA = new double[3];

    int i;

    if (depth == 0) {
      drawTriangle(gl, vA0, vB1, vC2);

      return;
    }

    for (i = 0; i < 3; i++) {
      vAB[i] = (vA0[i] + vB1[i]) / 2;
      vBC[i] = (vB1[i] + vC2[i]) / 2;
      vCA[i] = (vC2[i] + vA0[i]) / 2;
    }

    double modAB = MathUtil.mod(vAB);
    double modBC = MathUtil.mod(vBC);
    double modCA = MathUtil.mod(vCA);

    for (i = 0; i < 3; i++) {
      vAB[i] /= modAB;
      vBC[i] /= modBC;
      vCA[i] /= modCA;
    }

    subdivide(gl, vA0, vAB, vCA, depth - 1);
    subdivide(gl, vB1, vBC, vAB, depth - 1);
    subdivide(gl, vC2, vCA, vBC, depth - 1);
    subdivide(gl, vAB, vBC, vCA, depth - 1);
  }

  // --------------------------------------------------------------------------------

  private static void drawTriangle(GL gl, //
      double[] v1, double[] v2, double[] v3) {

    double[] v12 = {v1[0] - v2[0], v1[1] - v2[1], v1[2] - v2[2]};
    double[] v13 = {v1[0] - v3[0], v1[1] - v3[1], v1[2] - v3[2]};

    double[] cc = MathUtil.cross(v12, v13);

    double[] va;
    double[] vb;
    double[] vc;

    double[] na;
    double[] nb;
    double[] nc;

    if (cc[0] / v1[0] > 0 && cc[1] / v1[1] > 0 && cc[2] / v1[2] > 0) {
      cc = MathUtil.cross(v12, v13);
      va = v3;
      vb = v1;
      vc = v2;
    } else {
      cc = MathUtil.cross(v13, v12);
      va = v2;
      vb = v1;
      vc = v3;
    }

    cc = MathUtil.nor(cc);

    if (triaNormals) {
      na = cc;
      nb = cc;
      nc = cc;
    } else {
      na = MathUtil.nor(va);
      nb = MathUtil.nor(vb);
      nc = MathUtil.nor(vc);
    }

    gl.glBegin(GL.GL_TRIANGLES);

    gl.glNormal3dv(na, 0);
    gl.glVertex3d(va[0], va[1], va[2]);

    gl.glNormal3dv(nb, 0);
    gl.glVertex3d(vb[0], vb[1], vb[2]);

    gl.glNormal3dv(nc, 0);
    gl.glVertex3d(vc[0], vc[1], vc[2]);

    gl.glEnd();

    if (drawNormals) {
      drawNormal(gl, va, na);
      drawNormal(gl, vb, nb);
      drawNormal(gl, vc, nc);
    }
  }

  private static void drawNormal(GL gl, double[] v, double[] n) {
    gl.glDisable(GL.GL_LIGHTING);

    gl.glPushMatrix();
    gl.glTranslated(v[0], v[1], v[2]);

    double factor = 0.2;

    gl.glLineWidth(2.0f); // outside beg/end

    gl.glBegin(GL.GL_LINES);
    gl.glColor3dv(normColor1, 0);
    gl.glVertex3d(0, 0, 0);
    gl.glColor3dv(normColor2, 0);
    gl.glVertex3d(n[0] * factor, n[1] * factor, n[2] * factor);
    gl.glEnd();

    gl.glPointSize(5f); // outside beg/end

    gl.glBegin(GL.GL_POINTS);
    gl.glColor3dv(pointColor, 0);
    gl.glVertex3d(0, 0, 0);
    gl.glEnd();

    gl.glPopMatrix();

    gl.glEnable(GL.GL_LIGHTING);
  }
}
