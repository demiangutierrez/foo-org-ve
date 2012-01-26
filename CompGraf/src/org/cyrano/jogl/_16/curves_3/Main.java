package org.cyrano.jogl._16.curves_3;

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
// --------------------------------------------------------------------------------
// slightly based and adapted from:
// www.opengl.org/resources/code/samples/mjktips/grid/editgrid.c
// --------------------------------------------------------------------------------
public class Main extends BaseExample implements MouseListener, MouseMotionListener {

  private static final boolean IS_CURVE_2X2 = false;

  // --------------------------------------------------------------------------------

  private Camera camera = new Camera();

  private Light light = new Light();

  // --------------------------------------------------------------------------------

  private static final int uSize2x2 = 2;
  private static final int vSize2x2 = 2;

  private double[] grid2x2 = { //
  /**/-2.0, -2.0, +0.0,//
      +2.0, -2.0, +0.0,//
      -2.0, +2.0, +0.0,//
      +2.0, +2.0, +0.0};

  private static final int uSize4x4 = 4;
  private static final int vSize4x4 = 4;

  // --------------------------------------------------------------------------------

  private double[] grid4x4 = { //
  /**/-2.0, -2.0, 0.0,//
      -0.5, -2.0, 0.0,//
      +0.5, -2.0, 0.0,//
      +2.0, -2.0, 0.0,//
      -2.0, -0.5, 0.0,//
      -0.5, -0.5, 0.0,//
      +0.5, -0.5, 0.0,//
      +2.0, -0.5, 0.0,//
      -2.0, +0.5, 0.0,//
      -0.5, +0.5, 0.0,//
      +0.5, +0.5, 0.0,//
      +2.0, +0.5, 0.0,//
      -2.0, +2.0, 0.0,//
      -0.5, +2.0, 0.0,//
      +0.5, +2.0, 0.0,//
      +2.0, +2.0, 0.0};

  // --------------------------------------------------------------------------------

  private int gridSize = 20;

  // --------------------------------------------------------------------------------

  private int uSize;
  private int vSize;

  private double[] grid;

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

  public Main() {

    if (IS_CURVE_2X2) {
      uSize = uSize2x2;
      vSize = vSize2x2;
      grid = grid2x2;
    } else {
      uSize = uSize4x4;
      vSize = vSize4x4;
      grid = grid4x4;
    }
  }

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseMotionListener(camera);
    drawable.addMouseListener/*  */(camera);
    drawable.addKeyListener/*    */(camera);

    drawable.addMouseMotionListener(this);
    drawable.addMouseListener/*  */(this);

    GL gl = drawable.getGL();

    //gl.glDisable(GL.GL_CULL_FACE);

    gl.glShadeModel(GL.GL_SMOOTH);

    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthRange(0, 1);

    gl.glEnable(GL.GL_MAP2_VERTEX_3);

    //gl.glEnable(GL.GL_MAP2_NORMAL);

    gl.glMapGrid2d(gridSize, 0.0, 1.0, gridSize, 0.0, 1.0);

    light.createLight(gl);
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

        grid[selPoint * 3 + 0] = coord[0];
        grid[selPoint * 3 + 1] = coord[1];
        grid[selPoint * 3 + 2] = coord[2];
      }
    } else if (pick) {
      gl.glDisable(GL.GL_LIGHTING);
      idMode = !idMode;
      drawControlPoints(gl);
      idMode = !idMode;
      gl.glEnable(GL.GL_LIGHTING);

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

    gl.glDisable(GL.GL_LIGHTING);
    Primitives.drawAxes(gl);
    drawControlPoints(gl);

    //    gl.glEnable(GL.GL_LIGHTING);
    //    lightParent.calculateSpin();
    //    lightParent.drawAllLights(gl);

    light.setMaterial(gl);
    evaluateGrid(gl);

    // ----------------------------------------

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  private void evaluateGrid(GL gl) {
    gl.glColor3f(1.0f, 1.0f, 1.0f);
    gl.glMap2d(GL.GL_MAP2_VERTEX_3, 0, 1, 3, uSize, 0, 1, uSize * 3, vSize, grid, 0);
    gl.glEvalMesh2(GL.GL_LINE, 0, gridSize, 0, gridSize);

    //gl.glEvalMesh2(GL.GL_FILL, 0, gridSize, 0, gridSize);
  }

  // --------------------------------------------------------------------------------

  private void drawControlPoints(GL gl) {
    int i;

    gl.glPointSize(10.0f);

    gl.glBegin(GL.GL_POINTS);

    for (i = 0; i < uSize * vSize; i++) {
      glSetColorAndId(gl, 1.0f, 1.0f, 0.0f, (byte) (i + 1));
      gl.glVertex3dv(grid, i * 3);
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

    return bb.get(0);
  }

  // --------------------------------------------------------------------------------

  private void glSetColorAndId(GL gl, float r, float g, float b, byte id) {

    // ----------------------------------------
    // don't use alias (last byte) to store ids
    // ----------------------------------------

    if (idMode == true) {
      gl.glColor4b((byte) id, (byte) 0, (byte) 0, (byte) 0);
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
