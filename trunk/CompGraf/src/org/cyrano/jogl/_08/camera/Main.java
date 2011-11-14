package org.cyrano.jogl._08.camera;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.cyrano.jogl.util.Matrix;
import org.cyrano.jogl.util.MatrixOps;

import com.sun.opengl.util.FPSAnimator;

/**
 * @author Demián Gutierrez
 */
public class Main implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

  // --------------------------------------------------------------------------------
  // Camera
  // --------------------------------------------------------------------------------

  private double topX = 0.0f;
  private double topY = 1.0f;
  private double topZ = 0.0f;

  private double eyeX = 0.0f;
  private double eyeY = 0.0f;
  private double eyeZ = 1.0f;

  private double dist = 5;

  // --------------------------------------------------------------------------------
  // Camera movement
  // --------------------------------------------------------------------------------

  private float view_rotx = 0.0f;
  //private float view_roty = 0.0f; // CHECK

  private int prevMouseX;
  private int prevMouseY;

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
    drawable.addMouseListener(this);
    drawable.addMouseMotionListener(this);
    drawable.addKeyListener(this);

    GL gl = drawable.getGL();

    gl.glDisable(GL.GL_CULL_FACE);
    //gl.glEnable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);

    Body cb1 = new Body();
    cb1.bR = 1.0f;
    cb1.bG = 0.5f;
    cb1.bB = 0.5f;
    cb1.x = 2;
    cb1.y = 2;
    cb1.z = 0;
    cb1.parentZRot = 45;
    cb1.parentYRot = 45;

    bodyList.add(cb1);

    Body cb2 = new Body();
    cb2.bR = 0.5f;
    cb2.bG = 1.0f;
    cb2.bB = 0.5f;
    cb2.parentDist = 3;
    cb2.slfRotVel = 0;
    cb2.scale = 0.5f;
    cb2.parRotVel = 10;

    cb2.parent = cb2;
    cb1.bodyList.add(cb2);

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
  }

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

    GL gl = drawable.getGL();

    gl.glViewport(0, 0, w, h);

    calculatePerspective(gl);
  }

  // --------------------------------------------------------------------------------

  private void calculatePerspective(GL gl) {
    GLU glu = new GLU();

    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();

    double nr = 0.5;
    double fr = 2 * (dist - nr) + nr;

    //System.err.println("nr: " + nr);
    //System.err.println("fr: " + fr);
    //System.err.println("dist: " + dist);

    glu.gluPerspective(90, 1, nr, fr);

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

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    GLU glu = new GLU();

    calculatePerspective(gl);

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    double eyeDstX = eyeX * dist;
    double eyeDstY = eyeY * dist;
    double eyeDstZ = eyeZ * dist;

    glu.gluLookAt(eyeDstX, eyeDstY, eyeDstZ, 0, 0, 0, topX, topY, topZ);

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
  // MouseListener
  // --------------------------------------------------------------------------------

  public void mouseEntered(MouseEvent e) {
    // Empty
  }

  public void mouseExited(MouseEvent e) {
    // Empty
  }

  public void mousePressed(MouseEvent e) {
    prevMouseX = e.getX();
    prevMouseY = e.getY();
  }

  public void mouseReleased(MouseEvent e) {
    // Empty
  }

  public void mouseClicked(MouseEvent e) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  public void mouseDragged(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    Dimension size = e.getComponent().getSize();

    float thetaY = 360.0f * ((float) (prevMouseX - x) / (float) size.width);
    float thetaX = 360.0f * ((float) (prevMouseY - y) / (float) size.height);

    prevMouseX = x;
    prevMouseY = y;

    // thetaX = 0; // FOR DEBUG

    if ((view_rotx - thetaX) > +60) {
      thetaX = view_rotx - 60;
    }

    if ((view_rotx - thetaX) < -60) {
      thetaX = view_rotx + 60;
    }

    view_rotx -= thetaX;
    //view_roty += thetaY;

    //System.err.println(thetaX + ";" + thetaY);

    rotate(thetaX, thetaY);
  }

  // --------------------------------------------------------------------------------

  public void rotate(float thetaX, float thetaY) {
    Matrix curEye = new Matrix(4, 1);
    curEye.setData( //
        new double[]{eyeX, eyeY, eyeZ, 1});

    Matrix curTop = new Matrix(4, 1);
    curTop.setData( //
        new double[]{topX, topY, topZ, 1});

    Matrix ry = MatrixOps.rotateY(thetaY);

    Matrix newEye = MatrixOps.matrixMult(ry, curEye);
    Matrix newTop = MatrixOps.matrixMult(ry, curTop);

    curEye = newEye;
    curTop = newTop;

    Matrix curSid = MatrixOps.crossProduct(curEye, curTop);

    Matrix rs = MatrixOps.rotate( //
        -thetaX, curSid.get(0, 0), curSid.get(1, 0), curSid.get(2, 0));

    newEye = MatrixOps.matrixMult(rs, curEye);
    newTop = MatrixOps.matrixMult(rs, curTop);

    eyeX = newEye.get(0, 0);
    eyeY = newEye.get(1, 0);
    eyeZ = newEye.get(2, 0);

    topX = newTop.get(0, 0);
    topY = newTop.get(1, 0);
    topZ = newTop.get(2, 0);
  }

  // --------------------------------------------------------------------------------

  public void mouseMoved(MouseEvent e) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // KeyListener
  // --------------------------------------------------------------------------------

  public void keyTyped(KeyEvent evt) {
    switch (evt.getKeyChar()) {
      case 'A' :
      case 'a' :
        if (dist <= 10) {
          dist *= 2;
        }
        break;
      case 'Z' :
      case 'z' :
        if (dist >= 5) {
          dist /= 2;
        }
        break;
    }
  }

  // --------------------------------------------------------------------------------

  public void keyPressed(KeyEvent e) {
    //  Empty    
  }

  // --------------------------------------------------------------------------------

  public void keyReleased(KeyEvent e) {
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
