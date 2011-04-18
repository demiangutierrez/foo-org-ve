package org.cyrano.game;

import java.awt.event.ActionEvent;
import java.util.EventListener;

public interface RepaintListener extends EventListener {

  public void repaintRequest(ActionEvent evt);
}
