package org.cyrano.jogl._14.viewport;

import java.awt.event.KeyListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample implements KeyListener {

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

    int w = getW(gl);
    int h = getH(gl);

    // ----------------------------------------
    // draw lft
    // ----------------------------------------

    gl.glViewport(0, 0, w / 2, h);

    Primitives.drawAxes(gl);

    gl.glColor3f(1.0f, 0.0f, 0.0f);
    Primitives.drawRect(gl, +1.0f, -0.25f);

    gl.glColor3f(0.0f, 1.0f, 0.0f);
    Primitives.drawRect(gl, +0.5f, +0.25f);

    // ----------------------------------------
    // draw rgh
    // ----------------------------------------

    gl.glViewport(w / 2, 0, w / 2, h);

    Primitives.drawAxes(gl);

    gl.glColor3f(0.0f, 1.0f, 1.0f);
    Primitives.drawRect(gl, +1.0f, -0.25f);

    gl.glColor3f(1.0f, 0.0f, 1.0f);
    Primitives.drawRect(gl, +0.5f, +0.25f);

    // ----------------------------------------
    // restore
    // ----------------------------------------

    gl.glViewport(0, 0, w, h);

    // ----------------------------------------

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
