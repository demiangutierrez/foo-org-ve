package org.cyrano.jogl._14.framebuffer_3;

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

import org.cyrano.jogl.util.Matrix;
import org.cyrano.jogl.util.MatrixOps;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

/**
 * @author Demián Gutierrez
 */
public class Main implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

  private float angle = 0;

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

  //  private GLCanvas glCanvas;

  // --------------------------------------------------------------------------------

  private Texture texture1;
  private Texture texture2;

  // --------------------------------------------------------------------------------

  private void loadTexture() {
    try {
      InputStream is;
      TextureData textureData;

      is = ClassLoader.getSystemResourceAsStream("textures/floor1.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      texture1 = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/floor2.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      texture2 = TextureIO.newTexture(textureData);
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
  }

  // --------------------------------------------------------------------------------

  public void reshape(GLAutoDrawable drawable, //
      int x, int y, int w, int h) {

    GL gl = drawable.getGL();

    gl.glViewport(0, 0, w, h);

    calculatePerspective(gl);

    //    GLU glu = new GLU();
    //
    //    gl.glViewport(0, 0, w, h);
    //    gl.glMatrixMode(GL.GL_PROJECTION);
    //    gl.glLoadIdentity();
    //    glu.gluPerspective(60, w / h, 0.1, 1000.0);
    //    gl.glMatrixMode(GL.GL_MODELVIEW);
    //
  }

  // --------------------------------------------------------------------------------

  private void calculatePerspective(GL gl) {
    GLU glu = new GLU();

    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();

    //    double nr = 0.5;
    //    double fr = 2 * (dist - nr) + nr;
    double nr = 0.1;
    double fr = 1000;

    System.err.println("nr: " + nr);
    System.err.println("fr: " + fr);
    System.err.println("dist: " + dist);

    glu.gluPerspective(60, 1, nr, fr);

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    GLU glu = new GLU();

    //    gl.glClearStencil(0); //clear the stencil buffer
    //    gl.glClearDepth(1.0f);
    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT); //clear the buffers
    gl.glLoadIdentity();

    gl.glLoadIdentity();

    double frnDstX = frnX * dist;
    double frnDstY = frnY * dist;
    double frnDstZ = frnZ * dist;

    glu.gluLookAt(frnDstX, frnDstY, frnDstZ, 0, 0, 0, topX, topY, topZ);

    drawAxes(gl);

    gl.glTranslatef(0, 0, -10);

    //start
    gl.glColorMask(false, false, false, false); //disable the color mask
    gl.glDepthMask(false); //disable the depth mask

    gl.glEnable(GL.GL_STENCIL_TEST); //enable the stencil testing

    gl.glStencilFunc(GL.GL_ALWAYS, 1, 0xFFFFFFFF);
    gl.glStencilOp(GL.GL_REPLACE, GL.GL_REPLACE, GL.GL_REPLACE); //set the stencil buffer to replace our next lot of data

    bench(gl); //set the data plane to be replaced

    gl.glColorMask(true, true, true, true); //enable the color mask
    gl.glDepthMask(true); //enable the depth mask

    gl.glStencilFunc(GL.GL_EQUAL, 1, 0xFFFFFFFF);
    gl.glStencilOp(GL.GL_KEEP, GL.GL_KEEP, GL.GL_KEEP); //set the stencil buffer to keep our next lot of data

    gl.glDisable(GL.GL_TEXTURE_2D); //disable texturing of the shadow
    gl.glDisable(GL.GL_DEPTH_TEST); //disable depth testing of the shadow
    gl.glPushMatrix();
    gl.glScalef(1.0f, -1.0f, 1.0f); //flip the shadow vertically

    gl.glTranslatef(0, 2, 0); //translate the shadow onto ourdrawing plane
    gl.glRotatef(angle, 0, 1, 0); //rotate the shadow accordingly

    gl.glColor4f(0, 0, 0, 1); //color the shadow black
    square(gl); //draw our square as the shadow
    gl.glPopMatrix();
    gl.glEnable(GL.GL_DEPTH_TEST); //enable depth testing
    gl.glEnable(GL.GL_TEXTURE_2D); //enable texturing

    gl.glDisable(GL.GL_STENCIL_TEST); //disable the stencil testing

    //end

    gl.glEnable(GL.GL_BLEND); //enable alpha blending
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); //set teh alpha blending

    bench(gl); //draw our bench

    gl.glDisable(GL.GL_BLEND); //disable alpha blending

    gl.glRotatef(angle, 0, 1, 0); //rotate the square
    square(gl); //draw the square

    angle++;
  }

  // --------------------------------------------------------------------------------

  private void square(GL gl) {
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, 2.5f, 0.0f);
    gl.glScalef(2.0f, 2.0f, 2.0f);

    texture1.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(1.0f, 0.0f);
    gl.glVertex3f(-1.0f, -1.0f, 0.0f);
    gl.glTexCoord2f(1.0f, 1.0f);
    gl.glVertex3f(-1.0f, 1.0f, 0.0f);
    gl.glTexCoord2f(0.0f, 1.0f);
    gl.glVertex3f(1.0f, 1.0f, 0.0f);
    gl.glTexCoord2f(0.0f, 0.0f);
    gl.glVertex3f(1.0f, -1.0f, 0.0f);
    gl.glEnd();

    gl.glPopMatrix();
  }

  // --------------------------------------------------------------------------------

  private void bench(GL gl) {
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, -2.5f, 0.0f);
    gl.glScalef(4.0f, 2.0f, 4.0f);

    gl.glColor4f(1.0f, 1.0f, 1.0f, 0.7f);

    texture2.bind();

    gl.glBegin(GL.GL_QUADS);

    gl.glTexCoord2f(1.0f, 0.0f);
    gl.glVertex3f(-1.0f, -1.0f, 1.0f);
    gl.glTexCoord2f(1.0f, 1.0f);
    gl.glVertex3f(-1.0f, 1.0f, -0.5f);
    gl.glTexCoord2f(0.0f, 1.0f);
    gl.glVertex3f(1.0f, 1.0f, -0.5f);
    gl.glTexCoord2f(0.0f, 0.0f);
    gl.glVertex3f(1.0f, -1.0f, 1.0f);
    gl.glEnd();

    gl.glPopMatrix();
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
    canvas.addGLEventListener(new Main());
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
