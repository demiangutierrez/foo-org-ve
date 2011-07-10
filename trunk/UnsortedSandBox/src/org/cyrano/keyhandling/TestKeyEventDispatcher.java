package org.cyrano.keyhandling;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

public class TestKeyEventDispatcher extends JFrame implements KeyEventDispatcher {

  private boolean[] keyState = new boolean[256];

  // --------------------------------------------------------------------------------

  public TestKeyEventDispatcher() {

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    setSize(400, 400);
    setLocation(50, 50);

    setVisible(true);

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);

    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent evt) {
        System.err.println("KA:" + evt.getID());
      }
    });
  }

  // --------------------------------------------------------------------------------

  int lastKeyCode = -1;
  Timer timer;

  @Override
  public boolean dispatchKeyEvent(KeyEvent evt) {

    if (timer == null) {
      timer = new Timer(5, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent evt) {
          System.err.println(System.currentTimeMillis() + "****KEY_R****:" + evt.getActionCommand());
          keyState[Integer.parseInt(evt.getActionCommand())] = false;
          // RELEASING THE REPEAT KEY
          if (lastKeyCode == Integer.parseInt(evt.getActionCommand())) {
            lastKeyCode = -1;
          }
        }
      });
      timer.setRepeats(false);
    }

    if (evt.getID() == KeyEvent.KEY_PRESSED) {
      if (!keyState[evt.getKeyCode()]) {
        System.err.println(System.currentTimeMillis() + "****KEY_P****" + evt.getKeyCode());
        keyState[evt.getKeyCode()] = true;
      }

      if (evt.getKeyCode() == lastKeyCode) {
        timer.stop();
      }
      //      System.err.println("KEY_P:" + evt.getKeyCode());
      lastKeyCode = evt.getKeyCode();
    } else //

    // Forget key typed, it's useless
    if (evt.getID() == KeyEvent.KEY_TYPED) {
      //      System.err.println("**********************************++" + evt.getKeyCode());
      //      if (evt.getKeyCode() == lastKeyCode) {
      //        timer.stop();
      //      }
      //      System.err.println("KEY_T:" + evt.getKeyCode());
      //      lastKeyCode = evt.getKeyCode();
    } else //

    if (evt.getID() == KeyEvent.KEY_RELEASED) {

      // NOT REPEATING KEY
      if (lastKeyCode != evt.getKeyCode()) {
        System.err.println(System.currentTimeMillis() + "****XXX_R****:" + evt.getKeyCode());
        keyState[evt.getKeyCode()] = false;

      } else {
        //      System.err.println("KEY_R:" + evt.getKeyCode());
        timer.restart();
        timer.setActionCommand("" + evt.getKeyCode());
      }
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new TestKeyEventDispatcher();
  }
}
