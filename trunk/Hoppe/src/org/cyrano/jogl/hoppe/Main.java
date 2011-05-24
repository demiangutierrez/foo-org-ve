package org.cyrano.jogl.hoppe;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

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

  private List<double[]> pointList;

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseListener(this);
    drawable.addMouseMotionListener(this);
    drawable.addKeyListener(this);

    GL gl = drawable.getGL();

    gl.glDisable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);

    gl.glEnable(GL.GL_BLEND);
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

    PointListLoader pointListLoader = new PointListLoader();

    try {
      pointListLoader.load("input_pts/cactus.3337.pts");
      //pointListLoader.load("input_pts/cat10.10000.pts");
      //pointListLoader.load("input_pts/club71.16864.pts");
      //pointListLoader.load("input_pts/distcap.12745.pts");
      //pointListLoader.load("input_pts/engine.11444.pts");
      //pointListLoader.load("input_pts/fandisk2.sampled.pts");
      //pointListLoader.load("input_pts/hypersheet.sampled.pts");
      //pointListLoader.load("input_pts/knot108s.10000.pts");
      //pointListLoader.load("input_pts/mannequin.12772.pts");
      //pointListLoader.load("input_pts/mechpart.4102.pts");
      //pointListLoader.load("input_pts/monkey2.sampled.pts");
      //pointListLoader.load("input_pts/nascar.20621.pts");
      //pointListLoader.load("input_pts/oilpmp.30937.pts");
      //pointListLoader.load("input_pts/teapot.26103.pts");
    } catch (Exception e) {
      e.printStackTrace();
    }

    pointListLoader.normalize();

    pointList = pointListLoader.getPointList();
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

    glu.gluLookAt(0, 0, 2 * view_zoom, 0, 0, 1, 0, 1, 0);

    gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(view_rotz, 0.0f, 0.0f, 1.0f);

    gl.glPointSize(2);

    for (double[] point : pointList) {
      gl.glBegin(GL.GL_POINTS);
      gl.glVertex3d(point[0], point[1], point[2]);
      gl.glEnd();
      gl.glFlush();
    }
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
    // TODO: Refactor to a lego panel
    // TODO: Pass model by command arguments
    canvas.addGLEventListener(new Main());
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