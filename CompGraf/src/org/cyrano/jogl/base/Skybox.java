package org.cyrano.jogl.base;

import javax.media.opengl.GL;

public class Skybox {

  public static void drawSkybox( //
      GL gl, float size, //
      float eyeX, float eyeY, float eyeZ) {

    // ---------------------------------------------------
    // culling is done ccw (right hand rule defines front)
    // ---------------------------------------------------
    //    gl.glEnable(GL.GL_CULL_FACE);
    // ----------------------------------------------------

    gl.glPushMatrix();
    gl.glTranslated(eyeX, eyeY, eyeZ);

    // ----------------------------------------
    // D+-----+C  ** *----->X (0,1)
    //  |     |   ** |+--+
    //  |     |   ** ||  | TEXTURES
    //  |     |   ** |+--+
    // A+-----+B  ** V Y (0, 1)
    // ----------------------------------------

    gl.glDisable(GL.GL_DEPTH_TEST);

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

    gl.glPopMatrix();

    gl.glEnable(GL.GL_DEPTH_TEST);
  }
}
