package org.cyrano.jogl._14.framebuffer_2;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.Camera;
import org.cyrano.jogl.base.Primitives;

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

    gl.glDisable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glEnable(GL.GL_STENCIL_TEST);
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
        /*   */GL.GL_DEPTH_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT);

    gl.glLoadIdentity();

    camera.updateCameraBox();
    camera.updateCameraPos();

    Primitives.drawAxes(gl);

    // ----------------------------------------
    // draw the stencil mask
    // ----------------------------------------

    gl.glColorMask(false, false, false, false);
    gl.glDisable(GL.GL_DEPTH_TEST);

    gl.glStencilFunc(GL.GL_ALWAYS, 1, 1);
    gl.glStencilOp(GL.GL_REPLACE, GL.GL_REPLACE, GL.GL_REPLACE);

    gl.glColor3f(1.0f, 1.0f, 1.0f);
    Primitives.drawRect(gl, 0.5f, 0.26f);

    gl.glColorMask(true, true, true, true);
    gl.glEnable(GL.GL_DEPTH_TEST);

    // ----------------------------------------
    // draw the stenciled (green) rect
    // ----------------------------------------

    gl.glStencilOp(GL.GL_KEEP, GL.GL_KEEP, GL.GL_KEEP);
    gl.glStencilFunc(GL.GL_NOTEQUAL, 1, 1);

    gl.glColor3f(0.0f, 1.0f, 0.0f);
    Primitives.drawRect(gl, 1.0f, +0.25f);

    // ----------------------------------------
    // draw the NON stenciled (red) rect
    // ----------------------------------------

    //gl.glClear(GL.GL_STENCIL_BUFFER_BIT); // or...
    gl.glStencilFunc(GL.GL_ALWAYS, 1, 1);

    gl.glColor3f(1.0f, 0.0f, 0.0f);
    Primitives.drawRect(gl, 1.0f, -0.25f);

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
