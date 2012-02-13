package org.cyrano.jogl._14.viewport;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.Camera;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;
import org.cyrano.jogl.util.TextureCache;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample implements KeyListener {

  private Camera camera = new CameraBall();

  private boolean drawDepthBuffer;

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseMotionListener(camera);
    drawable.addMouseListener/*  */(camera);
    drawable.addKeyListener/*    */(camera);
    drawable.addKeyListener/*    */(this);

    GL gl = drawable.getGL();

    TextureCache.init("textures");

    gl.glDisable(GL.GL_CULL_FACE);

    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthRange(0, 1);
  }

  // --------------------------------------------------------------------------------

  int w;
  int h;

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

    this.w = w;
    this.h = h;

    camera.updateCameraBox();
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glViewport(0, 0, w / 2, h);

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    camera.updateCameraBox();
    camera.updateCameraPos();

    Primitives.drawAxes(gl);

    // ----------------------------------------
    // swap and see results
    // ----------------------------------------

    gl.glColor3f(1.0f, 0.0f, 0.0f);
    Primitives.drawRect(gl, +1.0f, -0.25f);

    gl.glColor3f(0.0f, 1.0f, 0.0f);
    Primitives.drawRect(gl, +0.5f, +0.25f);

    // ----------------------------------------

    gl.glViewport(w / 2, 0, w / 2, h);

    //    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    //    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
    //        /*   */GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    camera.updateCameraBox();
    camera.updateCameraPos();

    Primitives.drawAxes(gl);

    // ----------------------------------------
    // swap and see results
    // ----------------------------------------

    gl.glColor3f(1.0f, 0.0f, 0.0f);
    Primitives.drawRect(gl, +1.0f, -0.25f);

    gl.glColor3f(0.0f, 1.0f, 0.0f);
    Primitives.drawRect(gl, +0.5f, +0.25f);

    //    if (drawDepthBuffer) {
    FloatBuffer depthBufferData = //
    ByteBuffer.allocateDirect( //
        getW(gl) * getH(gl) * 4).asFloatBuffer();

    gl.glReadPixels(0, 0, getW(gl), getH(gl), //
        GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, depthBufferData);

    gl.glDrawPixels(getW(gl), getH(gl), //
        GL.GL_LUMINANCE, GL.GL_FLOAT, depthBufferData);
    //    }

    // ----------------------------------------

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  public void displayChanged(GLAutoDrawable drawable, //
      boolean modeChanged, boolean deviceChanged) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // KeyListener
  // --------------------------------------------------------------------------------

  public void keyTyped(KeyEvent evt) {
    switch (evt.getKeyChar()) {
      case 'D' :
      case 'd' :
        drawDepthBuffer = !drawDepthBuffer;
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
    new Main().run();
  }
}
