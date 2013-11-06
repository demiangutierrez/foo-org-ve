package org.cyrano.jogl.base;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.media.opengl.glu.GLU;

import org.cyrano.util.geometry.Matrix;
import org.cyrano.util.geometry.MatrixOps;

/**
 * @author Demián Gutierrez
 */
public class CameraFrst implements Camera {

  private float view_rotx = 0.0f;
  //private float view_roty = 0.0f; // CHECK

  private int prevMouseX;
  private int prevMouseY;

  public enum Direction {
    FRN, BCK, LFT, RGH
  }

  // --------------------------------------------------------------------------------

  private double eyeX = +0;
  private double eyeY = +0;
  private double eyeZ = +0;

  private double frnX = +0;
  private double frnY = +0;
  private double frnZ = +1;

  private double topX = +0;
  private double topY = +1;
  private double topZ = +0;

  // --------------------------------------------------------------------------------

  public CameraFrst() {
    // Empty
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

  // --------------------------------------------------------------------------------

  private Matrix calculateSide() {
    Matrix curFrn = new Matrix(4, 1);
    curFrn.setData( //
        new double[]{frnX, frnY, frnZ, 1});

    Matrix curTop = new Matrix(4, 1);
    curTop.setData( //
        new double[]{topX, topY, topZ, 1});

    return MatrixOps.crossProduct(curFrn, curTop);
  }

  // --------------------------------------------------------------------------------

  public void move(Direction direction, double step) {
    switch (direction) {
      case FRN :
        moveFrnBck(+frnX, +frnZ, step);
        break;
      case BCK :
        moveFrnBck(-frnX, -frnZ, step);
        break;
      case LFT : {
        Matrix curSid = calculateSide();
        moveLftRgh(-curSid.get(0, 0), -curSid.get(2, 0), step);
        break;
      }
      case RGH : {
        Matrix curSid = calculateSide();
        moveLftRgh(+curSid.get(0, 0), +curSid.get(2, 0), step);
        break;
      }
      default :
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void moveLftRgh(double mx, double mz, double step) {
    double mod = Math.sqrt( //
        mx * mx + mz * mz);

    mx /= mod;
    mz /= mod;

    eyeX = eyeX + mx * step;
    eyeZ = eyeZ + mz * step;
  }

  private void moveFrnBck(double mx, double mz, double step) {
    double mod = Math.sqrt( //
        frnX * frnX + frnZ * frnZ);

    mx /= mod;
    mz /= mod;

    eyeX = eyeX + mx * step;
    eyeZ = eyeZ + mz * step;
  }

  // --------------------------------------------------------------------------------
  // MouseListener
  // --------------------------------------------------------------------------------

  @Override
  public void mouseClicked(MouseEvent evt) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mousePressed(MouseEvent evt) {
    prevMouseX = evt.getX();
    prevMouseY = evt.getY();
  }

  @Override
  public void mouseReleased(MouseEvent evt) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseEntered(MouseEvent evt) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseExited(MouseEvent evt) {
    // TODO Auto-generated method stub

  }

  // --------------------------------------------------------------------------------
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  @Override
  public void mouseDragged(MouseEvent evt) {
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

    rotate(thetaX, thetaY);
  }

  @Override
  public void mouseMoved(MouseEvent evt) {
  }

  // --------------------------------------------------------------------------------
  // KeyListener
  // --------------------------------------------------------------------------------

  @Override
  public void keyTyped(KeyEvent evt) {
    switch (evt.getKeyChar()) {
      case 'j' :
      case 'J' :
        move(Direction.LFT, 1);
        break;
      case 'l' :
      case 'L' :
        move(Direction.RGH, 1);
        break;
      case 'i' :
      case 'I' :
        move(Direction.FRN, 1);
        break;
      case 'k' :
      case 'K' :
        move(Direction.BCK, 1);
        break;
    }

    System.err.println(eyeX + ";" + eyeY + ";" + eyeZ);
  }

  @Override
  public void keyPressed(KeyEvent evt) {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyReleased(KeyEvent evt) {
    // TODO Auto-generated method stub

  }

  @Override
  public void drawCameraParameters(int x, int y) {
    // TODO Auto-generated method stub

  }

  @Override
  public void updateCameraBox(int w, int h) {
    GLU glu = new GLU();
    double p = w * 1 / h;
    glu.gluPerspective(35, p, 1, 3200);
  }

  @Override
  public void updateCameraPos() {
    Matrix trans = MatrixOps.translate(eyeX, eyeY, eyeZ);

    Matrix curFrn = new Matrix(4, 1);
    curFrn.setData( //
        new double[]{frnX, frnY, frnZ, 1});

    // ------------------------------------------------------------
    // BEWARE: top needs no translation
    //Matrix curTop = new Matrix(4, 1);
    //curTop.setData( //
    //    new double[]{camera.topX, camera.topY, camera.topZ, 1});
    // ------------------------------------------------------------

    Matrix traFrn = MatrixOps.matrixMult(trans, curFrn);
    // ------------------------------------------------------------
    //Matrix traTop = MatrixOps.matrixMult(trans, curTop);
    // ------------------------------------------------------------

    GLU glu = new GLU();

    System.err.println("lookAt: ");
    System.err.println(eyeX + ";" + eyeY + ";" + eyeZ);

    glu.gluLookAt(//
        eyeX, eyeY, eyeZ, //
        traFrn.get(0, 0), traFrn.get(1, 0), traFrn.get(2, 0), //
        topX, topY, topZ);
  }

  @Override
  public float[] getCameraEye() {
    return new float[]{(float) eyeX, (float) eyeY, (float) eyeZ};
  }

  @Override
  public float[] getCameraFrn() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public float[] getCameraTop() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public float[] getLookAt() {
    // TODO Auto-generated method stub
    return null;
  }
}
