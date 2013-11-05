package org.cyrano.jogl._07.objects;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

public class Body {

  public List<Body> bodyList = new ArrayList<Body>();

  public Body parent;

  // --------------------------------------------------------------------------------

  public float bR;
  public float bG;
  public float bB;

  // --------------------------------------------------------------------------------

  public float x;
  public float y;
  public float z;

  // --------------------------------------------------------------------------------

  public float scale = 1;

  public float parentDist;

  public float parentZRot; // (1)
  public float parentYRot; // (2)

  public float parRotAng;
  public float parRotVel;

  public float slfRotVel = 50;
  public float slfRotAng = 0;

  // --------------------------------------------------------------------------------

  private void updateSlfRot(float dt) {
    slfRotAng = dt / 1000f * slfRotVel;
  }

  private void updateParRot(float dt) {
    parRotAng = dt / 1000f * parRotVel;
  }

  // --------------------------------------------------------------------------------

  public void draw(GL gl, float dt) {
    updateSlfRot(dt);
    updateParRot(dt);

    gl.glPushMatrix(); // A

    gl.glRotated(+parentYRot, 0, 1, 0);
    gl.glRotated(+parentZRot, 0, 0, 1);

    if (parent == null) {
      gl.glTranslatef(x, y, z);
    } else {
      gl.glRotated(+parRotAng, 0, 1, 0);
      gl.glTranslatef(parentDist, 0, 0);
      gl.glRotated(-parRotAng, 0, 1, 0);
    }

    gl.glPushMatrix(); // B

    gl.glScalef(scale, scale, scale);
    gl.glRotated(slfRotAng, 0, 1, 0);

    gl.glPushMatrix();
    gl.glColor3f(bR / 1, bG / 1, bB / 1);
    gl.glRotatef(180f, 0, 1, 0);
    gl.glPushMatrix();
    gl.glColor3f(bR / 1, bG / 1, bB / 1);
    gl.glRotatef(180f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(bR / 2, bG / 2, bB / 2);
    gl.glRotatef(0f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(bR / 3, bG / 3, bB / 3);
    gl.glRotatef(-90f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(bR / 4, bG / 4, bB / 4);
    gl.glRotatef(90f, 0, 1, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(bR / 5, bG / 5, bB / 5);
    gl.glRotatef(-90f, 1, 0, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glColor3f(bR / 6, bG / 6, bB / 6);
    gl.glRotatef(90f, 1, 0, 0);
    drawFace(gl);
    gl.glPopMatrix();

    gl.glPopMatrix(); // B

    for (Body body : bodyList) {
      body.draw(gl, dt);
    }

    gl.glPopMatrix(); // A
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
