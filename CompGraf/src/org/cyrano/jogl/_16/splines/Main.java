package org.cyrano.jogl._16.splines;

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

//    double ctrlpoints[][][] = {{{-1.5, -1.5, 4.0}, {-0.5, -1.5, 2.0}, {0.5, -1.5, -1.0}, {1.5, -1.5, 2.0}},
//        {{-1.5, -0.5, 1.0}, {-0.5, -0.5, 3.0}, {0.5, -0.5, 0.0}, {1.5, -0.5, -1.0}},
//        {{-1.5, 0.5, 4.0}, {-0.5, 0.5, 0.0}, {0.5, 0.5, 3.0}, {1.5, 0.5, 4.0}},
//        {{-1.5, 1.5, -2.0}, {-0.5, 1.5, -2.0}, {0.5, 1.5, 0.0}, {1.5, 1.5, -1.0}}};
    double ctrlpoints[] = {-1.5, -1.5, 4.0, -0.5, -1.5, 2.0, 0.5, -1.5, -1.0, 1.5, -1.5, 2.0,
        -1.5, -0.5, 1.0, -0.5, -0.5, 3.0, 0.5, -0.5, 0.0, 1.5, -0.5, -1.0,
        -1.5, 0.5, 4.0, -0.5, 0.5, 0.0, 0.5, 0.5, 3.0, 1.5, 0.5, 4.0,
        -1.5, 1.5, -2.0, -0.5, 1.5, -2.0, 0.5, 1.5, 0.0, 1.5, 1.5, -1.0};

    gl.glMap2d(GL.GL_MAP2_VERTEX_3, 0, 1, 3, 4,
        0, 1, 12, 4, ctrlpoints, 0);
gl.glEnable(GL.GL_MAP2_VERTEX_3);
gl.glMapGrid2d(20, 0.0, 1.0, 20, 0.0, 1.0);    
    
    for (int j = 0; j <= 8; j++) {
      gl.glBegin(GL.GL_LINE_STRIP);
      for (int i = 0; i <= 30; i++) {
        gl.glEvalCoord2d(i / 30.0, j / 8.0);
      }
      gl.glEnd();
      gl.glBegin(GL.GL_LINE_STRIP);
      for (int i = 0; i <= 30; i++) {
        gl.glEvalCoord2d(j / 8.0, i / 30.0);
      }
      gl.glEnd();
    }

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
    new Main().run();
  }
}
