package org.cyrano.jogl._15.pick_1;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.ByteBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.Camera;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample {

  private Camera camera = new CameraBall();

  // --------------------------------------------------------------------------------

  private int xMousePick;
  private int yMousePick;

  private boolean pick;

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseMotionListener(camera);
    drawable.addMouseListener/*  */(camera);
    drawable.addKeyListener/*    */(camera);

    drawable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        Main.this.mouseClicked(evt);
      }
    });

    GL gl = drawable.getGL();

    gl.glDisable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);
  }

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

    GL gl = drawable.getGL();
    gl.glViewport(0, 0, w, h);

    camera.updateCameraBox();
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    camera.updateCameraBox();
    camera.updateCameraPos();

    if (pick) {
      renderScene(gl);

      System.err.println("picked ID is: " + getPickId(gl));

      pick = false;
    }

    // ----------------------------------------

    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    Primitives.drawAxes(gl);

    renderScene(gl);

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  private void renderScene(GL gl) {
    glSetColorAndId(gl, 1.0f, 0.0f, 0.0f, (byte) 1);
    Primitives.drawRect(gl, +1.0f, -0.25f);

    glSetColorAndId(gl, 0.0f, 1.0f, 0.0f, (byte) 2);
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

    return bb.get(3);
  }

  // --------------------------------------------------------------------------------

  private void glSetColorAndId(GL gl, float r, float g, float b, byte id) {
    if (pick == true) {
      gl.glColor4b((byte) 0, (byte) 0, (byte) 0, id);
    } else {
      gl.glColor3f(r, g, b);
    }
  }

  // --------------------------------------------------------------------------------

  public void displayChanged(GLAutoDrawable drawable, //
      boolean modeChanged, boolean deviceChanged) {
    // Empty
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
    new Main().run();
  }
}
