package org.cyrano.jogl.base;

import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import org.cyrano.util.geometry.Matrix;
import org.cyrano.util.geometry.MatrixOps;

public class Camera //
    implements
      MouseListener,
      MouseMotionListener,
      KeyListener {

  // --------------------------------------------------------------------------------
  // Camera
  // --------------------------------------------------------------------------------

  protected double topX = 0.0f;
  protected double topY = 1.0f;
  protected double topZ = 0.0f;

  protected double frnX = 0.0f;
  protected double frnY = 0.0f;
  protected double frnZ = 1.0f;

  protected double dist = 2;

  // --------------------------------------------------------------------------------
  // Camera movement
  // --------------------------------------------------------------------------------

  protected float view_rotx = 0.0f;
  //private float view_roty = 0.0f; // CHECK

  protected int prevMouseX;
  protected int prevMouseY;

  // --------------------------------------------------------------------------------

  public void updateCameraPos() {
    GLU glu = new GLU();

    double frnDstX = frnX * dist;
    double frnDstY = frnY * dist;
    double frnDstZ = frnZ * dist;

    glu.gluLookAt(frnDstX, frnDstY, frnDstZ, 0, 0, 0, topX, topY, topZ);
  }

  // --------------------------------------------------------------------------------

  public void updateCameraBox() {
    GL gl = GLU.getCurrentGL();

    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();

    double nr = 0.5;
    double fr = 2 * (dist - nr) + nr;

    //System.err.println("nr: " + nr);
    //System.err.println("fr: " + fr);
    //System.err.println("dist: " + dist);

    GLU glu = new GLU();
    glu.gluPerspective(90, 1, nr, fr);

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  protected void rotate(float thetaX, float thetaY) {
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
  // MouseListener
  // --------------------------------------------------------------------------------

  public void mouseEntered(MouseEvent evt) {
    // Empty
  }

  public void mouseExited(MouseEvent evt) {
    // Empty
  }

  public void mousePressed(MouseEvent evt) {
    if (evt.getButton() != MouseEvent.BUTTON1) {
      return;
    }

    prevMouseX = evt.getX();
    prevMouseY = evt.getY();
  }

  public void mouseReleased(MouseEvent evt) {
    // Empty
  }

  public void mouseClicked(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  public void mouseDragged(MouseEvent evt) {
    if (evt.getModifiersEx() != InputEvent.BUTTON1_DOWN_MASK) {
      return;
    }

    int x = evt.getX();
    int y = evt.getY();

    Dimension size = evt.getComponent().getSize();

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
        dist *= 2;
        break;
      case 'Z' :
      case 'z' :
        dist /= 2;
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
}
