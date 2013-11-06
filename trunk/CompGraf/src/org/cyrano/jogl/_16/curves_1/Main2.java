package org.cyrano.jogl._16.curves_1;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;

/**
 * @author Demián Gutierrez
 */
public class Main2 extends BaseExample {

  public Main2() {
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

    gl.glColor3f(1, 1, 0);

    gl.glEnable(GL.GL_MAP1_VERTEX_3);

    // ----------------------------------------
    // define the curve
    // ----------------------------------------

    gl.glMap1d(GL.GL_MAP1_VERTEX_3, 0, 1, 3, 4, pointArray, 0);

    // ----------------------------------------
    // define the steps
    // ----------------------------------------

    gl.glMapGrid1d(50, 0.0, 1.0);

    // ----------------------------------------
    // eval & draw
    // ----------------------------------------

    gl.glEvalMesh1(GL.GL_LINE, 0, 50);

    Util.drawControlPoints(gl, pointArray);

    // ----------------------------------------

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main2();
  }
}
