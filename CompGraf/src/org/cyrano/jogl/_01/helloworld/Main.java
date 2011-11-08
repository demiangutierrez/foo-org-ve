package org.cyrano.jogl._01.helloworld;

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
    gl.glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glColor3f(1f, 1f, 1f);

    gl.glBegin(GL.GL_POLYGON);
    //gl.glColor3f(1f, 1f, 1f);
    gl.glVertex3f(-0.5f, -0.5f, 0f);
    //gl.glColor3f(1f, 0f, 0f);
    gl.glVertex3f(-0.5f, +0.5f, 0f);
    //gl.glColor3f(0f, 1f, 0f);
    gl.glVertex3f(+0.5f, +0.5f, 0f);
    //gl.glColor3f(0f, 0f, 1f);
    gl.glVertex3f(+0.5f, -0.5f, 0f);
    gl.glEnd();

    gl.glFlush();
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
