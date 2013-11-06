package org.cyrano.jogl.base;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.swing.JFrame;

import com.sun.opengl.util.FPSAnimator;

/**
 * @author Demián Gutierrez
 */
public abstract class BaseExample extends JFrame //
    implements
      GLEventListener,
      KeyListener {

  public static final int MAX_FPS = 60;

  public static final int W = 640;
  public static final int H = 480;

  // --------------------------------------------------------------------------------

  protected GLCanvas glCanvas;

  protected Camera camera;

  // --------------------------------------------------------------------------------

  public BaseExample() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void initBaseExample(String title, Camera camera) {
    this.camera = camera;

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setTitle(title);

    setSize(W, H);

    GLCapabilities glCapabilities = new GLCapabilities();
    glCapabilities.setStencilBits(8);
    glCapabilities.setDepthBits(32);

    glCanvas = new GLCanvas(glCapabilities);
    glCanvas.addGLEventListener(this);

    add(glCanvas, BorderLayout.CENTER);

    FPSAnimator fpsAnimator = //
    new FPSAnimator(glCanvas, MAX_FPS);

    fpsAnimator.start();

    setVisible(true);
  }

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

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

    GL gl = drawable.getGL();
    gl.glViewport(0, 0, w, h);

    camera.updateCameraBox(w, h);
  }

  // --------------------------------------------------------------------------------

  public void displayChanged(GLAutoDrawable drawable, //
      boolean modeChanged, boolean deviceChanged) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseMotionListener(camera);
    drawable.addMouseListener/*  */(camera);
    drawable.addKeyListener/*    */(camera);
    drawable.addKeyListener/*    */(this);

    GL gl = drawable.getGL();

    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDisable(GL.GL_CULL_FACE);

    gl.glShadeModel(GL.GL_SMOOTH);

    gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
    gl.glPolygonOffset(1.0f, 1.0f);
  }

  // --------------------------------------------------------------------------------
  // KeyListener
  // --------------------------------------------------------------------------------

  public void keyTyped(KeyEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void keyPressed(KeyEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void keyReleased(KeyEvent evt) {
    // Empty    
  }
}
