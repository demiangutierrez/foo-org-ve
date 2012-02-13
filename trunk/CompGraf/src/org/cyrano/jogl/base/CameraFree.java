package org.cyrano.jogl.base;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import org.cyrano.util.geometry.Matrix;
import org.cyrano.util.geometry.MatrixOps;

/**
 * @author Demián Gutierrez
 */
public class CameraFree implements Camera {

  public enum Direction {
    FRN, BCK, LFT, RGH
  }

  private double dist = 2;

  private float view_rotx = 0.0f;
  //private float view_roty = 0.0f; // CHECK

  private int prevMouseX;
  private int prevMouseY;

  // --------------------------------------------------------------------------------

  public double eyeX = +0;
  public double eyeY = +0;
  public double eyeZ = +0;

  private double frnX = +0;
  private double frnY = +0;
  private double frnZ = +1;

  private double topX = +0;
  private double topY = +1;
  private double topZ = +0;

  // --------------------------------------------------------------------------------

  public CameraFree() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void updateCameraPos() {
    GLU glu = new GLU();

    double[] lookAt = getLookAt();

    glu.gluLookAt( //
        lookAt[0], lookAt[1], lookAt[2], //
        lookAt[3], lookAt[4], lookAt[5], //
        lookAt[6], lookAt[7], lookAt[8]);
  }

  // --------------------------------------------------------------------------------

  public double[] getLookAt() {
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

    return new double[]{ //
    /**/eyeX,/*         */eyeY,/*         */eyeZ, //
        traFrn.get(0, 0), traFrn.get(1, 0), traFrn.get(2, 0), //
        topX,/*         */topY,/*         */topZ};
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

  //  public void update() {
  //    GLU glu = new GLU();
  //
  //    Matrix trans = MatrixOps.translate(eyeX, eyeY, eyeZ);
  //
  //    Matrix curFrn = new Matrix(4, 1);
  //    curFrn.setData( //
  //        new double[]{frnX, frnY, frnZ, 1});
  //
  //    // ------------------------------------------------------------
  //    // BEWARE: top needs no translation
  //    //Matrix curTop = new Matrix(4, 1);
  //    //curTop.setData( //
  //    //    new double[]{camera.topX, camera.topY, camera.topZ, 1});
  //    // ------------------------------------------------------------
  //
  //    Matrix traFrn = MatrixOps.matrixMult(trans, curFrn);
  //    // ------------------------------------------------------------
  //    //Matrix traTop = MatrixOps.matrixMult(trans, curTop);
  //    // ------------------------------------------------------------
  //
  //    glu.gluLookAt(//
  //        eyeX, eyeY, eyeZ, //
  //        traFrn.get(0, 0), traFrn.get(1, 0), traFrn.get(2, 0), //
  //        topX, topY, topZ);
  //  }

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
        moveFrnBck(+frnX, +frnY, +frnZ, step);
        break;
      case BCK :
        moveFrnBck(-frnX, -frnY, -frnZ, step);
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

  //  private void moveFrnBck(double mx, double mz, double step) {
  //    double mod = Math.sqrt( //
  //        frnX * frnX + frnZ * frnZ);
  //
  //    mx /= mod;
  //    mz /= mod;
  //
  //    eyeX = eyeX + mx * step;
  //    eyeZ = eyeZ + mz * step;
  //  }

  private void moveFrnBck(double mx, double my, double mz, double step) {
    double mod = Math.sqrt( //
        frnX * frnX + frnZ * frnZ);

    mx /= mod;
    my /= mod;
    mz /= mod;

    eyeX = eyeX + mx * step;
    eyeY = eyeY + my * step;
    eyeZ = eyeZ + mz * step;
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
      case 'j' :
        move(Direction.LFT, 0.1);
        break;
      case 'l' :
        move(Direction.RGH, 0.1);
        break;
      case 'i' :
        move(Direction.FRN, 0.1);
        break;
      case 'k' :
        move(Direction.BCK, 0.1);
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
