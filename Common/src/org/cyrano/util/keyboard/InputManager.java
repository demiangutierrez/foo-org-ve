package org.cyrano.util.keyboard;

import java.awt.KeyEventDispatcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

public class InputManager implements KeyEventDispatcher {

  private boolean[] keyState = new boolean[256];

  private int repeatingKey = -1;

  private Timer timer;

  // --------------------------------------------------------------------------------

  private void execPrs(int keyCode) {
    //    System.err.println( //
    //        System.currentTimeMillis() + " ****KEY_P**** " + keyCode);

    if (keyState[keyCode] == true) {
      throw new IllegalStateException();
    }

    keyState[keyCode] = true;
  }

  // --------------------------------------------------------------------------------

  private void execRls(int keyCode) {
    //    System.err.println( //
    //        System.currentTimeMillis() + " ****KEY_R**** " + keyCode);

    // ----------------------------------------
    // Can't check this due dead keys
    // ----------------------------------------

    //    if (keyState[keyCode] == false) {
    //      throw new IllegalStateException();
    //    }

    keyState[keyCode] = false;
  }

  // --------------------------------------------------------------------------------

  @Override
  public boolean dispatchKeyEvent(KeyEvent evt) {

    // ----------------------------------------
    // 0 is VK_UNDEFINED
    // ----------------------------------------

    if (evt.getKeyCode() < 1 || //
        evt.getKeyCode() >= keyState.length) {
      return true;
    }

    // ----------------------------------------
    // Ignore VK_DEAD_*, see KeyEvent
    // ----------------------------------------

    if (evt.getKeyCode() >= 0x80 && //
        evt.getKeyCode() <= 0x8f) {
      return true;
    }

    // ----------------------------------------

    if (timer == null) {
      timer = new Timer(5, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent evt) {
          int keyCode = Integer.parseInt(evt.getActionCommand());

          execRls(keyCode);

          if (repeatingKey == keyCode) {
            repeatingKey = -1;
          }
        }
      });
      timer.setRepeats(false);
    }

    // ----------------------------------------

    switch (evt.getID()) {
      case KeyEvent.KEY_PRESSED :
        System.err.println("KEY_P " + //
            evt.getKeyCode() + "/" + //
            Integer.toHexString(evt.getKeyCode()) + "/" + //
            evt.getKeyChar() + "/" + evt.isActionKey() + ";" + evt.paramString());

        if (!keyState[evt.getKeyCode()]) {
          execPrs(evt.getKeyCode());
        }

        if (repeatingKey == evt.getKeyCode()) {
          timer.stop();
        }

        repeatingKey = evt.getKeyCode();
        break;

      case KeyEvent.KEY_RELEASED :
        System.err.println("KEY_R " + //
            evt.getKeyCode() + "/" + //
            Integer.toHexString(evt.getKeyCode()) + "/" + //
            evt.getKeyChar() + "/" + evt.isActionKey() + ";" + evt.paramString());

        if (repeatingKey != evt.getKeyCode()) {
          execRls(evt.getKeyCode());
        } else {
          timer.restart();
          timer.setActionCommand( //
              Integer.toString(evt.getKeyCode()));
        }
        break;

      default :
        break;
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  public boolean getKeyStateFor(int keyCode) {
    return keyState[keyCode];
  }
}
