package org.cyrano.jogl._11;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class LightParent {

  protected double degSec = 50;
  protected double degAct = 0;

  protected long prevTime;
  protected long currTime;

  //@begnf
  protected float[] black  = {0.0f, 0.0f, 0.0f, 1.0f};
  protected float[] white  = {1.0f, 1.0f, 1.0f, 1.0f};
  protected float[] lgray  = {0.5f, 0.5f, 0.5f, 1.0f};
  protected float[] dgray  = {0.2f, 0.2f, 0.2f, 1.0f};
  protected float[] green  = {0.0f, 1.0f, 0.0f, 1.0f};
  protected float[] red    = {1.0f, 0.0f, 0.0f, 1.0f};
  protected float[] blue   = {0.0f, 0.0f, 1.0f, 1.0f};
  protected float[] yellow = {1.0f, 1.0f, 0.0f, 1.0f};
  //@endnf

  public boolean animate = true;

  // --------------------------------------------------------------------------------

  public LightParent() {
    super();
  }

  // --------------------------------------------------------------------------------

  public void calculateSpin() {
    if (!animate) {
      return;
    }

    currTime = System.currentTimeMillis();

    if (prevTime != 0) {
      degAct += (currTime - prevTime) / 1000f * degSec;
    }

    prevTime = currTime;
  }

  // --------------------------------------------------------------------------------

  public void createLight(GL gl) {
    gl.glEnable(GL.GL_LIGHTING);

    // ----------------------------------------

    //    gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, //
    //        lgray, 0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, //
        white, 0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, //
        white, 0);

    gl.glLightf(GL.GL_LIGHT0, GL.GL_CONSTANT_ATTENUATION, 1.0f);

    gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF, 50);

    gl.glEnable(GL.GL_LIGHT0);

    // ----------------------------------------

    //    gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, //
    //        lgray, 0);
    gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, //
        red, 0);
    gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, //
        red, 0); // Change spec light :) :) !!!

    gl.glLightf(GL.GL_LIGHT1, GL.GL_CONSTANT_ATTENUATION, 1.0f);

    //gl.glLightf(GL.GL_LIGHT1, GL.GL_SPOT_CUTOFF, 10);
    gl.glLightf(GL.GL_LIGHT1, GL.GL_SPOT_CUTOFF, 180);

    gl.glEnable(GL.GL_LIGHT1);

    // ----------------------------------------

    //    gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, //
    //        lgray, 0);
    gl.glLightfv(GL.GL_LIGHT2, GL.GL_DIFFUSE, //
        green, 0);
    gl.glLightfv(GL.GL_LIGHT2, GL.GL_SPECULAR, //
        red, 0);

    gl.glLightf(GL.GL_LIGHT2, GL.GL_CONSTANT_ATTENUATION, 1.0f);

    //gl.glLightf(GL.GL_LIGHT2, GL.GL_SPOT_CUTOFF, 10);
    gl.glLightf(GL.GL_LIGHT2, GL.GL_SPOT_CUTOFF, 180);

    gl.glEnable(GL.GL_LIGHT2);

    // ----------------------------------------

    //gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, lgray, 0);
  }

  // --------------------------------------------------------------------------------

  public void drawLight(GL gl, int lid, float[] src, float[] tgt) {
    gl.glLightfv(lid, GL.GL_POSITION, //
        src, 0);
    gl.glLightfv(lid, GL.GL_SPOT_DIRECTION, //
        tgt, 0);

    GLUT glut = new GLUT();

    gl.glPushMatrix();
    gl.glDisable(GL.GL_LIGHTING);

    float[] color = new float[4];
    gl.glGetLightfv(lid, GL.GL_DIFFUSE, color, 0);
    gl.glColor3f(color[0], color[1], color[2]);

    gl.glTranslatef(src[0], src[1], src[2]);
    glut.glutSolidSphere(0.1, 10, 10);

    gl.glEnable(GL.GL_LIGHTING);
    gl.glPopMatrix();
  }

  public void drawAllLights(GL gl) {
    gl.glPushMatrix();
    gl.glRotated(degAct, 0, 1, 0);

    drawLight(gl, GL.GL_LIGHT0, //
        new float[]{+2.0f, +2.0f, +2.0f, 1.0f}, //
        new float[]{-2.0f, -2.0f, -2.0f, 1.0f});
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glRotated(degAct / 2, 1, 0, 0);
    drawLight(gl, GL.GL_LIGHT1, //
        new float[]{+2.0f, +2.0f, -2.0f, 1.0f}, //
        new float[]{-2.0f, -2.0f, +2.0f, 1.0f});
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glRotated(degAct / 3, 0, 0, 1);
    drawLight(gl, GL.GL_LIGHT2, //
        new float[]{+1.0f, +1.0f, -3.0f, 1.0f}, //
        new float[]{-1.0f, -1.0f, +3.0f, 1.0f});
    gl.glPopMatrix();
  }

  // --------------------------------------------------------------------------------

  public void setMaterial(GL gl) {
    setMaterial(gl, 1, 1, 1, 1);
  }

  // --------------------------------------------------------------------------------

  public void setMaterial(GL gl, float r, float g, float b, float s) {
    setMaterialProperty(gl, GL.GL_AMBIENT_AND_DIFFUSE, r / 1.2f, g / 1.2f, b / 1.2f);
    setMaterialProperty(gl, GL.GL_SPECULAR, r, g, b);
    // setMaterialProperty(gl, GL.GL_EMISSION, r / 2, g / 2, b / 2);
    setMaterialProperty(gl, GL.GL_EMISSION, 0, 0, 0);
    setMaterialProperty(gl, GL.GL_SHININESS, s);
    gl.glColor3f(r, g, b);
  }

  // --------------------------------------------------------------------------------

  private void setMaterialProperty(GL gl, int property, float r, float g, float b) {
    gl.glMaterialfv(GL.GL_FRONT_AND_BACK, property, //
        new float[]{r, g, b, 1}, 0);
  }

  // --------------------------------------------------------------------------------

  private void setMaterialProperty(GL gl, int property, float v) {
    gl.glMaterialf(GL.GL_FRONT_AND_BACK, property, v);
  }
}
