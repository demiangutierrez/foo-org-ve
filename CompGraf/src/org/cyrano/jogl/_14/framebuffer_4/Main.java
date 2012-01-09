package org.cyrano.jogl._14.framebuffer_4;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.cyrano.util.geometry.Matrix;
import org.cyrano.util.geometry.MatrixOps;

/**
 * @author Demián Gutierrez
 */
public class Main implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

  // --------------------------------------------------------------------------------
  // Camera
  // --------------------------------------------------------------------------------

  private double topX = 0.0f;
  private double topY = 1.0f;
  private double topZ = 0.0f;

  private double frnX = 0.0f;
  private double frnY = 0.0f;
  private double frnZ = 1.0f;

  private double dist = 2;

  // --------------------------------------------------------------------------------
  // Camera movement
  // --------------------------------------------------------------------------------

  private float view_rotx = 0.0f;
  //private float view_roty = 0.0f; // CHECK

  private int prevMouseX;
  private int prevMouseY;

  // --------------------------------------------------------------------------------

  private GLCanvas glCanvas;
  private boolean saveFB;
  private int w;
  private int h;

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseListener(this);
    drawable.addMouseMotionListener(this);
    drawable.addKeyListener(this);

    GL gl = drawable.getGL();

    //loadTexture();

    gl.glDisable(GL.GL_CULL_FACE);
    //gl.glEnable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);
  }

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

    this.w = w;
    this.h = h;
    GL gl = drawable.getGL();

    gl.glViewport(0, 0, w, h);

    calculatePerspective(gl);
  }

  // --------------------------------------------------------------------------------

  private void calculatePerspective(GL gl) {
    GLU glu = new GLU();

    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();

    double nr = 0.5;
    double fr = 2 * (dist - nr) + nr;

    System.err.println("nr: " + nr);
    System.err.println("fr: " + fr);
    System.err.println("dist: " + dist);

    glu.gluPerspective(90, 1, nr, fr);

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    calculatePerspective(gl);

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    double frnDstX = frnX * dist;
    double frnDstY = frnY * dist;
    double frnDstZ = frnZ * dist;

    glu.gluLookAt(frnDstX, frnDstY, frnDstZ, 0, 0, 0, topX, topY, topZ);

    drawAxes(gl);

    //gl.glDisable(GL.GL_DEPTH_TEST);
    gl.glEnable(GL.GL_TEXTURE_2D);

    // SWAP
    gl.glColor3f(1.0f, 0.0f, 0.0f);
    //texture1.bind();
    drawRect(gl, 1.0f, -0.25f);

    gl.glColor3f(0.0f, 1.0f, 0.0f);
    //texture2.bind();
    drawRect(gl, 0.5f, +0.25f);

    //gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDisable(GL.GL_TEXTURE_2D);

    gl.glFlush();

    if (saveFB) {
      capture(gl);
      saveFB = false;
    }
  }

  // --------------------------------------------------------------------------------

  private void capture(GL gl) {
    System.err.println(w + ";" + h);

    int[] val = new int[1];
    gl.glGetIntegerv(GL.GL_DEPTH_BITS, val, 0);

    System.err.println(val[0]);

    IntBuffer pixelsRGB = ByteBuffer.allocateDirect(w * h * 4).asIntBuffer();
    gl.glReadPixels(0, 0, w, h, GL.GL_DEPTH_COMPONENT, GL.GL_UNSIGNED_INT, pixelsRGB);

    //    ByteBuffer pixelsRGB = ByteBuffer.allocateDirect(w * h * 4);
    //
    //    gl.glReadPixels(0, 0, w, h, GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, pixelsRGB);

    BufferedImage ret = transformPixelsDepth(pixelsRGB, w, h);

    //    BufferedImage ret = Export.exportRGBA(gl, w, h);

    try {
      ImageIO.write(ret, "png", new FileOutputStream("capture_cb.png"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // --------------------------------------------------------------------------------

  public static BufferedImage transformPixelsDepth( //
      IntBuffer pixelsSrc, int w, int h) {

    //    FloatBuffer floatSrc = pixelsSrc.asFloatBuffer();

    int[] pixelsTgt = new int[w * h];

    int currSrc = 0;
    int currTgt = 0;

    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {

        //        int crap = pixelsSrc.get(currSrc);
        int curr = pixelsSrc.get(currSrc++);

        int r = (int) (curr * 255);
        int g = (int) (curr * 255);
        int b = (int) (curr * 255);

        //        if (curr != 0) {
        //          System.err.println(curr + ";" + r + ";" + g + ";" + b);
        //        }

        int a = 0xFF000000;

        //@begnf
        // ---------------------------------
        // |...A...|...R...|...G...|...B...|
        // 32      24      16      08     00
        // ---------------------------------
        pixelsTgt[currTgt++] =
            ((a & 0x000000FF) << 24) |
            ((r & 0x000000FF) << 16) |
            ((g & 0x000000FF) <<  8) |
            ((b & 0x000000FF)      );
        //@endnf
      }
    }

    BufferedImage ret = new BufferedImage( //
        w, h, BufferedImage.TYPE_INT_ARGB);

    ret.setRGB(0, 0, w, h, pixelsTgt, 0, w);

    return ret;
  }

  // --------------------------------------------------------------------------------

  //  private void capture(GL gl) {
  //    BufferedImage ret = Export.exportRGBA(gl, w, h);
  //
  //    try {
  //      ImageIO.write(ret, "png", new FileOutputStream("capture_cb.png"));
  //    } catch (Exception e) {
  //      throw new RuntimeException(e);
  //    }
  //  }

  // --------------------------------------------------------------------------------

  private void drawRect(GL gl, float size, float z) {
    gl.glBegin(GL.GL_QUADS);

    // NORMAL
    gl.glTexCoord2f(0.0f, 0.0f);
    gl.glVertex3f(-size / 2, -size / 2, z);
    gl.glTexCoord2f(0.0f, 1.0f);
    gl.glVertex3f(-size / 2, +size / 2, z);
    gl.glTexCoord2f(1.0f, 1.0f);
    gl.glVertex3f(+size / 2, +size / 2, z);
    gl.glTexCoord2f(1.0f, 0.0f);
    gl.glVertex3f(+size / 2, -size / 2, z);

    gl.glEnd();
  }

  // --------------------------------------------------------------------------------

  private void drawAxes(GL gl) {
    gl.glBegin(GL.GL_LINES);

    gl.glColor3f(1.0f, 0.5f, 0.5f);
    gl.glVertex3f(0f, 0f, 0f);
    gl.glVertex3f(5f, 0f, 0f);

    gl.glColor3f(0.5f, 1.0f, 0.5f);
    gl.glVertex3f(0f, 0f, 0f);
    gl.glVertex3f(0f, 5f, 0f);

    gl.glColor3f(0.5f, 0.5f, 1.0f);
    gl.glVertex3f(0f, 0f, 0f);
    gl.glVertex3f(0f, 0f, 5f);

    gl.glEnd();
  }

  // --------------------------------------------------------------------------------

  public void displayChanged(GLAutoDrawable drawable, //
      boolean modeChanged, boolean deviceChanged) {
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
    //view_roty += thetaY;

    //System.err.println(thetaX + ";" + thetaY);

    rotate(thetaX, thetaY);

    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public void rotate(float thetaX, float thetaY) {
    Matrix curFrn = new Matrix(4, 1);
    curFrn.setData( //
        new double[]{frnX, frnY, frnZ, 1});

    Matrix curTop = new Matrix(4, 1);
    curTop.setData( //
        new double[]{topX, topY, topZ, 1});

    Matrix ry = MatrixOps.rotateY(thetaY);

    Matrix newFrn = MatrixOps.matrixMult(ry, curFrn);
    Matrix newTop = MatrixOps.matrixMult(ry, curTop);

    curFrn = newFrn;
    curTop = newTop;

    Matrix curSid = MatrixOps.crossProduct(curFrn, curTop);

    Matrix rs = MatrixOps.rotate( //
        -thetaX, curSid.get(0, 0), curSid.get(1, 0), curSid.get(2, 0));

    newFrn = MatrixOps.matrixMult(rs, curFrn);
    newTop = MatrixOps.matrixMult(rs, curTop);

    frnX = newFrn.get(0, 0);
    frnY = newFrn.get(1, 0);
    frnZ = newFrn.get(2, 0);

    topX = newTop.get(0, 0);
    topY = newTop.get(1, 0);
    topZ = newTop.get(2, 0);
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
      case 'A' :
      case 'a' :
        //        if (dist <= 10) {
        dist *= 2;
        //        }
        break;
      case 'Z' :
      case 'z' :
        //        if (dist >= 5) {
        dist /= 2;
        //        }
        break;
      case 'S' :
      case 's' :
        saveFB = true;
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

  public static void main(String[] args) {
    Frame frame = new Frame("JOGL Main");

    GLCanvas canvas = new GLCanvas();
    Main m = new Main();
    m.glCanvas = canvas;
    canvas.addGLEventListener(m);

    frame.add(canvas);
    frame.setSize(300, 300);
    frame.setVisible(true);

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
