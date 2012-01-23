package org.cyrano.jogl._16.curves_1;

import javax.media.opengl.GL;

public class Util {

  public static void drawControlPoints(GL gl, double[] pointArray) {
    gl.glColor3f(1.0f, 1.0f, 1.0f);

    gl.glPointSize(10.0f);

    gl.glBegin(GL.GL_POINTS);

    for (int i = 0; i < pointArray.length / 3; i++) {
      gl.glVertex3dv(pointArray, i * 3);
    }

    gl.glEnd();
  }
}
