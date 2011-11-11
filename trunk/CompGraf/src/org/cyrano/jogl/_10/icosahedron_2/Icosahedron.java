package org.cyrano.jogl._10.icosahedron_2;

import javax.media.opengl.GL;

import org.cyrano.util.misc.MathUtil;

import com.sun.opengl.util.texture.Texture;

public class Icosahedron {

  public static Texture texture;

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

  private Icosahedron() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static double r;
  public static double g;
  public static double b;

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

  private static double[] cross(double[] a, double[] b) {
    double[] r = {0, 0, 0};

    r[0] = a[1] * b[2] - a[2] * b[1];
    r[1] = a[2] * b[0] - a[0] * b[2];
    r[2] = a[0] * b[1] - a[1] * b[0];

    return r;
  }

  private static double[] calcTextureMap(double[] vtx) {
    double[] ret = new double[3];

    ret[0] = Math.sqrt(vtx[0] * vtx[0] + vtx[1] * vtx[1] + vtx[2] * vtx[2]);
    ret[1] = Math.acos(vtx[2] / ret[0]);
    ret[2] = Math.atan2(vtx[1], vtx[0]);

    ret[1] += Math.PI;
    ret[1] /= (2 * Math.PI);
    ret[2] += Math.PI;
    ret[2] /= (2 * Math.PI);

    return ret;
  }

  private static void drawTriangle(GL gl, //
      double[] v1, double[] v2, double[] v3) {

    double[] spherical;

    gl.glEnable(GL.GL_TEXTURE_2D);

    texture.bind();

    gl.glBegin(GL.GL_TRIANGLES);
    gl.glColor3d(r, g, b);

    double[] v12 = {v1[0] - v2[0], v1[1] - v2[1], v1[1] - v2[1]};
    double[] v13 = {v1[0] - v3[0], v1[1] - v3[1], v1[1] - v3[1]};

    double[] cc = cross(v12, v13);

    if (cc[0] / v1[0] > 0 && cc[1] / v1[1] > 0 && cc[2] / v1[2] > 0) {
      spherical = calcTextureMap(v3);
      gl.glTexCoord2d(spherical[1], spherical[2]);
      gl.glVertex3d(v3[0], v3[1], v3[2]);

      spherical = calcTextureMap(v1);
      gl.glTexCoord2d(spherical[1], spherical[2]);
      gl.glVertex3d(v1[0], v1[1], v1[2]);

      spherical = calcTextureMap(v2);
      gl.glTexCoord2d(spherical[1], spherical[2]);
      gl.glVertex3d(v2[0], v2[1], v2[2]);
    } else {
      spherical = calcTextureMap(v2);
      gl.glTexCoord2d(spherical[1], spherical[2]);
      gl.glVertex3d(v2[0], v2[1], v2[2]);

      spherical = calcTextureMap(v1);
      gl.glTexCoord2d(spherical[1], spherical[2]);
      gl.glVertex3d(v1[0], v1[1], v1[2]);

      spherical = calcTextureMap(v3);
      gl.glTexCoord2d(spherical[1], spherical[2]);
      gl.glVertex3d(v3[0], v3[1], v3[2]);
    }

    gl.glEnd();

    gl.glDisable(GL.GL_TEXTURE_2D);

    
    r /= 1.001;
    g /= 1.001;
    b /= 1.001;
  }
}
