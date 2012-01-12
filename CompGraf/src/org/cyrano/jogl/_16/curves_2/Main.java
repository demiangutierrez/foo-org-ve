package org.cyrano.jogl._16.curves_2;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.Camera;
import org.cyrano.jogl.base.Primitives;
import org.cyrano.jogl.util.TextureCache;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample {

  private Camera camera = new Camera();

  private static final int uSize = 2;
  private static final int vSize = 2;
  private static final int gridSize = 20;

  private double[] grid2x2 = { //
  /**/-2.0, -2.0, +0.0,//
      +2.0, -2.0, +0.0,//
      -2.0, +2.0, +0.0,//
      +2.0, +2.0, +0.0};

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseMotionListener(camera);
    drawable.addMouseListener/*  */(camera);
    drawable.addKeyListener/*    */(camera);

    GL gl = drawable.getGL();

    TextureCache.init("textures");

    gl.glDisable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);
    
    gl.glEnable(GL.GL_MAP2_VERTEX_3);
    gl.glMapGrid2d(gridSize, 0.0, 1.0, gridSize, 0.0, 1.0);

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

    //    float[] points = {-40, 40, 0, -10, 200, 0, 10, -200, 0, 40, 40, 0};
    //    
    //    gl.glMap1f(GL.GL_MAP1_VERTEX_3, 0, 1, 3, 4, points, 0);
    //    gl.glEnable(GL.GL_MAP1_VERTEX_3);
    //
    //    
    //    gl.glColor3f(1,  1, 0);
    //    
    //    gl.glBegin(GL.GL_LINE_STRIP);
    //    
    //    for (int k = 0; k <= 50; k++) {
    //      gl.glEvalCoord1f(k / 50f);
    //    }
    //    
    //    gl.glEnd();

    evaluateGrid(gl);
    drawControlPoints(gl);

    // ----------------------------------------

    gl.glFlush();
  }

  private void evaluateGrid(GL gl) {
    gl.glColor3f(1.0f, 1.0f, 1.0f);
    gl.glMap2d(GL.GL_MAP2_VERTEX_3, 0, 1, 3, uSize, 0, 1, uSize * 3, vSize, grid2x2, 0);
    gl.glEvalMesh2(GL.GL_LINE, 0, gridSize, 0, gridSize);
  }

  private void drawControlPoints(GL gl) {
    int i;

    gl.glColor3f(1.0f, 1.0f, 0.0f);
    gl.glPointSize(5.0f);

    gl.glBegin(GL.GL_POINTS);

    for (i = 0; i < uSize * vSize; i++) {
      gl.glVertex3dv(grid2x2, i * 3);
    }

    gl.glEnd();
  }

  // --------------------------------------------------------------------------------

  public void displayChanged(GLAutoDrawable drawable, //
      boolean modeChanged, boolean deviceChanged) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main().run();
  }
}
