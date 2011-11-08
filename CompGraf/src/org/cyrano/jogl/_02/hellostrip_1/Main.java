package org.cyrano.jogl._02.hellostrip_1;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

/**
 * @author Demián Gutierrez
 */
public class Main implements GLEventListener {

  public void init(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glDisable(GL.GL_CULL_FACE);
    //gl.glEnable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);
  }

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {
    GL gl = drawable.getGL();

    gl.glViewport(0, 0, w, h);

    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();
    gl.glOrtho(-4.0, 4.0, -4.0, 4.0, -1.0, 1.0);

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    // ----------------------------------------
    // avoid stitching!
    // ----------------------------------------

    gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
    gl.glPolygonOffset(1.0f, 1.0f);

    // ----------------------------------------

    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
    gl.glColor3f(1, 0, 0); // RED

    drawTriangles(gl);

    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
    gl.glColor3f(1, 1, 1); // WHT

    drawTriangles(gl);

    // ----------------------------------------
    // back to normal
    // ----------------------------------------

    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
    gl.glDisable(GL.GL_POLYGON_OFFSET_FILL);

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  public void drawTriangles(GL gl) {
    gl.glBegin(GL.GL_TRIANGLE_STRIP);

    gl.glVertex3f(-3, -1, 0);
    gl.glVertex3f(-2, +1, 0);
    gl.glVertex3f(-1, -1, 0);
    gl.glVertex3f(+0, +1, 0);
    gl.glVertex3f(+1, -1, 0);
    gl.glVertex3f(+2, +1, 0);
    gl.glVertex3f(+3, -1, 0);

    gl.glEnd();
  }

  // --------------------------------------------------------------------------------

  public void displayChanged(GLAutoDrawable drawable, //
      boolean modeChanged, boolean deviceChanged) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    Frame frame = new Frame("JOGL Main");

    GLCanvas canvas = new GLCanvas();
    canvas.addGLEventListener(new Main());

    frame.add(canvas);
    frame.setSize(300, 300);
    frame.setVisible(true);

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
