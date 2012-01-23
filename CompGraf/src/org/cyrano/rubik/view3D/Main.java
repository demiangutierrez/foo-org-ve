package org.cyrano.rubik.view3D;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.ByteBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.event.EventListenerList;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.Camera;
import org.cyrano.jogl.base.GLMouseListenerProxy;
import org.cyrano.jogl.base.GLMouseMotionListenerProxy;
import org.cyrano.jogl.base.Primitives;
import org.cyrano.rubik.model.Axis;
import org.cyrano.rubik.model.Cubie;
import org.cyrano.rubik.model.Facelet;
import org.cyrano.rubik.model.Model;
import org.cyrano.rubik.model.Turn;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample //
    implements
      KeyListener,
      MouseListener,
      MouseMotionListener {

  private static final double FACE_SIZE_FACTOR = 0.45;

  // --------------------------------------------------------------------------------

  private EventListenerList eventListenerList = //
  new EventListenerList();

  private GLMouseMotionListenerProxy mouseMotionListenerProxy;

  private GLMouseListenerProxy mouseListenerProxy;

  // --------------------------------------------------------------------------------

  private boolean idMode = false;

  // --------------------------------------------------------------------------------

  private Camera camera = new Camera();

  // --------------------------------------------------------------------------------

  private Model model;

  private byte selFacelet;

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    mouseMotionListenerProxy = //
    new GLMouseMotionListenerProxy(eventListenerList, glCanvas);

    mouseListenerProxy = //
    new GLMouseListenerProxy(eventListenerList, glCanvas);

    // ----------------------------------------

    drawable.addMouseMotionListener(mouseMotionListenerProxy);
    drawable.addMouseListener/*  */(mouseListenerProxy);

    mouseMotionListenerProxy.addMouseMotionListener(this);
    mouseListenerProxy.addMouseListener(this);

    drawable.addMouseMotionListener(camera);
    drawable.addMouseListener/*  */(camera);
    drawable.addKeyListener/*    */(camera);

    drawable.addKeyListener/*    */(this);

    // ----------------------------------------

    GL gl = drawable.getGL();

    gl.glEnable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);

    model = new Model(3);
  }

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

    GL gl = drawable.getGL();
    gl.glViewport(0, 0, w, h);

    camera.updateCameraBox();
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glLoadIdentity();

    camera.updateCameraBox();
    camera.updateCameraPos();

    mouseMotionListenerProxy.fireQueue();
    mouseListenerProxy.fireQueue();

    // ----------------------------------------

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    Primitives.drawAxes(gl);

    drawCube(gl);

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  private void drawCube(GL gl) {
    for (int k = -1; k <= +1; k++) {
      for (int j = -1; j <= +1; j++) {
        for (int i = -1; i <= +1; i++) {
          if (i == 0 && j == 0 && k == 0) {
            continue;
          }

          Cubie cubie = model.getCube().get(i, j, k);

          gl.glPushMatrix();

          //gl.glRotated(30, 0, 1, 0);

          gl.glTranslated( //
              cubie.position.x, //
              cubie.position.y, //
              cubie.position.z);

          drawCubie(gl, cubie);

          gl.glPopMatrix();
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void drawCubie(GL gl, Cubie cubie) {
    for (Facelet facelet : cubie.faceletList) {
      Color color = facelet.faceletColor.translateColor();

      float[] colorArray;

      if (selFacelet != facelet.id) {
        colorArray = color.getColorComponents(null);
      } else {
        colorArray = new float[]{0.5f, 0.5f, 0.5f};
      }

      glSetColorAndId(gl, //
          colorArray[0], colorArray[1], colorArray[2], //
          facelet.id);

      //      System.err.println(facelet.id);

      // ----------------------------------------

      gl.glBegin(GL.GL_TRIANGLE_STRIP);

      for (int j = -1; j <= +1; j += 2) {
        for (int i = -1; i <= +1; i += 2) {
          double x = 0;
          double y = 0;
          double z = 0;

          /*   */if (facelet.normal.x == +1) {
            x = 0.5 * facelet.normal.x;
            y = FACE_SIZE_FACTOR * i;
            z = FACE_SIZE_FACTOR * j;
          } else if (facelet.normal.x == -1) {
            x = 0.5 * facelet.normal.x;
            y = FACE_SIZE_FACTOR * j;
            z = FACE_SIZE_FACTOR * i;
          } else if (facelet.normal.y == +1) {
            x = FACE_SIZE_FACTOR * j;
            y = 0.5 * facelet.normal.y;
            z = FACE_SIZE_FACTOR * i;
          } else if (facelet.normal.y == -1) {
            x = FACE_SIZE_FACTOR * i;
            y = 0.5 * facelet.normal.y;
            z = FACE_SIZE_FACTOR * j;
          } else if (facelet.normal.z == +1) {
            x = FACE_SIZE_FACTOR * i;
            y = FACE_SIZE_FACTOR * j;
            z = 0.5 * facelet.normal.z;
          } else if (facelet.normal.z == -1) {
            x = FACE_SIZE_FACTOR * j;
            y = FACE_SIZE_FACTOR * i;
            z = 0.5 * facelet.normal.z;
          }

          if (x == 0 && y == 0 && z == 0) {
            throw new IllegalStateException( //
                "x == 0 && y == 0 && z == 0");
          }

          gl.glVertex3d(x, y, z);
        }

      }

      gl.glEnd();
    }
  }

  // --------------------------------------------------------------------------------

  public void displayChanged(GLAutoDrawable drawable, //
      boolean modeChanged, boolean deviceChanged) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  private void glSetColorAndId(GL gl, float r, float g, float b, byte id) {
    if (idMode == true) {
      gl.glColor4b((byte) 0, (byte) 0, (byte) 0, id);
    } else {
      gl.glColor3f(r, g, b);
    }
  }

  // --------------------------------------------------------------------------------

  private byte getPickId(GL gl, MouseEvent evt) {
    int[] viewport = new int[4];

    gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

    ByteBuffer bb = ByteBuffer.allocate(4);

    gl.glReadPixels( //
        /*          */evt.getX(), //
        viewport[3] - evt.getY(), //
        1, 1, GL.GL_RGBA, GL.GL_BYTE, bb);

    System.err.println( //
        bb.get(0) + ";" + bb.get(1) + ";" + bb.get(2) + ";" + bb.get(3));

    return bb.get(3);
  }

  // --------------------------------------------------------------------------------
  // KeyListener
  // --------------------------------------------------------------------------------

  public void keyTyped(KeyEvent evt) {
    switch (evt.getKeyChar()) {
      case 'D' : // FR
      case 'd' :
        model.rotate(Axis.Z_POS, Turn.CW, +1);
        break;
      case 'C' : // BK
      case 'c' :
        model.rotate(Axis.Z_NEG, Turn.CW, -1);
        break;
      case 'E' : // TP
      case 'e' :
        model.rotate(Axis.Y_POS, Turn.CW, +1);
        break;
      case 'X' : // BT
      case 'x' :
        model.rotate(Axis.Y_NEG, Turn.CW, -1);
        break;
      case 'S' : // LF
      case 's' :
        model.rotate(Axis.X_NEG, Turn.CW, -1);
        break;
      case 'F' : // RG
      case 'f' :
        model.rotate(Axis.X_POS, Turn.CW, +1);
        break;
    }

    glCanvas.repaint();
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
  // MouseListener
  // --------------------------------------------------------------------------------

  public void mouseReleased(MouseEvent evt) {
    //    pick = false;
    //    drag = false;

    selFacelet = -1;
  }

  // --------------------------------------------------------------------------------

  public void mousePressed(MouseEvent evt) {
    if (evt.getButton() != MouseEvent.BUTTON3) {
      return;
    }

    GL gl = glCanvas.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    idMode = !idMode;
    drawCube(gl);
    idMode = !idMode;

    System.err.println("picked ID is: " + (getPickId(gl, evt)/**/));
    System.err.println("select Fl is: " + (getPickId(gl, evt) - 1));

    selFacelet = (byte) (getPickId(gl, evt) - 1);

    //
    //    if (selFacelet != -1) {
    //      int[] viewport = new int[4];
    //      gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
    //
    //      GLU glu = new GLU();
    //
    //      FloatBuffer zDepth = FloatBuffer.allocate(1);
    //
    //      gl.glReadPixels( //
    //          /*          */xMousePick, //
    //          viewport[3] - yMousePick, //
    //          1, 1, GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, zDepth);
    //
    //      depth = zDepth.get(0);
    //
    //      double[] coord = new double[3];
    //
    //      glu.gluUnProject( //
    //          xMousePick, //
    //          viewport[3] - yMousePick, //
    //          depth, modMatrix, 0, proMatrix, 0, viewport, 0, coord, 0);
    //
    //      System.err.println("pick - depth: " + zDepth.get(0));
    //      System.err.println("pick - coord: " + //
    //          coord[0] + ";" + coord[1] + ";" + coord[2]);
    //    }
  }

  // --------------------------------------------------------------------------------

  public void mouseClicked(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void mouseEntered(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void mouseExited(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  public void mouseDragged(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void mouseMoved(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main().run();
  }
}
