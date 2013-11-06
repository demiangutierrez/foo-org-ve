package org.cyrano.jogl._16.curves_1;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;

/**
 * @author Demián Gutierrez
 */
public class Main1 extends BaseExample {

  public Main1() {
    initBaseExample(getClass().getName(), new CameraBall());
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    camera.updateCameraBox(getW(gl), getH(gl));
    camera.updateCameraPos();

    Primitives.drawAxes(gl);

    // ----------------------------------------

    //@begnf
    double[] pointArray = {
        -0.32, +0.08, 0,
        -0.05, +1,    0,
        +0.05, -1,    0,
        +0.32, +0.08, 0};
    //@endnf

    gl.glEnable(GL.GL_MAP1_VERTEX_3);
    gl.glMap1d(GL.GL_MAP1_VERTEX_3, 0, 1, 3, 4, pointArray, 0);

    gl.glColor3f(1, 1, 0);

    gl.glBegin(GL.GL_LINE_STRIP);

    for (int k = 0; k <= 50; k++) {
      gl.glEvalCoord1f(k / 50f);
    }

    gl.glEnd();

    Util.drawControlPoints(gl, pointArray);

    // ----------------------------------------

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main1();
  }
}
