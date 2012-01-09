package org.cyrano.jogl._11.light_3;

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
import javax.media.opengl.glu.GLU;

import org.cyrano.jogl._11.LightParent;
import org.cyrano.util.geometry.Matrix;
import org.cyrano.util.geometry.MatrixOps;
import org.cyrano.util.misc.MathUtil;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

/**
 * @author Demián Gutierrez
 */
public class Main extends LightParent //
    implements
      GLEventListener,
      MouseListener,
      MouseMotionListener,
      KeyListener {

  public boolean QUAD_NORMALS = true;

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
      InputStream is;
      TextureData textureData;

      is = ClassLoader.getSystemResourceAsStream("textures/wood-fence_256x256.jpg");

      // --------------------------------------------------------
      // w/o mipmaps
      // textureData = TextureIO.newTextureData(is, false, null);
      // --------------------------------------------------------
      // w.. mipmaps
      textureData = TextureIO.newTextureData(is, true, null);
      // --------------------------------------------------------

      texture = TextureIO.newTexture(textureData);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    drawable.addMouseListener(this);
    drawable.addMouseMotionListener(this);
    drawable.addKeyListener(this);

    GL gl = drawable.getGL();

    loadTexture();

    gl.glDisable(GL.GL_CULL_FACE);
    //gl.glEnable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_DEPTH_TEST);

    gl.glShadeModel(GL.GL_SMOOTH);

    // if this mode is set, OpenGL calculates ligth before texture
    // so no light + texture
    //gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_DECAL);
    gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
    createLight(gl);
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

    //    System.err.println("nr: " + nr);
    //    System.err.println("fr: " + fr);
    //    System.err.println("dist: " + dist);

    glu.gluPerspective(90, 1, nr, fr);

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    calculatePerspective(gl);

    calculateSpin();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    double frnDstX = frnX * dist;
    double frnDstY = frnY * dist;
    double frnDstZ = frnZ * dist;

    glu.gluLookAt(frnDstX, frnDstY, frnDstZ, 0, 0, 0, topX, topY, topZ);

    gl.glDisable(GL.GL_LIGHTING);
    drawAxes(gl);
    gl.glEnable(GL.GL_LIGHTING);

    drawAllLights(gl);

    gl.glEnable(GL.GL_TEXTURE_2D);
    texture.bind();

    // ----------------------------------------

    gl.glPushMatrix();
    setMaterial(gl);
    gl.glRotatef(180f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    setMaterial(gl);
    gl.glRotatef(0f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    setMaterial(gl);
    gl.glRotatef(-90f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    setMaterial(gl);
    gl.glRotatef(90f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    setMaterial(gl);
    gl.glRotatef(-90f, 1, 0, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    setMaterial(gl);
    gl.glRotatef(90f, 1, 0, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glDisable(GL.GL_TEXTURE_2D);

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  private void drawFace(GL gl) {
    gl.glBegin(GL.GL_QUADS);

    // V1
    if (QUAD_NORMALS) {
      gl.glNormal3f(0, 0, 1);
    } else {
      double[] n = {-0.5f, -0.5f, +0.5f};
      n = MathUtil.nor(n);
      gl.glNormal3dv(n, 0);
    }

    gl.glTexCoord2f(0.0f, 0.0f);
    gl.glVertex3f(-0.5f, -0.5f, +0.5f);

    // V2
    if (QUAD_NORMALS) {
      gl.glNormal3f(0, 0, 1);
    } else {
      double[] n = {-0.5f, -0.5f, +0.5f};
      n = MathUtil.nor(n);
      gl.glNormal3f(+0.5f, -0.5f, +0.5f);
    }

    gl.glTexCoord2f(1.0f, 0.0f);
    gl.glVertex3f(+0.5f, -0.5f, +0.5f);

    // V3
    if (QUAD_NORMALS) {
      gl.glNormal3f(0, 0, 1);
    } else {
      gl.glNormal3f(+0.5f, +0.5f, +0.5f);
    }

    gl.glTexCoord2f(1.0f, 1.0f);
    gl.glVertex3f(+0.5f, +0.5f, +0.5f);

    // V4
    if (QUAD_NORMALS) {
      gl.glNormal3f(0, 0, 1);
    } else {
      gl.glNormal3f(-0.5f, +0.5f, +0.5f);
    }

    gl.glTexCoord2f(0.0f, 1.0f);
    gl.glVertex3f(-0.5f, +0.5f, +0.5f);

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
        if (dist <= 50) {
          dist *= 2;
        }
        break;
      case 'Z' :
      case 'z' :
        if (dist >= 1) {
          dist /= 2;
        }
        break;
      case 'S' :
      case 's' :
        animate = !animate;
        prevTime = 0;
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

    FPSAnimator animator = new FPSAnimator(canvas, 60);
    Main m = new Main();
    m.glCanvas = canvas;
    canvas.addGLEventListener(m);
    animator.start();

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
