package org.cyrano.jogl.base;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Demián Gutierrez
 */
public interface Camera //
    extends
      MouseListener,
      MouseMotionListener,
      KeyListener {

  // --------------------------------------------------------------------------------

  public void drawCameraParameters(int x, int y);

  // --------------------------------------------------------------------------------

  public void updateCameraBox(int w, int h);

  public void updateCameraPos();

  // --------------------------------------------------------------------------------

  public float[] getCameraEye();

  public float[] getCameraFrn();

  public float[] getCameraTop();

  // --------------------------------------------------------------------------------

  public float[] getLookAt();
}
