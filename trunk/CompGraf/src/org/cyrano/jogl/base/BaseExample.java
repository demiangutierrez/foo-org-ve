package org.cyrano.jogl.base;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;

import com.sun.opengl.util.FPSAnimator;

public abstract class BaseExample implements GLEventListener {

  private static final int W = 500;
  private static final int H = 500;

  // --------------------------------------------------------------------------------

  protected int getW(GL gl) {
    int[] iv = new int[4];

    gl.glGetIntegerv(GL.GL_VIEWPORT, iv, 0);

    return iv[2];
  }

  // --------------------------------------------------------------------------------

  protected int getH(GL gl) {
    int[] iv = new int[4];

    gl.glGetIntegerv(GL.GL_VIEWPORT, iv, 0);

    return iv[3];
  }

  // --------------------------------------------------------------------------------

  public void run() {
    Frame frame = new Frame("JOGL example");

    GLCapabilities glCapabilities = new GLCapabilities();
    glCapabilities.setStencilBits(8);
    GLCanvas canvas = new GLCanvas(glCapabilities);

    FPSAnimator animator = new FPSAnimator(canvas, 60);
    canvas.addGLEventListener(this);
    animator.start();

    frame.add(canvas);
    frame.setSize(W, H);
    frame.setVisible(true);

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
