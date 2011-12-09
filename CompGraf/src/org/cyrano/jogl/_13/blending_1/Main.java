package org.cyrano.jogl._13.blending_1;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.Camera;
import org.cyrano.jogl.base.Primitives;
import org.cyrano.jogl.util.TextureCache;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample {

  private Camera camera = new Camera();

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseMotionListener(camera);
    drawable.addMouseListener/*  */(camera);
    drawable.addKeyListener/*    */(camera);

    GL gl = drawable.getGL();

    TextureCache.init("textures");

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

    Primitives.drawAxes(gl);

    // ----------------------------------------
    // setup blending
    // ----------------------------------------

    gl.glEnable(GL.GL_BLEND);
    gl.glBlendFunc( //
        GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

    // ----------------------------------------
    // swap and see results
    // ----------------------------------------

    gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
    Primitives.drawRect(gl, +0.5f, -0.25f);

    gl.glColor4f(1.0f, 1.0f, 0.5f, 0.5f);
    Primitives.drawRect(gl, +1.0f, +0.25f);

    // ----------------------------------------

    gl.glDisable(GL.GL_BLEND);

    // ----------------------------------------

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  public void displayChanged(GLAutoDrawable drawable, //
      boolean modeChanged, boolean deviceChanged) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main().run();
  }
}
