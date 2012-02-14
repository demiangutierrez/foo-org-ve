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
public class Main3 extends BaseExample {

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
    double pointArray[] = {
        -1.5, -1.5, +4.0,
        -0.5, -1.5, +2.0,
        +0.5, -1.5, -1.0,
        +1.5, -1.5, +2.0,
        -1.5, -0.5, +1.0,
        -0.5, -0.5, +3.0,
        +0.5, -0.5, +0.0,
        +1.5, -0.5, -1.0,
        -1.5, +0.5, +4.0,
        -0.5, +0.5, +0.0,
        +0.5, +0.5, +3.0,
        +1.5, +0.5, +4.0,
        -1.5, +1.5, -2.0,
        -0.5, +1.5, -2.0,
        +0.5, +1.5, +0.0,
        +1.5, +1.5, -1.0};
    //@endnf

    gl.glColor3f(1, 1, 0);

    gl.glEnable(GL.GL_MAP2_VERTEX_3);

    // ----------------------------------------
    // define the curve
    // ----------------------------------------

    gl.glMap2d(GL.GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, pointArray, 0);

    // ----------------------------------------
    // define the steps
    // ----------------------------------------

    //gl.glMapGrid2d(20, 0.0, 1.0, 20, 0.0, 1.0);
    gl.glMapGrid2d(8, 0.0, 1.0, 30, 0.0, 1.0);

    // ----------------------------------------
    // eval & draw
    // ----------------------------------------

    //gl.glEvalMesh2(GL.GL_FILL, 0, 8, 0, 30);
    gl.glEvalMesh2(GL.GL_LINE, 0, 8, 0, 30);

    //    for (int j = 0; j <= 8; j++) {
    //      gl.glBegin(GL.GL_LINE_STRIP);
    //      for (int i = 0; i <= 30; i++) {
    //        gl.glEvalCoord2d(i / 30.0, j / 8.0);
    //      }
    //      gl.glEnd();
    //
    //      gl.glBegin(GL.GL_LINE_STRIP);
    //      for (int i = 0; i <= 30; i++) {
    //        gl.glEvalCoord2d(j / 8.0, i / 30.0);
    //      }
    //      gl.glEnd();
    //    }

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
    new Main3().run();
  }
}

//for (int j = 0; j <= 30; j += 1) {
//  gl.glBegin(GL.GL_LINE_STRIP);
//  for (int i = 0; i <= 8; i += 1) {
//    double du = (1-0)/8f;
//    double dv = (1-0)/30f;
//    gl.glEvalCoord2d(i * du + 1, j * dv + 1);
//  }
//  gl.glEnd();
//}
//for (int i = 0; i <= 8; i += 1) {
//  gl.glBegin(GL.GL_LINE_STRIP);
//  for (int j = 0; j <= 30; j += 1) {
//    double du = (1-0)/8f;
//    double dv = (1-0)/30f;
//    gl.glEvalCoord2d(i * du + 1, j * dv + 1);
//  }
//  gl.glEnd();
//}
