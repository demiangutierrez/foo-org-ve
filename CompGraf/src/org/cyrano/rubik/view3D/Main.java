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
import javax.media.opengl.glu.GLU;
import javax.swing.event.EventListenerList;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.GLMouseListenerProxy;
import org.cyrano.jogl.base.GLMouseMotionListenerProxy;
import org.cyrano.jogl.base.Primitives;
import org.cyrano.rubik.model.Axis;
import org.cyrano.rubik.model.Command;
import org.cyrano.rubik.model.Cubie;
import org.cyrano.rubik.model.Facelet;
import org.cyrano.rubik.model.Model;
import org.cyrano.rubik.model.Turn;

import com.sun.opengl.util.GLUT;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample //
    implements
      KeyListener,
      MouseListener,
      MouseMotionListener {

  private static final double FACE_SIZE_FACTOR = 0.45;

  private boolean DRAW_FACELET_ID = false;

  // --------------------------------------------------------------------------------

  private EventListenerList eventListenerList = //
  new EventListenerList();

  private GLMouseMotionListenerProxy mouseMotionListenerProxy;

  private GLMouseListenerProxy mouseListenerProxy;

  // --------------------------------------------------------------------------------

  private boolean idMode = false;

  // --------------------------------------------------------------------------------

  private Model model;

  private byte selFacelet;

  // --------------------------------------------------------------------------------

  public Main() {
    initBaseExample(getClass().getName(), new CameraBall());
  }

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    super.init(drawable);

    mouseMotionListenerProxy = //
    new GLMouseMotionListenerProxy(eventListenerList, glCanvas);

    mouseListenerProxy = //
    new GLMouseListenerProxy(eventListenerList, glCanvas);

    // ----------------------------------------

    drawable.addMouseMotionListener(mouseMotionListenerProxy);
    drawable.addMouseListener/*  */(mouseListenerProxy);

    mouseMotionListenerProxy.addMouseMotionListener(this);
    mouseListenerProxy.addMouseListener(this);

    // ----------------------------------------

    model = new Model(3);
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glLoadIdentity();

    camera.updateCameraBox(getW(gl), getH(gl));
    camera.updateCameraPos();

    mouseMotionListenerProxy.fireQueue();
    mouseListenerProxy.fireQueue();

    // ----------------------------------------

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    Primitives.drawAxes(gl);

    if (prev != 0) {
      model.animateCommand(System.currentTimeMillis() - prev);
    }

    prev = System.currentTimeMillis();

    drawCube(gl);

    gl.glFlush();
  }

  long prev;

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

          gl.glMultMatrixd(cubie.getAnimRotationMatrix(), 0);

          gl.glTranslated( //
              cubie.getInitialPosition().x, //
              cubie.getInitialPosition().y, //
              cubie.getInitialPosition().z);

          drawCubie(gl, cubie);

          gl.glPopMatrix();
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void drawCubie(GL gl, Cubie cubie) {
    for (Facelet facelet : cubie.getFaceletList()) {
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

      // ----------------------------------------

      gl.glBegin(GL.GL_TRIANGLE_STRIP);

      double xMid = 0;
      double yMid = 0;
      double zMid = 0;

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

          xMid += x;
          yMid += y;
          zMid += z;
        }
      }

      xMid /= 4;
      yMid /= 4;
      zMid /= 4;

      gl.glEnd();

      if (DRAW_FACELET_ID) {
        drawFaceletId(gl, facelet, facelet.id, xMid, yMid, zMid);
      } else {
        drawFaceletId(gl, facelet, cubie.getCubeId(), xMid, yMid, zMid);
      }

    }
  }

  // --------------------------------------------------------------------------------

  private void drawFaceletId(GL gl, Facelet facelet, byte id, //
      double xMid, double yMid, double zMid) {

    GLUT glut = new GLUT();
    GLU glu = new GLU();

    double[] proMatrix = new double[16];
    double[] modMatrix = new double[16];

    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glGetDoublev(GL.GL_PROJECTION_MATRIX, //
        proMatrix, 0);

    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, //
        modMatrix, 0);

    int[] viewport = new int[4];

    gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

    double[] coord = new double[3];

    glu.gluProject(xMid, yMid, zMid, //
        modMatrix, 0, proMatrix, 0, //
        viewport, 0, coord, 0);

    float r = facelet.faceletColor.translateColor().getRed()/*  *// 255.0f;
    float g = facelet.faceletColor.translateColor().getGreen()/**// 255.0f;
    float b = facelet.faceletColor.translateColor().getBlue()/* *// 255.0f;

    gl.glColor3f(1 - r, 1 - g, 1 - b);

    double scale = 0.65;

    gl.glRasterPos3d( //
        facelet.normal.x * scale, //
        facelet.normal.y * scale, //
        facelet.normal.z * scale);

    glut.glutBitmapString(7, Integer.toString(id));
  }

  // --------------------------------------------------------------------------------

  private void glSetColorAndId(GL gl, float r, float g, float b, byte id) {
    if (idMode == true) {
      gl.glColor3b((byte) id, (byte) 0, (byte) 0);
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

    return bb.get(0);
  }

  // --------------------------------------------------------------------------------
  // KeyListener
  // --------------------------------------------------------------------------------

  public void keyTyped(KeyEvent evt) {
    Command command;

    switch (evt.getKeyChar()) {
      case 'D' : // FR
        command = new Command(Axis.Z_POS, Turn.CC, +1);
        model.command(command);
        break;
      case 'd' :
        command = new Command(Axis.Z_POS, Turn.CW, +1);
        model.command(command);
        break;
      case 'C' : // BK
        command = new Command(Axis.Z_NEG, Turn.CC, -1);
        model.command(command);
        break;
      case 'c' :
        command = new Command(Axis.Z_NEG, Turn.CW, -1);
        model.command(command);
        break;
      case 'E' : // TP
        command = new Command(Axis.Y_POS, Turn.CC, +1);
        model.command(command);
        break;
      case 'e' :
        command = new Command(Axis.Y_POS, Turn.CW, +1);
        model.command(command);
        break;
      case 'X' : // BT
        command = new Command(Axis.Y_NEG, Turn.CC, -1);
        model.command(command);
        break;
      case 'x' :
        command = new Command(Axis.Y_NEG, Turn.CW, -1);
        model.command(command);
        break;
      case 'Z' : // LF
        command = new Command(Axis.X_NEG, Turn.CC, -1);
        model.command(command);
        break;
      case 'z' :
        command = new Command(Axis.X_NEG, Turn.CW, -1);
        model.command(command);
        break;
      case 'F' : // RG
        command = new Command(Axis.X_POS, Turn.CC, +1);
        model.command(command);
        break;
      case 'f' :
        command = new Command(Axis.X_POS, Turn.CW, +1);
        model.command(command);
        break;
      case 'r' :
      case 'R' :
        model.redo();
        break;
      case 'u' :
      case 'U' :
        model.undo();
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

    selFacelet = (byte) (getPickId(gl, evt));

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
    new Main();
  }
}
