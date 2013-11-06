package org.cyrano.jogl._21.camerafp;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLJPanel;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraFrst;
import org.cyrano.jogl.base.Skybox;
import org.cyrano.jogl.base.TextureCache;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample {

  private static final String FLOOR1_TEXTURE = "floor1.bmp";
  private static final String FLOOR2_TEXTURE = "floor2.bmp";

  // --------------------------------------------------------------------------------

  public Main() {
    TextureCache.init("textures");

    initBaseExample(getClass().getName(), new CameraFrst());
  }

  // --------------------------------------------------------------------------------

  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    if ((drawable instanceof GLJPanel) && !((GLJPanel) drawable).isOpaque()
        && ((GLJPanel) drawable).shouldPreserveColorBufferIfTranslucent()) {
      gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
    } else {
      gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }

    gl.glPushMatrix();
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();

    camera.updateCameraBox(getW(gl), getH(gl));
    camera.updateCameraPos();

    float[] eye = camera.getCameraEye();

    Skybox.drawSkybox(gl, 4, eye[0], eye[1], eye[2]);

    double tileSize = +1.5;
    double tileYOff = -1.2;

    int floorWH = 80;

    gl.glEnable(GL.GL_TEXTURE_2D);
    gl.glEnable(GL.GL_DEPTH_TEST);

    // ----------------------------------------
    // D+-----+C  ** *----->X (0,1)
    //  |     |   ** |+--+
    //  |     |   ** ||  | TEXTURES
    //  |     |   ** |+--+
    // A+-----+B  ** V Y (0, 1)
    // ----------------------------------------

    String curtex = FLOOR1_TEXTURE;

    for (int i = 0; i < floorWH; i++) {
      for (int j = 0; j < floorWH; j++) {
        double x = j * tileSize - tileSize * floorWH / 2;
        double y = i * tileSize - tileSize * floorWH / 2;

        TextureCache.getInstance().getTexture(curtex).bind();

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0f, 0f);
        gl.glVertex3d(x, tileYOff, y); // C
        gl.glTexCoord2f(1f, 0f);
        gl.glVertex3d(x, tileYOff, y + tileSize); // D
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3d(x + tileSize, tileYOff, y + tileSize); // A
        gl.glTexCoord2f(0f, 1f);
        gl.glVertex3d(x + tileSize, tileYOff, y); // B
        gl.glEnd();

        curtex = curtex == FLOOR1_TEXTURE ? FLOOR2_TEXTURE : FLOOR1_TEXTURE;
      }

      if (floorWH % 2 == 0) {
        curtex = curtex == FLOOR1_TEXTURE ? FLOOR2_TEXTURE : FLOOR1_TEXTURE;
      }
    }

    gl.glPopMatrix();
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
