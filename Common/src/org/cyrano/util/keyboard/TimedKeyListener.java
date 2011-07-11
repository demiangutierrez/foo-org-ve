package org.cyrano.util.keyboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

// --------------------------------------------------------------------------------
// XXX: see http://bugs.sun.com/view_bug.do?bug_id=4153069
//--------------------------------------------------------------------------------
public class TimedKeyListener implements KeyListener, ActionListener {

  private KeyEvent evt;
  private Timer timer;
  private boolean released = false;

  //--------------------------------------------------------------------------------

  public TimedKeyListener() {
    timer = new Timer(1, this);
  }

  //--------------------------------------------------------------------------------

  public void keyPressed(KeyEvent evt) {
    released = false;
    timer.stop();
  }

  //--------------------------------------------------------------------------------

  public void keyReleased(KeyEvent evt) {
    if (!released) {
      this.evt = evt;
      timer.restart();
    }
  }

  //--------------------------------------------------------------------------------

  public void keyTyped(KeyEvent e) {
    // Empty
  }

  //--------------------------------------------------------------------------------

  public void actionPerformed(ActionEvent e) {
    released = true;
    timer.stop();
    keyReleased(evt);
  }

  //--------------------------------------------------------------------------------

  public boolean getReleased() {
    return released;
  }
}
