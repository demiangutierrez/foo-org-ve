package org.cyrano.jogl._21.camerafp;

import java.io.InputStream;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import org.cyrano.jogl.util.Matrix;
import org.cyrano.jogl.util.MatrixOps;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

/**
 * @author Demián Gutierrez
 */
public class Camera {

  public enum Direction {
    FRN, BCK, LFT, RGH
  }

  // --------------------------------------------------------------------------------

  private GLU glu = new GLU();

  private static final double SKYBOX_SIZE = 4;

  private Texture skybox_u;
  private Texture skybox_d;
  private Texture skybox_n;
  private Texture skybox_s;
  private Texture skybox_e;
  private Texture skybox_w;

  // --------------------------------------------------------------------------------

  private double eyeX = +0;
  private double eyeY = +0;
  private double eyeZ = +0;

  private double frnX = +0;
  private double frnY = +0;
  private double frnZ = +1;

  private double topX = +0;
  private double topY = +1;
  private double topZ = +0;

  // --------------------------------------------------------------------------------

  public Camera() {
    try {
      InputStream is;
      TextureData textureData;

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_u.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_u = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_d.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_d = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_n.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_n = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_s.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_s = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_e.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_e = TextureIO.newTexture(textureData);

      is = ClassLoader.getSystemResourceAsStream("textures/alpine_w.bmp");
      textureData = TextureIO.newTextureData(is, false, "bmp");
      skybox_w = TextureIO.newTexture(textureData);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // --------------------------------------------------------------------------------

  public void rotate(float thetaX, float thetaY) {
    Matrix curFrn = new Matrix(4, 1);
    curFrn.setData( //
        new double[]{frnX, frnY, frnZ, 1});

    Matrix curTop = new Matrix(4, 1);
    curTop.setData( //
        new double[]{topX, topY, topZ, 1});

    Matrix ry = MatrixOps.rotateY(thetaY);

    Matrix newFrn = MatrixOps.matrixMult(ry, curFrn);
    Matrix newTop = MatrixOps.matrixMult(ry, curTop);

    curFrn = newFrn;
    curTop = newTop;

    Matrix curSid = MatrixOps.crossProduct(curFrn, curTop);

    Matrix rs = MatrixOps.rotate( //
        -thetaX, curSid.get(0, 0), curSid.get(1, 0), curSid.get(2, 0));

    newFrn = MatrixOps.matrixMult(rs, curFrn);
    newTop = MatrixOps.matrixMult(rs, curTop);

    frnX = newFrn.get(0, 0);
    frnY = newFrn.get(1, 0);
    frnZ = newFrn.get(2, 0);

    topX = newTop.get(0, 0);
    topY = newTop.get(1, 0);
    topZ = newTop.get(2, 0);
  }

  // --------------------------------------------------------------------------------

  public void update() {
    Matrix trans = MatrixOps.translate(eyeX, eyeY, eyeZ);

    Matrix curFrn = new Matrix(4, 1);
    curFrn.setData( //
        new double[]{frnX, frnY, frnZ, 1});

    // ------------------------------------------------------------
    // BEWARE: top needs no translation
    //Matrix curTop = new Matrix(4, 1);
    //curTop.setData( //
    //    new double[]{camera.topX, camera.topY, camera.topZ, 1});
    // ------------------------------------------------------------

    Matrix traFrn = MatrixOps.matrixMult(trans, curFrn);
    // ------------------------------------------------------------
    //Matrix traTop = MatrixOps.matrixMult(trans, curTop);
    // ------------------------------------------------------------

    glu.gluLookAt(//
        eyeX, eyeY, eyeZ, //
        traFrn.get(0, 0), traFrn.get(1, 0), traFrn.get(2, 0), //
        topX, topY, topZ);
  }

  // --------------------------------------------------------------------------------

  private Matrix calculateSide() {
    Matrix curFrn = new Matrix(4, 1);
    curFrn.setData( //
        new double[]{frnX, frnY, frnZ, 1});

    Matrix curTop = new Matrix(4, 1);
    curTop.setData( //
        new double[]{topX, topY, topZ, 1});

    return MatrixOps.crossProduct(curFrn, curTop);
  }

  // --------------------------------------------------------------------------------

  public void drawSkybox() {
    GL gl = GLU.getCurrentGL();

    // ---------------------------------------------------
    // culling is done ccw (right hand rule defines front)
    // ---------------------------------------------------
    gl.glEnable(GL.GL_CULL_FACE);
    // ----------------------------------------------------

    gl.glEnable(GL.GL_TEXTURE_2D);
    gl.glDisable(GL.GL_DEPTH_TEST);

    gl.glPushMatrix();
    gl.glTranslated(eyeX, eyeY, eyeZ);

    // ----------------------------
    // D+-----+C  ** ^ Y (0, 1)
    //  |     |   ** |+--+
    //  |     |   ** ||  | TEXTURES
    //  |     |   ** |+--+
    // A+-----+B  ** *----->X (0,1)
    // ----------------------------

    skybox_n.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glEnd();

    skybox_s.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glEnd();

    skybox_e.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glEnd();

    skybox_w.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glEnd();

    skybox_d.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glEnd();

    skybox_u.bind();

    gl.glBegin(GL.GL_QUADS);
    gl.glTexCoord2f(0f, 0f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 0f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, -1 * SKYBOX_SIZE);
    gl.glTexCoord2f(1f, 1f);
    gl.glVertex3d(+1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glTexCoord2f(0f, 1f);
    gl.glVertex3d(-1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE, +1 * SKYBOX_SIZE);
    gl.glEnd();

    gl.glPopMatrix();
  }

  // --------------------------------------------------------------------------------

  public void move(Direction direction, double step) {
    switch (direction) {
      case FRN :
        moveFrnBck(+frnX, +frnZ, step);
        break;
      case BCK :
        moveFrnBck(-frnX, -frnZ, step);
        break;
      case LFT : {
        Matrix curSid = calculateSide();
        moveLftRgh(-curSid.get(0, 0), -curSid.get(2, 0), step);
        break;
      }
      case RGH : {
        Matrix curSid = calculateSide();
        moveLftRgh(+curSid.get(0, 0), +curSid.get(2, 0), step);
        break;
      }
      default :
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void moveLftRgh(double mx, double mz, double step) {
    double mod = Math.sqrt( //
        mx * mx + mz * mz);

    mx /= mod;
    mz /= mod;

    eyeX = eyeX + mx * step;
    eyeZ = eyeZ + mz * step;
  }

  private void moveFrnBck(double mx, double mz, double step) {
    double mod = Math.sqrt( //
        frnX * frnX + frnZ * frnZ);

    mx /= mod;
    mz /= mod;

    eyeX = eyeX + mx * step;
    eyeZ = eyeZ + mz * step;
  }
}
