package org.cyrano.jogl._20.skybox;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;
import org.cyrano.jogl.base.Skybox;
import org.cyrano.jogl.base.TextureCache;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample {

  public Main() {
    TextureCache.init("textures");

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

    gl.glDisable(GL.GL_TEXTURE_2D);
    Primitives.drawAxes(gl);

    gl.glColor3f(1, 1, 1);

    double size = 2;

    // ----------------------------------------
    // D+-----+C  ** *----->X (0,1)
    //  |     |   ** |+--+
    //  |     |   ** ||  | TEXTURES
    //  |     |   ** |+--+
    // A+-----+B  ** V Y (0, 1)
    // ----------------------------------------

    gl.glEnable(GL.GL_TEXTURE_2D);

    TextureCache.getInstance().getTexture("alpine_n.bmp").bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * size, -1 * size, -1 * size); // A
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * size, -1 * size, -1 * size); // B
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * size, +1 * size, -1 * size); // C
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * size, +1 * size, -1 * size); // D
    gl.glEnd();

    TextureCache.getInstance().getTexture("alpine_s.bmp").bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(+1 * size, -1 * size, +1 * size); // A
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(-1 * size, -1 * size, +1 * size); // B
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(-1 * size, +1 * size, +1 * size); // C
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(+1 * size, +1 * size, +1 * size); // D
    gl.glEnd();

    TextureCache.getInstance().getTexture("alpine_e.bmp").bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(+1 * size, -1 * size, -1 * size); // A
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * size, -1 * size, +1 * size); // B
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * size, +1 * size, +1 * size); // C
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(+1 * size, +1 * size, -1 * size); // D
    gl.glEnd();

    TextureCache.getInstance().getTexture("alpine_w.bmp").bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * size, -1 * size, +1 * size); // A
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(-1 * size, -1 * size, -1 * size); // B
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(-1 * size, +1 * size, -1 * size); // C
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * size, +1 * size, +1 * size); // D
    gl.glEnd();

    TextureCache.getInstance().getTexture("alpine_d.bmp").bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * size, -1 * size, +1 * size); // A
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * size, -1 * size, +1 * size); // B
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * size, -1 * size, -1 * size); // C
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * size, -1 * size, -1 * size); // D
    gl.glEnd();

    TextureCache.getInstance().getTexture("alpine_u.bmp").bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * size, +1 * size, -1 * size); // C
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * size, +1 * size, -1 * size); // D
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * size, +1 * size, +1 * size); // A
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * size, +1 * size, +1 * size); // B
    gl.glEnd();
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
