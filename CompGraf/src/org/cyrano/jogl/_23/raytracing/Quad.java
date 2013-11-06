package org.cyrano.jogl._23.raytracing;

import javax.media.opengl.GL;

public class Quad {

  public double[] vertexArray;

  public double colorR;
  public double colorG;
  public double colorB;

  // --------------------------------------------------------------------------------

  public void draw(GL gl) {
    gl.glColor3d(colorR, colorG, colorB);

    gl.glBegin(GL.GL_QUADS);

    for (int i = 0; i < vertexArray.length; i += 3) {
      gl.glVertex3d( //
          vertexArray[i + 0], //
          vertexArray[i + 1], //
          vertexArray[i + 2]);
    }

    gl.glEnd();
  }
}
