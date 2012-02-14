package org.cyrano.jogl._16.curves_1;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.Camera;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;
import org.cyrano.jogl.util.TextureCache;

/**
 * @author Demián Gutierrez
 */
public class Main0 extends BaseExample {

  private Camera camera = new CameraBall();

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseMotionListener(camera);
    drawable.addMouseListener/*  */(camera);
    drawable.addKeyListener/*    */(camera);

    GL gl = drawable.getGL();

    TextureCache.init("textures");

    gl.glDisable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);
  }

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

    GL gl = drawable.getGL();
    gl.glViewport(0, 0, w, h);

    camera.updateCameraBox();
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    camera.updateCameraBox();
    camera.updateCameraPos();

    Primitives.drawAxes(gl);

    // ----------------------------------------

    //@begnf
    double[] pointArray = {
        -0.32, +0.08, 0,
        +0.32, +0.08, 0};
    //@endnf

    gl.glEnable(GL.GL_MAP1_VERTEX_3);
    gl.glMap1d(GL.GL_MAP1_VERTEX_3, 0, 1, 3, 2, pointArray, 0);

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

  public void displayChanged(GLAutoDrawable drawable, //
      boolean modeChanged, boolean deviceChanged) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main0().run();
  }
}
