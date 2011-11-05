package org.cyrano.jogl._5.animator_2;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

public class CelestialBody {

  public CelestialBody parent;

  public float x;
  public float y;
  public float z;

  public float parentDist;
  //  public float parentNAng;
  //  public float parentEAng;
  public float parentRAng;
  public float parentRVel;
  //

  public float scale = 1;

  public float ang = 0;
  public float vel = 50;

  public List<CelestialBody> children = new ArrayList<CelestialBody>();

  private void calculateSpin(float dt) {
    ang = dt / 1000f * vel;
  }

  private void calculateSpin2(float dt) {
    parentRAng = dt / 1000f * parentRVel;
  }

  public void draw(GL gl, float dt) {
    calculateSpin(dt);
    calculateSpin2(dt);

    gl.glPushMatrix();

    if (parent == null) {
      gl.glTranslatef(x, y, z);
    } else {
      gl.glRotated(parentRAng, 0, 1, 0);
      gl.glTranslatef(parentDist, 0, 0);
      gl.glRotated(-parentRAng, 0, 1, 0);
    }

    gl.glPushMatrix();
    
    gl.glScalef(scale, scale, scale);
    gl.glRotated(ang, 0, 1, 0);

    gl.glPushMatrix();
    gl.glColor3f(0, 0, 1); // AZ
    gl.glRotatef(180f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(0, 1, 0); // VE
    gl.glRotatef(0f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(0, 1, 1); // CE
    gl.glRotatef(-90f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(1, 0, 0); // RO
    gl.glRotatef(90f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(1, 0, 1); // MA
    gl.glRotatef(-90f, 1, 0, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(1, 1, 0); // AM
    gl.glRotatef(90f, 1, 0, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPopMatrix();

    for (CelestialBody celestialBody : children) {
      celestialBody.draw(gl, dt);
    }

    gl.glPopMatrix();
  }

  // --------------------------------------------------------------------------------

  private void drawFace(GL gl) {
    gl.glBegin(GL.GL_QUADS);
    gl.glVertex3f(-0.5f, -0.5f, +0.5f);
    gl.glVertex3f(+0.5f, -0.5f, +0.5f);
    gl.glVertex3f(+0.5f, +0.5f, +0.5f);
    gl.glVertex3f(-0.5f, +0.5f, +0.5f);
    gl.glEnd();
  }
}
