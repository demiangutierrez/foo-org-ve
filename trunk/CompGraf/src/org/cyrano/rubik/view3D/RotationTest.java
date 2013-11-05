package org.cyrano.rubik.view3D;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.event.EventListenerList;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.GLMouseListenerProxy;
import org.cyrano.jogl.base.GLMouseMotionListenerProxy;
import org.cyrano.jogl.base.Primitives;

/**
 * @author Demián Gutierrez
 */
public class RotationTest extends BaseExample //
    implements
      KeyListener,
      MouseListener,
      MouseMotionListener {

  // --------------------------------------------------------------------------------

  private EventListenerList eventListenerList = //
  new EventListenerList();

  private GLMouseMotionListenerProxy mouseMotionListenerProxy;

  private GLMouseListenerProxy mouseListenerProxy;

  // --------------------------------------------------------------------------------

  public RotationTest() {
    initBaseExample(getClass().getName(), new CameraBall());
  }

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    super.init(drawable);

    mouseMotionListenerProxy = //
    new GLMouseMotionListenerProxy(eventListenerList, glCanvas);

    mouseListenerProxy = //
    new GLMouseListenerProxy(eventListenerList, glCanvas);

    // ----------------------------------------

    drawable.addMouseMotionListener(mouseMotionListenerProxy);
    drawable.addMouseListener/*  */(mouseListenerProxy);

    mouseMotionListenerProxy.addMouseMotionListener(this);
    mouseListenerProxy.addMouseListener(this);
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glLoadIdentity();

    camera.updateCameraBox(getW(gl), getH(gl));
    camera.updateCameraPos();

    mouseMotionListenerProxy.fireQueue();
    mouseListenerProxy.fireQueue();

    // ----------------------------------------

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    Primitives.drawAxes(gl);

    gl.glPushMatrix();
    gl.glTranslated(-1, -1, -1);
    drawCube(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glRotated(90, 1, 0, 0);
    gl.glTranslated(-1, -1, -1);
    drawCube(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glRotated(90, 0, 1, 0);
    gl.glRotated(90, 1, 0, 0);
    gl.glTranslated(-1, -1, -1);
    drawCube(gl);
    gl.glPopMatrix();

//    gl.glPushMatrix();
//    gl.glRotated(90, 0, 0, 1);
//    gl.glRotated(90, 0, 1, 0);
//    gl.glRotated(90, 1, 0, 0);
//    gl.glTranslated(-1, -1, -1);
//    drawCube(gl);
//    gl.glPopMatrix();

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
  // MouseListener
  // --------------------------------------------------------------------------------

  public void mouseReleased(MouseEvent evt) {
    //    pick = false;
    //    drag = false;
  }

  // --------------------------------------------------------------------------------

  public void mousePressed(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void mouseClicked(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void mouseEntered(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void mouseExited(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  public void mouseDragged(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void mouseMoved(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new RotationTest();
  }
}
