package org.cyrano.util.keyboard;

import java.awt.KeyboardFocusManager;

import javax.swing.JFrame;

public class InputManagerTestFrame extends JFrame {

  public InputManagerTestFrame() {

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    setSize(400, 400);
    setLocation(50, 50);

    setVisible(true);

    KeyboardFocusManager.getCurrentKeyboardFocusManager(). //
        addKeyEventDispatcher(new InputManager());
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new InputManagerTestFrame();
  }
}
