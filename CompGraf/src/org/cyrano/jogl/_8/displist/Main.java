package org.cyrano.jogl._8.displist;

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

  public static final int A_LIST_ID = 1;

  public void init(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glDisable(GL.GL_CULL_FACE);
    //gl.glEnable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);

    // ----------------------------------------
    // creates a call list (draws nothing)
    // ----------------------------------------

    gl.glNewList(A_LIST_ID, GL.GL_COMPILE);
    drawCube(gl);
    gl.glEndList();
  }

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

    GL gl = drawable.getGL();

    gl.glViewport(0, 0, w, h);

    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();
    gl.glFrustum(-1, +1, -1, +1, 1, 5); // (1)
    //gl.glFrustum(-1, +1, -1, +1, 1, 8); // (2)

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    glu.gluLookAt(0, 0, +5, 0, 0, 0, 0, 1, 0);

    //    +--+
    //    |ma|
    // +--+--+--+
    // |ce|ve|ro|
    // +--+--+--+
    //    |am|
    //    +--+
    //    |az|
    //    +--+
    //glu.gluLookAt(+3, +3, +3, 0, 0, 0, 0, 1, 0); // VE / MA / RO
    //glu.gluLookAt(-3, +3, +3, 0, 0, 0, 0, 1, 0); // CE / MA / VE
    //glu.gluLookAt(-3, -3, +3, 0, 0, 0, 0, 1, 0); // CE / AM / VE
    //glu.gluLookAt(+3, -3, +3, 0, 0, 0, 0, 1, 0); // VE / AM / RO

    //    +--+
    //    |ma|
    // +--+--+--+
    // |ro|az|ce|
    // +--+--+--+
    //    |am|
    //    +--+
    //    |ve|
    //    +--+
    //glu.gluLookAt(+3, +3, -3, 0, 0, 0, 0, 1, 0); // RO / MA / AZ
    //glu.gluLookAt(-3, +3, -3, 0, 0, 0, 0, 1, 0); // AZ / MA / CE
    //glu.gluLookAt(-3, -3, -3, 0, 0, 0, 0, 1, 0); // AZ / AM / CE
    //glu.gluLookAt(+3, -3, -3, 0, 0, 0, 0, 1, 0); // RO / AM / CE

    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        gl.glPushMatrix();
        gl.glTranslated(2 * x, 2 * y, +0f);
        gl.glCallList(A_LIST_ID);
        gl.glPopMatrix();
      }
    }

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  private void drawCube(GL gl) {
    gl.glPushMatrix();
    gl.glColor3f(0, 0, 1); // AZ
    gl.glRotatef(180f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(0, 1, 0); // VE
    gl.glRotatef(0f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(0, 1, 1); // CE
    gl.glRotatef(-90f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(1, 0, 0); // RO
    gl.glRotatef(90f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(1, 0, 1); // MA
    gl.glRotatef(-90f, 1, 0, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(1, 1, 0); // AM
    gl.glRotatef(90f, 1, 0, 0);
    drawFace(gl);
    gl.glPopMatrix();
  }

  // --------------------------------------------------------------------------------

  private void drawFace(GL gl) {
    gl.glBegin(GL.GL_QUADS);
    gl.glVertex3f(-0.5f, -0.5f, +0.5f);
    gl.glVertex3f(+0.5f, -0.5f, +0.5f);
    gl.glVertex3f(+0.5f, +0.5f, +0.5f);
    gl.glVertex3f(-0.5f, +0.5f, +0.5f);
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
