package org.cyrano.jogl._09.texture_4;

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
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.cyrano.util.geometry.Matrix;
import org.cyrano.util.geometry.MatrixOps;
import org.cyrano.util.misc.Hwh;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

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

  private double dist = 5;

  // --------------------------------------------------------------------------------
  // Camera movement
  // --------------------------------------------------------------------------------

  private float view_rotx = 0.0f;
  //private float view_roty = 0.0f; // CHECK

  private int prevMouseX;
  private int prevMouseY;

  // --------------------------------------------------------------------------------

  private GLCanvas glCanvas;

  // --------------------------------------------------------------------------------

  private Texture texture;

  // --------------------------------------------------------------------------------

  private void loadTexture() {
    try {
      List<TextureData> mipmapList = new LinkedList<TextureData>();

      for (int i = 0; true; i++) {
        TextureData mipmap;
        mipmap = TextureIO.newTextureData(openDataFile(i), false, null);
        mipmapList.add(mipmap);

        if (mipmap.getWidth() <= 1) {
          break;
        }
      }

      Buffer[] bufferArray = new Buffer[mipmapList.size()];

      for (TextureData mipmap : mipmapList) {
        bufferArray[mipmapList.indexOf(mipmap)] = mipmap.getBuffer();
      }

      TextureData textureBase = mipmapList.get(0);
      TextureData textureData = new TextureData(textureBase.getInternalFormat(), //
          Hwh.getW(textureBase), Hwh.getH(textureBase), 0, //
          textureBase.getPixelFormat(), textureBase.getPixelType(), //
          false, false, bufferArray, null);

      texture = TextureIO.newTexture(textureData);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // --------------------------------------------------------------------------------

  private InputStream openDataFile(int i) {
    switch (i) {
      case 0 :
        return ClassLoader.getSystemResourceAsStream("textures/wood-fence_256x256.jpg");
      case 1 :
        return ClassLoader.getSystemResourceAsStream("textures/wood-fence_128x128.jpg");
      case 2 :
        return ClassLoader.getSystemResourceAsStream("textures/wood-fence_064x064.jpg");
      case 3 :
        return ClassLoader.getSystemResourceAsStream("textures/wood-fence_032x032.jpg");
      case 4 :
        return ClassLoader.getSystemResourceAsStream("textures/wood-fence_016x016.jpg");
      case 5 :
        return ClassLoader.getSystemResourceAsStream("textures/wood-fence_008x008.jpg");
      case 6 :
        return ClassLoader.getSystemResourceAsStream("textures/wood-fence_004x004.jpg");
      case 7 :
        return ClassLoader.getSystemResourceAsStream("textures/wood-fence_002x002.jpg");
      case 8 :
        return ClassLoader.getSystemResourceAsStream("textures/wood-fence_001x001.jpg");
    }
    return null;
  }

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseListener(this);
    drawable.addMouseMotionListener(this);
    drawable.addKeyListener(this);

    GL gl = drawable.getGL();

    loadTexture();

    gl.glDisable(GL.GL_CULL_FACE);
    //gl.glEnable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);
  }

  // --------------------------------------------------------------------------------

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

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

    // ----------------------------------------
    // avoid stitching!
    // ----------------------------------------

    gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
    gl.glPolygonOffset(0.2f, 0.2f);

    // ----------------------------------------

    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);

    gl.glEnable(GL.GL_TEXTURE_2D);

    // ------------------------------------------------------------------
    // texture parameters
    // ------------------------------------------------------------------
    gl.glTexParameterf(GL.GL_TEXTURE_2D, //
        GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
    gl.glTexParameterf(GL.GL_TEXTURE_2D, //
        GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);

    gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_DECAL);
    // ------------------------------------------------------------------

    texture.bind();

    drawTriangles(gl);

    gl.glDisable(GL.GL_TEXTURE_2D);

    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
    gl.glColor3f(1, 1, 1); // WHT

    drawTriangles(gl);

    // ----------------------------------------
    // back to normal
    // ----------------------------------------

    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
    gl.glDisable(GL.GL_POLYGON_OFFSET_FILL);

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  public void drawTriangles(GL gl) {
    gl.glBegin(GL.GL_TRIANGLE_STRIP);

    gl.glTexCoord2f(0.0f, 0f);
    gl.glVertex3f(-3, -1, -0.5f);

    gl.glTexCoord2f(0.16f, 1f);
    gl.glVertex3f(-2, +1, +0);

    gl.glTexCoord2f(0.33f, 0f);
    gl.glVertex3f(-1, -1, +0);

    gl.glTexCoord2f(0.5f, 1f);
    gl.glVertex3f(+0, +1, -0.5f);

    gl.glTexCoord2f(0.66f, 0f);
    gl.glVertex3f(+1, -1, +0);

    gl.glTexCoord2f(0.83f, 1f);
    gl.glVertex3f(+2, +1, +0);

    gl.glTexCoord2f(1.0f, 0f);
    gl.glVertex3f(+3, -1, -0.5f);

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
