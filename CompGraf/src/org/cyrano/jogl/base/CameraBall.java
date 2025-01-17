package org.cyrano.jogl.base;

import static org.cyrano.jogl.base.Pol2Cart.*;
import static org.cyrano.util.geometry.MatrixOps.*;

import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import java.text.NumberFormat;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import org.cyrano.util.geometry.Matrix;

import com.sun.opengl.util.GLUT;

/**
 * @author Demián Gutierrez
 */
public class CameraBall implements Camera {

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

  // used only to restrict rotation
  protected float view_rotx = 0.0f;
  //private float view_roty = 0.0f; // CHECK

  protected int prevMouseX;
  protected int prevMouseY;

  public enum AxisRotationMode {
    NORMAL, X_LOCKED, Y_LOCKED, Z_LOCKED;
  }

  private AxisRotationMode axisRotationMode = AxisRotationMode.NORMAL;

  // --------------------------------------------------------------------------------

  @Override
  public void updateCameraPos() {
    GLU glu = new GLU();

    double frnDstX = frnX * dist;
    double frnDstY = frnY * dist;
    double frnDstZ = frnZ * dist;

    glu.gluLookAt(frnDstX, frnDstY, frnDstZ, 0, 0, 0, topX, topY, topZ);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void updateCameraBox(int w, int h) {
    GL gl = GLU.getCurrentGL();

    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();

    double nr = 0.1;
    double fr = 4 * (dist - nr) + nr;

    //System.err.println("nr: " + nr);
    //System.err.println("fr: " + fr);
    //System.err.println("dist: " + dist);

    Math.log(1);

    GLU glu = new GLU();
    glu.gluPerspective(90, ((float) w) / h, nr, fr);

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  protected void rotateInternal(float thetaX, float thetaY) {
    Matrix curFrn = new Matrix(4, 1);
    curFrn.setData( //
        new double[]{frnX, frnY, frnZ, 1});

    Matrix curTop = new Matrix(4, 1);
    curTop.setData( //
        new double[]{topX, topY, topZ, 1});

    //    Matrix ry = rotate( //
    //        thetaY, curTop.get(0, 0), curTop.get(1, 0), curTop.get(2, 0));
    Matrix r;

    /*   */if (axisRotationMode == AxisRotationMode.X_LOCKED) {
      r = rotateX(thetaY);
    } else if (axisRotationMode == AxisRotationMode.Y_LOCKED) {
      r = rotateY(thetaY);
    } else if (axisRotationMode == AxisRotationMode.Z_LOCKED) {
      r = rotateZ(thetaY);
    } else { // NORMAL
      r = rotateY(thetaY);
    }

    Matrix newFrn = matrixMult(r, curFrn);
    Matrix newTop = matrixMult(r, curTop);

    if (axisRotationMode == AxisRotationMode.NORMAL) {
      curFrn = newFrn;
      curTop = newTop;

      Matrix curSid = crossProduct(curFrn, curTop);

      Matrix rs = rotate( //
          -thetaX, curSid.get(0, 0), curSid.get(1, 0), curSid.get(2, 0));

      newFrn = matrixMult(rs, curFrn);
      newTop = matrixMult(rs, curTop);
    }

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

  @Override
  public void mouseEntered(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseExited(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mousePressed(MouseEvent evt) {
    if (evt.getButton() != MouseEvent.BUTTON1) {
      return;
    }

    prevMouseX = evt.getX();
    prevMouseY = evt.getY();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseReleased(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseClicked(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  @Override
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

    rotateDelta(thetaX, thetaY);
  }

  // --------------------------------------------------------------------------------

  private void rotateDelta(float thetaX, float thetaY) {
    float angleLimit = 90;

    if ((view_rotx - thetaX) > +angleLimit) {
      thetaX = view_rotx - angleLimit;
    }

    if ((view_rotx - thetaX) < -angleLimit) {
      thetaX = view_rotx + angleLimit;
    }

    view_rotx -= thetaX;
    //view_roty += thetaY;

    //System.err.println(thetaX + ";" + thetaY);

    rotateInternal(thetaX, thetaY);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseMoved(MouseEvent e) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // KeyListener
  // --------------------------------------------------------------------------------

  @Override
  public void keyTyped(KeyEvent evt) {
    switch (evt.getKeyChar()) {
      case '-' :
      case 'A' :
      case 'a' :
        dist *= 1.1;
        break;
      case '+' :
      case 'S' :
      case 's' :
        dist /= 1.1;
        break;
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void keyPressed(KeyEvent evt) {
    switch (evt.getKeyCode()) {
      case KeyEvent.VK_RIGHT :
        rotateInternal(0, (float) (-90 / 20));
        break;
      case KeyEvent.VK_LEFT :
        rotateInternal(0, (float) (+90 / 20));
        break;
      case KeyEvent.VK_UP :
        rotateDelta((float) (-90 / 20), 0);
        break;
      case KeyEvent.VK_DOWN :
        rotateDelta((float) (+90 / 20), 0);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void keyReleased(KeyEvent e) {
    // Empty    
  }

  // --------------------------------------------------------------------------------

  public void setAxisRotationMode(AxisRotationMode axisRotationMode) {
    this.axisRotationMode = axisRotationMode;
  }

  // --------------------------------------------------------------------------------

  @Override
  public float[] getCameraEye() {
    float eyeDstX = (float) (frnX * dist);
    float eyeDstY = (float) (frnY * dist);
    float eyeDstZ = (float) (frnZ * dist);

    return new float[]{eyeDstX, eyeDstY, eyeDstZ};
  }

  // --------------------------------------------------------------------------------

  @Override
  public float[] getCameraFrn() {
    float frnDstX = (float) (-frnX);
    float frnDstY = (float) (-frnY);
    float frnDstZ = (float) (-frnZ);

    return new float[]{frnDstX, frnDstY, frnDstZ};
  }

  // --------------------------------------------------------------------------------

  @Override
  public float[] getCameraTop() {
    return new float[]{(float) topX, (float) topY, (float) topZ};
  }

  // --------------------------------------------------------------------------------

  @Override
  public float[] getLookAt() {
    throw new UnsupportedOperationException();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void drawCameraParameters(int x, int y) {
    GL gl = GLU.getCurrentGL();

    double frnDstX = frnX * dist;
    double frnDstY = frnY * dist;
    double frnDstZ = frnZ * dist;

    // -----------------------------------------------------
    // Compensates -90deg rot around X on main display 
    // -----------------------------------------------------
    double[] crt = new double[]{frnDstX, frnDstZ, -frnDstY};
    // -----------------------------------------------------

    double[] pol = Pol2Cart.crtToPol(crt);

    GLUT glut = new GLUT();

    gl.glColor3f(1.0f, 1.0f, 1.0f);

    int font = GLUT.BITMAP_8_BY_13;

    NumberFormat nf = NumberFormat.getInstance();
    nf.setMinimumFractionDigits(2);
    nf.setMaximumFractionDigits(2);

    String msgPol = "pol(A, E, R) [{0}, {1}, {2}]";
    String msgCrt = "crt(X, Y, Z) [{0}, {1}, {2}]";

    gl.glRasterPos2i(x, y);
    glut.glutBitmapString(font, //
        MessageFormat.format(msgPol, //
            nf.format(pol[AZ]), //
            nf.format(pol[EL]), //
            nf.format(pol[RD])));

    gl.glRasterPos2i(x, y + 21);
    glut.glutBitmapString(font, //
        MessageFormat.format(msgCrt, //
            nf.format(crt[X]), //
            nf.format(crt[Y]), //
            nf.format(crt[Z])));
  }
}
