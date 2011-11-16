package org.cyrano.jogl._20.skybox;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

/**
 * @author Demián Gutierrez
 */
public class Main1 implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

  private float view_rotx = 0.0f, view_roty = 0.0f, view_rotz = 0.0f;
  private float view_zoom = 1;

  private int prevMouseX, prevMouseY;

  private GLU glu = new GLU();

  private Texture skybox_u;
  private Texture skybox_d;
  private Texture skybox_n;
  private Texture skybox_s;
  private Texture skybox_e;
  private Texture skybox_w;

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseListener(this);
    drawable.addMouseMotionListener(this);
    drawable.addKeyListener(this);

    GL gl = drawable.getGL();

    gl.glEnable(GL.GL_DEPTH_TEST);

    gl.glEnable(GL.GL_BLEND);
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

    try {
      InputStream is;
      TextureData textureData;

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_u.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_u = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_d.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_d = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_n.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_n = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_s.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_s = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_e.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_e = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_w.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_w = TextureIO.newTexture(textureData);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();

    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    glu.gluPerspective(35, 1, 1, 3200);
  }

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    if ((drawable instanceof GLJPanel) && !((GLJPanel) drawable).isOpaque()
        && ((GLJPanel) drawable).shouldPreserveColorBufferIfTranslucent()) {
      gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
    } else {
      gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }

    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glPushMatrix();
    gl.glLoadIdentity();

    glu.gluLookAt(0, 0, 2 * view_zoom, 0, 0, 1, 0, 1, 0);

    gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(view_rotz, 0.0f, 0.0f, 1.0f);

    double size = 2;

    // ----------------------------
    // D+-----+C  ** ^ Y (0, 1)
    //  |     |   ** |+--+
    //  |     |   ** ||  | TEXTURES
    //  |     |   ** |+--+
    // A+-----+B  ** *----->X (0,1)
    // ----------------------------

    // ---------------------------------------------------
    // culling is done ccw (right hand rule defines front)
    // ---------------------------------------------------
    gl.glEnable(GL.GL_CULL_FACE);
    // ----------------------------------------------------

    gl.glEnable(GL.GL_TEXTURE_2D);

    skybox_n.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * size, -1 * size, -1 * size); // A
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * size, -1 * size, -1 * size); // B
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * size, +1 * size, -1 * size); // C
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * size, +1 * size, -1 * size); // D
    gl.glEnd();

    skybox_s.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(+1 * size, -1 * size, +1 * size); // A
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(-1 * size, -1 * size, +1 * size); // B
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(-1 * size, +1 * size, +1 * size); // C
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(+1 * size, +1 * size, +1 * size); // D
    gl.glEnd();

    skybox_e.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(+1 * size, -1 * size, -1 * size); // A
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * size, -1 * size, +1 * size); // B
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * size, +1 * size, +1 * size); // C
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(+1 * size, +1 * size, -1 * size); // D
    gl.glEnd();

    skybox_w.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * size, -1 * size, +1 * size); // A
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(-1 * size, -1 * size, -1 * size); // B
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(-1 * size, +1 * size, -1 * size); // C
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * size, +1 * size, +1 * size); // D
    gl.glEnd();

    skybox_d.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * size, -1 * size, +1 * size); // A
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * size, -1 * size, +1 * size); // B
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * size, -1 * size, -1 * size); // C
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * size, -1 * size, -1 * size); // D
    gl.glEnd();

    skybox_u.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * size, +1 * size, -1 * size); // C
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * size, +1 * size, -1 * size); // D
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * size, +1 * size, +1 * size); // A
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * size, +1 * size, +1 * size); // B
    gl.glEnd();
  }

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
  }

  public void mouseEntered(MouseEvent e) {
    // Empty
  }

  public void mouseExited(MouseEvent e) {
    // Empty
  }

  public void mousePressed(MouseEvent e) {
    prevMouseX = e.getX();
    prevMouseY = e.getY();
  }

  public void mouseReleased(MouseEvent e) {
    // Empty
  }

  public void mouseClicked(MouseEvent e) {
    // Empty
  }

  public void mouseDragged(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    Dimension size = e.getComponent().getSize();

    float thetaY = 360.0f * ((float) (x - prevMouseX) / (float) size.width);
    float thetaX = 360.0f * ((float) (prevMouseY - y) / (float) size.height);

    prevMouseX = x;
    prevMouseY = y;

    view_rotx -= thetaX;
    view_roty += thetaY;
  }

  public void mouseMoved(MouseEvent e) {
    // Empty
  }

  public void keyTyped(KeyEvent e) {
    switch (e.getKeyChar()) {
      case KeyEvent.VK_Z :
        view_zoom *= 2;
        System.err.println("Zoom: " + view_zoom);
        break;
      case KeyEvent.VK_A :
        System.err.println("Zoom: " + view_zoom);
        view_zoom /= 2;
        break;
      case KeyEvent.VK_R :
        System.err.println("r");
        //reload();
        break;
    }
  }

  public void keyPressed(KeyEvent e) {
    //  Empty    
  }

  public void keyReleased(KeyEvent e) {
    // Empty    
  }

  public static void main(String[] args) {
    final Frame frame = new Frame("Lego Demo");
    final GLCanvas canvas = new GLCanvas();
    final Animator animator = new Animator(canvas);

    // TODO: Put in fs mode
    canvas.addGLEventListener(new Main1());
    frame.add(canvas);
    frame.setSize(900, 900);
    frame.addWindowListener(new WindowAdapter() {

      public void windowClosing(WindowEvent e) {
        new Thread(new Runnable() {

          public void run() {
            animator.stop();
            System.exit(0);
          }
        }).start();
      }
    });

    frame.setVisible(true);
    animator.start();
  }
}
