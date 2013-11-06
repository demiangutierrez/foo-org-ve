package org.cyrano.jogl._15.pick_2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraFree;
import org.cyrano.jogl.base.Primitives;

import com.sun.opengl.util.GLUT;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample {

  private List<Quad> quadList = new ArrayList<Quad>();

  // --------------------------------------------------------------------------------

  private boolean showHit = false;

  private double hitX;
  private double hitY;
  private double hitZ;

  // --------------------------------------------------------------------------------

  public Main() {
    initBaseExample(getClass().getName(), new CameraFree());

    // ----------------------------------------
    // init geometry
    // ----------------------------------------

    Quad quad;

    quad = new Quad();
    quad.vertexArray = new double[]{ //
    /**/-0.50, -0.50, -0.75, //
        -0.50, +0.50, -0.75, //
        +0.50, +0.50, -0.75, //
        +0.50, -0.50, -0.75};
    quad.colorR = 1.0;
    quad.colorG = 0.0;
    quad.colorB = 0.0;
    quadList.add(quad);

    quad = new Quad();
    quad.vertexArray = new double[]{ //
    /**/-0.25, -0.25, +0.75, //
        -0.25, +0.25, +0.75, //
        +0.25, +0.25, +0.75, //
        +0.25, -0.25, +0.75};
    quad.colorR = 0.0;
    quad.colorG = 1.0;
    quad.colorB = 0.0;
    quadList.add(quad);

    // ----------------------------------------
    // init camera
    // ----------------------------------------

    camera = new CameraFree();
  }

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    super.init(drawable);

    drawable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        Main.this.mouseClicked(evt);
      }
    });
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

    for (Quad quad : quadList) {
      quad.draw(gl);
    }

    // ----------------------------------------

    drawHit(gl);

    switchToOrtho(gl);

    drawHUD(gl);

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------

  private void drawHit(GL gl) {
    if (!showHit) {
      return;
    }

    GLUT glut = new GLUT();

    gl.glPushMatrix();

    gl.glTranslated(hitX, hitY, hitZ);
    gl.glColor3f(1.0f, 1.0f, 0.0f);

    glut.glutSolidSphere(0.1, 10, 10);

    gl.glPopMatrix();
  }

  // --------------------------------------------------------------------------------

  private void drawHUD(GL gl) {
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_DEPTH_BUFFER_BIT);

    int w = getW(gl);
    int h = getH(gl);

    gl.glColor3f(0.2f, 0.2f, 1.0f);

    gl.glBegin(GL.GL_LINES);
    gl.glVertex2d(w / 2 - 10, h / 2);
    gl.glVertex2d(w / 2 + 10, h / 2);

    gl.glVertex2d(w / 2, h / 2 - 10);
    gl.glVertex2d(w / 2, h / 2 + 10);
    gl.glEnd();
  }

  // --------------------------------------------------------------------------------

  private void switchToOrtho(GL gl) {
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glPushMatrix();
    gl.glLoadIdentity();
    gl.glOrtho(0, getW(gl), 0, getH(gl), -5, 1);
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();
  }

  // --------------------------------------------------------------------------------
  // MouseListener
  // --------------------------------------------------------------------------------

  private void mouseClicked(MouseEvent evt) {
    if (evt.getButton() != MouseEvent.BUTTON3) {
      return;
    }

    float[] lookAt = camera.getLookAt();

    Ray ray = new Ray( //
        lookAt[0], lookAt[1], lookAt[2], //
        lookAt[3], lookAt[4], lookAt[5]);

    List<RayInfo> rayInfoList = new ArrayList<RayInfo>();

    for (Quad quad : quadList) {
      RayInfo rayInfo = new RayInfo();

      rayInfo.quad = quad;
      rayInfo.t = ray.calcIntersects(quad.vertexArray);
      rayInfo.inside = isPointInsideQuad( //
          ray.calcX(rayInfo.t), //
          ray.calcY(rayInfo.t), //
          ray.calcZ(rayInfo.t), //
          quad.vertexArray);

      rayInfoList.add(rayInfo);
    }

    Collections.sort(rayInfoList, new RayInfoComparator());

    RayInfo rayInfo = rayInfoList.get(0);

    if (rayInfo.t > 0 && rayInfo.inside) {
      hitX = ray.calcX(rayInfo.t);
      hitY = ray.calcY(rayInfo.t);
      hitZ = ray.calcZ(rayInfo.t);
      showHit = true;
    } else {
      showHit = false;
    }

    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public boolean isPointInsideQuad( //
      double x, double y, double z, double[] quad) {

    double EPSILON = Math.PI / 100;

    double angle = 0;

    int npoints = quad.length / 3;

    for (int i = 0; i < npoints; i++) {
      double v1x = quad[3 * (i + 0) + 0] - x;
      double v1y = quad[3 * (i + 0) + 1] - y;
      double v1z = quad[3 * (i + 0) + 2] - z;

      double v2x = quad[3 * ((i + 1) % npoints) + 0] - x;
      double v2y = quad[3 * ((i + 1) % npoints) + 1] - y;
      double v2z = quad[3 * ((i + 1) % npoints) + 2] - z;

      double m1 = Math.sqrt(v1x * v1x + v1y * v1y + v1z * v1z);
      double m2 = Math.sqrt(v2x * v2x + v2y * v2y + v2z * v2z);

      double dot = v1x * v2x + v1y * v2y + v1z * v2z;
      double cos = dot / (m1 * m2);

      angle += Math.acos(cos);
    }

    return Math.abs(angle - 2 * Math.PI) < EPSILON;
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
