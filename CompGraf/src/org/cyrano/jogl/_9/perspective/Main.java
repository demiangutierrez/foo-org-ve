package org.cyrano.jogl._9.perspective;

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
    // Empty
  }
  
  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    gl.glMatrixMode(GL.GL_PROJECTION);

    glu.gluLookAt(0.5, 0, -2, 0, 0, 0, 0, 1, 0);
    gl.glFrustum(-1, 1, -1, 1, -1, 1);

    gl.glMatrixMode(GL.GL_MODELVIEW);
    
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);

    gl.glColor3f(1.0f, 1.0f, 1.0f);
    gl.glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);

    gl.glBegin(GL.GL_POLYGON);
    gl.glVertex2f(-0.5f, -0.5f);
    gl.glVertex2f(-0.5f, 0.5f);
    gl.glVertex2f(0.5f, 0.5f);
    gl.glVertex2f(0.5f, -0.5f);
    gl.glEnd();
    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
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
