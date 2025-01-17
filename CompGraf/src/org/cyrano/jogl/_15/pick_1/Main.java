package org.cyrano.jogl._15.pick_1;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.ByteBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample {

  // ----------------------------------------
  // set to true and see the results
  // ----------------------------------------
  private boolean draw = false;
  // ----------------------------------------

  private int xMousePick;
  private int yMousePick;

  private boolean pick;

  // --------------------------------------------------------------------------------

  public Main() {
    initBaseExample(getClass().getName(), new CameraBall());
  }

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    super.init(drawable);

    drawable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        Main.this.mouseClicked(evt);
      }
    });
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

    // ----------------------------------------
    // 1st pass
    // ----------------------------------------

    pickScene(gl);

    // ----------------------------------------
    // 2nd pass
    // ----------------------------------------

    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    Primitives.drawAxes(gl);

    drawScene(gl);

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  private void pickScene(GL gl) {
    if (!pick) {
      return;
    }

    drawScene(gl);

    System.err.println( //
        "picked ID is: " + getPickId(gl));

    pick = false;
  }

  // --------------------------------------------------------------------------------

  private void drawScene(GL gl) {
    setColorAndId(gl, 1.0f, 0.0f, 0.0f, (byte) 100);
    Primitives.drawRect(gl, +1.0f, -0.25f);

    setColorAndId(gl, 0.0f, 1.0f, 0.0f, (byte) 120);
    Primitives.drawRect(gl, +0.5f, +0.25f);
  }

  // --------------------------------------------------------------------------------

  private byte getPickId(GL gl) {
    int[] viewport = new int[4];

    gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

    ByteBuffer bb = ByteBuffer.allocate(4);

    gl.glReadPixels( //
        /*          */xMousePick, //
        viewport[3] - yMousePick, //
        1, 1, GL.GL_RGBA, GL.GL_BYTE, bb);

    return bb.get(0);
  }

  // --------------------------------------------------------------------------------

  private void setColorAndId(GL gl, float r, float g, float b, byte id) {
    if (pick || draw) {
      gl.glColor3b((byte) id, (byte) 0, (byte) 0);
    } else {
      gl.glColor3f(r, g, b);
    }
  }

  // --------------------------------------------------------------------------------

  private void mouseClicked(MouseEvent evt) {
    xMousePick = evt.getX();
    yMousePick = evt.getY();

    System.err.println(xMousePick + ";" + yMousePick);

    pick = true;

    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
