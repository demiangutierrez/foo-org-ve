package org.cyrano.jogl.base;

import javax.media.opengl.GL;

public class Primitives {

  private Primitives() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static void drawRect(GL gl, float size, float z) {
    gl.glBegin(GL.GL_QUADS);

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

  public static void drawAxes(GL gl) {
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
}
