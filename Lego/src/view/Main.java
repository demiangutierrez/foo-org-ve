/*
 * Created on 26/12/2006
 */
package view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;

/**
 * @author Demián Gutierrez
 */
public class Main implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

  private float view_rotx = 0.0f, view_roty = 0.0f, view_rotz = 0.0f;
  private float view_zoom = 1;

  private int prevMouseX, prevMouseY;

  private GLU glu = new GLU();

  private Part part;
  Integer lId = null;

  // ***************************************
  // XXX: Change according the real location
  private static final String ldraw_base = "/home/dmi/workspace/Lego/ldraw"; // LDraw home
  private static final String model_base = "/home/dmi/workspace/Lego/model"; // Where to the models to be displayed

  // ***************************************

  public Main(String[] args) throws Exception {
    String fs = System.getProperty("file.separator");

    PartLoader partLoader = PartLoader.getInstance();

    partLoader.getPath().add(ldraw_base + fs + "p");
    partLoader.getPath().add(ldraw_base + fs + "parts");
    partLoader.getPath().add(ldraw_base + fs + "parts" + fs + "s");
    partLoader.getPath().add(model_base);

    reload();
  }

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseListener(this);
    drawable.addMouseMotionListener(this);
    drawable.addKeyListener(this);

    GL gl = drawable.getGL();

    gl.glDisable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);

    gl.glEnable(GL.GL_BLEND);
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();

    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    // ??? I really need to understand this better
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

    glu.gluLookAt(0, 0, 200 * view_zoom, 0, 0, 1, 0, 1, 0);

    gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(view_rotz, 0.0f, 0.0f, 1.0f);

    // LDraw coordinate system is 180 rotated around X axis
    gl.glRotatef((float) 180, 1, 0, 0);

    if (lId == null) {
      lId = new Integer(1);
      gl.glNewList(lId.intValue(), GL.GL_COMPILE);

      try {
        part.render(gl, 0);
      } catch (Exception e) {
        e.printStackTrace();
      }

      gl.glEndList();
    }

    gl.glCallList(lId.intValue());

    gl.glPopMatrix();
  }

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    // Empty
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
        reload();
        break;
    }
  }

  public void keyPressed(KeyEvent e) {
    //  Empty    
  }

  public void keyReleased(KeyEvent e) {
    // Empty    
  }

  private void reload() {
    PartLoader partLoader = PartLoader.getInstance();
    try {
      //part = partLoader.loadPart("3004.DAT");    // Boring, just a lego part for testing
      //part = partLoader.loadPart("3034.DAT");    // Boring, just a lego part for testing
      //part = partLoader.loadPart("3062B.DAT");   // Boring, just a lego part for testing
      //part = partLoader.loadPart("3298P90.DAT"); // Boring, just a lego part for testing
      //part = partLoader.loadPart("3298s01.dat"); // Boring, just a lego part for testing
      //part = partLoader.loadPart("3839A.dat");   // Boring, just a lego part for testing
      //part = partLoader.loadPart("4-4cyli.dat"); // Boring, just a lego part for testing
      //part = partLoader.loadPart("3829b.DAT");   // Boring, just a lego part for testing
      //part = partLoader.loadPart("3829a.DAT");   // Boring, just a lego part for testing
      //part = partLoader.loadPart("3829.DAT");    // Boring, just a lego part for testing
      //part = partLoader.loadPart("1499-1.mpd");  // Medium twin seat spaceship
      //part = partLoader.loadPart("885-1.mpd");   // Very small spaceship (5)
      //part = partLoader.loadPart("487-1.mpd");   // Medium Ship with deployable car
      //part = partLoader.loadPart("6804-1.mpd");  // Very small car (2)
      //part = partLoader.loadPart("6807-1.mpd");  // Very (very) small spaceship with robot
      //part = partLoader.loadPart("6824.mpd");    // Very small spaceship (4)
      //part = partLoader.loadPart("6872-1.mpd");  // Medium ship with small robot
      //part = partLoader.loadPart("6890-1.mpd");  // Medium ship with deployable small ship
      //part = partLoader.loadPart("6926-1.mpd");  // Nice blue car (1)
      //part = partLoader.loadPart("6928-1.mpd");  // Nice blue car (2)
      //part = partLoader.loadPart("6950-1.mpd");  // Childhood car & rocket
      //part = partLoader.loadPart("6985.mpd");    // Big spaceship
      //part = partLoader.loadPart("886-1.mpd");   // Very small car (1)
      //part = partLoader.loadPart("1580-1.mpd");  // Medium car
      //part = partLoader.loadPart("6801-1.mpd");  // Very small spaceship (1)
      //part = partLoader.loadPart("6806-1.mpd");  // Very small spaceship (2)
      //part = partLoader.loadPart("6821-1.mpd");  // Small-med Bulldozer Car (1)
      //part = partLoader.loadPart("6847-1.mpd");  // Small-med Bulldozer Car (2)
      //part = partLoader.loadPart("6881-1.mpd");  // Small-med satelite-rocket launcher
      //part = partLoader.loadPart("6891.mpd");    // Medium Ship
      //part = partLoader.loadPart("6927-1.mpd");  // Medium car with rear deployable module
      //part = partLoader.loadPart("6929-1.mpd");  // Medium Childhood Ship
      //part = partLoader.loadPart("6972-1.mpd");  // Big lunar station small ship launcher
      //part = partLoader.loadPart("885-1.mpd");   // Very small spaceship (3)
      //part = partLoader.loadPart("889-1.mpd");   // Very small car with satelite

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {
    final Frame frame = new Frame("Lego Demo");
    final GLCanvas canvas = new GLCanvas();
    final Animator animator = new Animator(canvas);

    // TODO: Put in fs mode
    // TODO: Refactor to a lego panel
    // TODO: Pass model by command arguments
    canvas.addGLEventListener(new Main(args));
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
