package org.cyrano.jogl._16.curves_2;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.Camera;
import org.cyrano.jogl.base.Primitives;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample implements MouseListener, MouseMotionListener {

  private Camera camera = new Camera();

  private static final int uSize = 2;
  private static final int vSize = 2;
  private static final int gridSize = 20;

  private double[] grid2x2 = { //
  /**/-2.0, -2.0, +0.0,//
      +2.0, -2.0, +0.0,//
      -2.0, +2.0, +0.0,//
      +2.0, +2.0, +0.0};

  // --------------------------------------------------------------------------------

  private double[] proMatrix = new double[16];
  private double[] modMatrix = new double[16];

  private int selPoint = -1;

  private int xMouseDrag;
  private int yMouseDrag;
  private boolean drag;

  private double depth;

  private int xMousePick;
  private int yMousePick;
  private boolean pick;

  private boolean idMode;

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseMotionListener(camera);
    drawable.addMouseListener/*  */(camera);
    drawable.addKeyListener/*    */(camera);

    drawable.addMouseMotionListener(this);
    drawable.addMouseListener/*  */(this);

    GL gl = drawable.getGL();

    gl.glDisable(GL.GL_CULL_FACE);

    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthRange(0, 1);

    gl.glEnable(GL.GL_MAP2_VERTEX_3);
    gl.glMapGrid2d(gridSize, 0.0, 1.0, gridSize, 0.0, 1.0);
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

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    camera.updateCameraBox();
    camera.updateCameraPos();

    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glGetDoublev(GL.GL_PROJECTION_MATRIX, //
        proMatrix, 0);

    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, //
        modMatrix, 0);

    // ----------------------------------------

    if (drag) {
      if (selPoint != -1) {
        int[] viewport = new int[4];
        gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

        GLU glu = new GLU();

        double[] coord = new double[3];

        glu.gluUnProject( //
            xMouseDrag, //
            viewport[3] - yMouseDrag, //
            depth, modMatrix, 0, proMatrix, 0, viewport, 0, coord, 0);

        System.err.println("drag - depth: " + depth);
        System.err.println("drag - coord: " + //
            coord[0] + ";" + coord[1] + ";" + coord[2]);

        grid2x2[selPoint * 3 + 0] = coord[0];
        grid2x2[selPoint * 3 + 1] = coord[1];
        grid2x2[selPoint * 3 + 2] = coord[2];
      }
    } else if (pick) {
      idMode = !idMode;
      drawControlPoints(gl);
      idMode = !idMode;

      System.err.println("picked ID is: " + (getPickId(gl)/**/));
      System.err.println("select Pt is: " + (getPickId(gl) - 1));

      selPoint = getPickId(gl) - 1;

      if (selPoint != -1) {
        int[] viewport = new int[4];
        gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

        GLU glu = new GLU();

        FloatBuffer zDepth = FloatBuffer.allocate(1);

        gl.glReadPixels( //
            /*          */xMousePick, //
            viewport[3] - yMousePick, //
            1, 1, GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, zDepth);

        depth = zDepth.get(0);

        double[] coord = new double[3];

        glu.gluUnProject( //
            xMousePick, //
            viewport[3] - yMousePick, //
            depth, modMatrix, 0, proMatrix, 0, viewport, 0, coord, 0);

        System.err.println("pick - depth: " + zDepth.get(0));
        System.err.println("pick - coord: " + //
            coord[0] + ";" + coord[1] + ";" + coord[2]);
      }
    }

    // ----------------------------------------

    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    Primitives.drawAxes(gl);
    evaluateGrid(gl);
    drawControlPoints(gl);

    // ----------------------------------------

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  private void evaluateGrid(GL gl) {
    gl.glColor3f(1.0f, 1.0f, 1.0f);
    gl.glMap2d(GL.GL_MAP2_VERTEX_3, 0, 1, 3, uSize, 0, 1, uSize * 3, vSize, grid2x2, 0);
    gl.glEvalMesh2(GL.GL_LINE, 0, gridSize, 0, gridSize);
  }

  // --------------------------------------------------------------------------------

  private void drawControlPoints(GL gl) {
    int i;

    gl.glPointSize(10.0f);

    gl.glBegin(GL.GL_POINTS);

    for (i = 0; i < uSize * vSize; i++) {
      glSetColorAndId(gl, 1.0f, 1.0f, 0.0f, (byte) (i + 1));
      gl.glVertex3dv(grid2x2, i * 3);
    }

    gl.glEnd();
  }

  // --------------------------------------------------------------------------------

  private byte getPickId(GL gl) {
    int[] viewport = new int[4];

    gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

    ByteBuffer bb = ByteBuffer.allocate(4);

    gl.glReadPixels( //
        /*          */xMousePick, //
        viewport[3] - yMousePick, //
        1, 1, GL.GL_RGBA, GL.GL_BYTE, bb);

    return bb.get(3);
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

  public void displayChanged(GLAutoDrawable drawable, //
      boolean modeChanged, boolean deviceChanged) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // MouseListener
  // --------------------------------------------------------------------------------

  public void mouseEntered(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void mouseExited(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void mousePressed(MouseEvent evt) {
    if (evt.getButton() != MouseEvent.BUTTON3) {
      return;
    }

    xMousePick = evt.getX();
    yMousePick = evt.getY();

    pick = true;

    System.err.println("pick: " + xMousePick + ";" + yMousePick);

    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public void mouseReleased(MouseEvent evt) {
    pick = false;
    drag = false;

    selPoint = -1;
  }

  // --------------------------------------------------------------------------------

  public void mouseClicked(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  public void mouseDragged(MouseEvent evt) {
    if (evt.getModifiersEx() != InputEvent.BUTTON3_DOWN_MASK) {
      return;
    }

    xMouseDrag = evt.getX();
    yMouseDrag = evt.getY();

    drag = true;

    System.err.println("drag: " + xMouseDrag + ";" + yMouseDrag);

    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public void mouseMoved(MouseEvent e) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main().run();
  }
}
