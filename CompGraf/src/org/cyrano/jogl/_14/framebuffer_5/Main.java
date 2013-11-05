package org.cyrano.jogl._14.framebuffer_5;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample implements KeyListener {

  private boolean drawDepthBuffer;

  // --------------------------------------------------------------------------------

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
    // swap and see results
    // ----------------------------------------

    gl.glColor3f(1.0f, 0.0f, 0.0f);
    Primitives.drawRect(gl, +1.0f, -0.25f);

    gl.glColor3f(0.0f, 1.0f, 0.0f);
    Primitives.drawRect(gl, +0.5f, +0.25f);

    // ----------------------------------------

    if (drawDepthBuffer) {
      FloatBuffer depthBufferData = //
      ByteBuffer.allocateDirect( //
          getW(gl) * getH(gl) * 4).asFloatBuffer();

      gl.glReadPixels(0, 0, getW(gl), getH(gl), //
          GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, depthBufferData);

      gl.glDrawPixels(getW(gl), getH(gl), //
          GL.GL_LUMINANCE, GL.GL_FLOAT, depthBufferData);
    }

    // ----------------------------------------

    gl.glFlush();
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

  public static void main(String[] args) {
    new Main();
  }
}
