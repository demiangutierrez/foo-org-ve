package org.cyrano.jogl._02.hellocube_1;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

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
    gl.glFrustum(-1, +1, -1, +1, 1, 5);

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    glu.gluLookAt(0, 0, +2, 0, 0, 0, 0, 1, 0);

    //    +--+
    //    |ma|
    // +--+--+--+
    // |ce|ve|ro|
    // +--+--+--+
    //    |am|
    //    +--+
    //    |az|
    //    +--+
    //glu.gluLookAt(+2, +2, +2, 0, 0, 0, 0, 1, 0); // VE / MA / RO
    //glu.gluLookAt(-2, +2, +2, 0, 0, 0, 0, 1, 0); // CE / MA / VE
    //glu.gluLookAt(-2, -2, +2, 0, 0, 0, 0, 1, 0); // CE / AM / VE
    //glu.gluLookAt(+2, -2, +2, 0, 0, 0, 0, 1, 0); // VE / AM / RO

    //    +--+
    //    |ma|
    // +--+--+--+
    // |ro|az|ce|
    // +--+--+--+
    //    |am|
    //    +--+
    //    |ve|
    //    +--+
    //glu.gluLookAt(+2, +2, -2, 0, 0, 0, 0, 1, 0); // RO / MA / AZ
    //glu.gluLookAt(-2, +2, -2, 0, 0, 0, 0, 1, 0); // AZ / MA / CE
    //glu.gluLookAt(-2, -2, -2, 0, 0, 0, 0, 1, 0); // AZ / AM / CE
    //glu.gluLookAt(+2, -2, -2, 0, 0, 0, 0, 1, 0); // RO / AM / CE

    gl.glBegin(GL.GL_QUADS);

    gl.glColor3f(0, 0, 1); // AZ
    gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glVertex3f(-0.5f, +0.5f, -0.5f);
    gl.glVertex3f(+0.5f, +0.5f, -0.5f);
    gl.glVertex3f(+0.5f, -0.5f, -0.5f);

    gl.glColor3f(0, 1, 0); // VE
    gl.glVertex3f(-0.5f, -0.5f, +0.5f);
    gl.glVertex3f(+0.5f, -0.5f, +0.5f);
    gl.glVertex3f(+0.5f, +0.5f, +0.5f);
    gl.glVertex3f(-0.5f, +0.5f, +0.5f);

    gl.glColor3f(0, 1, 1); // CE
    gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glVertex3f(-0.5f, -0.5f, +0.5f);
    gl.glVertex3f(-0.5f, +0.5f, +0.5f);
    gl.glVertex3f(-0.5f, +0.5f, -0.5f);

    gl.glColor3f(1, 0, 0); // RO
    gl.glVertex3f(+0.5f, -0.5f, -0.5f);
    gl.glVertex3f(+0.5f, +0.5f, -0.5f);
    gl.glVertex3f(+0.5f, +0.5f, +0.5f);
    gl.glVertex3f(+0.5f, -0.5f, +0.5f);

    gl.glColor3f(1, 0, 1); // MA
    gl.glVertex3f(-0.5f, +0.5f, -0.5f);
    gl.glVertex3f(-0.5f, +0.5f, +0.5f);
    gl.glVertex3f(+0.5f, +0.5f, +0.5f);
    gl.glVertex3f(+0.5f, +0.5f, -0.5f);

    gl.glColor3f(1, 1, 0); // AM
    gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glVertex3f(+0.5f, -0.5f, -0.5f);
    gl.glVertex3f(+0.5f, -0.5f, +0.5f);
    gl.glVertex3f(-0.5f, -0.5f, +0.5f);

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
