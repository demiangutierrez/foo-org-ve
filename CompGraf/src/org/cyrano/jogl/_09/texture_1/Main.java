package org.cyrano.jogl._09.texture_1;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

/**
 * @author Demián Gutierrez
 */
public class Main implements GLEventListener {

  private Texture texture;

  // --------------------------------------------------------------------------------

  private void loadTexture() {
    try {
      InputStream is;
      TextureData textureData;

      is = ClassLoader.getSystemResourceAsStream("textures/wood-fence_256x256.jpg");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      texture = TextureIO.newTexture(textureData);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
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

    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();
    gl.glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);

    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glEnable(GL.GL_TEXTURE_2D);

    texture.bind();

    // NORMAL
    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0.0f, 0.0f);
    gl.glVertex3f(-0.5f, -0.5f, 0f);
    gl.glTexCoord2f(0.0f, 1.0f);
    gl.glVertex3f(-0.5f, +0.5f, 0f);
    gl.glTexCoord2f(1.0f, 1.0f);
    gl.glVertex3f(+0.5f, +0.5f, 0f);
    gl.glTexCoord2f(1.0f, 0.0f);
    gl.glVertex3f(+0.5f, -0.5f, 0f);
    gl.glEnd();

    // ROTATED
    //    gl.glBegin(GL.GL_QUADS);
    //    gl.glTexCoord2f(1.0f, 0.0f);
    //    gl.glVertex3f(-0.5f, -0.5f, 0f);
    //    gl.glTexCoord2f(0.0f, 0.0f);
    //    gl.glVertex3f(-0.5f, +0.5f, 0f);
    //    gl.glTexCoord2f(0.0f, 1.0f);
    //    gl.glVertex3f(+0.5f, +0.5f, 0f);
    //    gl.glTexCoord2f(1.0f, 1.0f);
    //    gl.glVertex3f(+0.5f, -0.5f, 0f);
    //    gl.glEnd();

    // REPEATED
    // ----------------------------------------
    // this should be outside of Beg/End block
    // ----------------------------------------
    //    gl.glTexParameteri(GL.GL_TEXTURE_2D, //
    //        GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
    //    gl.glTexParameteri(GL.GL_TEXTURE_2D, //
    //        GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
    //
    //    gl.glBegin(GL.GL_QUADS);
    //    gl.glTexCoord2f(0.0f, 0.0f);
    //    gl.glVertex3f(-0.5f, -0.5f, 0f);
    //    gl.glTexCoord2f(0.0f, 3.0f);
    //    gl.glVertex3f(-0.5f, +0.5f, 0f);
    //    gl.glTexCoord2f(3.0f, 3.0f);
    //    gl.glVertex3f(+0.5f, +0.5f, 0f);
    //    gl.glTexCoord2f(3.0f, 0.0f);
    //    gl.glVertex3f(+0.5f, -0.5f, 0f);
    //    gl.glEnd();

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  public void displayChanged(GLAutoDrawable drawable, //
      boolean modeChanged, boolean deviceChanged) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    Frame frame = new Frame("JOGL Main");

    GLCanvas canvas = new GLCanvas();
    canvas.addGLEventListener(new Main());

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
