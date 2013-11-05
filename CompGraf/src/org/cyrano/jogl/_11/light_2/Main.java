package org.cyrano.jogl._11.light_2;

import java.awt.event.KeyEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;

import org.cyrano.jogl.base.BaseExample;
import org.cyrano.jogl.base.CameraBall;
import org.cyrano.jogl.base.Primitives;
import org.cyrano.jogl.base.TestLights;

/**
 * @author Demián Gutierrez
 */
public class Main extends BaseExample {

  public boolean DRAW_SOLIDBODY = true;
  public boolean DRAW_WIREFRAME = false;

  public boolean DRAW_NORMALS = false;
  public boolean TRIA_NORMALS = false;

  public int DETAIL_LEVEL = 3;

  // --------------------------------------------------------------------------------

  private TestLights testLights;

  private GLCanvas glCanvas;

  // --------------------------------------------------------------------------------

  public Main() {
    initBaseExample(getClass().getName(), new CameraBall());
  }

  // --------------------------------------------------------------------------------

  public void init(GLAutoDrawable drawable) {
    super.init(drawable);

    testLights = new TestLights();
    testLights.createLight(drawable.getGL());
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

    gl.glDisable(GL.GL_LIGHTING);
    Primitives.drawAxes(gl);
    gl.glEnable(GL.GL_LIGHTING);

    testLights.calculateSpin();

    testLights.drawAllLights(gl);

    // ----------------------------------------
    // avoid stitching!
    // ----------------------------------------

    gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
    gl.glPolygonOffset(1.0f, 1.0f);

    // ----------------------------------------

    if (DRAW_SOLIDBODY) {
      gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);

      testLights.setMaterial(gl, 1, 1, 1, 100);

      Icosahedron.triaNormals = TRIA_NORMALS;
      Icosahedron.drawNormals = DRAW_NORMALS;
      Icosahedron.drawIcosahedron(gl, DETAIL_LEVEL);
    }

    if (DRAW_WIREFRAME) {
      gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);

      testLights.setMaterial(gl, 1, 1, 1, 32);

      Icosahedron.triaNormals = TRIA_NORMALS;
      Icosahedron.drawNormals = DRAW_NORMALS;
      Icosahedron.drawIcosahedron(gl, DETAIL_LEVEL);
    }

    gl.glFlush();
  }

  // --------------------------------------------------------------------------------
  // KeyListener
  // --------------------------------------------------------------------------------

  public void keyTyped(KeyEvent evt) {
    switch (evt.getKeyChar()) {
      case 'P' :
      case 'p' :
        testLights.switchAnimate();
        break;
    }

    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
