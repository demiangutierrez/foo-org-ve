package org.cyrano.jogl._07.objects;

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

  // --------------------------------------------------------------------------------
  // Animation
  // --------------------------------------------------------------------------------

  private long prevTime;
  private long currTime;

  // --------------------------------------------------------------------------------
  // Objects
  // --------------------------------------------------------------------------------

  List<Body> bodyList = new ArrayList<Body>();

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    //gl.glEnable(GL.GL_CULL_FACE);
    gl.glDisable(GL.GL_CULL_FACE);

    //gl.glShadeModel(GL.GL_FLAT);
    gl.glShadeModel(GL.GL_SMOOTH);

    gl.glEnable(GL.GL_DEPTH_TEST);

    // ----------------------------------------

    Body cb1 = new Body();
    cb1.bR = 1.0f;
    cb1.bG = 0.5f;
    cb1.bB = 0.5f;
    cb1.x = 2;
    cb1.y = 2;
    cb1.z = 0;
    cb1.parentZRot = 45;
    cb1.parentYRot = 45;
    cb1.scale = 1.0f;

    cb1.parent = null;
    bodyList.add(cb1);

    // ----------------------------------------

    Body cb2 = new Body();
    cb2.bR = 0.5f;
    cb2.bG = 1.0f;
    cb2.bB = 0.5f;
    cb2.parentDist = 3;
    cb2.slfRotVel = 0;
    cb2.parRotVel = 10;
    cb2.scale = 0.5f;

    cb2.parent = cb1;
    cb1.bodyList.add(cb2);

    // ----------------------------------------

    Body cb3 = new Body();
    cb3.bR = 0.5f;
    cb3.bG = 0.5f;
    cb3.bB = 1.0f;
    cb3.parentDist = 1;
    cb3.slfRotVel = 0;
    cb3.scale = 0.2f;
    cb3.parRotVel = 70;

    cb3.parent = cb2;
    cb2.bodyList.add(cb3);

    Body cb4 = new Body();
    cb4.bR = 0.5f;
    cb4.bG = 1.0f;
    cb4.bB = 1.0f;
    cb4.parentDist = 2f;

    //    cb4.x = -2;
    //    cb4.y = -2;
    //    cb4.z = 1;
    cb4.scale = 1.1f;
    cb4.parRotVel = 40;
    cb4.parentZRot = 0;
    cb4.parentYRot = 0;
    cb4.parent = cb1;
    cb1.bodyList.add(cb4);

    //bodyList.add(cb4);

    Body cb5 = new Body();
    cb5.bR = 1.0f;
    cb5.bG = 1.0f;
    cb5.bB = 0.5f;
    cb5.parentDist = 2;
    cb5.slfRotVel = 0;
    cb5.scale = 0.4f;
    cb5.parRotVel = 30;

    cb5.parent = cb4;
    cb4.bodyList.add(cb5);

    Body cb6 = new Body();
    cb6.bR = 1.0f;
    cb6.bG = 0.5f;
    cb6.bB = 1.0f;
    cb6.parentDist = 0.5f;
    cb6.slfRotVel = 0;
    cb6.scale = 0.3f;
    cb6.parRotVel = 100;

    cb6.parent = cb5;
    cb5.bodyList.add(cb6);
  }

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

    GL gl = drawable.getGL();

    gl.glViewport(0, 0, w, h);

    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();

    gl.glFrustum(-2, +2, -2, +2, 2, 10);

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  private float calculateDt() {
    // TODO: arreglar

    currTime = System.currentTimeMillis();

    if (prevTime == 0) {
      prevTime = currTime;
    }

    return currTime - prevTime;
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    GLU glu = new GLU();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    glu.gluLookAt(0, 0, +5, 0, 0, 0, 0, 1, 0);

    drawAxes(gl);

    float dt = calculateDt();

    for (Body body : bodyList) {
      body.draw(gl, dt);
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
