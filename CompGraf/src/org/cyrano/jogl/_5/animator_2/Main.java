package org.cyrano.jogl._5.animator_2;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.FPSAnimator;

/**
 * @author Demián Gutierrez
 */
public class Main implements GLEventListener {

  private double degSec = 50;
  private double degAct = 0;

  private long prevTime;
  private long currTime;

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glDisable(GL.GL_CULL_FACE);
    //gl.glEnable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);

    CelestialBody cb1 = new CelestialBody();
    cb1.x = 0;
    cb1.y = 0;
    cb1.z = 0;

    celestialBodieList.add(cb1);

    CelestialBody cb2 = new CelestialBody();
    cb2.parentDist = 3;
    cb2.vel = 0;
    cb2.parent = cb2;
    cb2.scale = 0.5f;
    cb2.parentRVel = 10;
    cb1.children.add(cb2);


    CelestialBody cb3 = new CelestialBody();
    cb3.parentDist = 1;
    cb3.vel = 0;
    cb3.parent = cb2;
    cb3.scale = 0.2f;
    cb3.parentRVel = 70;
    cb2.children.add(cb3);

  }

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

    GL gl = drawable.getGL();

    gl.glViewport(0, 0, w, h);

    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();
    //gl.glFrustum(-1, +1, -1, +1, 1, 5); // (1)
    gl.glFrustum(-1, +1, -1, +1, 1, 10); // (2)

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  private float calculateDt() {
    currTime = System.currentTimeMillis();

    if (prevTime == 0) {
      prevTime = currTime;
    }

    return currTime - prevTime;
  }

  // --------------------------------------------------------------------------------

  List<CelestialBody> celestialBodieList = new ArrayList<CelestialBody>();

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    glu.gluLookAt(0, 0, +8, 0, 0, 0, 0, 1, 0);
    //glu.gluLookAt(0, 0, +5, 0, 0, 0, 0, 1, 0);

    //    +--+
    //    |ma|
    // +--+--+--+
    // |ce|ve|ro|
    // +--+--+--+
    //    |am|
    //    +--+
    //    |az|
    //    +--+
    //glu.gluLookAt(+5, +5, +5, 0, 0, 0, 0, 1, 0); // VE / MA / RO
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

    drawAxes(gl);

    float dt = calculateDt();

    for (CelestialBody celestialBody : celestialBodieList) {
      celestialBody.draw(gl, dt);
    }
  }

  // --------------------------------------------------------------------------------

  private void drawAxes(GL gl) {
    gl.glBegin(GL.GL_LINES);

    gl.glColor3f(1.0f, 0.5f, 0.5f);
    gl.glVertex3f(0f, 0f, 0f);
    gl.glVertex3f(5f, 0f, 0f);

    gl.glColor3f(0.5f, 1.0f, 0.5f);
    gl.glVertex3f(0f, 0f, 0f);
    gl.glVertex3f(0f, 5f, 0f);

    gl.glColor3f(0.5f, 0.5f, 1.0f);
    gl.glVertex3f(0f, 0f, 0f);
    gl.glVertex3f(0f, 0f, 5f);

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

    FPSAnimator animator = new FPSAnimator(canvas, 60);
    canvas.addGLEventListener(new Main());
    animator.start();

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
