package org.cyrano.jogl.camerafp;

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

import org.cyrano.jogl.camerafp.Camera.Direction;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

/**
 * @author Demián Gutierrez
 */
public class Main implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

  private float view_rotx = 0.0f, view_roty = 0.0f;

  private int prevMouseX, prevMouseY;

  private GLU glu = new GLU();

  private Texture floor1;
  private Texture floor2;

  private Camera camera;

  // --------------------------------------------------------------------------------
  // GLEventListener
  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseListener(this);
    drawable.addMouseMotionListener(this);
    drawable.addKeyListener(this);

    GL gl = drawable.getGL();

    gl.glEnable(GL.GL_BLEND);
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

    camera = new Camera();

    try {
      InputStream is;
      TextureData textureData;

      is = ClassLoader.getSystemResourceAsStream("textures/floor1.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      floor1 = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/floor2.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      floor2 = TextureIO.newTexture(textureData);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    GL gl = drawable.getGL();

    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    // h -> 1
    // w -> p
    double p = w * 1 / h;

    glu.gluPerspective(35, p, 1, 3200);
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    if ((drawable instanceof GLJPanel) && !((GLJPanel) drawable).isOpaque()
        && ((GLJPanel) drawable).shouldPreserveColorBufferIfTranslucent()) {
      gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
    } else {
      gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }

    gl.glPushMatrix();
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();

    camera.update();
    camera.drawSkybox();

    double tileSize = +1.5;
    double tileYOff = -1.2;

    int floorWH = 80;

    gl.glEnable(GL.GL_TEXTURE_2D);
    gl.glEnable(GL.GL_DEPTH_TEST);

    // ----------------------------
    // D+-----+C  ** ^ Y (0, 1)
    //  |     |   ** |+--+
    //  |     |   ** ||  | TEXTURES
    //  |     |   ** |+--+
    // A+-----+B  ** *----->X (0,1)
    // ----------------------------

    Texture curr = floor1;

    for (int i = 0; i < floorWH; i++) {
      for (int j = 0; j < floorWH; j++) {
        double x = j * tileSize - tileSize * floorWH / 2;
        double y = i * tileSize - tileSize * floorWH / 2;

        curr.bind();

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0f, 0f);
        gl.glVertex3d(x, tileYOff, y); // C
        gl.glTexCoord2f(1f, 0f);
        gl.glVertex3d(x, tileYOff, y + tileSize); // D
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3d(x + tileSize, tileYOff, y + tileSize); // A
        gl.glTexCoord2f(0f, 1f);
        gl.glVertex3d(x + tileSize, tileYOff, y); // B
        gl.glEnd();

        curr = curr == floor1 ? floor2 : floor1;
      }

      if (floorWH % 2 == 0) {
        curr = curr == floor1 ? floor2 : floor1;
      }
    }

    gl.glPopMatrix();
  }

  // --------------------------------------------------------------------------------

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // MouseListener
  // --------------------------------------------------------------------------------

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

  // --------------------------------------------------------------------------------
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  public void mouseDragged(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    Dimension size = e.getComponent().getSize();

    float thetaY = 360.0f * ((float) (prevMouseX - x) / (float) size.width);
    float thetaX = 360.0f * ((float) (prevMouseY - y) / (float) size.height);

    prevMouseX = x;
    prevMouseY = y;

    // thetaX = 0; // FOR DEBUG

    if ((view_rotx - thetaX) > +60) {
      thetaX = view_rotx - 60;
    }

    if ((view_rotx - thetaX) < -60) {
      thetaX = view_rotx + 60;
    }

    view_rotx -= thetaX;
    view_roty += thetaY;

    camera.rotate(thetaX, thetaY);
  }

  // --------------------------------------------------------------------------------

  public void mouseMoved(MouseEvent e) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // KeyListener
  // --------------------------------------------------------------------------------

  public void keyTyped(KeyEvent evt) {
    switch (evt.getKeyChar()) {
      case 'j' :
        camera.move(Direction.LFT, 1);
        break;
      case 'l' :
        camera.move(Direction.RGH, 1);
        break;
      case 'i' :
        camera.move(Direction.FRN, 1);
        break;
      case 'k' :
        camera.move(Direction.BCK, 1);
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
  // Main
  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    final Frame frame = new Frame("Camera Demo");
    final GLCanvas canvas = new GLCanvas();
    final Animator animator = new Animator(canvas);

    // TODO: Put in fs mode
    canvas.addGLEventListener(new Main());
    frame.add(canvas);
    frame.setSize(1200, 600);
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
