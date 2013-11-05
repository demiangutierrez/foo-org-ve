package org.cyrano.jogl._14.framebuffer_1;

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
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    camera.updateCameraBox(getW(gl), getH(gl));
    camera.updateCameraPos();

    Primitives.drawAxes(gl);

    // ----------------------------------------
    // uncomment this and see the results
    // ----------------------------------------
    // gl.glDisable(GL.GL_DEPTH_TEST);
    // ----------------------------------------

    // ----------------------------------------
    // swap and see results
    // ----------------------------------------

    gl.glColor3f(1.0f, 0.0f, 0.0f);
    Primitives.drawRect(gl, +1.0f, -0.25f);

    gl.glColor3f(0.0f, 1.0f, 0.0f);
    Primitives.drawRect(gl, +0.5f, +0.25f);

    // ----------------------------------------

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
