package org.cyrano.jogl.base;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface Camera //
    extends
      MouseListener,
      MouseMotionListener,
      KeyListener {

  // --------------------------------------------------------------------------------

  public void updateCameraPos();

  public void updateCameraBox();
}
