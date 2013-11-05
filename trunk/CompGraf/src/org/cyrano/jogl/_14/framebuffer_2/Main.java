package org.cyrano.jogl._14.framebuffer_2;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample {

  public Main() {
    initBaseExample(getClass().getName(), new CameraBall());
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear( //
    /**/GL.GL_COLOR_BUFFER_BIT | //
        GL.GL_DEPTH_BUFFER_BIT | //
        GL.GL_STENCIL_BUFFER_BIT); // <-- Notice this

    gl.glLoadIdentity();

    camera.updateCameraBox(getW(gl), getH(gl));
    camera.updateCameraPos();

    Primitives.drawAxes(gl);

    // ----------------------------------------
    // draw the stencil mask
    // ----------------------------------------

    gl.glEnable(GL.GL_STENCIL_TEST);

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

    // gl.glClear(GL.GL_STENCIL_BUFFER_BIT); // or...
    gl.glStencilFunc(GL.GL_ALWAYS, 1, 1);

    gl.glColor3f(1.0f, 0.0f, 0.0f);
    Primitives.drawRect(gl, 1.0f, -0.25f);

    // ----------------------------------------

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
