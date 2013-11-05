package org.cyrano.jogl._14.framebuffer_4;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample {

  private boolean capture;

  // --------------------------------------------------------------------------------

  public Main() {
    initBaseExample(getClass().getName(), new CameraBall());
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | //
        /*   */GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    camera.updateCameraBox(getW(gl), getH(gl));
    camera.updateCameraPos();

    Primitives.drawAxes(gl);

    // ----------------------------------------

    gl.glColor3f(1.0f, 0.0f, 0.0f);
    Primitives.drawRect(gl, 1.0f, -0.25f);

    gl.glColor3f(0.0f, 1.0f, 0.0f);
    Primitives.drawRect(gl, 0.5f, +0.25f);

    gl.glFlush();

    capture(gl);
  }

  // --------------------------------------------------------------------------------

  private void capture(GL gl) {
    if (!capture) {
      return;
    }

    capture = false;

    // ----------------------------------------

    int w = getW(gl);
    int h = getH(gl);

    FloatBuffer pixelsRGB = FloatBuffer.allocate(w * h * 4);

    gl.glReadPixels( //
        0, 0, w, h, //
        GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, pixelsRGB);

    try {
      ImageIO.write(transformPixelsDepth(pixelsRGB, w, h), //
          "png", new FileOutputStream("depth.png"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // --------------------------------------------------------------------------------

  private BufferedImage transformPixelsDepth( //
      FloatBuffer pixelsSrc, int w, int h) {

    int[] pixelsTgt = new int[w * h];

    int currSrc = 0;
    int currTgt = 0;

    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {

        // ----------------------------------------
        // (0, 0) is at the lower left in viewport 
        // ----------------------------------------

        currSrc = (h - j - 1) * w + i;

        float curr = 1 - pixelsSrc.get(currSrc);

        int r = (int) (curr * 0xFF);
        int g = (int) (curr * 0xFF);
        int b = (int) (curr * 0xFF);

        int a = 0xFF;

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
  // KeyListener
  // --------------------------------------------------------------------------------

  public void keyTyped(KeyEvent evt) {
    switch (evt.getKeyChar()) {
      case 'D' :
      case 'd' :
        capture = true;
        break;
    }

    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
