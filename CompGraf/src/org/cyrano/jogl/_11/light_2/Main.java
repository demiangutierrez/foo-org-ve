package org.cyrano.jogl._11.light_2;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.cyrano.jogl.util.Matrix;
import org.cyrano.jogl.util.MatrixOps;

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

  private double frnX = 0.0f;
  private double frnY = 0.0f;
  private double frnZ = 1.0f;

  private double dist = 5;

  // --------------------------------------------------------------------------------
  // Camera movement
  // --------------------------------------------------------------------------------

  private float view_rotx = 0.0f;
  //private float view_roty = 0.0f; // CHECK

  private int prevMouseX;
  private int prevMouseY;

  // --------------------------------------------------------------------------------

  private GLCanvas glCanvas;

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseListener(this);
    drawable.addMouseMotionListener(this);
    drawable.addKeyListener(this);

    GL gl = drawable.getGL();

    //gl.glDisable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);

    gl.glEnable(GL.GL_LIGHTING);

    gl.glShadeModel(GL.GL_SMOOTH);

    gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, new float[]{0.0f, 0.0f, 0.0f, 0.0f}, 0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);

    //    gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, new float[]{1.0f, 1.0f, 1.0f, 0.0f}, 0);

//    gl.glLightModelf(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, GL.GL_TRUE);

//    gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF, 90);

    gl.glEnable(GL.GL_LIGHT0);
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

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    calculatePerspective(gl);

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    double frnDstX = frnX * dist;
    double frnDstY = frnY * dist;
    double frnDstZ = frnZ * dist;

    glu.gluLookAt(frnDstX, frnDstY, frnDstZ, 0, 0, 0, topX, topY, topZ);

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

    gl.glDisable(GL.GL_LIGHTING);
    drawAxes(gl);
    gl.glEnable(GL.GL_LIGHTING);

    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, //
        new float[]{4.0f, 4.0f, 4.0f, 1.0f}, 0);
//    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPOT_DIRECTION, new float[]{-2.0f, -2.0f, -2.0f, 0.0f}, 0);

    //    gl.glPushMatrix();
    //    gl.glTranslatef(2.5f, 2.5f, 2.5f);
    //    GLUquadric earth = glu.gluNewQuadric();
    //    glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
    //    glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
    //    glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
    //    final float radius = 0.1f;
    //    final int slices = 16;
    //    final int stacks = 16;
    //    glu.gluSphere(earth, radius, slices, stacks);
    //    glu.gluDeleteQuadric(earth);
    //    gl.glPopMatrix();

    //    gl.glBegin(GL.GL_QUADS);
    //    setMaterial(gl, 0.8f, 0.8f, 0.8f, 1);
    //    gl.glVertex3f(-3, -3, -3);
    //    gl.glVertex3f(-3, +3, -3);
    //    gl.glVertex3f(-3, +3, +3);
    //    gl.glVertex3f(-3, -3, +3);
    //
    //    gl.glVertex3f(-3, -3, -3);
    //    gl.glVertex3f(+3, -3, -3);
    //    gl.glVertex3f(+3, +3, -3);
    //    gl.glVertex3f(-3, +3, -3);
    //    
    //    gl.glVertex3f(-3, -3, -3);
    //    gl.glVertex3f(-3, -3, +3);
    //    gl.glVertex3f(+3, -3, +3);
    //    gl.glVertex3f(+3, -3, -3);
    //    
    //    gl.glEnd();

    // ----------------------------------------
    // avoid stitching!
    // ----------------------------------------

    gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
    gl.glPolygonOffset(1.0f, 1.0f);

    // ----------------------------------------

    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
//    gl.glColor3f(1, 0, 0); // RED
    setMaterial(gl, 1, 0, 0, 32);

    Icosahedron.drawIcosahedron(gl, 0);

    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
    //gl.glColor3f(1, 1, 1); // WHT
    setMaterial(gl, 1, 1, 1, 32);

//    Icosahedron.drawIcosahedron(gl, 1);

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  private void setMaterial(GL gl, float r, float g, float b, float s) {
    setMaterialProperty(gl, GL.GL_AMBIENT_AND_DIFFUSE, r / 1.2f, g / 1.2f, b / 1.2f);
    setMaterialProperty(gl, GL.GL_SPECULAR, r, g, b);
    //setMaterialProperty(gl, GL.GL_EMISSION, r / 2, g / 2, b / 2);
    setMaterialProperty(gl, GL.GL_EMISSION, 0, 0, 0);
    setMaterialProperty(gl, GL.GL_SHININESS, s);
    gl.glColor3f(r, g, b);
  }

  private void setMaterialProperty(GL gl, int property, float r, float g, float b) {
    gl.glMaterialfv(GL.GL_FRONT_AND_BACK, property, //
        new float[]{r, g, b, 1}, 0);
  }

  private void setMaterialProperty(GL gl, int property, float v) {
    gl.glMaterialf(GL.GL_FRONT_AND_BACK, property, v);
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

    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public void rotate(float thetaX, float thetaY) {
    Matrix curFrn = new Matrix(4, 1);
    curFrn.setData( //
        new double[]{frnX, frnY, frnZ, 1});

    Matrix curTop = new Matrix(4, 1);
    curTop.setData( //
        new double[]{topX, topY, topZ, 1});

    Matrix ry = MatrixOps.rotateY(thetaY);

    Matrix newFrn = MatrixOps.matrixMult(ry, curFrn);
    Matrix newTop = MatrixOps.matrixMult(ry, curTop);

    curFrn = newFrn;
    curTop = newTop;

    Matrix curSid = MatrixOps.crossProduct(curFrn, curTop);

    Matrix rs = MatrixOps.rotate( //
        -thetaX, curSid.get(0, 0), curSid.get(1, 0), curSid.get(2, 0));

    newFrn = MatrixOps.matrixMult(rs, curFrn);
    newTop = MatrixOps.matrixMult(rs, curTop);

    frnX = newFrn.get(0, 0);
    frnY = newFrn.get(1, 0);
    frnZ = newFrn.get(2, 0);

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

    glCanvas.repaint();
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
    Main m = new Main();
    m.glCanvas = canvas;
    canvas.addGLEventListener(m);

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
