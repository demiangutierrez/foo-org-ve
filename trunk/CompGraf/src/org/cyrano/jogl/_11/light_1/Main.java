package org.cyrano.jogl._11.light_1;

import java.awt.event.KeyEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;
import org.cyrano.jogl.base.TestLights;
import org.cyrano.util.misc.MathUtil;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample {

  public boolean QUAD_NORMALS = true;

  // --------------------------------------------------------------------------------

  private TestLights testLights;

  // --------------------------------------------------------------------------------

  public Main() {
    initBaseExample(getClass().getName(), new CameraBall());
  }

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    super.init(drawable);

    testLights = new TestLights();
    testLights.createLight(drawable.getGL());
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    camera.updateCameraBox(getW(gl), getH(gl));
    camera.updateCameraPos();

    gl.glDisable(GL.GL_LIGHTING);
    Primitives.drawAxes(gl);
    gl.glEnable(GL.GL_LIGHTING);

    testLights.calculateSpin();

    testLights.drawAllLights(gl);

    // ----------------------------------------

    gl.glPushMatrix();
    testLights.setMaterial(gl);
    gl.glRotatef(180f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    testLights.setMaterial(gl);
    gl.glRotatef(0f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    testLights.setMaterial(gl);
    gl.glRotatef(-90f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    testLights.setMaterial(gl);
    gl.glRotatef(90f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    testLights.setMaterial(gl);
    gl.glRotatef(-90f, 1, 0, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    testLights.setMaterial(gl);
    gl.glRotatef(90f, 1, 0, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  private void drawFace(GL gl) {
    gl.glBegin(GL.GL_QUADS);

    // V1
    if (QUAD_NORMALS) {
      gl.glNormal3f(0, 0, 1);
    } else {
      double[] n = {-0.5f, -0.5f, +0.5f};
      n = MathUtil.nor(n);
      gl.glNormal3dv(n, 0);
    }

    gl.glVertex3f(-0.5f, -0.5f, +0.5f);

    // V2
    if (QUAD_NORMALS) {
      gl.glNormal3f(0, 0, 1);
    } else {
      double[] n = {-0.5f, -0.5f, +0.5f};
      n = MathUtil.nor(n);
      gl.glNormal3f(+0.5f, -0.5f, +0.5f);
    }

    gl.glVertex3f(+0.5f, -0.5f, +0.5f);

    // V3
    if (QUAD_NORMALS) {
      gl.glNormal3f(0, 0, 1);
    } else {
      gl.glNormal3f(+0.5f, +0.5f, +0.5f);
    }

    gl.glVertex3f(+0.5f, +0.5f, +0.5f);

    // V4
    if (QUAD_NORMALS) {
      gl.glNormal3f(0, 0, 1);
    } else {
      gl.glNormal3f(-0.5f, +0.5f, +0.5f);
    }

    gl.glVertex3f(-0.5f, +0.5f, +0.5f);

    gl.glEnd();
  }

  // --------------------------------------------------------------------------------
  // KeyListener
  // --------------------------------------------------------------------------------

  public void keyTyped(KeyEvent evt) {
    switch (evt.getKeyChar()) {
      case 'P' :
      case 'p' :
        testLights.switchAnimate();
        break;
    }

    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
