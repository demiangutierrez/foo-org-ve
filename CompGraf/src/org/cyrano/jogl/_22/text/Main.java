package org.cyrano.jogl._22.text;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import org.cyrano.jogl.base.BaseExample;

import com.sun.opengl.util.GLUT;

public class Main extends BaseExample implements KeyListener {

  public Main() {
    initBaseExample(getClass().getName());
  }

  public void display(GLAutoDrawable drawable) {

    String[] fonts = {"BitMap 9 by 15", "BitMap 8 by 13",

    "Times Roman 10 Point ", "Times Roman 24 Point ",

    "Helvetica 10 Point ", "Helvetica 12 Point ", "Helvetica 18 Point "};

    final GL gl = drawable.getGL();

    final GLUT glut = new GLUT();

    gl.glClear(GL.GL_COLOR_BUFFER_BIT); // Set display window to color.

    gl.glColor3f(1.0f, 0.0f, 0.0f); // Set text e.color to black

    gl.glMatrixMode(GL.GL_MODELVIEW);

    gl.glLoadIdentity();

    int x = 20, y = 15;

    for (int i = 0; i < 7; i++) {

      gl.glRasterPos2i(x, y); // set position

      glut.glutBitmapString(i + 2, fonts[i]);

      y += 20;

    }

  }

  public void init(GLAutoDrawable drawable) {

    final GL gl = drawable.getGL();

    final GLU glu = new GLU();

    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f); //set background to white

    glu.gluOrtho2D(0.0, 200.0, 0.0, 150.0); // define drawing area

    drawable.addKeyListener(this);

  }

  public void keyPressed(KeyEvent e)

  {

    if (e.getKeyCode() == KeyEvent.VK_ESCAPE)

      System.exit(0);

  }

  public void keyReleased(KeyEvent e) {
  }

  public void keyTyped(KeyEvent e) {
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    GL gl = drawable.getGL();
    gl.glViewport(0, 0, w, h);

    //    camera.updateCameraBox();
  }
}